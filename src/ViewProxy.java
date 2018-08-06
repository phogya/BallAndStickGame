/**
 * Class ViewProxy provides the network proxy for the view object in the Network
 * Balls and Sticks Game. The view proxy resides in the server program and 
 * communicates with the client program.
 *
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ViewProxy implements ModelListener {

// Hidden data members.

        private Socket socket;
	    private DataOutputStream out;
	    private DataInputStream in;
	    private ViewListener viewListener;

// Exported constructors.

        /**
         * Construct a new view proxy.
         *
         * @param  socket  Socket.
         */
        public ViewProxy (Socket socket) throws IOException {
            this.socket = socket;
            socket.setTcpNoDelay (true);
            out = new DataOutputStream (socket.getOutputStream());
            in = new DataInputStream (socket.getInputStream());
        }

// Exported operations.

        /**
         * Set the view listener object for this view proxy.
         *
         * @param  viewListener  View listener.
         */
        public void setViewListener (ViewListener viewListener) {
        	if (this.viewListener == null) {
        		this.viewListener = viewListener;
        		new ReaderThread() .start();
        	} else {
                this.viewListener = viewListener;
            }
        }

        /**
         * Set the visibility of a ball
         * 
         * @param i		The ball to set
         * @param b		Whether it is set to visible or not
         */
    	public void setBall(int i, boolean b) throws IOException{
			out.writeByte('B');
			out.writeByte(i);
			out.writeBoolean(b);
			out.flush();
    	}

    	/**
    	 * Set the visibility of a stick
    	 * 
    	 * @param i		The stick to set
    	 * @param b		Whether is is set to visible or not
    	 */
    	public void setStick(int i, boolean b) throws IOException {
			out.writeByte('S');
			out.writeByte(i);
			out.writeBoolean(b);
			out.flush();
    	}

        /**
         * Report that the message has changed
         *
         * @param c		The new message code
         * @param s		A string for the new message if necessary
         */
        public void setMessage (int c, String s) throws IOException {
			out.writeByte ('M');
			out.writeByte(c);
			out.writeUTF(s);
            out.flush();
        }
        
    	/**
    	 * Enable the board
    	 */
    	public void enableNGButton() throws IOException {
    		out.writeByte('N');
    		out.flush();
    	}
        
    	/**
    	 * Report that the new game button was pressed to clear the board
    	 */
    	public void boardCleared() throws IOException {
    		out.writeByte('C');
    		out.flush();
    	}

    	/**
    	 * Ends the players' games
    	 */
    	public void endGame() throws IOException {
    		out.writeByte('E');
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
                    String name;
                    int i;
                    byte b = in.readByte();
                    switch (b) {
                        case 'J':
                            name = in.readUTF();
                            viewListener.join (ViewProxy.this, name);
                            break;
						case 'B':
							i = in.readByte();
							viewListener.ballClick(i, ViewProxy.this);
							break;
						case 'S':
							i = in.readByte();
							viewListener.stickClick(i, ViewProxy.this);
							break;
						case 'C':
							viewListener.clearBoard();
							break;
						case 'W':
							viewListener.windClosed();
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