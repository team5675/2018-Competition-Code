package Modes;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class halfLeftScale extends Constants{
	
public static void halfLeftScale(boolean isAuto) {
		
		if (isAuto = true) {
			
clamp.set(Value.kForward);
			
			deploy.set(Value.kReverse);
			
			driveEncoder.reset();
			
			while((driveEncoder.getDistance()>-205)) {
				
				driveTrain.arcadeDrive(-0.77, angleRotation(0, gyro.getAngle()));
				
				System.out.println("loop 1: "+liftencoder.getRaw());
			}
		}
	}

}
