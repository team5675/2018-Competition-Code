package SwerveDrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;


public class WheelDrive {
	
private Talon angleMotor;

private Talon speedMotor;

private PIDController pidController;


public WheelDrive (int angleMotor, int speedMotor, int encoder) { //Idk if this is even close to correct...
	
	this.angleMotor = new Talon (angleMotor);
	
	this.speedMotor = new Talon (speedMotor);
	
	pidController = new PIDController (1, 0, 2, new AnalogInput (encoder), this.angleMotor); //the numbers are our PID Constants
	
	
	pidController.setOutputRange(-1, 1);
	
	pidController.setContinuous (); //if using encoders or endless pots
	
	pidController.enable (); //make sure we turn it on ;)
}

private final double MAX_VOLTS = 0;               //getting setpoint for PID Control; based max voltage of our drive

public void drive (double speed, double angle) {
	
	speedMotor.set(speed);
	
	double setpoint = angle * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5);
	
	if (setpoint < 0) {
		setpoint = MAX_VOLTS + setpoint;          //turning one way
		
	}
	if (setpoint > MAX_VOLTS) {                   //turning other way
		setpoint = setpoint - MAX_VOLTS;
		
	}
	
	
	pidController.setSetpoint(setpoint);          //let em rip, as they say
}
}
