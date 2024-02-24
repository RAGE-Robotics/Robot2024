package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.PidController;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

public class DriveAngle implements ITask {
    private double m_angle;
    private PidController m_pidRot = new PidController(Constants.kPathFollowingAngleP,
            Constants.kPathFollowingAngleI, Constants.kPathFollowingAngleD, Constants.kDt);

    public DriveAngle(double angle) {
        m_angle = angle;
    }

    public void onStart(double timestamp) {

    }

    public void onUpdate(double timestamp) {
        double angleSetpoint = m_angle;
        double currentAngle = SwerveDrive.getInstance().getPose().getRotation().getRadians();
        double angleError = angleSetpoint - currentAngle;
        if (angleError > Math.PI) {
            angleSetpoint -= 2 * Math.PI;
        }
        double rot = m_pidRot.update(angleSetpoint, currentAngle);

        SwerveDrive.getInstance().set(Mode.Velocity, 0, 0, rot);
    }

    public boolean isDone() {
        return Math.abs(m_angle
                - SwerveDrive.getInstance().getPose().getRotation().getRadians()) < Constants.kDriveAngleTolerance;
    }

    public void onStop() {
        SwerveDrive.getInstance().set(Mode.Disabled, 0, 0, 0);
    }
}
