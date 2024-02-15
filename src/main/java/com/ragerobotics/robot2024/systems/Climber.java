package com.ragerobotics.robot2024.systems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber {
    public Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);

    public Climber() {
        retract();
    }

    public void retract() {
        m_solenoid.set(false);
    }

    public void extend() {
        m_solenoid.set(true);
    }
}
