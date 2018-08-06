/**
 * Interface ModelListener specifies the interface for an object that is
 * triggered by events from the model object in the Network Balls and 
 * Sticks game.
 *
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.IOException;

public interface ModelListener {

// Exported operations.
	
	/**
	 * Set a balls visibility
	 *
	 * @param i		Ball index (0..8).
	 * @param b		The visibility value
	 */
	public void setBall (int i, boolean b) throws IOException;

	/**
	 * Set a sticks visibility
	 *
	 * @param  i  Stick index (0..11).
	 */
	public void setStick (int i, boolean b) throws IOException;
	
	/**
	 * Change the message on a players board
	 * 
	 * @param c		The code for which message to set
	 * @param s		A string for sending necessary data for message
	 * @throws IOException
	 */
	public void setMessage (int c, String s) throws IOException;
	
	/**
	 * Enables the new game button
	 * 
	 * @throws IOException
	 */
	public void enableNGButton () throws IOException;

	/**
	 * Ends the players' games after a player has closed their window
	 */
	public void endGame() throws IOException;
}