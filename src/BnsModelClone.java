/**
 * Class BnsModelClone provides a client-side clone of the server-side model
 * object in the Network Balls and Sticks game.
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.IOException;

public class BnsModelClone implements ModelListener {

// Hidden data members.

	private BnsBoard board = new BnsBoard();
    private ModelListener modelListener;

// Exported constructors

    /**
     * Construct a new BnsModelClone
     */
    public BnsModelClone() {}

// Exported operations
    
    /**
	 * Returns a reference to the BnsBoard object in this BnsModelClone.
	 *
	 * @return  BnsBoard.
	 */
    public BnsBoard getBoard() { return board; }
    
    /**
     * Set the model listener for this BnsModelClone.
     *
     * @param  modelListener  Model listener.
     */
    public void setModelListener (ModelListener modelListener) {
        this.modelListener = modelListener;
    }

	/**
	 * Sets the visibility of a ball
	 * 
	 * @param i		The ball to set
	 * @param b		Whether to set to visible or not
	 */
	public void setBall(int i, boolean b) throws IOException {
		this.modelListener.setBall(i, b);
		board.setBall(i, b);
	}

	/**
	 * Sets the visibility of a stick
	 * 
	 * @param i		The stick to set
	 * @param b		Whether to set to visible or not
	 */
	public void setStick(int i, boolean b) throws IOException {
		this.modelListener.setStick(i, b);
		board.setStick(i, b);
	}

	/**
	 * Sets a message on the player's board
	 * 
	 * @param c		The message code
	 * @param s		String if the message needs some data
	 */
	public void setMessage(int c, String s) throws IOException {
		modelListener.setMessage(c, s);
		
	}

	/**
	 * Enables the new game button
	 */
	public void enableNGButton() throws IOException {
		modelListener.enableNGButton();
	}

	/**
	 * Ends the game
	 */
	public void endGame() throws IOException {
		modelListener.endGame();
	}
}
