package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class TwoSpeaker extends TaskList {
        public TwoSpeaker(boolean redAlliance) {
                add(new FollowPath(new Path(
                                new Pose2d(new Translation2d(0, 0), new Rotation2d(Math.PI / 2)),
                                new Pose2d(new Translation2d(0.5, 0), new Rotation2d(Math.PI / 2))),
                                true, 5.0));

                add(new SpinShooter());
                add(new Wait(1));
                add(new RunShooter());
                add(new Wait(1));
                add(new StowShooter());

                add(new TaskList(new StartIntake(), new FollowPath(new Path(
                                new Pose2d(new Translation2d(0.5, 0), new Rotation2d(Math.PI / 2)),
                                new Pose2d(new Translation2d(2.0, 0), new Rotation2d(Math.PI / 2))),
                                true, 5.0), new StopIntake()));

                add(new SpinShooter());
                add(new Wait(1));
                add(new RunShooter());
                add(new Wait(1));
                add(new StowShooter());
        }
}
