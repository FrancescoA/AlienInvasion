package game;

import java.awt.Dimension;

import util.Location;
import util.Pixmap;
import util.Vector;
import view.Canvas;

public class DownRightMissile extends Missile{

	public DownRightMissile(Pixmap image, Location center, Dimension size,
			Canvas canvas) {
		super(image, center, size, canvas);
		
	}
	
	public void update (double elapsedTime, Dimension bounds){
	    	
		Vector DOWNRIGHT = DOWN_VELOCITY.returnDirectionSum(RIGHT_VELOCITY);
		translate(DOWNRIGHT);
	}
	
	

}
