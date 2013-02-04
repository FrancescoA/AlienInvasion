package game;

import java.awt.Dimension;

import util.Location;
import util.Pixmap;
import util.Sprite;

public class PowerUp extends Sprite{

	public static final Dimension POWER_SIZE = new Dimension(15,15);
	
	public PowerUp(Pixmap image, Location center, Dimension size) {
		super(image, center, size);
		
	}
	

}
