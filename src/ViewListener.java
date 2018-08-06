/**
 * Interface ViewListener specifies the interface for an object that is
 * triggered by events from the view object in the Network Balls and
 * Sticks game.
 *
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.IOException;

public interface ViewListener {

        /**
         * Join the given session.
         *
         * @param  proxy    Reference to view proxy object.
         * @param  name     player name
         */
        public void join(ViewProxy proxy, String name) throws IOException;
		
		/**
		 * Reset the board
		 */
		public void clearBoard () throws IOException;
		
		/**
		 * Report that the window has been closed
		 */
		public void windClosed() throws IOException;

		/**
		 * Report that a stick was clicked
		 * 
		 * @param i
		 * @param viewProxy
		 * @throws IOException
		 */
		public void stickClick(int i, ViewProxy viewProxy) throws IOException;
		
		/**
		 * Report that a ball was clicked
		 * 
		 * @param i
		 * @param viewProxy
		 * @throws IOException
		 */
		public void ballClick(int i, ViewProxy viewProxy) throws IOException;
	}
