package view;

import game.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.Timer;


/**
 * Creates an area of the screen in which the game will be drawn that supports:
 * <UL>
 *   <LI>animation via the Timer
 *   <LI>mouse input via the MouseListener
 *   <LI>keyboard input via the KeyListener
 * </UL>
 * 
 * @author Robert C Duvall
 */
public class Canvas extends JComponent {
    // default serialization ID
    private static final long serialVersionUID = 1L;
    // animate 25 times per second if possible
    public static final int FRAMES_PER_SECOND = 25;
    // better way to think about timed events (in milliseconds)
    public static final int ONE_SECOND = 1000;
    public static final int DEFAULT_DELAY = ONE_SECOND / FRAMES_PER_SECOND;
    // input state
    public static final int NO_KEY_PRESSED = -1;


    
    // drives the animation
    private Timer myTimer;
    // game to be animated
    private Model myGame;
    // input state
    private int myLastKeyPressed;
    private Point myLastMousePosition;
    private Set<Integer> myCurrentKeys;

    /**
     * Create a panel so that it knows its size
     */
    public Canvas (Dimension size) {
        // set size (a bit of a pain)
        setPreferredSize(size);
        setSize(size);
        // prepare to receive input
        setFocusable(true);
        requestFocus();
        setInputListeners();
    }

    /**
     * Paint the contents of the canvas.
     * 
     * Never called by you directly, instead called by Java runtime
     * when area of screen covered by this container needs to be
     * displayed (i.e., creation, uncovering, change in status)
     * 
     * @param pen used to paint shape on the screen
     */
    @Override
    public void paintComponent (Graphics pen) {
        pen.setColor(Color.WHITE);
        pen.fillRect(0, 0, getSize().width, getSize().height);
        // first time needs to be special cased :(
        if (myGame != null) {
            myGame.paint((Graphics2D) pen);
        }
    }

    /**
     * Returns last key pressed by the user.
     */
    public int getLastKeyPressed () {
        return myLastKeyPressed;
    }
    
    public Set<Integer> getPressedKeys(){
    	return myCurrentKeys;
    }

    /**
     * Returns last position of the mouse in the canvas.
     */
    public Point getLastMousePosition () {
        return myLastMousePosition;
    }

    /**
     * Start the animation.
     */
    public void start () {
        final int stepTime = DEFAULT_DELAY;
        // create a timer to animate the canvas
        myTimer = new Timer(stepTime, 
            new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                    myGame.update((double) stepTime / ONE_SECOND);
                    repaint();
                }
            });
        
        // start animation
        
        myGame = new Model(this);
        myTimer.start();
        
        
    }

    /**
     * Stop the animation.
     */
    public void stop () {
        myTimer.stop();
    }

    /**
     * Create listeners that will update state based on user input.
     */
    private void setInputListeners () {
        // initialize input state
    	myCurrentKeys = new HashSet<Integer>();
        myLastKeyPressed = NO_KEY_PRESSED;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                int myLastKey = e.getKeyCode();
                myCurrentKeys.add(myLastKey);
            }
            @Override
            public void keyReleased (KeyEvent e) {
                int myLastKey = e.getKeyCode();
                myCurrentKeys.remove(myLastKey);
                
            }
        });
        
        myLastMousePosition = new Point();
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved (MouseEvent e) {
                myLastMousePosition = e.getPoint();
            }
        });
    }



}
