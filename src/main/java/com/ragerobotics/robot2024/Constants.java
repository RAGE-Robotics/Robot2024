package com.ragerobotics.robot2024;

public class Constants {
    public static final double kWheelRadius = 0.0508; // meters
    public static final double kEncoderTicksPerWheelRotation = 2048.0 * 6.75;
    public static final double kEncoderTicksPerSteeringRotation = 4096.0;
    public static final double kModuleCenterDistance = 0.31115; // meters
    public static final double kDt = 1.0 / 50.0; // seconds
    public static final double kMaxV = 4.0; // m/s
    public static final double kMaxDriverV = 1.75; // m/s
    public static final double kTurningFactor = 2.0;

    public static final int kFrontLeftDriveMotor = 14;
    public static final int kFrontLeftSteeringMotor = 17;
    public static final int kFrontRightDriveMotor = 1;
    public static final int kFrontRightSteeringMotor = 7;
    public static final int kBackLeftDriveMotor = 13;
    public static final int kBackLeftSteeringMotor = 4;
    public static final int kBackRightDriveMotor = 3;
    public static final int kBackRightSteeringMotor = 10;

    public static final double kFrontLeftAngleOffset = -0.12885438618239387;
    public static final double kFrontRightAngleOffset = -3.034213998437798;
    public static final double kBackLeftAngleOffset = -3.9975539332299808;
    public static final double kBackRightAngleOffset = -0.34974761963792617;

    public static final double kDriveP = 0.0;
    public static final double kDriveI = 0.0;
    public static final double kDriveD = 0.0;
    public static final double kDriveF = 0.07;

    public static final double kSteeringP = 0.5;
    public static final double kSteeringI = 0.002;
    public static final double kSteeringD = 0.12;
    public static final double kSteeringF = 0.0;

    public static final int kDriverController = 0;
    public static final int kOperatorController = 1;

    public static final int kTalonStatusPeriod = 100; // ms

    public static final double kPathFollowingTolerance = 0.05; // m
    public static final double kPathFollowingRampTime = 3.0; // s
    public static final double kPathFollowingLookAheadRadius = 0.1; // m
    public static final double kPathFollowingMaxV = 1.0; // m/s
    public static final double kPathFollowingMaxRotV = 2.0; // rad/s;

    public static final double kPathFollowingPositionP = 5.0;
    public static final double kPathFollowingPositionI = 0.0;
    public static final double kPathFollowingPositionD = 0.5;

    public static final double kPathFollowingAngleP = 5.0;
    public static final double kPathFollowingAngleI = 0.0;
    public static final double kPathFollowingAngleD = 0.1;

    public static final double kAngleP = 5.0;
    public static final double kAngleI = 0.0;
    public static final double kAngleD = 0.1;

    public static final double kEpsilon = 1e-6;

    public static final int kIntakeBackCanId = 12;
    public static final int kIntakeFrontCanId = 16;
    public static final int kIntakeSensorChannel = 3;
    public static final double kIntakeGain = 1.0;

    public static final int kLEDChannel = 9; // PWM

    public static final double kMinPressure = 100;
    public static final double kMaxPressure = 120;
    public static final int kClimberSolenoidForwardA = 3;
    public static final int kClimberSolenoidReverseA = 1;
    public static final int kClimberSolenoidForwardB = 4;
    public static final int kClimberSolenoidReverseB = 2;

    public static final double kDropperRotationP = 0.09;
    public static final double kDropperRotationI = 0;
    public static final double kDropperRotationD = 0.01;
    public static final double kDropperRotationF = 0;

    public static final double kDropperInPos = 0;
    public static final double kDropperVertPos = 2.25;
    public static final double kDropperGearRatio = 72.0 / 22.0;
    public static final double kDropperTicksPerRotation = 8192.0;
    public static final double kDropperTolerance = 0.2;

    public static final int kDropperBeltMotorId = 11;
    public static final int kDropperRotatingMotorId = 5;
    public static final int kDropperRollerMotorId = 15;
    public static final int kDropperSensorChannel = 1;

    public static final double kTriggerDeadband = 0.05;

    public static final int kWallSensorChannelA = 0;
    public static final int kWallSensorChannelB = 2;

    public static final double kDriveAngleTolerance = 0.25;
}
