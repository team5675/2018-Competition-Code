package Autos;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DigitalInput;
import Autos.autoSelections;
import Autos.autoExecution;

public class autoSelections{

	public static double autoMode = 0;
	
	static String gameData1 = DriverStation.getInstance().getGameSpecificMessage().substring(0, 1);
	
	static String gameData2 = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2);

	
 public static void autoSelections(DigitalInput switch1, DigitalInput switch2, DigitalInput switch3) {
		
	 
		if ((switch1.get() == false) && (switch2.get() == true) && (switch3.get() == true)){//1st toggle is on, rest are not
			
			System.out.println("Left Side Smart Is a Go");
			
			if (gameData1.equals("L") && gameData2.equals("L")) {
				
				autoExecution.rightScale(true);
				
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
		
	}
}
