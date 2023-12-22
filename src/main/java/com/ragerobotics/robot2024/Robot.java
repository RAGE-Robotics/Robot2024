package com.ragerobotics.robot2024;

import java.util.ArrayList;

import com.ragerobotics.robot2024.systems.ISystem;
import com.ragerobotics.robot2024.systems.SwerveDrive;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
    public enum Mode {
        Disabled, Auto, Teleop
    }

    private ArrayList<ISystem> m_systems = new ArrayList<>();

    public Robot() {
        m_systems.add(SwerveDrive.getInstance());
    }

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
        for (ISystem system : m_systems) {
            if (!system.isOkay()) {
                System.err.println("Error with system: " + system);
            }
        }
    }

    @Override
    public void disabledPeriodic() {
        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Disabled);
        }
    }

    @Override
    public void autonomousPeriodic() {
        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Auto);
        }
    }

    @Override
    public void teleopPeriodic() {
        double timestamp = Timer.getFPGATimestamp();
        for (ISystem system : m_systems) {
            system.onUpdate(timestamp, Mode.Teleop);
        }
    }
}
