package com.ragerobotics.robot2024.systems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.ragerobotics.robot2024.Constants;
import com.ragerobotics.robot2024.Robot;
import com.ragerobotics.robot2024.SwerveModule;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
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

    private SwerveModule m_frontLeftModule = new SwerveModule(
            new Translation2d(Constants.kModuleCenterDistance, Constants.kModuleCenterDistance), new TalonFX(0),
            new TalonSRX(1));
    private SwerveModule m_frontRightModule = new SwerveModule(
            new Translation2d(Constants.kModuleCenterDistance, -Constants.kModuleCenterDistance), new TalonFX(0),
            new TalonSRX(1));
    private SwerveModule m_backLeftModule = new SwerveModule(
            new Translation2d(-Constants.kModuleCenterDistance, Constants.kModuleCenterDistance), new TalonFX(0),
            new TalonSRX(1));
    private SwerveModule m_backRightModule = new SwerveModule(
            new Translation2d(-Constants.kModuleCenterDistance, -Constants.kModuleCenterDistance), new TalonFX(0),
            new TalonSRX(1));

    private AHRS m_navx = new AHRS(Port.kMXP);

    private SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(m_frontLeftModule.getLoc(),
            m_frontRightModule.getLoc(), m_backLeftModule.getLoc(), m_backRightModule.getLoc());

    private SwerveDrivePoseEstimator m_poseEstimator = new SwerveDrivePoseEstimator(m_kinematics,
            m_navx.getRotation2d(),
            new SwerveModulePosition[] { m_frontLeftModule.getPosition(), m_frontRightModule.getPosition(),
                    m_backLeftModule.getPosition(), m_backRightModule.getPosition() },
            new Pose2d(), VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
            VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(30)));

    private SwerveDrive() {

    }

    @Override
    public void onUpdate(double timestamp, Robot.Mode mode) {

    }
}
