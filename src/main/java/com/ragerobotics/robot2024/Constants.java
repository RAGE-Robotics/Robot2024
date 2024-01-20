package com.ragerobotics.robot2024;

public class Constants {
    public static final double kWheelRadius = 0.0254; // meters
    public static final double kEncoderTicksPerWheelRotation = 1024.0 * 6.75;
    public static final double kEncoderTicksPerSteeringRotation = 4096.0;
    public static final double kModuleCenterDistance = 0.288925; // meters
    public static final double kDt = 1.0 / 50.0; // seconds
    public static final double kMaxV = 3.0; // m/s

    public static final int kFrontLeftDriveMotor = 14;
    public static final int kFrontLeftSteeringMotor = 11;
    public static final int kFrontRightDriveMotor = 1;
    public static final int kFrontRightSteeringMotor = 10;
    public static final int kBackLeftDriveMotor = 13;
    public static final int kBackLeftSteeringMotor = 4;
    public static final int kBackRightDriveMotor = 3;
    public static final int kBackRightSteeringMotor = 7;

    public static final double kFrontLeftAngleOffset = -5.238544390629465;
    public static final double kFrontRightAngleOffset = -1.5447186534008406;
    public static final double kBackLeftAngleOffset = -1.8039614065535141;
    public static final double kBackRightAngleOffset = 4.063515107109064;

    public static final double kDriveP = 0.0;
    public static final double kDriveI = 0.0;
    public static final double kDriveD = 0.0;
    public static final double kDriveF = 0.0;

    public static final double kSteeringP = 0.1;
    public static final double kSteeringI = 0.0;
    public static final double kSteeringD = 0.0;
    public static final double kSteeringF = 0.0;

    public static final int kDriverController = 0;
}
