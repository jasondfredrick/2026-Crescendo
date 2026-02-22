package frc.robot.Constants;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

public class Swerve_Constants { 
    // Deadbands
    public static final double LX_Deadband = 0.05;
    public static final double LY_Deadband = 0.05;
    public static final double RX_Deadband = 0.01;

    // Physics
    public static final double maxVelocity = 4.5;
    public static final double maxAngularSpeed = 10.0;
    public static final double maxModuleSpeed = 4.5;

    //Constants
    public static final double aimToleranceRadians = 0.05;
    public static final double aimToleranceRadiansPerSecond = 0.02;
    public static final double driveToleranceMeters = 0.05;
    public static final double driveToleranceMetersPerSecond = 0.2;
    public static final LoggedNetworkNumber turnKP = new LoggedNetworkNumber("turnKP",4.0);
    public static final LoggedNetworkNumber turnKI = new LoggedNetworkNumber("turnKI",0.0);
    public static final LoggedNetworkNumber turnKD = new LoggedNetworkNumber("turnKD",0.0);
    public static final LoggedNetworkNumber driveKP = new LoggedNetworkNumber("driveKP",0.5);
    public static final LoggedNetworkNumber driveKI = new LoggedNetworkNumber("driveKI",0.0);
    public static final LoggedNetworkNumber driveKD = new LoggedNetworkNumber("driveKD",0.0);
}
