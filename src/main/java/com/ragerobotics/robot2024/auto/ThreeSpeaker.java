package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ThreeSpeaker extends TaskList {
        public ThreeSpeaker(boolean redAlliance) {
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

                add(new DriveAngle(redAlliance ? 0 : Math.PI));

                add(new TaskList(new StartIntake(), new FollowPath(new Path(
                                new Pose2d(new Translation2d(2.0, 0), new Rotation2d(redAlliance ? 0 : Math.PI)),
                                new Pose2d(new Translation2d(1.35, redAlliance ? -1.65 : 1.65),
                                                new Rotation2d(redAlliance ? 0 : Math.PI))),
                                true, 5.0), new StopIntake()));

                add(new StartIntake());
                add(new Wait(1));
                add(new StopIntake());

                add(new DriveAngle(redAlliance ? 1.0471975511965976 : Math.PI - 1.0471975511965976));

                add(new ParallelTask(new DriveVision(), new TaskList(new SpinShooter(), new Wait(1), new RunShooter(),
                                new Wait(1), new StowShooter())));
        }
}
