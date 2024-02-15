// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.ragerobotics.robot2024.systems;

import com.kauailabs.navx.frc.AHRS;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot;
import com.ragerobotics.robot2024.SwerveModule;
import com.ragerobotics.robot2024.Util;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.I2C.Port;

public class SwerveDrive implements ISystem {
        private static SwerveDrive instance;

        public static SwerveDrive getInstance() {
                if (instance == null) {
                        instance = new SwerveDrive();
                }

                return instance;
        }

        public enum Mode {
                Disabled,
                Velocity
        }

        private SwerveModule m_frontLeftModule = new SwerveModule(
                        new Translation2d(Constants.kModuleCenterDistance, Constants.kModuleCenterDistance),
                        Util.makeTalonFX(Constants.kFrontLeftDriveMotor, false, false),
                        Util.makeTalonSRX(Constants.kFrontLeftSteeringMotor, false, true, false, true),
                        Constants.kFrontLeftAngleOffset);
        private SwerveModule m_frontRightModule = new SwerveModule(
                        new Translation2d(Constants.kModuleCenterDistance, -Constants.kModuleCenterDistance),
                        Util.makeTalonFX(Constants.kFrontRightDriveMotor, true, false),
                        Util.makeTalonSRX(Constants.kFrontRightSteeringMotor, false, true, false, true),
                        Constants.kFrontRightAngleOffset);
        private SwerveModule m_backLeftModule = new SwerveModule(
                        new Translation2d(-Constants.kModuleCenterDistance, Constants.kModuleCenterDistance),
                        Util.makeTalonFX(Constants.kBackLeftDriveMotor, false, false),
                        Util.makeTalonSRX(Constants.kBackLeftSteeringMotor, false, true, false, true),
                        Constants.kBackLeftAngleOffset);
        private SwerveModule m_backRightModule = new SwerveModule(
                        new Translation2d(-Constants.kModuleCenterDistance, -Constants.kModuleCenterDistance),
                        Util.makeTalonFX(Constants.kBackRightDriveMotor, false, false),
                        Util.makeTalonSRX(Constants.kBackRightSteeringMotor, false, true, false, true),
                        Constants.kBackRightAngleOffset);

        private AHRS m_navx = new AHRS(Port.kMXP);

        private SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftModule.getLoc(),
                        m_frontRightModule.getLoc(), m_backLeftModule.getLoc(), m_backRightModule.getLoc());

        private SwerveDrivePoseEstimator m_poseEstimator = new SwerveDrivePoseEstimator(m_kinematics,
                        m_navx.getRotation2d(),
                        new SwerveModulePosition[] { m_frontLeftModule.getPosition(), m_frontRightModule.getPosition(),
                                        m_backLeftModule.getPosition(), m_backRightModule.getPosition() },
                        new Pose2d(), VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
                        VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(30)));

        private Mode m_mode;
        private double m_vx, m_vy, m_rot;

        private SwerveDrive() {
                set(Mode.Disabled, 0, 0, 0);
        }

        public Pose2d getPose() {
                return m_poseEstimator.getEstimatedPosition();
        }

        public void resetPose(Pose2d pose) {
                m_poseEstimator.resetPosition(m_navx.getRotation2d(),
                                new SwerveModulePosition[] { m_frontLeftModule.getPosition(),
                                                m_frontRightModule.getPosition(),
                                                m_backLeftModule.getPosition(), m_backRightModule.getPosition() },
                                pose);
        }

        public void set(Mode mode, double vx, double vy, double rot) {
                m_mode = mode;
                m_vx = vx;
                m_vy = vy;
                m_rot = rot;

                if (mode == Mode.Disabled) {
                        m_frontLeftModule.brake();
                        m_frontRightModule.brake();
                        m_backLeftModule.brake();
                        m_backRightModule.brake();
                } else {
                        m_frontLeftModule.coast();
                        m_frontRightModule.coast();
                        m_backLeftModule.coast();
                        m_backRightModule.coast();
                }
        }

        @Override
        public void onUpdate(double timestamp, Robot.Mode mode) {
                m_poseEstimator.update(m_navx.getRotation2d(),
                                new SwerveModulePosition[] { m_frontLeftModule.getPosition(),
                                                m_frontRightModule.getPosition(), m_backLeftModule.getPosition(),
                                                m_backRightModule.getPosition() });

                // Add vision update here

                if (m_mode == Mode.Velocity) {
                        SwerveModuleState states[] = m_kinematics
                                        .toSwerveModuleStates(ChassisSpeeds.discretize(
                                                        ChassisSpeeds.fromFieldRelativeSpeeds(m_vx, m_vy, m_rot,
                                                                        m_poseEstimator.getEstimatedPosition()
                                                                                        .getRotation()),
                                                        Constants.kDt));
                        SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.kMaxV);
                        m_frontLeftModule.drive(states[0]);
                        m_frontRightModule.drive(states[1]);
                        m_backLeftModule.drive(states[2]);
                        m_backRightModule.drive(states[3]);
                } else {
                        SwerveModuleState state = new SwerveModuleState(0, new Rotation2d(0));
                        m_frontLeftModule.drive(state);
                        m_frontRightModule.drive(state);
                        m_backLeftModule.drive(state);
                        m_backRightModule.drive(state);
                }
        }
}
