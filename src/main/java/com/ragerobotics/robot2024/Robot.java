package com.ragerobotics.robot2024;

import java.util.ArrayList;

import com.ragerobotics.robot2024.auto.CrossLine;
import com.ragerobotics.robot2024.auto.DoNothing;
import com.ragerobotics.robot2024.auto.ITask;
import com.ragerobotics.robot2024.auto.Square;
import com.ragerobotics.robot2024.systems.Climber;
import com.ragerobotics.robot2024.systems.Dropper;
import com.ragerobotics.robot2024.systems.ISystem;
import com.ragerobotics.robot2024.systems.Intake;
import com.ragerobotics.robot2024.systems.SwerveDrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    public enum Mode {
        Disabled, Auto, Teleop
    }

    private ArrayList<ISystem> m_systems = new ArrayList<>();

    private XboxController m_driverController = new XboxController(Constants.kDriverController);
    private XboxController m_operatorController = new XboxController(Constants.kOperatorController);

    private ITask m_autoTask = new Square();

    private Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);

    public Robot() {
        m_systems.add(SwerveDrive.getInstance());
        m_systems.add(Intake.getInstance());
        m_systems.add(Climber.getInstance());
        m_systems.add(Dropper.getInstance());

        m_compressor.enableAnalog(Constants.kMinPressure, Constants.kMaxPressure);
    }

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putBoolean("intake_sensor_tripped", Intake.getInstance().intakeSensorTripped());
        SmartDashboard.putBoolean("dropper_sensor_tripped", Dropper.getInstance().dropperSensorTripped());
        SmartDashboard.putNumber("x", SwerveDrive.getInstance().getPose().getX());
        SmartDashboard.putNumber("y", SwerveDrive.getInstance().getPose().getY());
        SmartDashboard.putNumber("heading", SwerveDrive.getInstance().getPose().getRotation().getRadians());
        SmartDashboard.putNumber("pressure", m_compressor.getPressure());
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

        if (m_autoTask != null) {
            m_autoTask.onStart(timestamp);
        }
    }

    @Override
    public void autonomousPeriodic() {
        double timestamp = Timer.getFPGATimestamp();

        if (m_autoTask != null) {
            if (!m_autoTask.isDone()) {
                m_autoTask.onUpdate(timestamp);
            } else {
                m_autoTask.onStop();
                m_autoTask = new DoNothing();
            }
        }

        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Auto);
        }
    }

    @Override
    public void teleopInit() {
        if (m_autoTask != null) {
            m_autoTask.onStop();
        }
    }

    @Override
    public void teleopPeriodic() {
        if (m_driverController.getLeftBumper() && m_driverController.getRightBumper()) {
            SwerveDrive.getInstance().resetPose(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)));
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
        vx *= Constants.kMaxDriverV;
        vy *= Constants.kMaxDriverV;
        double rot = -m_driverController.getRightX() * Constants.kTurningFactor;
        negative = rot < 0;
        rot *= rot;
        if (negative) {
            rot *= -1;
        }
        SwerveDrive.getInstance().set(SwerveDrive.Mode.Velocity, vx, vy, rot);

        double intakeDemand = Math.max(m_driverController.getLeftTriggerAxis(),
                m_operatorController.getLeftTriggerAxis()) * Constants.kIntakeGain;
        Intake.getInstance().intake(intakeDemand);

        if (m_driverController.getAButton() || m_operatorController.getAButton()) {
            Climber.getInstance().retract();
        }
        if (m_driverController.getYButton() || m_operatorController.getYButton()) {
            Climber.getInstance().extend();
        }

        if (intakeDemand > Constants.kTriggerDeadband) {
            Dropper.getInstance().transfer();
        } else {
            if (Dropper.getInstance().getState() == Dropper.State.Intaking) {
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
