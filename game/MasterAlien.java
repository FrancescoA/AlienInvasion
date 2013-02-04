package game;

import java.awt.Dimension;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;
import view.Canvas;

public class MasterAlien extends Alien{
	
	public static final Dimension DEFAULT_SIZE = new Dimension(48,48);
	public static final int DEFAULT_OFFSET = 50;
	public static final int DEFAULT_DAMAGE = - 15;
	public static final int DEFAULT_POINTS = 10;
	
	private static final int MOVE_SPEED = 8;
	private static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);

	protected int myHealth = 2;

	private Canvas myCanvas;
	
	
	public MasterAlien(Pixmap image, Location center, Dimension size,
			Canvas canvas) {
		super(image, center, size, canvas);
		
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
