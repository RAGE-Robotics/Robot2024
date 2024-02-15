package com.ragerobotics.lib.control;

public class PidController {
    private double m_kP, m_kI, m_kD, m_dt, m_I, m_oldE;

    public PidController(double kP, double kI, double kD, double dt) {
        m_kP = kP;
        m_kI = kI;
        m_kD = kD;
        m_dt = dt;
    }

    public double update(double setpoint, double current) {
        double e = setpoint - current;
        m_I += e * m_dt;
        double de_dt = (e - m_oldE) / m_dt;
        m_oldE = e;
        return m_kP * e + m_kI * m_I + m_kD * de_dt;
    }
}
