package game;


import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.ValueText;
import util.Vector;
import view.Canvas;


public class Ship extends Sprite{
	
	
	private static final Pixmap MISSILE_IMAGE = new Pixmap("missile.gif");
	protected static final Pixmap TRIMISSILE_IMAGE = new Pixmap("triMissile.png");
	private static final Pixmap RAPIDMISSILE_IMAGE = new Pixmap("rapidMissile.png");
	
	public static final Dimension DEFAULT_SIZE = new Dimension(64,64);
	public static final int DEFAULT_OFFSET = 50;
	public static final int DEFAULT_HEALTH = 100;
	public static int DEFAULT_COOLDOWN;
	
	
	public static final int DEFAULT_ADD_TRIMISSILES = 60;
	public static final int DEFAULT_ADD_RAPIDMISSILES = 200;
	public static final int DEFAULT_ADD_HEALTH = 30;
	
	public int myAmmoType;
	
	
	private static final int MOVE_LEFT = KeyEvent.VK_LEFT;
	private static final int MOVE_RIGHT = KeyEvent.VK_RIGHT;
	private static final int MOVE_UP = KeyEvent.VK_UP;
	private static final int MOVE_DOWN = KeyEvent.VK_DOWN;
	private static final int SPACE = KeyEvent.VK_SPACE;
	private static final int MOVE_SPEED = 10;
	private int coolDownTime = DEFAULT_COOLDOWN;
	
	private static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
	private static final Vector RIGHT_VELOCITY = new Vector(RIGHT_DIRECTION, MOVE_SPEED);
	private static final Vector UP_VELOCITY = new Vector(UP_DIRECTION, MOVE_SPEED);
	private static final Vector DOWN_VELOCITY = new Vector(DOWN_DIRECTION, MOVE_SPEED);
	
	private int myRapidMissiles;
	private int myTriMissiles;
	private Canvas myCanvas;
	private ArrayList<Missile> myMissiles;
	private ValueText myHealth;
	
	public Ship(Pixmap image, Location center, Dimension size, Canvas canvas){
		super(image, center, size);
		myCanvas = canvas;
		myMissiles = new ArrayList<Missile>();
		myHealth = new ValueText("Health", DEFAULT_HEALTH);
		resetValues();
	}

	private void resetValues(){
		myAmmoType = 0;
		myTriMissiles = 0;
		myRapidMissiles = 0;
		DEFAULT_COOLDOWN = 8;
	}
	
	public ValueText getMyHealth(){
		return myHealth;
	}
	
	public ArrayList<Missile> getMissiles(){
		return myMissiles;
		
	}
		
	public void fireMissile(){
		if(myAmmoType == 0){
			fireStandardMissile();
		}
		
		if(myAmmoType == 1){
			fireTriMissiles();
		}
		
		if(myAmmoType == 2){
			fireRapidMissiles();
		}
		

						
	}

	private void fireStandardMissile() {
		Missile toFire = new Missile(MISSILE_IMAGE,
					new Location(this.getRight(), this.getY()),
					Missile.DEFAULT_SIZE,
					myCanvas);
		myMissiles.add(toFire);
	}
	

	
	private void fireRapidMissiles() {
		myRapidMissiles--;
		Missile toFire = new Missile(RAPIDMISSILE_IMAGE,
				new Location(this.getRight(), this.getY()),
				Missile.DEFAULT_SIZE,
				myCanvas);
		
		myMissiles.add(toFire);
		if(myRapidMissiles <=0){
			resetValues();
		}
	}

	private void fireTriMissiles() {
		myTriMissiles--;
		
		Missile RIGHT = new Missile(TRIMISSILE_IMAGE,
				new Location(this.getRight(), this.getY()),
				Missile.TRI_SIZE,
				myCanvas);
		Missile UP = new UpRightMissile(TRIMISSILE_IMAGE,
				new Location(this.getRight(), this.getY()),
				Missile.TRI_SIZE,
				myCanvas);
		Missile DOWN = new DownRightMissile(TRIMISSILE_IMAGE,
				new Location(this.getRight(), this.getY()),
				Missile.TRI_SIZE,
				myCanvas);
		myMissiles.add(RIGHT);
		myMissiles.add(UP);
		myMissiles.add(DOWN);
		
		if(myTriMissiles <=0){
			resetValues();
		}
	}

	
	
	public void update (double elapsedTime, Dimension bounds){
		
		Set<Integer> key = myCanvas.getPressedKeys();
		coolDownTime --;
		boolean hasUP = key.contains(MOVE_UP);
		boolean hasDOWN = key.contains(MOVE_DOWN);
		boolean hasLEFT = key.contains(MOVE_LEFT);
		boolean hasRIGHT = key.contains(MOVE_RIGHT);
		boolean hasSPACE = key.contains(SPACE);
		
		
		if(hasUP && hasRIGHT){
			Vector UPRIGHT = UP_VELOCITY.returnDirectionSum(RIGHT_VELOCITY);
			translate(UPRIGHT);
		}
		
		if(hasUP && hasLEFT){
    	   Vector UPLEFT = UP_VELOCITY.returnDirectionSum(LEFT_VELOCITY);
    	   translate(UPLEFT);
		}
		
		if(hasDOWN && hasRIGHT){
			Vector DOWNRIGHT = DOWN_VELOCITY.returnDirectionSum(RIGHT_VELOCITY);
			translate(DOWNRIGHT);
		}
        
        if(hasDOWN && hasLEFT){
        	Vector DOWNLEFT = DOWN_VELOCITY.returnDirectionSum(LEFT_VELOCITY);
        	translate(DOWNLEFT);
        }
		
        if(hasDOWN){
        	translate(DOWN_VELOCITY);
        }
        
        if(hasUP){
        	translate(UP_VELOCITY);
        }
        
        if(hasLEFT){
        	translate(LEFT_VELOCITY);
        }
        
        if(hasRIGHT){
        	translate(RIGHT_VELOCITY);
        }
        
        if(hasSPACE){
        	if(coolDownTime <= 0){
        		fireMissile();
        		coolDownTime = DEFAULT_COOLDOWN;
        	}
        }
	}
	
	
	//POWERUPS
	
	public void gotTriMissiles(){
		DEFAULT_COOLDOWN = 8;
		myTriMissiles += DEFAULT_ADD_TRIMISSILES;
		myAmmoType = 1;
	}
	
	public void gotHealth(){
		myHealth.updateValue(DEFAULT_ADD_HEALTH);
		
	}
	
	public void gotRapidShooting(){
		myRapidMissiles += DEFAULT_ADD_RAPIDMISSILES;
		myAmmoType = 2;
		DEFAULT_COOLDOWN = 2;
	}
	

}
