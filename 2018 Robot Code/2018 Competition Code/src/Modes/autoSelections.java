package Modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DigitalInput;
import Modes.autoExecutions;

public class autoSelections {
	
	
	static String gameData1 = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
	
	static String gameData2 = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2);

	
 public static void autoSelection(DigitalInput switch1, DigitalInput switch2, DigitalInput switch3) {
		
	 
		if ((switch1.get() == false) && (switch2.get() == true) && (switch3.get() == true)){//1st toggle is on, rest are not
			
			System.out.println("Left Side Smart Is a Go");
			
			if (gameData1.equals("L") && gameData2.equals("L")) {
				
				autoExecutions.leftSwitch(true);
				
			}
			
			if (gameData1.equals("R") && gameData2.equals("L")) {
				
				autoExecutions.leftScale(true);
			}
			
			if (gameData1.equals("L") && gameData2.equals("R")) {
				
				autoExecutions.leftSwitch(true);
			}
			
			if (gameData1.equals("R") && gameData2.equals("R")) {
				
				autoExecutions.driveForward(true);
			}
			
		}
		
		if ((switch1.get() == true) && (switch2.get() == false) && (switch3.get() == true)) { //center switch
			
			System.out.println("Center Switch is Trucking!");
			
			
			if((gameData1.equals("L")) && (gameData2.equals("L"))) {
			
				autoExecutions.centerSwitchLeft(true);
			}
			
			if((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecutions.centerSwitchRight(true);
			}
		}
		
		if ((switch1.get() == true) && (switch2.get() == true) && (switch3.get() == false)) { //right side smart
			
			System.out.println("Right Side Smart is Going!");
			
			if ((gameData1.equals("R")) && (gameData2.equals("R"))) {
			
			
				autoExecutions.rightSwitch(true);
				
			}
			
			if ((gameData1.equals("L")) && (gameData2.equals("R"))) {
				
				autoExecutions.rightScale(true);
			}
			
			if ((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecutions.rightSwitch(true);
				
			}
			
			if ((gameData1.equals("L")) && (gameData2.equals("L"))) {
				
				autoExecutions.driveForward(true);
				
			}
		}
		
		
		if ((switch1.get() == true) && (switch2.get() == false) && (switch3.get() == false)){//right side start & cross
			
			System.out.println("Right Side Plus Crossing is Going");
			
			if (((gameData1.equals("L")) || (gameData1.equals("R"))) && (gameData2.equals("L"))) {
				
				autoExecutions.leftCross(true);
			}
			
			if ((gameData1.equals("L")) && (gameData2.equals("R"))) {
				
				autoExecutions.rightScale(true);
			}
		}
		
		if ((switch1.get() == false) && (switch2.get() == false) && (switch3.get() == true)){//left side start & cross
			
			System.out.println("Left Side Plus Crossing is Going");
			
			if ((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecutions.leftScale(true);
			}
			 
			if ((gameData1.equals("R")) && (gameData2.equals("R"))) {
				
				autoExecutions.rightCross(true);
			}
		}
		
		if ((switch1.get() == false) && (switch2.get() == false) && (switch3.get() == false)) {// right scale and halfway cross
			
			System.out.println("Right side scale plus halfway is on the way");
			
			if ((gameData1.equals("R")) && (gameData2.equals("R"))) {
				
				autoExecutions.rightScale(true);
				
			}
			
			if ((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecutions.halfLeftScale(true);
				
			}
		}
		
		if ((switch1.get() == true) && (switch2.get() == true) && (switch3.get() == true)) {// left scale and halfway cross
			
			System.out.println("Left side scale plus halfway is on the way");
			
			if ((gameData1.equals("R")) && (gameData2.equals("L"))) {
				
				autoExecutions.leftScale(true);
				
			}
			
			if ((gameData1.equals("R")) && (gameData2.equals("R"))) {
				
				autoExecutions.halfRightScale(true);
				
			}
		}
		
		else {
			
			System.out.println("Just Driving Foward");
			
			autoExecutions.driveForward(true);
			
		}
	}
}
