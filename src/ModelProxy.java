/**
 * Class ModelProxy provides the network proxy for the model object in the
 * Network Balls and Sticks Game The model proxy resides in the client 
 * program and communicates with the server program.
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ModelProxy implements ViewListener {

// Hidden data members.

	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ModelListener modelListener;

// Exported constructors.

	/**
	 * Construct a new model proxy.
	 *
	 * @param  socket  Socket.
	 *
	 * @exception  java.io.IOException
	 *     Thrown if an I/O error occurred.
	 */
	public ModelProxy (Socket socket) throws IOException {
		this.socket = socket;
		socket.setTcpNoDelay (true);
		out = new DataOutputStream (socket.getOutputStream());
		in = new DataInputStream (socket.getInputStream());
	}

// Exported operations.

	/**
	 * Set the model listener object for this model proxy.
	 *
	 * @param  modelListener  Model listener.
	 */
	public void setModelListener (ModelListener modelListener) {
		this.modelListener = modelListener;
		new ReaderThread() .start();
	}

	/**
	 * Join the given session.
	 *
	 * @param  proxy    Reference to view proxy object.
	 * @param  name     player name.
	 *
	 * @exception  java.io.IOException
	 *     Thrown if an I/O error occurred.
	 */
    public void join (ViewProxy proxy, String name) throws IOException {
		out.writeByte ('J');
		out.writeUTF (name);
		out.flush();
    }
    
	/**
	 * Click a stick on the board.
	 * 
	 * @param i		The stick to click
	 * @param vp	The ViewProxy that clicked the stick
	 */
	public void stickClick(int i, ViewProxy vp) throws IOException {
		out.writeByte ('S');
		out.writeByte (i);
        out.flush();
	}

	/**
	 * Click a ball on the board
	 * 
	 * @param i		The ball to click
	 * @param vp	The ViewProxy that clicked the ball
	 */
	public void ballClick(int i, ViewProxy vp) throws IOException {
		out.writeByte ('B');
		out.writeByte (i);
        out.flush();
	}
	
	/**
	 * Clear the board
	 */
	public void clearBoard() throws IOException {
		out.writeByte ('C');
		out.flush();
	}
	
	/**
	 * Report that the window has been closed
	 * 
	 * @throws IOException 
	 */
	public void windClosed() throws IOException {
		out.writeByte('W');
		out.flush();
	}

// Hidden helper classes.

	/**
	 * Class ReaderThread receives messages from the network, decodes them, and
	 * invokes the proper methods to process them.
	 */
	private class ReaderThread extends Thread {
        public void run() {
            try {
                for (;;) {
                    int i;
                    String s;
                    boolean bool;
					byte b = in.readByte();
					switch (b) {
                        case 'B':
                            i = in.readByte();
                            bool = in.readBoolean();
                            modelListener.setBall(i, bool);
                            break;
                        case 'S':
                        	i = in.readByte();
                        	bool = in.readBoolean();
                            modelListener.setStick(i, bool);
                            break;
                        case 'M':
                        	i = in.readByte();
                        	s = in.readUTF();
                        	modelListener.setMessage(i, s);
                        	break;
                        case 'N':
                        	modelListener.enableNGButton();
                        	break;
                        case 'E':
                        	modelListener.endGame();
                        	break;
						default:
							break;
						}
					}
				}
			catch (IOException exc) {}
			finally
				{
				try
					{
					socket.close();
					}
				catch (IOException exc) {}
				}
			}
		}
}
