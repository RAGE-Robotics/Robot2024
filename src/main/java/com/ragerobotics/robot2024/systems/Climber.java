package com.ragerobotics.robot2024.systems;

import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot.Mode;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber implements ISystem {
    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;
    }

    private Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.kClimberSoldenoid);
    private boolean m_state = false;

    private Climber() {
        retract();
    }

    public void retract() {
        m_state = false;
    }

    public void extend() {
        m_state = true;
    }

    @Override
    public void onUpdate(double timestamp, Mode mode) {
        m_solenoid.set(m_state);
    }
}
