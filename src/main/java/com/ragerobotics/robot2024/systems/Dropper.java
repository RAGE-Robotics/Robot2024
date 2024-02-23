package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
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
        Stowed, Intaking, Holding, Up, Dropping
    }

    private TalonSRX m_beltMotor = Util.makeTalonSRX(Constants.kDropperBeltMotorId, false, false, false, false);
    private TalonSRX m_rotatingMotor = Util.makeTalonSRX(Constants.kDropperRotatingMotorId, false, true, false, false);
    private TalonSRX m_rollerMotor = Util.makeTalonSRX(Constants.kDropperRollerMotorId, false, false, false, false);

    private DigitalInput m_dropperSensor = new DigitalInput(0);

    private State m_state = State.Stowed;

    public void dropperStow() {
        m_state = State.Stowed;
    }

    public void transfer() {
        m_state = State.Intaking;
    }

    public void hold() {
        m_state = State.Holding;
    }

    public void dropperVerticle() {
        m_state = State.Up;
    }

    public void drop() {
        m_state = State.Dropping;
    }

    private Dropper() {
        m_rotatingMotor.config_kP(0, Constants.kDropperRotationP);
        m_rotatingMotor.config_kI(0, Constants.kDropperRotationI);
        m_rotatingMotor.config_kD(0, Constants.kDropperRotationD);
        m_rotatingMotor.config_kF(0, Constants.kDropperRotationF);

        m_rotatingMotor.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyClosed);
    }

    @Override
    public void onUpdate(double timestamp, Mode mode) {
        if (m_state == State.Stowed) {
            m_beltMotor.set(ControlMode.PercentOutput, 0.0);
            m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Intaking) {
            m_beltMotor.set(ControlMode.PercentOutput, 1.0);
            m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Holding) {
            m_beltMotor.set(ControlMode.PercentOutput, 0.1);
            m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperInPos);
        }

        if (m_state == State.Up) {
            m_beltMotor.set(ControlMode.PercentOutput, -0.1);
            m_rollerMotor.set(ControlMode.PercentOutput, 0.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperVertPos / 4096 * Constants.kDropperGearRatio);
        }

        if (m_state == State.Dropping) {
            m_beltMotor.set(ControlMode.PercentOutput, 1.0);
            m_rollerMotor.set(ControlMode.PercentOutput, 1.0);
            m_rotatingMotor.set(ControlMode.Position, Constants.kDropperVertPos / 4096 * Constants.kDropperGearRatio);
        }

    }

    public boolean dropperSensorTripped() {
        return !m_dropperSensor.get();
    }
}
