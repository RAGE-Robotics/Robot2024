package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Util;
import com.ragerobotics.robot2024.Robot.Mode;

import edu.wpi.first.wpilibj.DigitalInput;

public class Dropper implements ISystem {
    private static Dropper instance;

    public static Dropper getInstance() {
        if (instance == null) {
            instance = new Dropper();
        }

        return instance;

    }

    public enum State {
        Stowed, Intaking, Up, Dropping, ShootSpin, ShootFeed
    }

    private TalonSRX m_beltMotor = Util.makeTalonSRX(Constants.kDropperBeltMotorId, true, false, false, false);
    private TalonSRX m_rotatingMotor = Util.makeTalonSRX(Constants.kDropperRotatingMotorId, true, true, true, false);
    private TalonFX m_shooterMotor = Util.makeTalonFX(Constants.kDropperShooterMotorId, false, false);

    private DigitalInput m_dropperSensor = new DigitalInput(Constants.kDropperSensorChannel);

    private State m_state = State.Stowed;

    public void dropperStow() {
        m_state = State.Stowed;
    }

    public void transfer() {
        m_state = State.Intaking;
    }

    public void dropperVertical() {
        m_state = State.Up;
    }

    public void drop() {
        m_state = State.Dropping;
    }

    public void ShooterSpinUp() {
        m_state = State.ShootSpin;
    }

    public void Shoot() {
        m_state = State.ShootFeed;
    }

    private Dropper() {
        m_rotatingMotor.config_kP(0, Constants.kDropperRotationP);
        m_rotatingMotor.config_kI(0, Constants.kDropperRotationI);
        m_rotatingMotor.config_kD(0, Constants.kDropperRotationD);
        m_rotatingMotor.config_kF(0, Constants.kDropperRotationF);

        m_shooterMotor.config_kP(0, Constants.kDropperShooterP);
        m_shooterMotor.config_kI(0, Constants.kDropperShooterI);
        m_shooterMotor.config_kD(0, Constants.kDropperShooterD);
        m_shooterMotor.config_kF(0, Constants.kDropperShooterF);
    }

    @Override
    public void onUpdate(double timestamp, Mode mode) {
        if (m_state == State.Stowed) {
            m_beltMotor.set(ControlMode.PercentOutput, 0.0);
            m_shooterMotor.set(ControlMode.Velocity, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Intaking) {
            m_beltMotor.set(ControlMode.PercentOutput, dropperSensorTripped() ? 0 : 1.0);
            m_shooterMotor.set(ControlMode.Velocity, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Up) {
            m_beltMotor.set(ControlMode.PercentOutput, 0);
            m_shooterMotor.set(ControlMode.Velocity, 0.0);
            m_rotatingMotor.set(ControlMode.Position,
                    Constants.kDropperVertPos / 2 / Math.PI * Constants.kDropperTicksPerRotation
                            * Constants.kDropperGearRatio);
        }

        if (m_state == State.ShootSpin) {
            m_beltMotor.set(ControlMode.PercentOutput, 0.0);
            m_shooterMotor.set(ControlMode.Velocity, 500.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.ShootFeed) {
            m_beltMotor.set(ControlMode.PercentOutput, 1.0);
            m_shooterMotor.set(ControlMode.Velocity, 500.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Dropping) {
            if (dropperUp()) {
                m_shooterMotor.set(ControlMode.Velocity, 500.0);
                m_beltMotor.set(ControlMode.PercentOutput, 1.0);
            } else {
                m_shooterMotor.set(ControlMode.Velocity, 0);
                m_beltMotor.set(ControlMode.PercentOutput, 0.3);
            }
            m_rotatingMotor.set(ControlMode.Position,
                    Constants.kDropperVertPos / 2 / Math.PI * Constants.kDropperTicksPerRotation
                            * Constants.kDropperGearRatio);
        }

    }

    public boolean dropperSensorTripped() {
        return !m_dropperSensor.get();
    }

    public boolean dropperStowed() {
        double position = m_rotatingMotor.getSelectedSensorPosition() / Constants.kDropperTicksPerRotation
                / Constants.kDropperGearRatio * 2 * Math.PI;
        return Math.abs(position - Constants.kDropperInPos) < Constants.kDropperTolerance;
    }

    public boolean dropperUp() {
        double position = m_rotatingMotor.getSelectedSensorPosition() / Constants.kDropperTicksPerRotation
                / Constants.kDropperGearRatio * 2 * Math.PI;
        return Math.abs(position - Constants.kDropperVertPos) < Constants.kDropperTolerance;
    }

    public State getState() {
        return m_state;
    }
}
