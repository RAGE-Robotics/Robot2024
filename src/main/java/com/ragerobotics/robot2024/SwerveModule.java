package com.ragerobotics.robot2024;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class SwerveModule {
    private TalonFX m_driveMotor, m_steeringMotor;

    public SwerveModule(TalonFX driveMotor, TalonFX steeringMotor) {
        m_driveMotor = driveMotor;
        m_steeringMotor = steeringMotor;
    }
}
