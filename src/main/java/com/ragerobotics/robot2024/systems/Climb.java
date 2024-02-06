package com.ragerobotics.robot2024.systems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

import org.ejml.interfaces.linsol.ReducedRowEchelonForm;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Climb {
      // DoubleSolenoid corresponds to a double solenoid.
  // In this case, it's connected to channels 1 and 2 of a PH with the default CAN ID.
    public static DoubleSolenoid m_doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    public static Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);


public void Climber() {
    m_doubleSolenoid.set(DoubleSolenoid.Value.kForward);
    
    return;
    
}
public void Compressor() {
    // Get compressor current draw.
    double draw = m_compressor.getCurrent();
    // Get whether the compressor is active.
    boolean isActive = m_compressor.isEnabled();
    // Get the digital pressure switch connected to the PCM/PH.
    // The switch is open when the pressure is over ~120 PSI.
    boolean isFull = m_compressor.getPressureSwitchValue();
    m_compressor.enableAnalog(55, 60);
    if (isFull) {
        isActive = false;
    }
}

}
