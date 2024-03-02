package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class CrossLine extends TaskList {
    public CrossLine(boolean redAlliance) {
        add(new FollowPath(new Path(new Pose2d(new Translation2d(0, 0), new Rotation2d(redAlliance ? 0 : Math.PI)),
                new Pose2d(new Translation2d(2, 0), new Rotation2d(redAlliance ? 0 : Math.PI))), true, 5.0));
    }
}
