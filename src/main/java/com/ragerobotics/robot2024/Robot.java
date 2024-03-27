package com.ragerobotics.robot2024;

import java.util.ArrayList;

import com.ragerobotics.lib.control.PidController;
import com.ragerobotics.robot2024.auto.CrossLine;
import com.ragerobotics.robot2024.auto.DoNothing;
import com.ragerobotics.robot2024.auto.ITask;
import com.ragerobotics.robot2024.auto.OneAmp;
import com.ragerobotics.robot2024.auto.OneSpeakerAmp;
import com.ragerobotics.robot2024.auto.OneSpeakerFar;
import com.ragerobotics.robot2024.auto.TwoSpeaker;
import com.ragerobotics.robot2024.auto.WaitCrossLine;
import com.ragerobotics.robot2024.systems.Climber;
import com.ragerobotics.robot2024.systems.Dropper;
import com.ragerobotics.robot2024.systems.ISystem;
import com.ragerobotics.robot2024.systems.Intake;
import com.ragerobotics.robot2024.systems.LEDs;
import com.ragerobotics.robot2024.systems.SwerveDrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    public enum Mode {
        Disabled, Auto, Teleop
    }

    private ArrayList<ISystem> m_systems = new ArrayList<>();

    private XboxController m_driverController = new XboxController(Constants.kDriverController);
    private XboxController m_operatorController = new XboxController(Constants.kOperatorController);

    private SendableChooser<ITask> m_autoTask = new SendableChooser<ITask>();

    private Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);

    private boolean m_fast = false;

    private PidController m_turnController = new PidController(Constants.kVisionP, Constants.kVisionI,
            Constants.kVisionD,
            Constants.kDt);

    public Robot() {
        m_systems.add(SwerveDrive.getInstance());
        m_systems.add(Intake.getInstance());
        m_systems.add(Climber.getInstance());
        m_systems.add(Dropper.getInstance());
        m_systems.add(LEDs.getInstance());

        m_autoTask.setDefaultOption("Do nothing", new DoNothing());
        m_autoTask.addOption("Cross the line (Blue)", new CrossLine(false));
        m_autoTask.addOption("Cross the line (Red)", new CrossLine(true));
        m_autoTask.addOption("Cross the line with wait (Blue)", new WaitCrossLine(false));
        m_autoTask.addOption("Cross the line with wait (Red)", new WaitCrossLine(true));
        m_autoTask.addOption("Amp (Blue)", new OneAmp(false));
        m_autoTask.addOption("Amp (Red)", new OneAmp(true));
        m_autoTask.addOption("One Speaker (Blue) (Amp)", new OneSpeakerAmp(false));
        m_autoTask.addOption("One Speaker (Red) (Amp)", new OneSpeakerAmp(true));
        m_autoTask.addOption("Two Speaker (Center)", new TwoSpeaker(false));
        m_autoTask.addOption("One Speaker (Blue) (Far)", new OneSpeakerFar(false));
        m_autoTask.addOption("One Speaker (Red) (Far)", new OneSpeakerFar(true));
        SmartDashboard.putData(m_autoTask);
    }

    @Override
    public void robotInit() {
        m_compressor.enableAnalog(Constants.kMinPressure, Constants.kMaxPressure);
        LEDs.allianceColor();
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putBoolean("intake_sensor_tripped", Intake.getInstance().intakeSensorTripped());
        SmartDashboard.putBoolean("dropper_sensor_tripped", Dropper.getInstance().dropperSensorTripped());
        SmartDashboard.putNumber("x", SwerveDrive.getInstance().getPose().getX());
        SmartDashboard.putNumber("y", SwerveDrive.getInstance().getPose().getY());
        SmartDashboard.putNumber("heading", SwerveDrive.getInstance().getPose().getRotation().getRadians());
        SmartDashboard.putNumber("pressure", m_compressor.getPressure());
        SmartDashboard.putBoolean("fast", m_fast);
    }

    @Override
    public void disabledInit() {
        SwerveDrive.getInstance().set(SwerveDrive.Mode.Disabled, 0, 0, 0);
    }

    @Override
    public void disabledPeriodic() {
        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Disabled);
        }
    }

    @Override
    public void autonomousInit() {
        double timestamp = Timer.getFPGATimestamp();

        if (m_autoTask.getSelected() != null) {
            m_autoTask.getSelected().onStart(timestamp);
        }
    }

    @Override
    public void autonomousPeriodic() {
        double timestamp = Timer.getFPGATimestamp();

        if (m_autoTask.getSelected() != null) {
            if (!m_autoTask.getSelected().isDone()) {
                m_autoTask.getSelected().onUpdate(timestamp);
            } else {
                m_autoTask.getSelected().onStop();
            }
        }

        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Auto);
        }

        LEDs.rainbowColors();
    }

    @Override
    public void teleopInit() {
        if (m_autoTask.getSelected() != null) {
            m_autoTask.getSelected().onStop();
        }

        LEDs.allianceColor();
    }

    @Override
    public void teleopPeriodic() {
        if (m_driverController.getStartButton()) {
            SwerveDrive.getInstance().resetPose(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)));
        }

        if (m_driverController.getBButtonPressed()) {
            m_fast = !m_fast;
        }

        double vx = -m_driverController.getLeftY();
        double vy = -m_driverController.getLeftX();
        boolean negative = vx < 0;
        vx *= vx;
        if (negative) {
            vx *= -1;
        }
        negative = vy < 0;
        vy *= vy;
        if (negative) {
            vy *= -1;
        }
        vx *= m_fast ? Constants.kMaxDriverVFast : Constants.kMaxDriverV;
        vy *= m_fast ? Constants.kMaxDriverVFast : Constants.kMaxDriverV;

        if (m_driverController.getXButtonPressed()) {
            m_turnController.reset();
        }

        double rot = 0;
        if (!m_driverController.getXButton()) {
            rot = -m_driverController.getRightX() * Constants.kTurningFactor;
            negative = rot < 0;
            rot *= rot;
            if (negative) {
                rot *= -1;
            }
        } else {
            NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
            NetworkTableEntry tx = table.getEntry("tx");
            rot = m_turnController.update(Constants.kVisionOffset, tx.getDouble(0));
        }

        SwerveDrive.getInstance().set(SwerveDrive.Mode.Velocity, vx, vy, rot);

        double intakeDemand = Math.max(m_driverController.getLeftTriggerAxis(),
                m_operatorController.getLeftTriggerAxis()) * Constants.kIntakeGain;
        Intake.getInstance().intake(intakeDemand);

        if (Intake.getInstance().intakeSensorTripped()) {
            LEDs.noteIn();
        } else if (LEDs.greenOn) {
            LEDs.allianceColor();
        }

        if (m_driverController.getAButton() || m_operatorController.getAButton()) {
            Climber.getInstance().retract();
        }
        if (m_driverController.getYButton() || m_operatorController.getYButton()) {
            Climber.getInstance().extend();
        }

        if (intakeDemand > Constants.kTriggerDeadband) {
            Dropper.getInstance().transfer();
        } else if (m_driverController.getLeftBumper() || m_operatorController.getLeftBumper()) {
            Dropper.getInstance().ShooterSpinUp();
        } else if (m_driverController.getRightBumper() || m_operatorController.getRightBumper()) {
            Dropper.getInstance().Shoot();
        } else {
            if (Dropper.getInstance().getState() == Dropper.State.Intaking
                    || Dropper.getInstance().getState() == Dropper.State.ShootSpin
                    || Dropper.getInstance().getState() == Dropper.State.ShootFeed) {
                Dropper.getInstance().dropperStow();
            }

            if (m_driverController.getPOV() == 0 || m_operatorController.getPOV() == 0) {
                Dropper.getInstance().dropperVertical();
            } else if (m_driverController.getPOV() == 180 || m_operatorController.getPOV() == 180) {
                Dropper.getInstance().dropperStow();
            } else if (Math.max(m_driverController.getRightTriggerAxis(),
                    m_operatorController.getRightTriggerAxis()) > Constants.kTriggerDeadband) {
                Dropper.getInstance().drop();
            } else if (Dropper.getInstance().getState() == Dropper.State.Dropping) {
                Dropper.getInstance().dropperVertical();
            }
        }

        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Teleop);
        }
    }
}
