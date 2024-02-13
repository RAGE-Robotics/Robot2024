package com.ragerobotics.robot2024.systems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

import javax.print.DocFlavor.STRING;

import org.ejml.equation.Function;
import org.ejml.interfaces.linsol.ReducedRowEchelonForm;
import org.opencv.ml.StatModel;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class Climb {
    public static Solenoid m_solenoid = new Solenoid(PneumaticsModuleType.REVPH, 0);
    public static Compressor m_compressor = new Compressor(PneumaticsModuleType.REVPH);


    static void Climber() {
            m_solenoid.set(false);
        }
    static void Compressor() {
    // Get compressor current draw.
    double draw = m_compressor.getCurrent();
    // Get whether the compressor is active.
    boolean isActive = m_compressor.isEnabled();
    // Get the digital pressure switch connected to the PCM/PH.
    // The switch is open when the pressure is over ~120 PSI.
    boolean isFull = m_compressor.getPressureSwitchValue();
    m_compressor.enableAnalog(100, 110);
    
    }
}
