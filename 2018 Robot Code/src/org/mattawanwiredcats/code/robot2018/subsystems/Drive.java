package org.mattawanwiredcats.code.robot2018.subsystems;

import edu.wpi.first.wpilibj.Spark;
import org.mattawanwiredcats.code.robot2018.Controls;

public class Drive {
	static Spark leftSpark = new Spark(0);
	static Spark rightSpark = new Spark(1);
	public static void operatorLoop() {
		leftSpark.set(Controls.getLeftSide());
		rightSpark.set(Controls.getRightSide());
	}
}