/**
 * Class Bns is the client main program for the Network Ball and
 * Sticks game.The command line arguments specify the host and 
 * port to which the server is listening for connections as well
 * as the name of the player for the game.
 * 
 * Usage: java Bns host port playername
 *
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Bns {

    /**
     * Main program.
     */
    public static void main (String[] args) throws Exception {
    	
    	// Check arguments
        if(args.length != 3) {
        	usage();
        }
        
        String host = args[0];
        int port = 0;
        String name = args[2];
        
        try {
        	port = Integer.parseInt (args[1]);
        } catch (NumberFormatException e) {
        	System.err.println("Port must be an integer.");
        	usage();
        }
        
        // Set up client
        Socket socket = new Socket();
        try {
        	socket.connect (new InetSocketAddress (host, port));
        } catch (ConnectException e) {
        	System.err.println("There must be a server running at the specified port.");
        	usage();
        }

        BnsModelClone model = new BnsModelClone();
        BnsUI view = BnsUI.create(name);
        ModelProxy proxy = new ModelProxy (socket);
        model.setModelListener (view);
        view.setViewListener (proxy);
        proxy.setModelListener (model);
        proxy.join (null, name);
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java Bns <host> <port> <playername>");
        System.exit (1);
    }
}