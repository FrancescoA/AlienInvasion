package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import util.Location;
import util.Pixmap;
import util.Sprite;
import view.Canvas;

public class Splashscreen extends Sprite{
	
	private Canvas myCanvas;
	
	private static final int SPACE = KeyEvent.VK_SPACE;

	
	public Splashscreen(Pixmap image, Location center, Dimension size, Canvas canvas) {
		super(image, center, size);
		myCanvas = canvas;
	}
	
	
    public void update (double elapsedTime, Dimension bounds){
    	
    	int key = myCanvas.getLastKeyPressed();
    	if(key== SPACE){
    		this.setVisible(false);
    	}
    	
    	
    }
	
}
