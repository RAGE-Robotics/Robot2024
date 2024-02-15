package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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

        private TalonSRX m_MotorBack = Util.makeTalonSRX(Constants.kIntakeBackCanID, false, false, false, false);
        private TalonSRX m_MotorFront = Util.makeTalonSRX(Constants.kIntakeFrontCanID, false, false, false, false);
        private DigitalInput m_IntakeSensor = new DigitalInput(Constants.kIntakeSensorChannel);

        private double m_demand = 0;

        private Intake() {

        }

        public void intake(double demand) {
                m_demand = demand;
        }

        @Override
        public void onUpdate(double timestamp, Mode mode) {
                m_MotorFront.set(ControlMode.Velocity, m_IntakeSensor.get() ? 0 : m_demand);
                m_MotorBack.set(ControlMode.Velocity, m_IntakeSensor.get() ? 0 : m_demand);
        }
}
