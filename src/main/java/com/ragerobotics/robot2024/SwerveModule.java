package com.ragerobotics.robot2024;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule {
    private Translation2d m_loc;
    private TalonFX m_driveMotor;
    private TalonSRX m_steeringMotor;

    public SwerveModule(Translation2d loc, TalonFX driveMotor, TalonSRX steeringMotor) {
        m_loc = loc;
        m_driveMotor = driveMotor;
        m_steeringMotor = steeringMotor;
    }

    public Translation2d getLoc() {
        return m_loc;
    }

    public SwerveModuleState getState() {
        double distance = m_driveMotor.getSelectedSensorVelocity() / Constants.kEncoderTicksPerWheelRotation
                / (m_driveMotor.getStatusFramePeriod(0) / 1000.0) * 2 * Math.PI * Constants.kWheelRadius;
        double angle = 0;

        return new SwerveModuleState(distance, new Rotation2d(angle));
    }

    public SwerveModulePosition getPosition() {
        double distance = m_driveMotor.getSelectedSensorPosition() / Constants.kEncoderTicksPerWheelRotation * 2
                * Math.PI * Constants.kWheelRadius;
        double angle = 0;

        return new SwerveModulePosition(distance, new Rotation2d(angle));
    }

    public void drive(SwerveModuleState setpoint) {
        double angle = 0;
        Rotation2d rot = new Rotation2d(angle);

        SwerveModuleState state = SwerveModuleState.optimize(setpoint, rot);
        m_driveMotor.set(ControlMode.Velocity, state.speedMetersPerSecond
                * (1000.0 / m_driveMotor.getStatusFramePeriod(0)) * Constants.kEncoderTicksPerWheelRotation);
        m_steeringMotor.set(ControlMode.Position, state.angle.getDegrees());
    }
}
