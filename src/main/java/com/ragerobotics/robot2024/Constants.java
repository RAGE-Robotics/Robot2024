package com.ragerobotics.robot2024;

public class Constants {
    public static final double kWheelRadius = 0.0254; // meters
    public static final double kEncoderTicksPerWheelRotation = 2048.0 * 6.75;
    public static final double kEncoderTicksPerSteeringRotation = 4096.0;
    public static final double kModuleCenterDistance = 0.288925; // meters
    public static final double kDt = 1.0 / 50.0; // seconds
    public static final double kMaxV = 1.0; // m/s

    public static final int kFrontLeftDriveMotor = 14;
    public static final int kFrontLeftSteeringMotor = 11;
    public static final int kFrontRightDriveMotor = 1;
    public static final int kFrontRightSteeringMotor = 10;
    public static final int kBackLeftDriveMotor = 13;
    public static final int kBackLeftSteeringMotor = 4;
    public static final int kBackRightDriveMotor = 3;
    public static final int kBackRightSteeringMotor = 7;

    public static final double kFrontLeftAngleOffset = 0.11351457830353745;
    public static final double kFrontRightAngleOffset = -4.706253057233147;
    public static final double kBackLeftAngleOffset = -4.382583110989277;
    public static final double kBackRightAngleOffset = -0.6350680461846554;

    public static final double kDriveP = 0.01;
    public static final double kDriveI = 0.0;
    public static final double kDriveD = 0.01;
    public static final double kDriveF = 0.05;

    public static final double kSteeringP = 0.3;
    public static final double kSteeringI = 0.001;
    public static final double kSteeringD = 1.0;
    public static final double kSteeringF = 0.0;

    public static final int kDriverController = 0;

    public static final int kTalonStatusPeriod = 100;
}
