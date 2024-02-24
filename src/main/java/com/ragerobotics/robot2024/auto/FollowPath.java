package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;
import com.ragerobotics.lib.control.PidController;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.systems.SwerveDrive;
import com.ragerobotics.robot2024.systems.SwerveDrive.Mode;

import edu.wpi.first.math.geometry.Pose2d;

public class FollowPath implements ITask {
    private PidController m_pidX = new PidController(Constants.kPathFollowingPositionP,
            Constants.kPathFollowingPositionI, Constants.kPathFollowingPositionD, Constants.kDt);
    private PidController m_pidY = new PidController(Constants.kPathFollowingPositionP,
            Constants.kPathFollowingPositionI, Constants.kPathFollowingPositionD, Constants.kDt);
    private PidController m_pidRot = new PidController(Constants.kPathFollowingAngleP,
            Constants.kPathFollowingAngleI, Constants.kPathFollowingAngleD, Constants.kDt);

    private Path m_path;
    private boolean m_resetPosition;

    private double m_startTime;

    public FollowPath(Path path, boolean resetPosition) {
        m_path = path;
        m_resetPosition = resetPosition;
    }

    public void onStart(double timestamp) {
        m_startTime = timestamp;

        if (m_resetPosition) {
            SwerveDrive.getInstance().resetPose(m_path.getStart());
        }
    }

    public void onUpdate(double timestamp) {
        Pose2d setpoint = m_path.getVirtual(timestamp - m_startTime);
        Pose2d current = SwerveDrive.getInstance().getPose();

        double vx = m_pidX.update(setpoint.getX(), current.getX());
        double vy = m_pidY.update(setpoint.getY(), current.getY());

        double angleSetpoint = setpoint.getRotation().getRadians();
        double angleError = angleSetpoint - current.getRotation().getRadians();
        if (angleError > Math.PI) {
            angleSetpoint -= 2 * Math.PI;
        }
        double rot = m_pidRot.update(angleSetpoint, current.getRotation().getRadians());

        boolean negative = vx < 0;
        vx = (negative ? -1 : 1) * Math.min(Math.abs(vx), Constants.kPathFollowingMaxV);

        negative = vy < 0;
        vy = (negative ? -1 : 1) * Math.min(Math.abs(vy), Constants.kPathFollowingMaxV);

        negative = rot < 0;
        rot = (negative ? -1 : 1) * Math.min(Math.abs(rot), Constants.kPathFollowingMaxRotV);

        SwerveDrive.getInstance().set(Mode.Velocity, vx, vy, rot);
    }

    public boolean isDone() {
        return m_path.isDone();
    }

    public void onStop() {
        SwerveDrive.getInstance().set(Mode.Disabled, 0, 0, 0);
    }
}
