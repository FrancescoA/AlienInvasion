package game;


import java.awt.Dimension;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;


public class AlienMissile extends Sprite{
	
	private static final int DAMAGE = 5;
	protected static final int MOVE_SPEED = 15;
	protected static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
	private static final Dimension DEFAULT_SIZE = new Dimension(25,5);
	
	private Canvas myCanvas;
	
	public AlienMissile(Pixmap image, Location center, Dimension size, Canvas canvas) {
		super(image, center, size);
		myCanvas = canvas;
	}
	
	public void update (double elapsedTime, Dimension bounds){
		translate(LEFT_VELOCITY);
	}

	
}
