package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class Square extends TaskList {
    public Square() {
        boolean redAlliance = false;
        if (DriverStation.getAlliance().isPresent()) {
            redAlliance = DriverStation.getAlliance().get() == Alliance.Red;
        }

        add(new FollowPath(new Path(new Pose2d(new Translation2d(0, 0), new Rotation2d(redAlliance ? Math.PI : 0)),
                new Pose2d(new Translation2d(1.0, 0), new Rotation2d(redAlliance ? Math.PI : 0)),
                new Pose2d(new Translation2d(1.0, 1.0), new Rotation2d(redAlliance ? Math.PI : 0)),
                new Pose2d(new Translation2d(0, 1.0), new Rotation2d(redAlliance ? Math.PI : 0)),
                new Pose2d(new Translation2d(0, 0), new Rotation2d(redAlliance ? Math.PI : 0))), true, 10.0));
    }
}
