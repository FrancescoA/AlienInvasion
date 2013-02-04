package game;


import java.awt.Dimension;
import java.awt.List;
import java.util.ArrayList;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.ValueText;
import util.Vector;
import view.Canvas;

public class Boss extends Sprite {
	
	
	private static final int MOVE_SPEED = 4;
	private static final Dimension SIZE = new Dimension(180,180);
	private static final Pixmap MISSILE_IMAGE = new Pixmap("alienmissile.png");
	
	private static final Vector LEFT_VELOCITY = new Vector(LEFT_DIRECTION, MOVE_SPEED);
	private static final Vector RIGHT_VELOCITY = new Vector(RIGHT_DIRECTION, MOVE_SPEED);
	private static final Vector UP_VELOCITY = new Vector(UP_DIRECTION, MOVE_SPEED);
	private static final Vector DOWN_VELOCITY = new Vector(DOWN_DIRECTION, MOVE_SPEED);
	private static final int DEFAULT_DAMAGE = -50;
	private static final int DEFAULT_POINTS = 10000;
	private static final int DEFAULT_COOLDOWN = 2;
	private static final int HEALTH = 200;
	
	private Canvas myCanvas;
	boolean mySwitch;
	boolean bossMode;
	private ValueText myHealth;
	int coolDownTime;
	ArrayList<AlienMissile>myMissiles;

	public Boss(Pixmap image, Location center, Dimension size, Canvas canvas) {
		super(image, center, size);
		myCanvas = canvas;
		mySwitch = true;
		myMissiles = new ArrayList<AlienMissile>();
		bossMode = false;
		myHealth = new ValueText("Boss Health", HEALTH );
	}
	
	public void update(double elapsedTime, Dimension bounds){
		if(bossMode){
			coolDownTime--;
			if(mySwitch){
				translate(UP_VELOCITY);
			}
			else{
				translate(DOWN_VELOCITY);
			}

			if(coolDownTime <= 0){
				fireMissile();
				coolDownTime = DEFAULT_COOLDOWN;
			}
		}
		else{
			translate(LEFT_VELOCITY);
		}
	}
	
	private void fireMissile() {
		AlienMissile bottom = new AlienMissile(MISSILE_IMAGE,
											new Location(this.getX(), this.getBottom()-4),
											new Dimension(25,5),
											myCanvas);
		AlienMissile top = new AlienMissile(MISSILE_IMAGE,
				new Location(this.getX() , this.getTop() +4),
				new Dimension(25,5),
				myCanvas);
		
		myMissiles.add(top);
		myMissiles.add(bottom);
		
	}

	public void setBossMode(boolean s){
		bossMode = s;
	}
	
	public boolean isInBossMode(){
		return bossMode;
	}
	
	public void switchDirection(){
		mySwitch = !mySwitch;
	}

    public int getDamage(){
    	return DEFAULT_DAMAGE;
    }
    
    public int getPoints(){
    	return DEFAULT_POINTS;
    }
    
	public ValueText getMyHealth(){
		return myHealth;
	}
    
	public ArrayList<AlienMissile> getMissiles() {
		return myMissiles;
	}

	public static Dimension getSize() {
		return SIZE;
	}
}
