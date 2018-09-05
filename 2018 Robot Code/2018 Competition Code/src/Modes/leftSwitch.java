package Modes;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class leftSwitch extends Constants {
	
public static void leftSwitch(boolean isAuto) {
		
		
		if (isAuto = true) {
			
		
		while((driveEncoder.getDistance() > -100)) {
			
			driveTrain.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
		}
		
		while((gyro.getAngle() < 90)) {
			
			driveTrain.arcadeDrive(0, angleRotation(90, gyro.getAngle()));
		}
		
		if(liftencoder.getRaw()<68000){
			
			
			lift1.set(1);
			
			lift2.set(1);
			
		}
		
		else{
			
			
			lift1.set(0);
			
			lift2.set(0);
		}
		
while((gyro.getAngle() < 100)) {
			
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
