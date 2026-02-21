// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;
import frc.robot.Commands.*;
import frc.robot.Constants.Swerve_Constants;
import frc.robot.Subsystems.*;

import java.io.File;
import java.util.function.BooleanSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  private CommandXboxController driver;

  private Swerve swerve;

  private Swerve_Commands swerve_Commands;

  public RobotContainer() {
    driver = new CommandXboxController(0);
    swerve = new Swerve(new File(Filesystem.getDeployDirectory(),"swerve"));

    swerve_Commands = new Swerve_Commands(swerve);

    configureBindings();
  }

  private void configureBindings() {

    //Swerve Bindings
    swerve.setDefaultCommand(swerve_Commands.drive(
      () -> getAllianceInvert()*-MathUtil.applyDeadband(driver.getLeftY(), Swerve_Constants.LY_Deadband),
      () -> getAllianceInvert()*-MathUtil.applyDeadband(driver.getLeftX(), Swerve_Constants.LX_Deadband),
      () -> MathUtil.applyDeadband(driver.getRightX(), Swerve_Constants.RX_Deadband),
      () -> true
    ));

    driver.b()
    .onTrue(swerve_Commands.zeroGyro());

    // Other Bindings

    driver.back()
    .onTrue(new InstantCommand(() -> CommandScheduler.getInstance().cancelAll()));
    
    //Swerve Invert

    new Trigger(new BooleanSupplier() {
      @Override
      public boolean getAsBoolean() {
        return (DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == DriverStation.Alliance.Red : false);
      }
    })
    .onTrue(new InstantCommand(() -> swerve.motorInvert()));

    new Trigger(new BooleanSupplier() {
      @Override
      public boolean getAsBoolean() {
        return (DriverStation.getAlliance().isPresent() ? true : false);
      }
    })
    .onTrue(new InstantCommand(() -> swerve.motorInvert()));

    new Trigger(new BooleanSupplier() {
      @Override
      public boolean getAsBoolean() {
        return (DriverStation.isEnabled());
      }
    })
    .onTrue(new InstantCommand(() -> swerve.motorInvert()));
    
    
  }
  

  public Command getAutonomousCommand() {
    return swerve_Commands.runAuto();
  }
  
  private double getAllianceInvert(){
    if(DriverStation.getAlliance().isPresent() ? DriverStation.getAlliance().get() == DriverStation.Alliance.Red : false)
    {
      return -1;
    }
    return 1;
  }

  public void autonomousInit() {
    swerve.autonomousInit();
  }
}