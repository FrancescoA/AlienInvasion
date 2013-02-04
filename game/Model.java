package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import javax.media.j3d.Bounds;
import javax.swing.Timer;

import quicktime.qd3d.math.Point2D;

import util.Location;
import util.Pixmap;
import util.Sound;
import util.Sprite;
import util.Text;
import util.ValueText;
import view.Canvas;



 
public class Model {
	
    // resources used in the game
	private int myLevel;
	
	private static final int SPACE = KeyEvent.VK_SPACE;
	private static final int ONE = KeyEvent.VK_1;
	private static final int TWO = KeyEvent.VK_2;
	private static final int THREE = KeyEvent.VK_3;
	private static final int H = KeyEvent.VK_H;
	private static final int S = KeyEvent.VK_S;
	
	
	private static final Pixmap SHIP_IMAGE = new Pixmap("spaceship1.png");
	private static final Pixmap ALIEN_IMAGE = new Pixmap("ufo.png");
	private static final Pixmap MASTER_ALIEN_IMAGE = new Pixmap("alien2.png");
	private static final Pixmap BACKGROUND_IMAGE = new Pixmap("background.png");
	private static final Pixmap SPLASH_IMAGE = new Pixmap("splashscreen.png");
	private static final Pixmap BOSS_IMAGE = new Pixmap("deathstar.png");

	//power up info
	private static final Pixmap TRI_POWER_IMAGE = new Pixmap("trimissile.gif");
	private static final Pixmap RAPID_POWER_IMAGE = new Pixmap("rapidfire.gif");
	private static final Pixmap HEALTH_POWER_IMAGE = new Pixmap("healthpower.gif");
	//power up timer
	private Timer myPowerTimer;
	private final int POWERWAIT = 25000;
	
    // game play values
    private int numberOfAliens = 150;
    private int numberOfMasterAliens = 100;
    private int winScore = 700;
    

    // heads up display values
    private static final int LABEL_X_OFFSET = 50;
    private static final int LABEL_Y_OFFSET = 25;
    private static final Color LABEL_COLOR = Color.WHITE;
    private static final String SCORE_LABEL = "Score";
  

    // bounds and input for game
    private Canvas myView;
    private Sprite myScreen;
    // game state
    protected int WIDTH ;
    protected int HEIGHT ;
    
    
    protected Ship myShip;
    protected List<Alien> myAliens;
    protected Boss myBoss;

    protected Sprite myTriPower;
    protected Sprite myRapidPower;
    protected Sprite myHealthPower;
   
    
    private ValueText myScore;
    int lastKeyPressed;
    
   
  

    
    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        
        myLevel = -1;
        WIDTH = myView.getWidth();
        HEIGHT = myView.getHeight();
        initStatus();
        initSprites(canvas.getSize());
        initPowerUps();
        
        
        myPowerTimer = new Timer(POWERWAIT, 
            	new ActionListener() {
    	public void actionPerformed (ActionEvent e) {
                    initPowerUps();
                }
            });
 
        myPowerTimer.start();
       
        
        
    }
    
    public void cheat(){
    	if(myView.getPressedKeys().contains(ONE)){
    		myLevel = 0;
    		myBoss.reset();
    		myBoss.setVisible(false);
    		myBoss.setBossMode(false);
    		killAllAliens();
    		changeSprites();
    	}
    	
      	if(myView.getPressedKeys().contains(TWO)){
    		myLevel = 1;
    		myBoss.reset();
    		myBoss.setVisible(false);
    		myBoss.setBossMode(false);
       		killAllAliens();
    		changeSprites();
    	}
      	
      	if(myView.getPressedKeys().contains(THREE)){
    		myLevel = 2;
    		myBoss.setVisible(true);
    		killAllAliens();
    		
    	}
      	
      	if(myView.getPressedKeys().contains(H)){
      		myShip.getMyHealth().updateValue(100);
      	}
      	
      	if(myView.getPressedKeys().contains(S)){
      		myScore.updateValue(100);
      	}
    }

    private void splashScreen(Pixmap image) {
    	image.paint((Graphics2D) myView.getGraphics(), 
    			new Location(WIDTH/2,HEIGHT/2),
    			new Dimension(WIDTH, HEIGHT));
    
		
	}

	/**
     * Update game for this moment, given the time since the last moment.
     */
    public void update (double elapsedTime) {
 
    	
        Dimension bounds = myView.getSize();
        updateSprites(elapsedTime, bounds);
        intersectSprites(bounds);
        checkRules();
        cheat();
        
    }
    
    public void killAllAliens(){
    	for(Alien i: myAliens){
    		i.setVisible(false);
    	}
    	for(AlienMissile x : myBoss.getMissiles()){
    		x.setVisible(false);
    	}
    }

    /**
     * Draw all elements of the game.
     */
    public void paint (Graphics2D pen) {
    	//myScreen.paint(pen);

    	BACKGROUND_IMAGE.paint(pen, new Location(WIDTH/2,HEIGHT/2), new Dimension(WIDTH,HEIGHT));
    	paintStatus(pen);
    	myShip.paint(pen);
    	myRapidPower.paint(pen);
    	myTriPower.paint(pen);
    	myHealthPower.paint(pen);
    	

    	ArrayList<Missile> missiles = myShip.getMissiles();
    	for(Missile m : missiles){
    		m.paint(pen);
    	}

    	for(Alien a: myAliens){
    		a.paint(pen);
    	}
    	
    	
    	myBoss.paint(pen);
    	ArrayList<AlienMissile> aMissiles= myBoss.getMissiles();
   		for(AlienMissile am: aMissiles){
   			am.paint(pen);  		
    	}

   		if(myLevel < 0){
   		splashScreen(SPLASH_IMAGE);
   			if(myView.getPressedKeys().contains(SPACE)){
   				myLevel++;
   				changeSprites();
   			}
   		}
    }
        
 
    
    
    
    /**
     * Create ball, paddle, and blocks that will appear in the game.
     */
    public void initSprites (Dimension bounds) {
    	
    	myAliens = new ArrayList<Alien>();
    	myShip = new Ship(SHIP_IMAGE, 
				new Location(bounds.width/2, bounds.height/2), 
				Ship.DEFAULT_SIZE,
				myView);
    	
    	myBoss = new Boss(BOSS_IMAGE,
    					new Location(bounds.getWidth() + 100 , bounds.getHeight()/2),
    					Boss.getSize(),
    					myView);
    					

    	
    	changeSprites();
    
    
    }

	private void changeSprites() {
		if(myLevel ==0){
        	
    		List<Location> alienLocs = generateAlienLocations(numberOfAliens);
    		for(Location loc : alienLocs){
    			Alien addAlien = new Alien(ALIEN_IMAGE,
    									loc,
    									Alien.DEFAULT_SIZE,
    									myView);
    		
    			myAliens.add(addAlien);
    		}
    	}
    	
    
    	if(myLevel ==1){
    		List<Location> masterAlienLocs = generateAlienLocations(numberOfMasterAliens);
    		for(Location loc: masterAlienLocs){
    			Alien addAlien2 = new MasterAlien(MASTER_ALIEN_IMAGE,
    												loc,
    												MasterAlien.DEFAULT_SIZE,
    												myView);
    			myAliens.add(addAlien2);
    		}
    	}
	}

	private void initPowerUps() {
		myTriPower = new PowerUp(TRI_POWER_IMAGE,
    							generateRandomLocation(0,WIDTH,0,HEIGHT),
    							PowerUp.POWER_SIZE);
    	
    	myRapidPower = new PowerUp(RAPID_POWER_IMAGE,
    							generateRandomLocation(0,WIDTH,0,HEIGHT),
    							PowerUp.POWER_SIZE);
    	
    	myHealthPower = new PowerUp(HEALTH_POWER_IMAGE,
								generateRandomLocation(0,WIDTH,0,HEIGHT),
								PowerUp.POWER_SIZE);
	}

    /**
     * Create "heads up display", i.e., status labels that will appear in the game.
     */
    private void initStatus () {
        myScore = new ValueText(SCORE_LABEL, 0);
        
    }

    
    /**
     * Update sprites based on time since the last update.
     */
    private void updateSprites (double elapsedTime, Dimension bounds) {
    	
    	myShip.update(elapsedTime, bounds);
    	
    	
        List<Missile> missiles = myShip.getMissiles();
        for(Missile m : missiles){
        	m.update(elapsedTime, bounds);
        }
    	
        
        for(Alien a: myAliens){
        	a.update(elapsedTime, bounds);
        }
        
        if(myLevel ==2){
        	myBoss.update(elapsedTime, bounds);
        	ArrayList<AlienMissile> lasers = myBoss.getMissiles();
        	for(AlienMissile al: lasers){
        		al.update(elapsedTime, bounds);
        	}
        }
        
  
        

    }

    /**
     * Check for intersections between sprites
     */
    private void intersectSprites (Dimension bounds) {
        
    	
    	for(Missile m: myShip.getMissiles()){
    		if(m.intersects(myBoss) && myBoss.isInBossMode()){
    			myBoss.getMyHealth().updateValue(-2);
    			m.setVisible(false);
    		}
    		for(Alien a: myAliens){
    			
        		if(a.intersects(myShip)){
        			a.setVisible(false);
        			myShip.getMyHealth().updateValue(a.getDamage());
        		}
        		
        		if((a.getRight() < 0) && (a.isVisible())){
        			a.reset();
        		}

    			if(m.intersects(a) && (a.getLeft() < myView.getSize().width)){
    				m.setVisible(false);
    				a.updateHealth(-1);
    				if(a.getHealth() <=0){
    					a.setVisible(false);
    					myScore.updateValue(a.getPoints());
    				}
    			}
    		}
    		if(m.intersects(myBoss) && myBoss.isInBossMode()){
    			myBoss.getMyHealth().updateValue(-1);
    		}
    	}
		
    	
        for(AlienMissile x : myBoss.getMissiles()){
        	if(x.intersects(myShip)){
        		myShip.getMyHealth().updateValue(-1);
        	}
        }
        
    	if(myBoss.intersects(myShip)){
    		myBoss.getMyHealth().updateValue(-2);
    		myShip.getMyHealth().updateValue(myBoss.getDamage());
    	}
    	
    	if(myBoss.getRight() <= bounds.width + 5){
    		myBoss.setBossMode(true);
    	}
    	
    	if((myBoss.getBottom() >= bounds.height) || (myBoss.getTop() <= 0)){
    		myBoss.switchDirection();
    	}
    	
    	
    	
    	
    	//powerups
    	if(myShip.intersects(myTriPower)){
    		myShip.gotTriMissiles();
    		myTriPower.setVisible(false);
    	}
    	if(myShip.intersects(myRapidPower)){
    		myShip.gotRapidShooting();
    		myRapidPower.setVisible(false);
    	}
    	if(myShip.intersects(myHealthPower)){
    		myShip.gotHealth();
    		myHealthPower.setVisible(false);
    	}
 
        
    }

    /**
     * Check that if the game is won or lost.
     */
    private void checkRules () {
    	if(myShip.getMyHealth().getValue() <= 0){
    		Text youLose = new Text("YOU LOSE");
    		
    		youLose.paint((Graphics2D) myView.getGraphics(), 
    			new Location(WIDTH/2,HEIGHT/2),
    			LABEL_COLOR);
    		
    		myView.stop();
    		
    	}
    	
    	if(myLevel == 2){myBoss.setVisible(true);}
    	if(myScore.getValue() >= winScore){
    		winScore +=700;
    		myLevel++;
    		changeSprites();

    	}
    	
    	if(myBoss.getMyHealth().getValue() <0){
    		Text youWin = new Text("YOU WIN");
    		youWin.paint((Graphics2D) myView.getGraphics(), 
    			new Location(WIDTH/2,HEIGHT/2),
    			LABEL_COLOR);
    		
    		myView.stop();
    	}
    	
    
    	
       
    }

    /**
     * Display labels on screen
     */
    private void paintStatus (Graphics2D pen) {
        myScore.paint(pen, new Point(LABEL_X_OFFSET, LABEL_Y_OFFSET), LABEL_COLOR);
        myShip.getMyHealth().paint(pen, new Point(myView.getSize().width - LABEL_X_OFFSET, LABEL_Y_OFFSET), LABEL_COLOR); 
        
        if(myLevel ==2){
        	myBoss.getMyHealth().paint(pen, new Point(myView.getSize().width/2, LABEL_Y_OFFSET), LABEL_COLOR);
        }
    }
 
    
    
    //random generators
    public List<Location> generateAlienLocations(Integer numLoc){
    	List<Location> locs = new ArrayList<Location>();
    	
    	
    	int alienSparsity = 20; //denotes opposite of alien density
  		int minX = WIDTH;
		int maxX = minX + alienSparsity*numLoc;
		int minY = 0;
		int maxY = HEIGHT;
		
    	for(int i = 0; i < numLoc ; i++){    		
    		locs.add(generateRandomLocation(minX, maxX, minY, maxY));
    		
    	}
    	return locs;
    }

    private Location generateRandomLocation(int Xmin, int Xmax, int Ymin, int Ymax){
		int x = genRandomInt(Xmin, Xmax);
		int y = genRandomInt(Ymin, Ymax);
		return new Location(x,y);
    	
    }

	private int genRandomInt(int min, int max) {
		int y = min + (int)(Math.random()*((max-min) + 1));
		return y;
	}
}
