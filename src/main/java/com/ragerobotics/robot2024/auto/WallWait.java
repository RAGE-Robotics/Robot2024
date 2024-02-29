package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.Constants;

import edu.wpi.first.wpilibj.DigitalInput;

public class WallWait implements ITask {
    public DigitalInput m_digitalInputA;
    public DigitalInput m_digitalInputB;

    public WallWait() {
        m_digitalInputA = new DigitalInput(Constants.kWallSensorChannelA);
        m_digitalInputB = new DigitalInput(Constants.kWallSensorChannelB);
    }

    public void onStart(double timestamp) {

    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return !m_digitalInputA.get() && !m_digitalInputB.get();
    }

    public void onStop() {

    }
}
