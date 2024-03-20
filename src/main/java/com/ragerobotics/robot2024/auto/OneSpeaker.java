package com.ragerobotics.robot2024.auto;

import com.ragerobotics.lib.control.Path;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class OneSpeaker extends TaskList {
        public enum Position {
                Amp, Center, Far
        }

        public OneSpeaker(boolean redAlliance, Position position) {

                if (position == Position.Amp) {
                        add(new SpinShooter());
                        add(new Wait(2));
                        add(new RunShooter());
                        add(new Wait(2));
                        add(new StowShooter());

                        add(new TaskList(new StartIntake(), new FollowPath(new Path(
                                        new Pose2d(new Translation2d(0, 0),
                                                        new Rotation2d(redAlliance ? 0.5235987755982988
                                                                        : Math.PI - 0.5235987755982988)),
                                        new Pose2d(new Translation2d(4,
                                                        0),
                                                        new Rotation2d(redAlliance ? 0.5235987755982988
                                                                        : Math.PI - 0.5235987755982988))),
                                        true, 5.0), new StopIntake()));
                } else if (position == Position.Center) {
                        add(new FollowPath(new Path(
                                        new Pose2d(new Translation2d(0, 0),
                                                        new Rotation2d(redAlliance ? Math.PI / 2 : 0)),
                                        new Pose2d(new Translation2d(0.5,
                                                        0),
                                                        new Rotation2d(redAlliance ? Math.PI / 2 : 0))),
                                        true, 5.0));

                        add(new SpinShooter());
                        add(new Wait(2));
                        add(new RunShooter());
                        add(new Wait(2));
                        add(new StowShooter());

                        add(new TaskList(new StartIntake(), new FollowPath(new Path(
                                        new Pose2d(new Translation2d(0.5, 0),
                                                        new Rotation2d(redAlliance ? Math.PI / 2 : 0)),
                                        new Pose2d(new Translation2d(3,
                                                        0),
                                                        new Rotation2d(redAlliance ? Math.PI / 2 : 0))),
                                        true, 5.0), new StopIntake()));
                } else if (position == Position.Far) {

                }
        }
}
