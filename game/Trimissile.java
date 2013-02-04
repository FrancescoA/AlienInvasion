package game;

import java.awt.Dimension;
import java.awt.Graphics2D;

import util.Location;
import util.Pixmap;
import view.Canvas;

public class Trimissile extends Missile{
	
	
	
	Missile myUp;
	Missile myDown;
	
	public Trimissile(Pixmap image, Location center, Dimension size,
			Canvas canvas) {
		super(image, center, size, canvas);
		
		myUp = new UpRightMissile(image, center, size, canvas);
		
		myDown = new DownRightMissile(image, center, size, canvas);
	}
	
	public void update (double elapsedTime, Dimension bounds){
	    	translate(RIGHT_VELOCITY);
	}
	
	

}
