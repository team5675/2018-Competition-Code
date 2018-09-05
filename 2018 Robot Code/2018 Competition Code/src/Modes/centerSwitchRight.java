package Modes;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
public class centerSwitchRight extends Constants {

public static void centerSwitchRight(boolean isAuto) {
		
		if(isAuto = true) {//right side
		
		deploy.set(Value.kReverse);
		
		clamp.set(Value.kForward);
		
		
		while((driveEncoder.getDistance()>-10)) {
			
			driveTrain.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
		}
		
		
		while((gyro.getAngle() < 15)) {
			
			driveTrain.arcadeDrive(0, angleRotation(15, gyro.getAngle()));
		}
		
		
		driveEncoder.reset();
		
		
		while((driveEncoder.getDistance()>-80)) {
			
			driveTrain.arcadeDrive(0.65, (gyro.getAngle()-24)*0.06);
			
			if(liftencoder.getRaw()<68000){
				
				
				lift1.set(1);
				
				lift2.set(1);
				
			}
			
			else{
				
				
				lift1.set(0);
				
				lift2.set(0);
			}
		}
		
		
		while((gyro.getAngle()>-65)) {
			
			driveTrain.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
			
			deploy.set(Value.kForward);
			
			intake1.set(1);
			
			intake2.set(-1);
			
			lift1.set(.2);
			
			lift2.set(.2);
		}
	}
	}
		
	public static void centerSwitchLeft(boolean isAuto) {
		if (isAuto = true) {//left side
			
			clamp.set(Value.kForward);
			
			deploy.set(Value.kReverse);
			
			
			while((driveEncoder.getDistance()>-10)) {
				
				driveTrain.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
			}
			
			
			while((gyro.getAngle()>-30)) {
				
				driveTrain.arcadeDrive(0, angleRotation(-30, gyro.getAngle()));
			}
			
			
			driveEncoder.reset();
			
			
			while((driveEncoder.getDistance()>-86)) {
				
				
				driveTrain.arcadeDrive(0.7, (gyro.getAngle()+24)*.06);
				
				if(liftencoder.getRaw()<68000){
						
					lift1.set(1);
						
					lift2.set(1);
			}
				else{
						
					lift1.set(0);
						
					lift2.set(0);
			}
			}
			
			
			while((gyro.getAngle()>-65)) {
				
				driveTrain.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
				
				deploy.set(Value.kForward);
				
				intake1.set(1);
				
				intake2.set(-1);
				
				lift1.set(.2);
				
				lift2.set(.2);
		}
		}
		}
	
	
}
