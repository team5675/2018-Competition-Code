package Modes;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class leftCross extends Constants{
	
public static void leftCross(boolean isAuto) {
		
		if(isAuto = true) {
		
		clamp.set(Value.kForward);
		
		deploy.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance()>-190)) {
			
			driveTrain.arcadeDrive(0.75, gyro.getAngle() * 0.1);
		}
		
		while((driveEncoder.getDistance()>-215)) {
			
			driveTrain.arcadeDrive(0.6, gyro.getAngle() * 0.1);
		}
		
		while((gyro.getAngle()>-80)) {
			
			driveTrain.arcadeDrive(0, 0.72);
		}
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance() > -208)) {
			
			driveTrain.arcadeDrive(0.7, (gyro.getAngle() + 87) * 0.08);
		}
		
		while((gyro.getAngle()<5)) {
			
			driveTrain.arcadeDrive(0, -0.62);
			
			if(liftencoder.getRaw()<260000) {
				
				lift1.set(1);
				
				lift2.set(1);
			}
			
			else {
				
				lift1.set(0.2);
				
				lift2.set(0.2);
			}
		}
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance() > -12)) {
			
			driveTrain.arcadeDrive(0.55, (gyro.getAngle()-20)*0.08);
			
			if(liftencoder.getRaw() < 260000) {
				
				lift1.set(1);
				
				lift2.set(1);
			}
			else {
				
				lift1.set(0.25);
				
				lift2.set(0.25);
			}
		}
		
		driveEncoder.reset();
		
		lift1.set(.25);
		
		lift2.set(.25);
		
		intake1.set(.6);
		
		intake2.set(-.6);
		
		driveTrain.arcadeDrive(0, 0);
		
		Timer.delay(1);
		
		intake1.set(0);
		
		intake2.set(0);
		
		clamp.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while(driveEncoder.getDistance() < 15) {
			
			driveTrain.arcadeDrive(-.5, 0);
		}
		
		driveTrain.arcadeDrive(0,0);
		
		while((liftencoder.getDistance() > 200)) {
			
			lift1.set(-.8);
			
			lift2.set(-.8);
		}
		
		lift1.set(0);
		
		lift2.set(0);
		
		while(gyro.getAngle() < 135) {
			
			driveTrain.arcadeDrive(0, -0.75);
		}
		
		driveTrain.arcadeDrive(0,0);
	}
	}

}
