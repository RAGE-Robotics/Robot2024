package com.ragerobotics.robot2024.systems;

import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot.Mode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Climber implements ISystem {
    private static Climber instance;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }

        return instance;
    }

    private DoubleSolenoid m_solenoidA = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.kClimberSolenoidForwardA, Constants.kClimberSolenoidReverseA);
    private DoubleSolenoid m_solenoidB = new DoubleSolenoid(PneumaticsModuleType.REVPH,
            Constants.kClimberSolenoidForwardB, Constants.kClimberSolenoidReverseB);
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
        m_solenoidA.set(m_state ? Value.kForward : Value.kReverse);
        m_solenoidB.set(m_state ? Value.kForward : Value.kReverse);
    }
}
