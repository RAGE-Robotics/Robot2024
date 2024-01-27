package com.ragerobotics.robot2024;

import java.util.ArrayList;

import com.ragerobotics.robot2024.auto.DoNothing;
import com.ragerobotics.robot2024.auto.ITask;
import com.ragerobotics.robot2024.systems.ISystem;
import com.ragerobotics.robot2024.systems.SwerveDrive;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {
    public enum Mode {
        Disabled, Auto, Teleop
    }

    private ArrayList<ISystem> m_systems = new ArrayList<>();

    private XboxController m_driverController = new XboxController(Constants.kDriverController);

    private ITask m_autoTask = new DoNothing();

    public Robot() {
        m_systems.add(SwerveDrive.getInstance());
    }

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {

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
            m_autoTask.onUpdate(timestamp);
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

        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Teleop);
        }
    }
}
