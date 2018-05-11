/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5675.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * <p>The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 *
 * <p>WARNING: While it may look like a good choice to use for your code if
 * you're inexperienced, don't. Unless you know what you are doing, complex code
 * will be much more difficult under this system. Use IterativeRobot or
 * Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
	Compressor comp = new Compressor(0);
	DoubleSolenoid sol = new DoubleSolenoid(0, 1);
	XboxController con = new XboxController(0);
	
	public Robot() {
		
	}

	@Override
	public void robotInit() {
		comp.setClosedLoopControl(false);
		comp.start();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 *
	 * <p>If you wanted to run a similar autonomous mode with an IterativeRobot
	 * you would write:
	 *
	 * <blockquote><pre>{@code
	 * Timer timer = new Timer();
	 *
	 * // This function is run once each time the robot enters autonomous mode
	 * public void autonomousInit() {
	 *     timer.reset();
	 *     timer.start();
	 * }
	 *
	 * // This function is called periodically during autonomous
	 * public void autonomousPeriodic() {
	 * // Drive for 2 seconds
	 *     if (timer.get() < 2.0) {
	 *         myRobot.drive(-0.5, 0.0); // drive forwards half speed
	 *     } else if (timer.get() < 5.0) {
	 *         myRobot.drive(-1.0, 0.0); // drive forwards full speed
	 *     } else {
	 *         myRobot.drive(0.0, 0.0); // stop robot
	 *     }
	 * }
	 * }</pre></blockquote>
	 */
	@Override
	public void autonomous() {
		
	}

	/**
	 * Runs the motors with arcade steering.
	 *
	 * <p>If you wanted to run a similar teleoperated mode with an IterativeRobot
	 * you would write:
	 *
	 * <blockquote><pre>{@code
	 * // This function is called periodically during operator control
	 * public void teleopPeriodic() {
	 *     myRobot.arcadeDrive(stick);
	 * }
	 * }</pre></blockquote>
	 */
	@Override
	public void operatorControl() {
		sol.set(DoubleSolenoid.Value.kOff);
		while(isEnabled()) {
		if (con.getAButton()) {
			sol.set(DoubleSolenoid.Value.kForward);
		}
		else if (con.getBButton()) {
			sol.set(DoubleSolenoid.Value.kReverse);
		}
		}
	}

	/**
	 * Runs during test mode.
	 */
	@Override
	public void test() {
	}
}
