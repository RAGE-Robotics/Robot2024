package com.ragerobotics.robot2024.auto;

public class Wait implements ITask {
    private double m_delay = 0;
    private double m_startTime = -1;
    private double m_lastTime = 0;

    public Wait(double delay) {
        m_delay = delay;
    }

    public void onStart(double timestamp) {
        m_startTime = timestamp;
        m_lastTime = timestamp;
    }

    public void onUpdate(double timestamp) {
        m_lastTime = timestamp;
    }

    public boolean isDone() {
        return m_lastTime > m_startTime + m_delay;
    }

    public void onStop() {

    }
}
