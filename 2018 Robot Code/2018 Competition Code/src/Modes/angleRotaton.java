package Modes;

public class angleRotaton {
	
public static double angleRotation(double targetAngle, double theta) {
		
		double error = 0;
		
		double Kp = .06;
		
		double rotation = 0;
		
		double threshold = 2;
		
		error = targetAngle - theta;
		
		if (error > threshold) {
			
			rotation = error * Kp;	
		}
		
		if (error < threshold) {
			
			rotation = 0;
		}
		
		return rotation;
	}
	
}
