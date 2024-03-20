package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class WaitCrossLine extends TaskList {
    public WaitCrossLine(boolean redAlliance) {
        add(new Wait(12));

        add(new FollowPath(new Path(new Pose2d(new Translation2d(0, 0), new Rotation2d(redAlliance ? Math.PI : 0)),
                new Pose2d(new Translation2d(2, 0), new Rotation2d(redAlliance ? Math.PI : 0))), true, 5.0));
    }
}
