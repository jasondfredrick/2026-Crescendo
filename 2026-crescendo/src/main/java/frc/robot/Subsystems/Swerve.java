package frc.robot.Subsystems;

import frc.robot.Constants.Swerve_Constants;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class Swerve extends SubsystemBase {

    private SwerveDrive swerve;

    private PIDController turnController;
    private PIDController driveController;

    public Swerve(File directory) {
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        try {
            swerve = new SwerveParser(directory).createSwerveDrive(Swerve_Constants.maxVelocity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        turnController = new PIDController(Swerve_Constants.turnKP, Swerve_Constants.turnKI, Swerve_Constants.turnKD);
        driveController = new PIDController(Swerve_Constants.driveKP, Swerve_Constants.driveKI, Swerve_Constants.driveKD);
        turnController.setTolerance(0.02, 0.01);
        driveController.setTolerance(0.05, 0.01);

        motorInvert();
        swerve.chassisVelocityCorrection = true;
    }

    public void drive(Translation2d translation2d, double rotationSpeed, boolean fieldRelative) {
        swerve.drive(translation2d, rotationSpeed, fieldRelative, true); 
    }

    public Command getAuto() {
        return Commands.none();
    }

    public void setChassisSpeeds(ChassisSpeeds velocity) {
        swerve.setChassisSpeeds(velocity);
    }

    public void resetOdometry(Pose2d pose) {
        swerve.resetOdometry(pose);
      }

    public void zeroGyro() {
        swerve.zeroGyro();        
    }

    public ChassisSpeeds getRobotVelocity() {
        return swerve.getRobotVelocity();
    }

    public Pose2d getPose() {
        return swerve.getPose();
    }

    public boolean turnAligned() {
        return (turnController.atSetpoint());
    }

    public void autonomousInit(){
        motorInvert();
    }

    public void motorInvert(){
        //boolean invert = isRedAlliance(); 
        for(int i = 0; i < 4; i++){
            swerve.getModules()[i].getDriveMotor().setInverted(true);
        } 
    }

    public boolean isRedAlliance(){
        return DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == DriverStation.Alliance.Red : false; 
    }

    public double allianceInvert()
    {
        if(isRedAlliance())
        {
            return -1;
        }
        return 1;
    }

    @Override
    public void periodic() {
    }
}