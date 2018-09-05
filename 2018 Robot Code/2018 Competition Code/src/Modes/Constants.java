package Modes;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Spark;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.RobotDrive;

public class Constants extends autoSelections {
	
	
	boolean whichAuto = false;
	
	
	static Compressor comp = new Compressor(0);//Come on, you know this one
	
	
	static Encoder liftencoder = new Encoder(2, 3);
	
	static Encoder driveEncoder = new Encoder(0, 1);//Is that explanatory enough?
	
	
	static AHRS gyro = new AHRS(SerialPort.Port.kMXP);//Autonomous Setting System
	
	
	static RobotDrive driveTrain = new RobotDrive(0, 1, 2, 3);//goes left to right, right Matt?
	
	
	static Spark lift1 = new Spark(4);//sorry, cubes. No free hour'deourves with this ride
	
	static Spark lift2 = new Spark(5);
	
	
	static Spark intake1 = new Spark(6);//eating 'em cubes
	
	static Spark intake2 = new Spark(8);
	   
	
	static DoubleSolenoid deploy = new DoubleSolenoid(2, 3);//flipping claw up and down and all around
	
	static DoubleSolenoid clamp = new DoubleSolenoid(0, 1);//the CHAD clamp
	
	
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
