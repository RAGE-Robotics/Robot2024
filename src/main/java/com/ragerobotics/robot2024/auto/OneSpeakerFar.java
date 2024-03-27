package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class OneSpeakerFar extends TaskList {
    public OneSpeakerFar(boolean redAlliance) {
        add(new SpinShooter());
        add(new Wait(1));
        add(new RunShooter());
        add(new Wait(1));
        add(new StowShooter());

        double initialRotationRadians = 0.5235987755982988;
        Rotation2d initialRotation = new Rotation2d(redAlliance ? initialRotationRadians - Math.PI / 2
                : Math.PI - initialRotationRadians + Math.PI / 2);

        add(new FollowPath(new Path(
                new Pose2d(new Translation2d(0, 0), initialRotation),
                new Pose2d(new Translation2d(0, 2), initialRotation)),
                true, 5.0));
        add(new FollowPath(new Path(
                new Pose2d(new Translation2d(0, 2), initialRotation),
                new Pose2d(new Translation2d(4, 2), initialRotation)),
                true, 5.0));
    }
}
