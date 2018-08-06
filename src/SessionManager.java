/**
 * Class SessionManager maintains the sessions' model objects in the Network Balls
 * and Sticks Game server.
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.IOException;

public class SessionManager implements ViewListener {

// Hidden data members.

    private BnsModel currentSession = null;

// Exported constructors.

    /**
     * Construct a new session manager.
     */
    public SessionManager() {}

// Exported operations.

    /**
     * Join the given session.
     *
     * @param  proxy    Reference to view proxy object.
     *
     * @exception  java.io.IOException
     *     Thrown if an I/O error occurred.
     */
    public synchronized void join (ViewProxy proxy, String name) throws IOException 
    {
            // No players at all
        if( currentSession == null) {
            BnsModel model = new BnsModel();
            model.p1name = name;
            model.addModelListener(proxy);
            proxy.setViewListener(model);
            currentSession = model;

            // One player waiting for partner
        } else if( currentSession.numPlayers == 1) {
            BnsModel model = currentSession;
            model.p2name = name;
            model.numPlayers += 1;
            model.addModelListener (proxy);
            proxy.setViewListener (model);

            // No players waiting for partner
        } else if( currentSession.numPlayers == 2) {
            BnsModel model = new BnsModel();
            model.p1name = name;
            currentSession = model;
            model.addModelListener(proxy);
            proxy.setViewListener(model);
            
            // A player closed a session before another player joined
        } else if( currentSession.numPlayers == 0) {
        	BnsModel model = currentSession;
            model.p1name = name;
            model.numPlayers = 1;
            model.addModelListener(proxy);
            proxy.setViewListener(model);
            currentSession = model;

        }
    }

    /**
     * Click a stick on the board
     * 
     * @param i		The stick to click
     * @param name	The name of the player clicking the stick
     */
    public void stickClick(int i, ViewProxy viewProxy) throws IOException {}

	/**
	 * Click a ball on the board
	 * 
	 * @param i		The ball to click
	 * @param name	The name of the player clicking the ball
	 */
    public void ballClick(int i, ViewProxy viewProxy) throws IOException {}

	/**
	 * Reset the board
	 */
	public void clearBoard() throws IOException {}

	/**
	 * Report that the window has been closed
	 */
	public void windClosed() {}
}
