package com.ragerobotics.robot2024;

public class Constants {
    public static final double kWheelRadius = 0.15; // meters
    public static final double kEncoderTicksPerWheelRotation = 2048.0 * 6.75;
    public static final double kModuleCenterDistance = 0.25; // meters
    public static final double kDt = 1.0 / 50.0; // seconds
    public static final double kMaxV = 3.0; // m/s

    public static final int kFrontLeftDriveMotor = 0;
    public static final int kFrontLeftSteeringMotor = 1;
    public static final int kFrontRightDriveMotor = 2;
    public static final int kFrontRightSteeringMotor = 3;
    public static final int kBackLeftDriveMotor = 4;
    public static final int kBackLeftSteeringMotor = 5;
    public static final int kBackRightDriveMotor = 6;
    public static final int kBackRightSteeringMotor = 7;

    public static final double kDriveP = 1.0;
    public static final double kDriveI = 0.0;
    public static final double kDriveD = 0.0;
    public static final double kDriveF = 0.0;

    public static final double kSteeringP = 1.0;
    public static final double kSteeringI = 0.0;
    public static final double kSteeringD = 0.0;
    public static final double kSteeringF = 0.0;
}
