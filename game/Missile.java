package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;



public class Missile extends Sprite{
	
	
	
	public static final Dimension DEFAULT_SIZE = new Dimension(25,11);
	public static final Dimension TRI_SIZE = new Dimension(14,13);

	public static final int DEFAULT_OFFSET = 50;
	
	private static final int MOVE_UP = KeyEvent.VK_UP;
	private static final int MOVE_LEFT = KeyEvent.VK_LEFT;
	private static final int MOVE_DOWN = KeyEvent.VK_DOWN;
	private static final int MOVE_RIGHT = KeyEvent.VK_RIGHT;
	
	
	
	
	protected static final int MOVE_SPEED = 15;
	protected static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
	protected static final Vector RIGHT_VELOCITY = new Vector(RIGHT_DIRECTION, MOVE_SPEED);
	protected static final Vector UP_VELOCITY = new Vector(UP_DIRECTION, MOVE_SPEED);
	protected static final Vector DOWN_VELOCITY = new Vector(DOWN_DIRECTION, MOVE_SPEED);

    private Canvas myCanvas;
    
    public Missile (Pixmap image, Location center, Dimension size, Canvas canvas){
    	
    	super(image, center, size);
    	myCanvas = canvas;
    }
    
    public void update (double elapsedTime, Dimension bounds){
    	
    	
    	translate(RIGHT_VELOCITY);
    }
    

	
	

}
