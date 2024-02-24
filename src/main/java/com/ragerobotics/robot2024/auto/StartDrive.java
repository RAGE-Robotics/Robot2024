package com.ragerobotics.robot2024.auto;

import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

public class StartDrive implements ITask {
    private double m_vx, m_vy, m_rot;

    public StartDrive(double vx, double vy, double rot) {
        m_vx = vx;
        m_vy = vy;
        m_rot = rot;
    }

    public void onStart(double timestamp) {
        SwerveDrive.getInstance().set(Mode.Velocity, m_vx, m_vy, m_rot);
    }

    public void onUpdate(double timestamp) {

    }

    public boolean isDone() {
        return true;
    }

    public void onStop() {

    }
}
