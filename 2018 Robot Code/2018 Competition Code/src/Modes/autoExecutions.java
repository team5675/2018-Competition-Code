package Modes;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class autoExecutions extends org.usfirst.frc.team5675.robot.Robot{
	
	
	
public static double angleRotation(double targetAngle, double theta) {
		
		double error = 0;
		
		double Kp = .01;
		
		double rotation = 0;
		
		double threshold = 5;
		
		error = targetAngle - theta;
		
		if (error > threshold) {
			
			rotation = error * Kp;	
		}
		
		if (error < threshold) {
			
			rotation = 0;
		}
		
		return rotation;
	}
	
	
public static void centerSwitchLeft(boolean isAuto) {
		if (isAuto = true) {//left side
			
			clamp.set(Value.kForward);
			
			deploy.set(Value.kReverse);
			
			System.out.println(driveEncoder);
			
			while((driveEncoder.getDistance() > -10)) {
				
				chassis.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
			}
			
			
			while((gyro.getAngle()>-30)) {
				
				chassis.arcadeDrive(0, angleRotation(-30, gyro.getAngle()));
			}
			
			
			driveEncoder.reset();
			
			
			while((driveEncoder.getDistance()>-86)) {
				
				
				chassis.arcadeDrive(0.7, (gyro.getAngle()+24)*.06);
				
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
				
				chassis.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
				
				deploy.set(Value.kForward);
				
				intake1.set(1);
				
				intake2.set(-1);
				
				lift1.set(.2);
				
				lift2.set(.2);
		}
		}
		}


public static void centerSwitchRight(boolean isAuto) {
	
	if(isAuto = true) {//right side
	
	deploy.set(Value.kReverse);
	
	clamp.set(Value.kForward);
	
	
	while((driveEncoder.getDistance()>-10)) {
		
		chassis.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
	}
	
	
	while((gyro.getAngle() < 15)) {
		
		chassis.arcadeDrive(0, angleRotation(15, gyro.getAngle()));
	}
	
	
	driveEncoder.reset();
	
	
	while((driveEncoder.getDistance()>-80)) {
		
		chassis.arcadeDrive(0.65, (gyro.getAngle()-24)*0.06);
		
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
		
		chassis.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
		
		deploy.set(Value.kForward);
		
		intake1.set(1);
		
		intake2.set(-1);
		
		lift1.set(.2);
		
		lift2.set(.2);
	}
}
}


public static void driveForward(boolean isAuto) {
	
	if(isAuto = true) {
		
		comp.start();
		
		while(driveEncoder.getDistance()>-120) {
			
			deploy.set(Value.kReverse);//down and clamped
			
			clamp.set(Value.kForward);
			
			chassis.arcadeDrive(0.6, gyro.getAngle() * angleRotation(0, gyro.getAngle()));//skrt skrt, but easy on the gyro
			
			Timer.delay(0.05);
		}		
		}
		}


public static void leftCross(boolean isAuto) {
	
	if(isAuto = true) {
	
	clamp.set(Value.kForward);
	
	deploy.set(Value.kReverse);
	
	driveEncoder.reset();
	
	while((driveEncoder.getDistance()>-190)) {
		
		chassis.arcadeDrive(0.75, gyro.getAngle() * 0.1);
	}
	
	while((driveEncoder.getDistance()>-215)) {
		
		chassis.arcadeDrive(0.6, gyro.getAngle() * 0.1);
	}
	
	while((gyro.getAngle()>-80)) {
		
		chassis.arcadeDrive(0, 0.72);
	}
	
	driveEncoder.reset();
	
	while((driveEncoder.getDistance() > -208)) {
		
		chassis.arcadeDrive(0.7, (gyro.getAngle() + 87) * 0.08);
	}
	
	while((gyro.getAngle()<5)) {
		
		chassis.arcadeDrive(0, -0.62);
		
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
		
		chassis.arcadeDrive(0.55, (gyro.getAngle()-20)*0.08);
		
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
	
	chassis.arcadeDrive(0, 0);
	
	Timer.delay(1);
	
	intake1.set(0);
	
	intake2.set(0);
	
	clamp.set(Value.kReverse);
	
	driveEncoder.reset();
	
	while(driveEncoder.getDistance() < 15) {
		
		chassis.arcadeDrive(-.5, 0);
	}
	
	chassis.arcadeDrive(0,0);
	
	while((liftencoder.getDistance() > 200)) {
		
		lift1.set(-.8);
		
		lift2.set(-.8);
	}
	
	lift1.set(0);
	
	lift2.set(0);
	
	while(gyro.getAngle() < 135) {
		
		chassis.arcadeDrive(0, -0.75);
	}
	
	chassis.arcadeDrive(0,0);
}
}


public static void rightCross(boolean isAuto) {
	
	if(isAuto = true) {
		
		clamp.set(Value.kForward);
		
		deploy.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance()>-190)) {
			
			chassis.arcadeDrive(0.75, gyro.getAngle() * 0.1);
		}
		
		while((driveEncoder.getDistance()>-215)) {
			
			chassis.arcadeDrive(0.6, gyro.getAngle() * 0.1);
		}
		
		while((gyro.getAngle()>80)) {
			
			chassis.arcadeDrive(0, -0.72);
		}
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance() > -208)) {
			
			chassis.arcadeDrive(0.7, (gyro.getAngle() - 87) * 0.08);
		}
		
		while((gyro.getAngle() < -5)) {
			
			chassis.arcadeDrive(0, 0.62);
			
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
			
			chassis.arcadeDrive(0.55, (gyro.getAngle() + 20)*0.08);
			
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
		
		chassis.arcadeDrive(0, 0);
		
		Timer.delay(1);
		
		intake1.set(0);
		
		intake2.set(0);
		
		clamp.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while(driveEncoder.getDistance() < 15) {
			
			chassis.arcadeDrive(-.5, 0);
		}
		
		chassis.arcadeDrive(0,0);
		
		while((liftencoder.getDistance() > 200)) {
			
			lift1.set(-.8);
			
			lift2.set(-.8);
		}
		
		lift1.set(0);
		
		lift2.set(0);
		
		while(gyro.getAngle() < -135) {
			
			chassis.arcadeDrive(0, 0.75);
		}
		
		chassis.arcadeDrive(0,0);
		
	}
}


public static void leftSwitch(boolean isAuto) {
	
	
	if (isAuto = true) {
		
	
	while((driveEncoder.getDistance() > -130)) {
		
		chassis.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));

	}
	
	while((gyro.getAngle() < 90)) {
		
		chassis.arcadeDrive(0, (angleRotation(90, gyro.getAngle())) * -1);
	}
	
	if(liftencoder.getRaw() < 68000){
		
		
		lift1.set(1);
		
		lift2.set(1);
		
	}
	
	else{
		
		
		lift1.set(0);
		
		lift2.set(0);
	}
	
while((gyro.getAngle() < 100)) {
		
		chassis.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
		
		deploy.set(Value.kForward);
		
		intake1.set(1);
		
		intake2.set(-1);
		
		lift1.set(.2);
		
		lift2.set(.2);
		
	}
}
}


public static void rightSwitch(boolean isAuto) {
	
	if(isAuto = true) {
	
while((driveEncoder.getDistance() > -100)) {
		
		chassis.arcadeDrive(0.6, angleRotation(0, gyro.getAngle()));
	}
	
	while((gyro.getAngle() < -90)) {
		
		chassis.arcadeDrive(0, angleRotation(-90, gyro.getAngle()));
	}
	
	if(liftencoder.getRaw()<68000){
		
		
		lift1.set(1);
		
		lift2.set(1);
		
	}
	
	else{
		
		
		lift1.set(0);
		
		lift2.set(0);
	}
	
while((gyro.getAngle() < -100)) {
		
		chassis.arcadeDrive(0, angleRotation(-65, gyro.getAngle()));
		
		deploy.set(Value.kForward);
		
		intake1.set(1);
		
		intake2.set(-1);
		
		lift1.set(.2);
		
		lift2.set(.2);
	}
}
}


public static void leftScale(boolean isAuto) {
	
	if(isAuto = true) {
		
		clamp.set(Value.kForward);
		
		deploy.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance()>-205)) {
			
			chassis.arcadeDrive(-0.77, angleRotation(0, gyro.getAngle()));
			
			System.out.println("loop 1: "+liftencoder.getRaw());
		}
		
		while((driveEncoder.getDistance()>-276)) {
			
			chassis.arcadeDrive(0.5, gyro.getAngle()*0.1);
			
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
			
			chassis.arcadeDrive(0, -0.75);
			
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
			
			chassis.arcadeDrive(0.5, (gyro.getAngle()-50)*0.08);
			
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
		
		chassis.arcadeDrive(0, 0);
		
		intake1.set(.9);
		
		intake2.set(-.9);
		
		Timer.delay(1);
		
		intake1.set(0);
		
		intake2.set(0);
		
		clamp.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while(driveEncoder.getDistance() < 12) {
			
			chassis.arcadeDrive(-.5, 0);
		}
		
		chassis.arcadeDrive(0,0);
		
		while((liftencoder.getDistance() > 200)) {
			
			lift1.set(-.8);
			
			lift2.set(-.8);
		}
		
		lift1.set(0);
		
		lift2.set(0);
		
		while(gyro.getAngle() > -135) {
			
			chassis.arcadeDrive(0, -0.75);
		}
		
		chassis.arcadeDrive(0, 0);
		
	}
}


public static void rightScale(boolean isAuto) {
	
	if(isAuto = true) {
	
	clamp.set(Value.kForward);
	
	deploy.set(Value.kReverse);
	
	driveEncoder.reset();
	
	while((driveEncoder.getDistance()>-205)) {
		
		chassis.arcadeDrive(0.77, angleRotation(0, gyro.getAngle()));
		
		System.out.println("loop 1: "+liftencoder.getRaw());
	}
	
	while((driveEncoder.getDistance()>-276)) {
		
		chassis.arcadeDrive(0.5, gyro.getAngle()*0.1);
		
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
	
	while((gyro.getAngle()>-45)) {
		
		chassis.arcadeDrive(0, 0.75);
		
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
		
		chassis.arcadeDrive(0.5, (gyro.getAngle()+50)*0.08);
		
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
	
	chassis.arcadeDrive(0, 0);
	
	intake1.set(.9);
	
	intake2.set(-.9);
	
	Timer.delay(1);
	
	intake1.set(0);
	
	intake2.set(0);
	
	clamp.set(Value.kReverse);
	
	driveEncoder.reset();
	
	while(driveEncoder.getDistance() < 12) {
		
		chassis.arcadeDrive(-.5, 0);
	}
	
	chassis.arcadeDrive(0,0);
	
	while((liftencoder.getDistance() > 200)) {
		
		lift1.set(-.8);
		
		lift2.set(-.8);
	}
	
	lift1.set(0);
	
	lift2.set(0);
	
	while(gyro.getAngle() > -135) {
		
		chassis.arcadeDrive(0, 0.75);
	}
	
	chassis.arcadeDrive(0, 0);
}
}


public static void halfLeftScale(boolean isAuto) {
	
	if (isAuto = true) {
		
clamp.set(Value.kForward);
		
		deploy.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance()>-205)) {
			
			chassis.arcadeDrive(-0.77, angleRotation(0, gyro.getAngle()));
			
			System.out.println("loop 1: "+liftencoder.getRaw());
		}
	}
}


public static void halfRightScale(boolean isAuto) {
	
	if (isAuto = true) {
		
clamp.set(Value.kForward);
		
		deploy.set(Value.kReverse);
		
		driveEncoder.reset();
		
		while((driveEncoder.getDistance()>-205)) {
			
			chassis.arcadeDrive(-0.77, angleRotation(0, gyro.getAngle()));
			
			System.out.println("loop 1: "+liftencoder.getRaw());
		}
		
	}
}


}