package com.ragerobotics.robot2024;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SwerveModule {
    private TalonFX m_driveMotor;
    private TalonSRX m_steeringMotor;

    public SwerveModule(TalonFX driveMotor, TalonSRX steeringMotor) {
        m_driveMotor = driveMotor;
        m_steeringMotor = steeringMotor;
    }
}
