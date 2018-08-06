/**
 * Class BnsServer is the server main program for the Network Ball and Sticks 
 * Game. The command line arguments specify the host and port to which the 
 * server should listen for connections.
 * 
 * Usage: java BnsServer host port
 * 
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BnsServer {

    /**
     * Main program.
     */
    public static void main (String[] args) throws Exception {
    	
    	// Check arguments
        if (args.length != 2) {
        	usage();
        }
        
        String host = args[0];
        int port = 0;
        
        try {
        	port = Integer.parseInt (args[1]);
        } catch (NumberFormatException e) {
        	System.err.println("Port must be an integer.");
        	usage();
        }
        
		ServerSocket serversocket = new ServerSocket();
        serversocket.bind (new InetSocketAddress (host, port));

        SessionManager manager = new SessionManager();

        for (;;)
        {
            Socket socket = serversocket.accept();
            ViewProxy proxy = new ViewProxy (socket);
            proxy.setViewListener (manager);
        }
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage()
    {
        System.err.println ("Usage: java BnsServer <host> <port>");
        System.exit (1);
    }
}