/*----------------------------------------------------------------------------*/
/*Official 2018 Mattawan WiredCats Competition code
 * 
 * WIP
 */
package org.usfirst.frc.team5675.robot;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.hal.DIOJNI;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.kauailabs.navx.frc.AHRS;
import Autos.autoSelections;


public class Robot extends SampleRobot {
	
	Joystick stick = new Joystick(0);
	
	Boolean swtch, scale;
	
	double bheight, cheight, sheight, theight;

	RobotDrive chassis = new RobotDrive(0, 1, 2, 3);//chassis
	
	XboxController xbox1 = new XboxController(0);
	
	XboxController xbox2 = new XboxController(1);
	
	Spark lift1 = new Spark(4);//lift motors
	
	Spark lift2 = new Spark(5);
	
	Spark intake1 = new Spark(6);//intake wheel motors
	
	Spark intake2 = new Spark(8);
	
	Spark climber = new Spark(9);//climber motor
	
	Spark arm = new Spark(7);
	
	Compressor comp = new Compressor(0);
		
	DoubleSolenoid deploy = new DoubleSolenoid(2, 3);//claw pneumatic deployment
	
	DoubleSolenoid clamp = new DoubleSolenoid(0, 1);//claw clamping pneumatics
	
	Double intakedrive;//value to control intake wheels
	
	Encoder liftencoder = new Encoder(2, 3);//elevator encoder
	
	Encoder driveEncoder = new Encoder(0, 1);
	
	AHRS gyro = new AHRS(SerialPort.Port.kMXP);
	
	DigitalInput switch1 = new DigitalInput(4);
	
	DigitalInput switch2 = new DigitalInput(5);
	
	DigitalInput switch3 = new DigitalInput(6);
	
	double auto, lspeed;
	
	public Robot() {
	}

	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		
		gyro.reset();
		driveEncoder.reset();
		driveEncoder.setDistancePerPulse(0.0097714);
		comp.setClosedLoopControl(false);
		comp.start();
		clamp.set(Value.kReverse);
		deploy.set(Value.kReverse);
		theight = 300000;
	}

	
	@Override
	public void autonomous() {
		
		
		if (isAutonomous()) {
		autoSelections.autoSelections(switch1, switch2, switch3);
		}
	
		
		while(DriverStation.getInstance().getGameSpecificMessage().length()<1) {//wait until we get the signal
			Timer.delay(0.05);
		}
		
		if(DriverStation.getInstance().getGameSpecificMessage().substring(0, 1).equals("L")) {//read it 
			swtch = false;
		}
		else {
			swtch = true;
		}
		if(DriverStation.getInstance().getGameSpecificMessage().substring(1, 2).equals("L")) {
			scale = false;
		}
		else {
			scale = true;
		}
		gyro.reset();
		driveEncoder.reset();
		if((switch1.get() == false) && (switch2.get() == true) && (switch3.get() == true)) {
		System.out.println("you are currently just driving forward");
		//Drive forward mode
		while((driveEncoder.getDistance()>-120) && isAutonomous()) {
			chassis.arcadeDrive(0.5, gyro.getAngle() * 0.06);
			Timer.delay(0.05);
		}
		chassis.arcadeDrive(0, 0);
		}
		//2 in the switch code
		if((switch1.get() == true) && (switch2.get() == false) && (switch3.get() == true)) { //table thing
			System.out.println("You are currently doing the switch");
		if(swtch) {
		deploy.set(Value.kReverse);
		clamp.set(Value.kForward);
		while((driveEncoder.getDistance()>-10) && isAutonomous()) {
			chassis.arcadeDrive(0.6, gyro.getAngle()*.1);
		}
		while((gyro.getAngle()<15) && isAutonomous()) {
			chassis.arcadeDrive(0, -0.6);
		}
		driveEncoder.reset();
		while((driveEncoder.getDistance()>-80) && isAutonomous()) {
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
		while((gyro.getAngle()>-65) && isAutonomous()) {
			chassis.arcadeDrive(0, 0.6);
			deploy.set(Value.kForward);
			intake1.set(1);
			intake2.set(-1);
			lift1.set(.2);
			lift2.set(.2);
		}
		/*
		while(liftencoder.getRaw()>200) { //second cube in switch, doesn't work yet
			chassis.arcadeDrive(0, 0);
			lift1.set(-1);
			lift2.set(-1);
		}
			lift1.set(0);
			lift2.set(0);
		driveEncoder.reset();
		while(driveEncoder.getDistance()>-22) {
			chassis.arcadeDrive(0.5, (gyro.getAngle()+70)*.06);
			intake1.set(-1);
			intake2.set(1);
		}
		driveEncoder.reset();
		deploy.set(Value.kReverse);
		while(driveEncoder.getDistance()<7) {
			chassis.arcadeDrive(-0.5, (gyro.getAngle()+70)*.1);
			intake1.set(-1);
			intake2.set(1);
		}
		while(liftencoder.getRaw()<68000) {
			lift1.set(1);
			lift2.set(1);
		}
		lift1.set(0);
		lift2.set(0);
		while(gyro.getAngle()<12) {
			chassis.arcadeDrive(0, -0.6);
			intake1.set(0);
			intake2.set(0);
		}
		driveEncoder.reset();
		while(driveEncoder.getDistance()>-3) {
			chassis.arcadeDrive(0.5, (gyro.getAngle()-20)*.1);
			intake1.set(1);
			intake2.set(-1);
		}*/
		chassis.arcadeDrive(0, 0);
		lift1.set(0);
		lift2.set(0);
		intake1.set(0);
		intake2.set(0);
		}
		else if(!swtch) {
			clamp.set(Value.kForward);
			while((driveEncoder.getDistance()>-10) && isAutonomous()) {
				chassis.arcadeDrive(0.6, gyro.getAngle()*.1);
			}
			while((gyro.getAngle()>-30) && isAutonomous()) {
				chassis.arcadeDrive(0, 0.6);
			}
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-86) && isAutonomous()) {
				chassis.arcadeDrive(0.7, (gyro.getAngle()+38)*.1);
				if(liftencoder.getRaw()<68000){
						lift1.set(1);
						lift2.set(1);
			}
				else{
						lift1.set(0);
						lift2.set(0);
			}
			}
			while((gyro.getAngle()<0) && isAutonomous()) {
				chassis.arcadeDrive(0, -0.55);
				intake1.set(.4);
				intake2.set(-.4);
			}
			deploy.set(Value.kReverse);
			/*
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-4) {
				chassis.arcadeDrive(0.5, (gyro.getAngle()-68)*.07);
				intake1.set(-1);
				intake2.set(1);
			}
			driveEncoder.reset();
			clamp.set(Value.kForward);
			while(driveEncoder.getDistance()<4) {
				chassis.arcadeDrive(-0.5, (gyro.getAngle()-68)*.07);
				intake1.set(-1);
				intake2.set(1);
			}
			while(gyro.getAngle()>-20) {
				chassis.arcadeDrive(0, 0.7);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-4) {
				chassis.arcadeDrive(0.5, (gyro.getAngle()+25)*.08);
				intake1.set(1);
				intake2.set(-1);
				clamp.set(Value.kReverse);
			}*/
			chassis.arcadeDrive(0, 0);
			intake1.set(1);
			intake2.set(-1);
			Timer.delay(1);
			intake1.set(0);
			intake2.set(0);
			lift1.set(0);
			lift2.set(0);
		}
		}
		/*if(scale) {//2 scale in middle(whoops)
			while(driveEncoder.getDistance()>-10) {
				chassis.arcadeDrive(0.6, gyro.getAngle()*0.06);
			}
			while(gyro.getAngle()<38) {
				chassis.arcadeDrive(0, -0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-145) {
				chassis.arcadeDrive(0.75, (gyro.getAngle()-42)*.1);
			}
			while(gyro.getAngle()>-8) {
				chassis.arcadeDrive(0, 0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-90) {
				chassis.arcadeDrive(0.7, (gyro.getAngle()+15)*.08);
			}
			while(driveEncoder.getDistance()>-140) {
				chassis.arcadeDrive(0.55, (gyro.getAngle()+15)*.08);
				if(liftencoder.getRaw()<120000) {
					lift1.set(1);
					lift2.set(1);
				}
				else {
					lift1.set(0);
					lift2.set(0);
				}
			}
			chassis.arcadeDrive(0, 0);
			deploy.set(Value.kReverse);
			driveEncoder.reset();
			/*
			while(driveEncoder.getDistance()<25) {
				chassis.arcadeDrive(-0.65, (gyro.getAngle()+15)*.08);
			}
			while(gyro.getAngle()>-142) {
				chassis.arcadeDrive(0, 0.7);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-15) {
				chassis.arcadeDrive(0.6, (gyro.getAngle()+153)*.08);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()<15) {
				chassis.arcadeDrive(-0.6, (gyro.getAngle()+153)*.08);
			}
			while(gyro.getAngle()<-45) {
				chassis.arcadeDrive(0, -0.7);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-25) {
				chassis.arcadeDrive(0.6, (gyro.getAngle()+30)*.05);
			}
			chassis.arcadeDrive(0, 0);
		}
		else if(!scale) {
			while(driveEncoder.getDistance()>-10) {
				chassis.arcadeDrive(0.6, gyro.getAngle()*0.1);
			}
			while(gyro.getAngle()>-45) {
				chassis.arcadeDrive(0, 0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-160) {
				chassis.arcadeDrive(0.75, (gyro.getAngle()+53)*.08);
			}
			while(gyro.getAngle()<1) {
				chassis.arcadeDrive(0, -0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-70) {
				chassis.arcadeDrive(0.7, (gyro.getAngle()-10)*.06);
			}
			while(driveEncoder.getDistance()>-133) {
				chassis.arcadeDrive(0.55, (gyro.getAngle()-10)*.08);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()<13) {
				chassis.arcadeDrive(-0.55, (gyro.getAngle()-10)*.08);
			}
			while(gyro.getAngle()<140) {
				chassis.arcadeDrive(0, -0.7);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-18) {
				chassis.arcadeDrive(0.6, (gyro.getAngle()-150)*0.08);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()<18) {
				chassis.arcadeDrive(-0.6, (gyro.getAngle()-150)*0.08);
			}
			while(gyro.getAngle()>22) {
				chassis.arcadeDrive(0, 0.7);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-15) {
				chassis.arcadeDrive(0.6, (gyro.getAngle()-15)*.08);
			}
			chassis.arcadeDrive(0, 0);
		}*/
		if((switch1.get() == true) && (switch2.get() == true) && (switch3.get() == false)) {
			System.out.println("You are currently doing the scale");
		if(scale) {//two on the scale, starting right
			clamp.set(Value.kForward);
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-205) && isAutonomous()) {
				chassis.arcadeDrive(0.77, gyro.getAngle()*0.1);
				System.out.println("loop 1: "+liftencoder.getRaw());
			}
			while((driveEncoder.getDistance()>-276) && isAutonomous()) {
				chassis.arcadeDrive(0.5, gyro.getAngle()*0.1);
				System.out.println("loop 2: "+liftencoder.getRaw());
				if(liftencoder.getRaw()<260000) {
					lift1.set(1);
					lift2.set(1);
				}
				else {
					lift1.set(.2);
					lift2.set(.2);
				}
			}
			while((gyro.getAngle()>-45) && isAutonomous()) {
				chassis.arcadeDrive(0, 0.75);
				System.out.println("loop 3: "+liftencoder.getRaw());
				if(liftencoder.getRaw()<260000) {
					lift1.set(1);
					lift2.set(1);
				}
				else {
					lift1.set(.25);
					lift2.set(.25);
				}
			}
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-12) && isAutonomous()) {
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
			/*driveEncoder.reset();
			while((gyro.getAngle()>-135) && isAutonomous()) {
				chassis.arcadeDrive(0, 0.7);
				if(liftencoder.getRaw()>150) {
					lift1.set(-.4);
					lift2.set(-.4);
					intake1.set(1);
					intake2.set(-1);
				}
				else {
					lift1.set(0);
					lift2.set(0);
				}
			}
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-85) && isAutonomous()) {
				chassis.arcadeDrive(0.65, (gyro.getAngle()+142)*0.08);
				intake1.set(-1);
				intake2.set(1);
				if(liftencoder.getRaw()>150) {
					lift1.set(-.4);
					lift2.set(-.4);
				}
				else {
					lift1.set(0);
					lift2.set(0);
				}
			}
			driveEncoder.reset();
			intake1.set(0);
			intake2.set(0);
			while((driveEncoder.getDistance()<55) && isAutonomous()) {
				chassis.arcadeDrive(-0.6, (gyro.getAngle()+142)*0.08);
				if(driveEncoder.getDistance()<5) {
					intake1.set(-.4);
					intake2.set(-.4);
				}
				else {
					intake1.set(0);
					intake2.set(0);
				}
				if(liftencoder.getRaw()<68000) {
					lift1.set(1);
					lift2.set(1);
				}
				else {
					lift1.set(0);
					lift2.set(0);
				}
			}
			while((gyro.getAngle()<-50) && isAutonomous()) {
				chassis.arcadeDrive(0, -0.7);
				if(liftencoder.getRaw()<68000) {
					lift1.set(1);
					lift2.set(1);
				}
				else {
					lift1.set(0);
					lift2.set(0);
				}
			}
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-6) && isAutonomous()) {
				chassis.arcadeDrive(0.5, (gyro.getAngle()+50)*0.08);
				lift1.set(.25);
				lift2.set(.25);
			}*/
			chassis.arcadeDrive(0, 0);
			intake1.set(.8);
			intake2.set(-.8);
			Timer.delay(1);
			intake1.set(0);
			intake2.set(0);
			clamp.set(Value.kReverse);
			driveEncoder.reset();
			while(driveEncoder.getDistance()<12) {
				chassis.arcadeDrive(-.5, 0);
			}
			chassis.arcadeDrive(0,0);
			while((liftencoder.getDistance()>200) && isAutonomous()) {
				lift1.set(-.8);
				lift2.set(-.8);
			}
			lift1.set(0);
			lift2.set(0);
			while(gyro.getAngle()>-135) {
				chassis.arcadeDrive(0, 0.75);
			}
			chassis.arcadeDrive(0,0);
		}
		else if(!scale) {
			clamp.set(Value.kForward);
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-190) && isAutonomous()) {
				chassis.arcadeDrive(0.75, gyro.getAngle()*0.1);
			}
			while((driveEncoder.getDistance()>-215) && isAutonomous()) {
				chassis.arcadeDrive(0.6, gyro.getAngle()*0.1);
			}
			while((gyro.getAngle()>-80) && isAutonomous()) {
				chassis.arcadeDrive(0, 0.72);
			}
			driveEncoder.reset();
			while((driveEncoder.getDistance()>-208) && isAutonomous()) {
				chassis.arcadeDrive(0.7, (gyro.getAngle()+87)*0.08);
			}
			while((gyro.getAngle()<5) && isAutonomous()) {
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
			while((driveEncoder.getDistance()>-12) && isAutonomous()) {
				chassis.arcadeDrive(0.55, (gyro.getAngle()-20)*0.08);
				if(liftencoder.getRaw()<260000) {
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
			/*
			while(driveEncoder.getDistance()<35) {
				chassis.arcadeDrive(-0.5, (gyro.getAngle()-20)*0.08);
				lift1.set(0.25);
				lift2.set(0.25);
			}
			while(gyro.getAngle()<130) {
				chassis.arcadeDrive(0, -0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-15) {
				chassis.arcadeDrive(0.5, (gyro.getAngle()-135)*0.08);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()<15) {
				chassis.arcadeDrive(-0.5, (gyro.getAngle()-135)*0.08);
			}
			while(gyro.getAngle()>28) {
				chassis.arcadeDrive(0, 0.6);
			}
			driveEncoder.reset();
			while(driveEncoder.getDistance()>-35) {
				chassis.arcadeDrive(0.5, (gyro.getAngle()-20)*0.08);
			}*/
			intake1.set(.6);
			intake2.set(-.6);
			chassis.arcadeDrive(0, 0);
			Timer.delay(1);
			intake1.set(0);
			intake2.set(0);
			clamp.set(Value.kReverse);
			driveEncoder.reset();
			while(driveEncoder.getDistance()<15) {
				chassis.arcadeDrive(-.5, 0);
			}
			chassis.arcadeDrive(0,0);
			while((liftencoder.getDistance()>200) && isAutonomous()) {
				lift1.set(-.8);
				lift2.set(-.8);
			}
			lift1.set(0);
			lift2.set(0);
			while(gyro.getAngle()<135) {
				chassis.arcadeDrive(0, -0.75);
			}
			chassis.arcadeDrive(0,0);
		}
		}
	}
	@Override
	public void operatorControl() {
		gyro.reset();
		driveEncoder.reset();
		Timer.delay(0.005);
		while(isOperatorControl() & isEnabled()) {
			
			//System.out.println("lift: "+liftencoder.getRaw());
			
			chassis.arcadeDrive(xbox1.getRawAxis(1)*-1, xbox1.getRawAxis(4)*-1);//chassis
			//chassis.arcadeDrive(stick.getRawAxis(1)*-1, stick.getRawAxis
			//(0)*-1);//chassis
			
			if(!(((liftencoder.getRaw() < 160) & (xbox2.getRawAxis(1) > 0)) || ((liftencoder.getRaw() > theight) & (xbox2.getRawAxis(1) < 0)))) {
				if((liftencoder.getRaw() > 800) || (xbox2.getRawAxis(1) < 0)) {
				//	lspeed = Math.sin(liftencoder.getRaw()/228771);
					lift1.set(xbox2.getRawAxis(1)*-1);//elevator motors
					lift2.set(xbox2.getRawAxis(1)*-1);
				}
				else {
					lift1.set(xbox2.getRawAxis(1)*-.25);
					lift2.set(xbox2.getRawAxis(1)*-.25);
				}
			}
			else {
				lift1.set(0);
				lift2.set(0);
			}
			
			if(xbox2.getBButton()) {//claw clamping pneumatics
				deploy.set(Value.kForward);
			}
			else if(xbox2.getAButton()){
				deploy.set(Value.kReverse);
			}
			
			if(xbox2.getRawButton(6)) {//claw deployment pneumatics
				clamp.set(Value.kForward);
			}
			else if(xbox2.getRawButton(5)) {
				clamp.set(Value.kReverse);
			}
			
			intakedrive = xbox2.getRawAxis(2) - xbox2.getRawAxis(3);//intake claw wheels
			intake1.set(intakedrive * -1);
			intake2.set(intakedrive);
	
			if(xbox2.getYButton()) {//climber wench motor
				climber.set(-1);
			}
			else {
				climber.set(0);
			}
			
				arm.set(xbox2.getRawAxis(5)*-.6);

			//System.out.println(gyro.getAngle());
			System.out.println(driveEncoder.getRaw());
			
			Timer.delay(0.005);
		}
	}

	@Override
	public void test() {
	}
}
