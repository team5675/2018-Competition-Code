package org.mattawanwiredcats.code.robot2018;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;

public class Controls {
	static XboxController controller1 = new XboxController(0);
	static XboxController controller2 = new XboxController(1);
	
	public static double getRightSide() {
		return controller1.getRawAxis(0);
	}
	
	public static double getLeftSide() {
		return controller1.getRawAxis(1);
	}
	
	public static double getLazySusan() {
		return controller2.getRawAxis(0);
	}
	
	public static double getElevator() {
		return controller2.getRawAxis(1);
	}
	
	public static boolean getOpenClaw() {
		return controller2.getBumper(Hand.kRight);
	}
	
	public static boolean getCloseClaw() {
		return controller2.getBumper(Hand.kLeft);
	}
	
	public static double getClawExit() {
		return controller2.getTriggerAxis(Hand.kRight);
	}
	
	public static double getClawEnter() {
		return controller2.getTriggerAxis(Hand.kLeft);
	}
	
	public static boolean getClimbUp() {
		return controller2.getYButton();
	}
}