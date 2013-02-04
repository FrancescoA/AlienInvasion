package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;

public class Alien extends Sprite{
	
	public static final Dimension DEFAULT_SIZE = new Dimension(45,45);
	public static final int DEFAULT_OFFSET = 50;
	public static final int DEFAULT_DAMAGE = - 10;
	public static final int DEFAULT_POINTS = 5;

		
	private static final int MOVE_SPEED = 4;
	
	protected int myHealth = 1;
	protected static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
    protected static final Vector RIGHT_VELOCITY = new Vector(RIGHT_DIRECTION, MOVE_SPEED);
	
    private Canvas myCanvas;
    
    public Alien(Pixmap image, Location center, Dimension size, Canvas canvas){
    	super(image,center,size);
    	myCanvas = canvas;
    }
    
    public void update(double elapsedTime, Dimension bounds){
    	
    	translate(LEFT_VELOCITY);
    }
    
    public int getDamage(){
    	return DEFAULT_DAMAGE;
    }
    
    public int getPoints(){
    	return DEFAULT_POINTS;
    }
    
    public int getHealth(){
    	return myHealth;
    }
  
    public void updateHealth(int a){
    	myHealth +=a;
    }
	
	
}
