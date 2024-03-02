package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class OneAmp extends TaskList {
        public OneAmp(boolean redAlliance) {
                add(new ParallelTask(
                                new FollowPath(new Path(
                                                new Pose2d(new Translation2d(0, 0),
                                                                new Rotation2d(redAlliance ? Math.PI : 0)),
                                                new Pose2d(new Translation2d(0.4572,
                                                                (redAlliance ? -1 : 1) * 0.4572),
                                                                new Rotation2d(redAlliance ? Math.PI : 0))),
                                                true, 5.0),
                                new TaskList(new Wait(2), new StartDrop(), new Wait(2), new StopDrop())));
                add(new FollowPath(
                                new Path(new Pose2d(
                                                new Translation2d(0.4572,
                                                                (redAlliance ? -1 : 1) * 0.4572),
                                                new Rotation2d(redAlliance ? Math.PI : 0)),
                                                new Pose2d(new Translation2d(1.5,
                                                                (redAlliance ? -1 : 1) * 0.3),
                                                                new Rotation2d(redAlliance ? Math.PI : 0))),
                                true, 5.0));
        }
}
