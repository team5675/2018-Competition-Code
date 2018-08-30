package Autos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DigitalInput;
import Autos.autoSelections;
import Autos.autoExecution;

public class autoSelections{
	
	
	static String gameData1 = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
	
	static String gameData2 = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2);

	
 public static void autoSelections(DigitalInput switch1, DigitalInput switch2, DigitalInput switch3) {
		
	 
		if ((switch1.get() == false) && (switch2.get() == true) && (switch3.get() == true)){//1st toggle is on, rest are not
			
			System.out.println("Left Side Smart Is a Go");
			
			if (gameData1.equals("L") && gameData2.equals("L")) {
				
				autoExecution.leftSwitch(true);
				
			}
			
			if (gameData1.equals("R") && gameData2.equals("L")) {
				
				autoExecution.rightScale(true);
			}
			
			if (gameData1.equals("L") && gameData2.equals("R")) {
				
				autoExecution.leftSwitch(true);
			}
			
			if (gameData1.equals("R") && gameData2.equals("R")) {
				
				autoExecution.driveForward(true);
			}
			
			
		}
		
		if ((switch1.get() == true) && (switch2.get() == false) && (switch3.get() == true)) {
			
			System.out.println("Center Switch is Trucking!");
			
			
			if((gameData1.equals("L")) && ((gameData2.equals("L")) || (gameData2.equals("R")))) {
			
				autoExecution.centerSwitchLeft(true);
			}
			
			if((gameData1.equals("R")) && ((gameData2.equals("L")) || (gameData2.equals("R")))) {
				
				autoExecution.centerSwitchRight(true);
			}
		}
		
		if ((switch1.get() == true) && (switch2.get() == true) && (switch3.get() == false)) {
			
			System.out.println("Right Side Smart is Going!");
			
			if ((gameData1.equals("R")) && (gameData2.equals("R"))) {
			
			
				autoExecution.rightSwitch(true);
				
			}
			
			if ((gameData1.equals("L")) && (gameData2.equals("R"))) {
				
				autoExecution.rightScale(true);
			}
			
			if ((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecution.rightSwitch(true);
				
			}
			
			if ((gameData1.equals("L")) && (gameData2.equals("L"))) {
				
				autoExecution.driveForward(true);
				
			}
		}
		
		
		if ((switch1.get() == true) && (switch2.get() == false) && (switch3.get() == false)){//right side start & cross
			
			System.out.println("Right Side Plus Crossing is Going");
			
			if (((gameData1.equals("L")) || (gameData1.equals("R"))) && (gameData2.equals("L"))) {
				
				autoExecution.leftCross(true);
			}
			
			if (((gameData1.equals("L")) || (gameData1.equals("R"))) && (gameData2.equals("R"))) {
				
				autoExecution.rightScale(true);
			}
		}
		
		if ((switch1.get() == false) && (switch2.get() == false) && (switch3.get() == true)){//left side start & cross
			
			System.out.println("Left Side Plus Crossing is Going");
			
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("L"))) {
				
				autoExecution.leftScale(true);
			}
			 
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("R"))) {
				
				autoExecution.rightCross(true);
			}
		}
		
		if ((switch1.get() == false) && (switch2.get() == false) && (switch3.get() == false)) {// right scale and halfway cross
			
			System.out.println("Right side scale plus halfway is on the way");
			
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("R"))) {
				
				autoExecution.rightScale(true);
				
			}
			
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("L"))) {
				
				autoExecution.halfLeftScale(true);
				
			}
		}
		
		if ((switch1.get() == true) && (switch2.get() == true) && (switch3.get() == true)) {// left scale and halfway cross
			
			System.out.println("Left side scale plus halfway is on the way");
			
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("L"))) {
				
				autoExecution.leftScale(true);
				
			}
			
			if (((gameData1.equals("R")) || (gameData1.equals("L"))) && (gameData2.equals("R"))) {
				
				autoExecution.halfRightScale(true);
				
			}
		}
		
		else {
			
			System.out.println("Just Driving Foward");
			
			autoExecution.driveForward(true);
		}
	}
}
