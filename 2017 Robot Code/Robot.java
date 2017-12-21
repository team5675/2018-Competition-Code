package org.usfirst.frc.team5675.robot;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.vision.VisionRunner;
import edu.wpi.first.wpilibj.vision.VisionThread;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Parity;
import edu.wpi.first.wpilibj.SerialPort.StopBits;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.XboxController;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive class.
 */
public class Robot extends SampleRobot {
	
	
    RobotDrive robotDrive;
    Joystick stick;

    //vision
    private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private VisionThread visionThread;
	private double centerX = 0.0;
	private double centerX1 = 0.0;
	private double centerX2 = 0.0;
	private final Object imgLock = new Object();
	Ultrasonic rangefinder;
	AnalogInput range = new AnalogInput(3);
	boolean run = true;
	int timerR = 0;
	double tVal = 0;
	double dist;
	double distO;
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	double DriveG;
	Encoder roller = new Encoder(5, 6, false, Encoder.EncodingType.k4X);
	
	
    // Channels for the wheels
    final int frontLeftChannel	= 2;
    final int rearLeftChannel	= 3;
    final int frontRightChannel	= 1;
    final int rearRightChannel	= 0;
    
    // The channel on the driver station that the joystick is connected to
    final int joystickChannel	= 0;
    
    //misc
	XboxController xbox;
	XboxController xbox2;
	DigitalInput switch1 = new DigitalInput(2);
	DigitalInput switch2 = new DigitalInput(3);
	DigitalInput switch3 = new DigitalInput(4);
	
	double range1;
	double range2;
	double odistance, ndistance;
	Boolean turn;
	int count = 0;
	double angle1, angle2;
    
    //motor init
    //1 775pro (spark) (runs fuel collector) (counterclockwise default, button to switch)
	Spark fuelColMotor;
    //1 SIM (spark) (runs shooter) (button controlled)
	Spark shooterMotor;
    //1 Window Motor (runs shooter) (button controlled)
	Spark windowShooterMotor;
    //2 Servos (runs the gear mechanism) (opens/closes half turn)
	Servo gearServo1;
	Servo gearServo2;
	boolean gearServosOpen;
    //1 775pro (spark) (climbing mechanism)
	Spark climbMotor;

    
	@Override
	public void robotInit() {
		robotDrive.setSafetyEnabled(false);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    camera.setExposureManual(0);
	  //  camera.setExposureAuto();
	  //  camera.setWhiteBalanceAuto();
	   // camera.setExposureManual(0);
	   // camera.setWhiteBalanceManual(0);
	    //roborio-5675-frc.frc-robot.local
	    //dSen = new SerialPort(9600, SerialPort.Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
	    //initalizes the vision
	    visionThread = new VisionThread(camera, new Pipeline(), pipeline -> {
	    	//DriverStation.reportError(Double.toString(camera.getLastFrameTime()), true);
	    	if(pipeline.filterContoursOutput().isEmpty()) {
	    		synchronized (imgLock) {
	    		centerX = 160;
	    		}
	    	}
	        if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect r1 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            //System.out.println("Found " + r1.area() + " " + pipeline.filterContoursOutput().size() + " " + r1.x); //information\
	            if(pipeline.filterContoursOutput().size() >= 2) {
	            	Rect r2 = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
	            	synchronized(imgLock) {
	            		centerX1 = r1.x + (r1.width / 2);
	            		centerX2 = r2.x + (r2.width/2);
	            		centerX = (centerX1 + centerX2)/2;
	            	}
	            	DriverStation.reportWarning("Centerx: "+centerX, true);
	            } else {
    	            synchronized (imgLock) {
    	                //centerX = r1.x + (r1.width / 2);
    	            	centerX = 0;
    	            }
	            }
	        }
	        if(isAutonomous()){
	        	Timer.delay(.01);
	        }
	        else{
	            Timer.delay(0.1);
	        }
	        
	    	
	    });
	    //visionThread.setDaemon(true);
	    visionThread.start();
	    gyro.reset();
	    roller.reset();
	    roller.setDistancePerPulse(0.00690966126);
	    

	}
	
    public Robot() {
    	//motor define
    	gearServo1 = new Servo(7); //right side
		gearServo2 = new Servo(8); // left side
		fuelColMotor = new Spark(4);
		shooterMotor = new Spark(5);
		climbMotor = new Spark(9);
		windowShooterMotor = new Spark(6);
		
		rangefinder = new Ultrasonic(0,1);
		rangefinder.setAutomaticMode(true);
		xbox = new XboxController(0);
		xbox2 = new XboxController(1);
		gearServosOpen = false;
		
    	//defines the robot drive and modifies its operation.
        robotDrive = new RobotDrive(frontLeftChannel, rearLeftChannel, frontRightChannel, rearRightChannel);
    	robotDrive.setInvertedMotor(MotorType.kFrontLeft, true);	// invert the left side motors
    	robotDrive.setInvertedMotor(MotorType.kRearLeft, true);	
    	//robotDrive.setInvertedMotor(MotorType.kFrontRight, true);// you may need to change or remove this to match your robot
        robotDrive.setExpiration(0.1);

        //stick = new Joystick(joystickChannel)
    }
        
    //defines robot operations during autonomous mode
    public void autonomous(){
    	DriverStation.reportWarning("started"+switch1.get()+switch2.get()+switch3.get(), true);
    	//Center peg
    	if(switch1.get() == true && switch2.get() == false && switch3.get() == true){
    		gyro.reset();
    		synchronized (imgLock) {
        		centerX = this.centerX;
        		//System.out.println(centerX);
        	}
        	double drive = centerX-160;
        	while(rangefinder.getRangeInches()>30 && isAutonomous()) {
        		//System.out.println(centerX-160);
        		synchronized (imgLock) {
            		centerX = this.centerX;
            		//System.out.println(centerX);
            	}
        		DriveG = 0 - gyro.getAngle();
        		drive = centerX-160;
        		System.out.println(drive);
        		robotDrive.mecanumDrive_Cartesian(drive*0.005, -.3, DriveG*0.01+.02, 0);
        		Timer.delay(0.001);
        	}
        	roller.reset();
        	while(roller.getDistance() < 6.5 && isAutonomous()){
        	robotDrive.mecanumDrive_Cartesian(-.5, 0, 0, 0);
        	}
        	//Timer.delay(.6);
        	while(rangefinder.getRangeInches() > 12 && isAutonomous()) {
        		robotDrive.mecanumDrive_Cartesian(0, -.3, 0.015, 0);
        		System.out.println(rangefinder.getRangeInches());
        		Timer.delay(0.001);
        	}
        	System.out.println(rangefinder.getRangeInches());
        	if(isAutonomous()){
    	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	gearServo1.setAngle(30);
    	gearServo2.setAngle(130);
        robotDrive.mecanumDrive_Cartesian(0, .3, 0, 0);
        Timer.delay(1.5);
    	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	gearServo1.setAngle(130);
    	gearServo2.setAngle(30);
        	}
    	}
    	//Right peg
    	else if(switch1.get() ==true && switch2.get() == true && switch3.get() == false){
    	robotDrive.mecanumDrive_Cartesian(0, -.45, .015, 0);
    	Timer.delay(1.35); //2.8
    	gyro.reset();
    	//while(gyro.getAngle() > -50){
    	robotDrive.mecanumDrive_Cartesian(0, 0, -0.31, 0);
    	//}
    	Timer.delay(1.3); //1.75
    	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	synchronized (imgLock) {
    		centerX = this.centerX;
    		//System.out.println(centerX);
    	}
    	double drive = centerX-160;
    	while(rangefinder.getRangeInches()>32 && isAutonomous()) {
    		//System.out.println(centerX-160);
    		synchronized (imgLock) {
        		centerX = this.centerX;
        		//System.out.println(centerX);
        		DriverStation.reportWarning("first loop", true);
        	}
    		DriveG = 60 - gyro.getAngle();
    	    drive = centerX-160;
    		System.out.println(drive);
    		robotDrive.mecanumDrive_Cartesian(drive*0.008, -.28, 0.025, 0);
    		Timer.delay(0.001);
    	}
    	roller.reset();
    	while(roller.getDistance() < 9){
    	robotDrive.mecanumDrive_Cartesian(-.5, 0, 0, 0);
    	gearServo1.setAngle(130);
    	gearServo2.setAngle(30);
    	}
    	//Timer.delay(.5);
    	while(rangefinder.getRangeInches() > 12 && isAutonomous()) {
        	gearServo1.setAngle(130);
        	gearServo2.setAngle(30);
    		robotDrive.mecanumDrive_Cartesian(0, -.3, 0.015, 0);
    		System.out.println(rangefinder.getRangeInches());
    		DriverStation.reportWarning("Second loop", true);
    		Timer.delay(.001);
    	}
    	System.out.println(rangefinder.getRangeInches());
    	if(isAutonomous()){
	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
	gearServo1.setAngle(30);
	gearServo2.setAngle(130);
    robotDrive.mecanumDrive_Cartesian(0, .3, 0, 0);
    Timer.delay(1.5);
	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
	gearServo1.setAngle(130);
	gearServo2.setAngle(30);
    	}
    	}
    	//Left peg
    	else if(switch1.get() == false && switch2.get() == true && switch3.get() == true){
    		robotDrive.mecanumDrive_Cartesian(-.3, -.3, 0, 0);
    		Timer.delay(3);
    		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    		synchronized (imgLock) {
        		centerX = this.centerX;
        		//System.out.println(centerX);
        	}
        	double drive = centerX-160;
        	while(rangefinder.getRangeInches()>30 && isAutonomous()) {
        		//System.out.println(centerX-160);
        		synchronized (imgLock) {
            		centerX = this.centerX;
            		//System.out.println(centerX);
            	}
        	    drive = centerX-160;
        		System.out.println(drive);
        		robotDrive.mecanumDrive_Cartesian(drive*0.009, -.30, 0, 0);
        		Timer.delay(0.001);
        	}
        	robotDrive.mecanumDrive_Cartesian(-.5, 0, 0, 0);
        	Timer.delay(.72);
        	while(rangefinder.getRangeInches() > 12 && isAutonomous()) {
        		robotDrive.mecanumDrive_Cartesian(0, -.3, 0.015, 0);
        		System.out.println(rangefinder.getRangeInches());
        		Timer.delay(0.001);
        	}
        	System.out.println(rangefinder.getRangeInches());
        	if(isAutonomous()){
    	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	gearServo1.setAngle(30);
    	gearServo2.setAngle(130);
        robotDrive.mecanumDrive_Cartesian(0, .3, 0, 0);
        Timer.delay(1.5);
    	robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	gearServo1.setAngle(130);
    	gearServo2.setAngle(30);
        	}
    	}
    	//Drive forward
    	else if(switch1.get() == false && switch2.get() == false && switch3.get() == false){
    		robotDrive.mecanumDrive_Cartesian(0, -.5, 0.02, 0);
    		Timer.delay(2.5);
    		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	}
    	else if(switch1.get() == false && switch2.get() == false && switch3.get() == true){
    		fuelColMotor.set(1);
    		shooterMotor.set(1);
    		Timer.delay(10);
    		fuelColMotor.set(0);
    		shooterMotor.set(0);
    		robotDrive.mecanumDrive_Cartesian(.8, -.05, 0.03, 0);
    		Timer.delay(3);
    		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    	}
    	//Do nothing
    }
    	
    
    /**
     * Runs the motors with Mecanum drive.
     */
    public void operatorControl() {
        while (isOperatorControl() && isEnabled()) {
        	//DriverStation.reportWarning(dSen.readString(), true);
        	//System.out.println(rangefinder.getRangeInches());
        	//System.out.println(range.getVoltage());
        	
        	//VISION TEST FOR TELEOP***
        	
        	
        	/*if(xbox.getYButton()){
        		robotDrive.mecanumDrive_Cartesian(0, 0, -.4, 0);
        		Timer.delay(0.2);
        		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        		Timer.delay(.5);
        		for(int i=0; i<=999; i++){
            		if(i==0){
            			 range1 = 0;
            		}
            		range1 = range1 + rangefinder.getRangeInches();
            		if(i==999){
            			range1 = range1/1000;
            		}
            	}
        		robotDrive.mecanumDrive_Cartesian(0, 0, .4, 0);
        		Timer.delay(0.4);
        		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        		Timer.delay(.5);
        		for(int i=0; i<=999; i++){
            		if(i==0){
            			 range2 = 0;
            		}
            		range2 = range2 + rangefinder.getRangeInches();
            		if(i==999){
            			range2 = range2/1000;
            		}
            	}
        		//alignment
        		if(range1<range2){
        			odistance = 1100;
        			ndistance = 1000;
        			turn = true;
        			count = 0;
        			while(turn){
        				System.out.println("turning left");
        				robotDrive.mecanumDrive_Cartesian(0, 0, -0.22, 0);
        				odistance = ndistance;
        				for(int i=0; i<=999; i++){
                    		if(i==0){
                    			 ndistance = 0;
                    		}
                    		
                    		
                    		ndistance += rangefinder.getRangeInches();
                    		
                    		System.out.println(ndistance/i);
                    		if(i==999){
                    			ndistance = ndistance/1000;
                    			
                    			
                    		}
                    	}
        				if(ndistance>odistance){
                			count++;
                		} else {
                			count++;
                		}
        				if(ndistance<odistance){
        					//count=0;
        				}
                		if(count >= 3){
                			turn = false;
                		}
        			}
        		}
        		else{
        			odistance = 1100;
        			ndistance = 1000;
        			turn = true;
        			count = 0;
        			while(turn){
        				System.out.println("turning right");
        				robotDrive.mecanumDrive_Cartesian(0, 0, 0.2, 0);
        				odistance = ndistance;
        				int tempCount = 0;
        				for(int i=0; i<=999; i++){
                    		if(i==0){
                    			 ndistance = 0;
                    		}
                        		ndistance += rangefinder.getRangeInches();
                        		System.out.println(ndistance);
                    		if(i==999){
                    			ndistance = ndistance/1000;
                    			
                    		}
                    	}
                		if(ndistance>odistance){
                			count++;
                		}
                		if(ndistance<odistance){
                			//count = 0;
                		}
                		if(count>=3){
                			turn = false;
                		}
        			}
        		}
        		
        		robotDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        		synchronized (imgLock) {
            		centerX = this.centerX;
            	}
        		Timer.delay(.5);
        	double drive = centerX - (IMG_WIDTH / 2);
        	while((drive > 10 || drive <-10) && !xbox.getYButton()) {
        		//System.out.println(centerX-160);
        		synchronized (imgLock) {
            		centerX = this.centerX;
            		//System.out.println(centerX);
            	}
        		//System.out.println(drive);
        	//drive = centerX - (IMG_WIDTH / 2);
        	/*if((drive*.005)<.40 && drive>0){
        		drive = .40/.005;
        	}
        	if(drive < 60 && drive >= 10) {
        	drive = 60;
        	}
        	if(drive > -60 && drive <= -10) {
            	drive = -60;
            	}
            robotDrive.mecanumDrive_Cartesian(drive*.005, 0, 0, 0);
        	}
        	while(rangefinder.getRangeInches() > 25) {
        	robotDrive.mecanumDrive_Cartesian(0,-.4,0,0);
        	}
        	}	*/
        	
        	
        	// Use the joystick X axis for lateral movement, Y axis for forward movement, and Z axis for rotation.
        	// This sample does not use field-oriented drive, so the gyro input is set to zero.
            robotDrive.mecanumDrive_Cartesian(xbox.getRawAxis(0), xbox.getRawAxis(1), xbox.getRawAxis(4), 0);
            
            System.out.println(""+rangefinder.getRangeInches());
            
            
            //servo
            if (xbox2.getBumper(Hand.kRight))
			{
            	if(gearServo1.getAngle()>30){
            		angle1 = gearServo1.getAngle();
			gearServo1.setAngle(angle1-1);
            	}
            	if(gearServo2.getAngle()<130){
            		angle2 = gearServo2.getAngle();
			gearServo2.setAngle(angle2+1);
            	}
			DriverStation.reportWarning("open", true);
			}
			else if (xbox2.getBumper(Hand.kLeft))
			{
				if(gearServo1.getAngle()<130){
            		angle1 = gearServo1.getAngle();
			gearServo1.setAngle(angle1+1);
            	}
				if(gearServo2.getAngle()>30){
				gearServo2.setAngle(gearServo2.getAngle()-1);
				}
				DriverStation.reportWarning("close", true);
			}
            
            //fuel collection
            /*if(xbox.getBButton() == true) {
            	fuelColMotor.set(-.5);
            }*/
            
            if(xbox2.getXButton() == true) {
            	fuelColMotor.set(1);
            }
            
            if(xbox2.getAButton() == true) {
            	fuelColMotor.set(0);
            }
            
            //shooting ball
            if(xbox2.getRawAxis(3) == 1) {
            	shooterMotor.set(1);
            } else {
            	shooterMotor.set(0);
            }
            
            
            //climbing mechanism
            if(xbox2.getYButton() == true) {
            	climbMotor.set(1);
            } else {
            	climbMotor.set(0);
            }
            
            
            //delay
            Timer.delay(0.005);	// wait 5ms to avoid hogging CPU cycles
        }
    }
    
}
