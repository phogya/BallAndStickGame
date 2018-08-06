/**
 * Class BnsBoard encapsulates the state of the Balls and
 * Sticks game board.
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */

public class BnsBoard {

// Exported constants.

	// Number of balls
    public static final int numBalls = 9;
    // Number of sticks
    public static final int numSticks = 12;

// Hidden data members.

    // Ball array
    private boolean[] balls = new boolean[numBalls];
    // Stick array
    private boolean[] sticks = new boolean[numSticks];

// Exported constructors.

    /**
     * Construct a new Balls and Sticks board. All balls and sticks are initially active.
     */
    public BnsBoard()
    {
        for(int i=0; i<numBalls; i++) {
        	balls[i] = true;
        }
        for(int i=0; i<numSticks; i++) {
        	sticks[i] = true;
        }   
    }
    
 // Exported operations.
    
    /**
     * Sets the value of the specified ball
     * 
     * @param i		The ball to set
     * @param b		The value to set
     */
    public synchronized void setBall(int i, boolean b) {
    	
    	if(b == true) {
    		sticks[i] = true;
    		
    	} else {
    		balls[i] = false;
        	
        	if(i == 0) {
        		sticks[0] = false;
        		sticks[6] = false;
        	} else if (i == 1) {
        		sticks[0] = false;
        		sticks[1] = false;
        		sticks[8] = false;
        	} else if (i == 2) {
        		sticks[1] = false;
        		sticks[10] = false;
        	} else if (i == 3) {
        		sticks[6] = false;
        		sticks[2] = false;
        		sticks[7] = false;
        	} else if (i == 4) {
        		sticks[8] = false;
        		sticks[2] = false;
        		sticks[3] = false;
        		sticks[9] = false;
        	} else if (i == 5) {
        		sticks[10] = false;
        		sticks[3] = false;
        		sticks[11] = false;
        	} else if (i == 6) {
        		sticks[7] = false;
        		sticks[4] = false;
        	} else if (i == 7) {
        		sticks[9] = false;
        		sticks[4] = false;
        		sticks[5] = false;
        	} else if (i == 8) {
        		sticks[11] = false;
        		sticks[5] = false;
        	}
    	}
    }
    
    /**
     * Sets the value of the specified stick to b
     * 
     * @param i		The stick to click
     * @param b		The value to set
     */
    public synchronized void setStick(int i, boolean b) {
    	sticks[i] = b;
    }
    
    /**
     * Resets the board to its initial state.
     */
    public synchronized void clearBoard() {
    	for(int i=0; i<numBalls; i++) {
        	balls[i] = true;
        }
        for(int i=0; i<numSticks; i++) {
        	sticks[i] = true;
        }
    }
    
    /**
     * Checks if all sticks and balls have been
     * clicked.
     * 
     * @return		Whether they have all been clicked
     */
    public synchronized boolean isEmpty() {
    	for(int i=0; i<numBalls; i++) {
    		if(balls[i] == true) {
    			return false;
    		}
    	}
    	return true;
    }
}