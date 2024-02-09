//motor go brr ;-;
package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Util;

import edu.wpi.first.wpilibj.DigitalInput; //wait wpi as in the school what thats kinda neat


public class IntakeSystem {
        private TalonSRX m_MotorBack = Util.makeTalonSRX(Constants.kIntakeBackCanId, false, false, false, false); //even falses make a true
        private TalonSRX m_MotorFront = Util.makeTalonSRX(Constants.kIntakeFrontCanId, false, false, false, false);

        public void SpinMotors(double demand) { // Remove parameter if there is only one speed.
                m_MotorFront.set(ControlMode.Velocity, demand);
                m_MotorBack.set(ControlMode.Velocity, demand); // Output value is position change / 100ms. Motor Dependent
        }

        public void StopMotors() {
                m_MotorFront.set(ControlMode.Velocity, 0);
                m_MotorBack.set(ControlMode.Velocity, 0);
        }
}
