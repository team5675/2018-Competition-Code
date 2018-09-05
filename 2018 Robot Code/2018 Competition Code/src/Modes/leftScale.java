package Modes;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class leftScale extends Constants{

public static void leftScale(boolean isAuto) {
		
		if(isAuto = true) {
			
			clamp.set(Value.kForward);
			
			deploy.set(Value.kReverse);
			
			driveEncoder.reset();
			
			while((driveEncoder.getDistance()>-205)) {
				
				driveTrain.arcadeDrive(-0.77, angleRotation(0, gyro.getAngle()));
				
				System.out.println("loop 1: "+liftencoder.getRaw());
			}
			
			while((driveEncoder.getDistance()>-276)) {
				
				driveTrain.arcadeDrive(0.5, gyro.getAngle()*0.1);
				
				System.out.println("loop 2: "+liftencoder.getRaw());
				
				if(liftencoder.getRaw() < 260000) {
					
					lift1.set(1);
					
					lift2.set(1);
				}
				
				else {
					lift1.set(.2);
					
					lift2.set(.2);
				}
			}
			
			while((gyro.getAngle()>45)) {
				
				driveTrain.arcadeDrive(0, -0.75);
				
				System.out.println("loop 3: "+liftencoder.getRaw());
				
				if(liftencoder.getRaw() < 260000) {
					
					lift1.set(1);
					
					lift2.set(1);
				}
				
				else {
					
					lift1.set(.25);
					
					lift2.set(.25);
				}
			}
			
			driveEncoder.reset();
			
			while((driveEncoder.getDistance()>-12)) {
				
				driveTrain.arcadeDrive(0.5, (gyro.getAngle()-50)*0.08);
				
				System.out.println("loop 4: "+liftencoder.getRaw());
				
				if(liftencoder.getRaw()<260000) {
					
					lift1.set(1);
					
					lift2.set(1);
				}
				else {
					
					lift1.set(.25);
					
					lift2.set(.25);
				}
			}
			
			driveTrain.arcadeDrive(0, 0);
			
			intake1.set(.9);
			
			intake2.set(-.9);
			
			Timer.delay(1);
			
			intake1.set(0);
			
			intake2.set(0);
			
			clamp.set(Value.kReverse);
			
			driveEncoder.reset();
			
			while(driveEncoder.getDistance() < 12) {
				
				driveTrain.arcadeDrive(-.5, 0);
			}
			
			driveTrain.arcadeDrive(0,0);
			
			while((liftencoder.getDistance() > 200)) {
				
				lift1.set(-.8);
				
				lift2.set(-.8);
			}
			
			lift1.set(0);
			
			lift2.set(0);
			
			while(gyro.getAngle() > -135) {
				
				driveTrain.arcadeDrive(0, -0.75);
			}
			
			driveTrain.arcadeDrive(0, 0);
			
		}
	}
	
}
