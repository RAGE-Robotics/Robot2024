package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot.Mode;
import com.ragerobotics.robot2024.Util;

import edu.wpi.first.wpilibj.DigitalInput;

public class Intake implements ISystem {
        private static Intake instance;

        public static Intake getInstance() {
                if (instance == null) {
                        instance = new Intake();
                }

                return instance;
        }

        private VictorSPX m_MotorBack = Util.makeVictorSPX(Constants.kIntakeBackCanId, true);
        private VictorSPX m_MotorFront = Util.makeVictorSPX(Constants.kIntakeFrontCanId, true);
        private DigitalInput m_IntakeSensor = new DigitalInput(Constants.kIntakeSensorChannel);

        private double m_demand = 0;

        private Intake() {

        }

        public void intake(double demand) {
                m_demand = demand;
        }

        @Override
        public void onUpdate(double timestamp, Mode mode) {
                m_MotorFront.set(ControlMode.PercentOutput, m_IntakeSensor.get() ? m_demand : 0);
                m_MotorBack.set(ControlMode.PercentOutput, m_IntakeSensor.get() ? m_demand : 0);
        }

        public boolean intakeSensorTripped() {
                return !m_IntakeSensor.get();
        }
}
