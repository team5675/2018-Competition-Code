package Modes;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;

public class driveForward extends Constants{

public static void driveForward(boolean isAuto) {
		
		if(isAuto = true) {
			
			comp.start();
			
			while(driveEncoder.getDistance()>-120) {
				
				deploy.set(Value.kReverse);//down and clamped
				
				clamp.set(Value.kForward);
				
				driveTrain.arcadeDrive(0.6, gyro.getAngle() * angleRotation(0, gyro.getAngle()));//skrt skrt, but easy on the gyro
				
				Timer.delay(0.05);
			}		
			}
		}

	
}
