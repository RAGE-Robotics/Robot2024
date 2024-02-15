package com.ragerobotics.lib.control;

import java.util.ArrayList;

import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.systems.SwerveDrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class Path {
    private ArrayList<Pose2d> m_points = new ArrayList<Pose2d>();
    private int m_index = 1;
    private boolean m_almostDone = false;

    public Path(Pose2d... points) {
        for (Pose2d point : points) {
            m_points.add(point);
        }
    }

    public Pose2d getStart() {
        return m_points.get(0);
    }

    private double sqrComponent(double m, double b, double r, double robotX, double robotY) {
        return Math.pow(2 * m * (b - robotY) - 2 * robotX, 2)
                - 4 * (m * m + 1) * (robotX * robotX + Math.pow((b - robotY), 2) - r * r);
    }

    public Pose2d getVirtual(double t) {
        double lookAheadRadius = (t / Constants.kPathFollowingRampTime) * Constants.kPathFollowingLookAheadRadius;
        lookAheadRadius = Math.min(lookAheadRadius, Constants.kPathFollowingLookAheadRadius);

        Pose2d lastPoint = m_points.get(m_index - 1);
        Pose2d targetPoint = m_points.get(m_index);
        double m = (targetPoint.getY() - lastPoint.getY()) / (targetPoint.getX() - lastPoint.getX());
        double b = targetPoint.getY() - m * targetPoint.getX();

        if (m == 0) {
            m = Constants.kEpsilon;
        }

        Pose2d currentPoint = SwerveDrive.getInstance().getPose();

        double sqr = sqrComponent(m, b, lookAheadRadius, currentPoint.getX(), currentPoint.getY());
        Translation2d virtualLocation = null;

        if (sqr > 0) {
            double x1 = (-2 * (m * (b - currentPoint.getY()) - currentPoint.getX()) + Math.sqrt(sqr))
                    / (2 * (m * m + 1));
            double y1 = m * x1 + b;
            Translation2d location1 = new Translation2d(x1, y1);

            double x2 = (-2 * (m * (b - currentPoint.getY()) - currentPoint.getX()) - Math.sqrt(sqr))
                    / (2 * (m * m + 1));
            double y2 = m * x2 + b;
            Translation2d location2 = new Translation2d(x2, y2);

            virtualLocation = location1;

            if (location2.getDistance(targetPoint.getTranslation()) < location1
                    .getDistance(targetPoint.getTranslation())) {
                virtualLocation = location2;
            }
        }

        double m2 = -1 / m;
        double b2 = currentPoint.getY() - m2 * currentPoint.getX();

        double x = (b2 - b) / (m + m2);
        double y = m * x + b;

        Translation2d interceptLocation = new Translation2d(x, y);

        if (virtualLocation == null) {
            virtualLocation = interceptLocation;
        }

        Pose2d virtualSetpoint;

        if (isAtPoint(targetPoint, new Pose2d(virtualLocation, new Rotation2d(0))) || m_almostDone) {
            if (m_points.size() > m_index + 1 && m_almostDone) {
                m_index++;
                virtualSetpoint = getVirtual(t);
            } else {
                m_almostDone = true;
                virtualSetpoint = m_points.get(m_points.size() - 1);
            }
        } else {
            double segmentLength = lastPoint.getTranslation().getDistance(targetPoint.getTranslation());
            double percentComplete = lastPoint.getTranslation().getDistance(interceptLocation) / segmentLength;
            double slope = targetPoint.getRotation().getRadians() - lastPoint.getRotation().getRadians();
            virtualSetpoint = new Pose2d(virtualLocation,
                    new Rotation2d(slope * percentComplete + lastPoint.getRotation().getRadians()));
        }

        return virtualSetpoint;
    }

    private boolean isAtPoint(Pose2d point, Pose2d robot) {
        return (robot.getTranslation().getDistance(point.getTranslation()) < Constants.kPathFollowingTolerance);
    }

    public boolean isDone() {
        return (m_index == m_points.size() - 1)
                && isAtPoint(m_points.get(m_points.size() - 1), SwerveDrive.getInstance().getPose());
    }
}
