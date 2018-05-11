/* Official Test Swerve Drive Code for 2019*/

package org.usfirst.frc.team5675.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;

import SwerveDrive.SwerveDrive;
import SwerveDrive.WheelDrive;


public class Robot extends TimedRobot {
	AHRS ahrs;
	public static OI m_oi;
	
	public GenericHID Controller  = new XboxController (0);
	
	
	private WheelDrive backRight = new WheelDrive(0, 0, 0);//actual  port ID's of angle, speed, encoders here

	private WheelDrive backLeft = new WheelDrive(0, 0, 0);

	private WheelDrive frontRight = new WheelDrive(0, 0, 0);

	private WheelDrive frontLeft = new WheelDrive(0, 0, 0);
	
	private SwerveDrive swerveDrive = new SwerveDrive (backRight, backLeft, frontRight, frontLeft);

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	
	@Override
	public void robotInit() {
		m_oi = new OI();
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		try {
            ahrs = new AHRS(SPI.Port.kMXP); 
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
	}


	@Override
	public void disabledInit() {
		
	}

	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}


	@Override
	public void autonomousInit() {
		
	}

	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	
	@Override
	public void teleopInit() {
		
		
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}
	
	
	@Override
	public void teleopPeriodic() {
		//Scheduler.getInstance().run();
		
		
		swerveDrive.drive (Controller.getRawAxis(1), Controller.getRawAxis(0), Controller.getRawAxis(4), ahrs.getYaw()); //get them inputs
		
		
	}

	
	@Override
	public void testPeriodic() {
	}
}
