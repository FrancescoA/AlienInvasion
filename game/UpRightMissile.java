package game;

import java.awt.Dimension;

import util.Location;
import util.Pixmap;
import util.Vector;
import view.Canvas;

public class UpRightMissile extends Missile {
	

	public UpRightMissile(Pixmap image, Location center, Dimension size,
			Canvas canvas) {
		super(image, center, size, canvas);
	}
	
	public void update (double elapsedTime, Dimension bounds){
		
		Vector UPRIGHT = UP_VELOCITY.returnDirectionSum(RIGHT_VELOCITY);
		translate(UPRIGHT);
	}

}
