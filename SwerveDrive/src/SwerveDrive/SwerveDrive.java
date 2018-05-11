package SwerveDrive;



public class SwerveDrive {

	public double L = 33;//length of axle distances
	
	public double W = 28;//width of axle distances
	
	
	public void drive (double x1, double y1, double rotation, double theta) {
		double r = Math.sqrt((L * L) + (W * W));
		y1 *= -1; //if axis is funky
		
		
		double foward = y1 * Math.cos(theta) + x1 * Math.sin(theta); // field-centric??
		
		double strafe = -y1 * Math.sin(theta) + x1 * Math.cos(theta);
		
		
		double a = strafe - rotation * (L / r); //quick mafs for figuring speed and angles later
		
		double b = strafe + rotation * (L / r);
		
		double c = foward - rotation * (W / r);
		
		double d = foward + rotation * (W / r);
		
		
		double backRightSpeed = 0; //calculating speed
				
		double backLeftSpeed = 0;
				
		double frontRightSpeed = 0;
				
		double frontLeftSpeed = 0;
		
		
		if (a < 0) {
			
			
			backRightSpeed = Math.sqrt ((a * a ) + (d * d)) * -1;
			
			backLeftSpeed = Math.sqrt ((a * a ) + (c * c)) * -1;
			
		}
		
		if (b < 0) {
			
			
			frontRightSpeed = Math.sqrt ((b * b ) + (d * d))  * -1;
					
			frontLeftSpeed = Math.sqrt ((b * b ) + (c * c)) * -1;
			
		}
		
		if (c < 0) {
			
			
			backLeftSpeed = Math.sqrt ((a * a ) + (c * c)) * -1;
			
			frontLeftSpeed = Math.sqrt ((b * b ) + (c * c)) * -1;
		}
		
		
		if (d < 0) {
			
			frontRightSpeed = Math.sqrt ((b * b ) + (d * d))  * -1;
			
			backRightSpeed = Math.sqrt ((a * a ) + (d * d)) * -1;
			
		}
		
		
		else {
			
			backRightSpeed = Math.sqrt ((a * a ) + (d * d));
			
			backLeftSpeed = Math.sqrt ((a * a ) + (c * c));
					
			frontRightSpeed = Math.sqrt ((b * b ) + (d * d));
					
			frontLeftSpeed = Math.sqrt ((b * b ) + (c * c));
			
		}
		
		
		double backRightAngle = Math.atan2 (a, d) / Math.PI; //and the angles
		
		double backLeftAngle = Math.atan2 (a, c) / Math.PI;
		
		double frontRightAngle = Math.atan2 (b, d) / Math.PI;
		
		double frontLeftAngle = Math.atan2 (b, c) / Math.PI;
		
		
		backRight.drive(backRightSpeed, backRightAngle); //just using a class for this stuff
		
		backLeft.drive(backLeftSpeed, backLeftAngle);
		
		frontRight.drive(frontRightSpeed, frontRightAngle);
		
		frontLeft.drive(frontLeftSpeed, frontLeftAngle);
	
	}
		
		private WheelDrive backRight;
		
		private WheelDrive backLeft;
		
		private WheelDrive frontRight;
		
		private WheelDrive frontLeft;
		
		
		public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft ) {
			
			
			this.backRight = backRight;
			
			this.backLeft = backLeft;
			
			this.frontRight = frontRight;
			
			this.frontLeft = frontLeft;
			
		}
		}

	
	

