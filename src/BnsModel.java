/**
 * Class BnsModel provides the server-side model object in the Network Balls
 * and Sticks game.
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.IOException;

public class BnsModel implements ViewListener {

// Public data members
    public int turn;
    public int numPlayers = 1;
    public String p1name = null;
    public String p2name = null;

// Hidden data members.
    private BnsBoard board = new BnsBoard();
    private ModelListener p1 = null;
    private ModelListener p2 = null;

// Exported constructors.

    /**
     * Construct a new BnsModel.
     */
    public BnsModel() {}

// Exported operations.
    
    /**
	 * Add the given model listener to this BnsModel.
	 *
	 * @param  modelListener  Model listener.
	 */
    public synchronized void  addModelListener(ModelListener modelListener) throws IOException {
		
    	// Set the players' model listeners
		if( p1 == null ) {
            p1 = modelListener;
        } else {
            p2 = modelListener;
        }
		
		// Set messages and game state
		if( this.numPlayers == 1 ) {
			p1.setMessage(1, "");
        }
        if( this.numPlayers == 2 ) {
            turn = 1;
            
            // Setup clients' boards
            p1.setMessage(2, p2name);
            p2.setMessage(3, p1name);
            
    		for(int i=0; i<BnsBoard.numBalls; i++) {
    			p1.setBall(i, true);
    			p2.setBall(i, true);
    		}
    		for(int i=0; i<BnsBoard.numSticks; i++) {
    			p1.setStick(i, true);
    			p2.setStick(i, true);
    		}
    		p1.enableNGButton();
    		p2.enableNGButton();
            
        }
	}
    
    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     * @param  name     player name
     */
    public synchronized void join (ViewProxy proxy, String name) {}

	/**
	 * Clicks a stick on the board.
	 * 
	 * @param i		The stick to click
	 * @param vp	The ViewProxy that clicked the stick
	 */
	public synchronized void stickClick(int i, ViewProxy vp) throws IOException {
		
		// Check if it is player 1 attempting to make a move on their turn
		if(vp.equals(p1) && turn == 1) {
			// Update board state
			board.setStick(i, false);
			
			// Report update to all clients
			try{
				p1.setStick(i, false);
				p2.setStick(i, false);
			} catch (IOException e) {
				// Client failed
			}
			
			// Handle turn switching
			p1.setMessage(3, p2name);
			p2.setMessage(2, p1name);
			turn = 2;
		}
		
		// Check if it is player 2 attempting to make a move on their turn
		if(vp.equals(p2) && turn == 2) {
			// Update board state
			board.setStick(i, false);
			
			// Report update to all clients
			try{
				p1.setStick(i, false);
				p2.setStick(i, false);
			} catch (IOException e) {
				// Client failed
			}
			
			// Handle turn switching
			p2.setMessage(3, p2name);
			p1.setMessage(2, p1name);
			turn = 1;
		}
	}

	/**
	 * Clicks a ball on the board.
	 * 
	 * @param i		The ball to click
	 * @param vp	The ViewProxy clicking the ball
	 */
	public synchronized void ballClick(int i, ViewProxy vp) throws IOException {
		
		// Check if it is player 1 attempting to make a move on their turn
		if(vp.equals(p1) && turn == 1) {
			
			// Update board state
			board.setBall(i, false);
			
			if(i == 0) {
	    		p1.setStick(6, false);
	    		p1.setStick(0, false);
	    		p2.setStick(6, false);
	    		p2.setStick(0, false);
	    	} else if (i == 1) {
	    		p1.setStick(0, false);
	    		p1.setStick(1, false);
	    		p1.setStick(8, false);
	    		p2.setStick(0, false);
	    		p2.setStick(1, false);
	    		p2.setStick(8, false);
	    	} else if (i == 2) {
	    		p1.setStick(1, false);
	    		p1.setStick(10, false);
	    		p2.setStick(1, false);
	    		p2.setStick(10, false);
	    	} else if (i == 3) {
	    		p1.setStick(6, false);
	    		p1.setStick(2, false);
	    		p1.setStick(7, false);
	    		p2.setStick(6, false);
	    		p2.setStick(2, false);
	    		p2.setStick(7, false);
	    	} else if (i == 4) {
	    		p1.setStick(8, false);
	    		p1.setStick(2, false);
	    		p1.setStick(3, false);
	    		p1.setStick(9, false);
	    		p2.setStick(8, false);
	    		p2.setStick(2, false);
	    		p2.setStick(3, false);
	    		p2.setStick(9, false);
	    	} else if (i == 5) {
	    		p1.setStick(10, false);
	    		p1.setStick(3, false);
	    		p1.setStick(11, false);
	    		p2.setStick(10, false);
	    		p2.setStick(3, false);
	    		p2.setStick(11, false);
	    	} else if (i == 6) {
	    		p1.setStick(7, false);
	    		p1.setStick(4, false);
	    		p2.setStick(7, false);
	    		p2.setStick(4, false);
	    	} else if (i == 7) {
	    		p1.setStick(9, false);
	    		p1.setStick(4, false);
	    		p1.setStick(5, false);
	    		p2.setStick(9, false);
	    		p2.setStick(4, false);
	    		p2.setStick(5, false);
	    	} else if (i == 8) {
	    		p1.setStick(11, false);
	    		p1.setStick(5, false);
	    		p2.setStick(11, false);
	    		p2.setStick(5, false);
	    	}
			
			// Report update to all clients
			p1.setBall(i, false);
			p2.setBall(i, false);
			
			// Check win state
			if(board.isEmpty()) {
				p1.setMessage(5, p2name);
				p2.setMessage(4, p1name);
			} else {
				// Handle turn switching
				p2.setMessage(2, p1name);
				p1.setMessage(3, p2name);
				turn = 2;
			}
		}
		
		// Check if it is player 2 attempting to make a move on their turn
		if(vp.equals(p2) && turn == 2) {
			
			// Update board state
			board.setBall(i, false);
			
			if(i == 0) {
	    		p1.setStick(6, false);
	    		p1.setStick(0, false);
	    		p2.setStick(6, false);
	    		p2.setStick(0, false);
	    	} else if (i == 1) {
	    		p1.setStick(0, false);
	    		p1.setStick(1, false);
	    		p1.setStick(8, false);
	    		p2.setStick(0, false);
	    		p2.setStick(1, false);
	    		p2.setStick(8, false);
	    	} else if (i == 2) {
	    		p1.setStick(1, false);
	    		p1.setStick(10, false);
	    		p2.setStick(1, false);
	    		p2.setStick(10, false);
	    	} else if (i == 3) {
	    		p1.setStick(6, false);
	    		p1.setStick(2, false);
	    		p1.setStick(7, false);
	    		p2.setStick(6, false);
	    		p2.setStick(2, false);
	    		p2.setStick(7, false);
	    	} else if (i == 4) {
	    		p1.setStick(8, false);
	    		p1.setStick(2, false);
	    		p1.setStick(3, false);
	    		p1.setStick(9, false);
	    		p2.setStick(8, false);
	    		p2.setStick(2, false);
	    		p2.setStick(3, false);
	    		p2.setStick(9, false);
	    	} else if (i == 5) {
	    		p1.setStick(10, false);
	    		p1.setStick(3, false);
	    		p1.setStick(11, false);
	    		p2.setStick(10, false);
	    		p2.setStick(3, false);
	    		p2.setStick(11, false);
	    	} else if (i == 6) {
	    		p1.setStick(7, false);
	    		p1.setStick(4, false);
	    		p2.setStick(7, false);
	    		p2.setStick(4, false);
	    	} else if (i == 7) {
	    		p1.setStick(9, false);
	    		p1.setStick(4, false);
	    		p1.setStick(5, false);
	    		p2.setStick(9, false);
	    		p2.setStick(4, false);
	    		p2.setStick(5, false);
	    	} else if (i == 8) {
	    		p1.setStick(11, false);
	    		p1.setStick(5, false);
	    		p2.setStick(11, false);
	    		p2.setStick(5, false);
	    	}
			
			// Report update to all clients
			p1.setBall(i, false);
			p2.setBall(i, false);
			
			// Check for win condition
			if(board.isEmpty()) {
				p2.setMessage(5, p1name);
				p1.setMessage(4, p2name);
			} else {
				// Handle turn switching
				p1.setMessage(2, p2name);
				p2.setMessage(3, p1name);
				turn = 1;
			}
		}
	}

	/**
	 * Resets the board and game to initial states
	 */
	public synchronized void clearBoard() throws IOException {
		
		// Update board state
		board.clearBoard();
		
		// Report updates to all clients
		for(int i=0; i<BnsBoard.numBalls; i++) {
			p1.setBall(i, true);
			p2.setBall(i, true);
		}
		for(int i=0; i<BnsBoard.numSticks; i++) {
			p1.setStick(i, true);
			p2.setStick(i, true);
		}
		
		// Reset turn to P1, new games always have P1 go first
		turn = 1;
        p1.setMessage(2, p2name);
        p2.setMessage(3, p1name);
	}

	/**
	 * Closes both clients after one has closed their window
	 * 
	 * @throws IOException 
	 */
	public synchronized void windClosed() throws IOException {
		
		p1.endGame();
		if( p2 != null ) {
			p2.endGame();
		}
		// Set numPlayers to special state to let sessionManager know
		// that a window was closed with one player in the session.
		numPlayers = 0;
		
		// Set p1 back to null
		p1 = null;
	}
}
