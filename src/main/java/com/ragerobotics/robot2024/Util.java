package com.ragerobotics.robot2024;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Util {
    public static TalonFX makeTalonFX(int id, boolean invertMotor, boolean invertSensor) {
        TalonFX talon = new TalonFX(id);
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Coast);
        talon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        talon.setInverted(invertMotor);
        talon.setSensorPhase(invertSensor);
        talon.setSelectedSensorPosition(0);
        talon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, Constants.kTalonStatusPeriod);

        return talon;
    }

    public static TalonSRX makeTalonSRX(int id, boolean invertMotor, boolean hasSensor, boolean invertSensor,
            boolean sensorAbsolute) {
        TalonSRX talon = new TalonSRX(id);
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Coast);
        talon.setInverted(invertMotor);

        if (hasSensor) {
            talon.configSelectedFeedbackSensor(
                    sensorAbsolute ? FeedbackDevice.CTRE_MagEncoder_Absolute : FeedbackDevice.CTRE_MagEncoder_Relative);
            talon.setSensorPhase(invertSensor);

            if (!sensorAbsolute) {
                talon.setSelectedSensorPosition(0);
            }
        }

        talon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, Constants.kTalonStatusPeriod);

        return talon;
    }

    public static VictorSPX makeVictorSPX(int id, boolean invertMotor) {
        VictorSPX victor = new VictorSPX(id);
        victor.configFactoryDefault();
        victor.setNeutralMode(NeutralMode.Coast);
        victor.setInverted(invertMotor);

        return victor;
    }
}
