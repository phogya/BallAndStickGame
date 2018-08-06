/**
 * Class BnsUI provides the graphical user interface for the game of Balls and
 * Sticks.
 *
 * @author  Peter Hogya
 * @version 16-Nov-2017
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class BnsUI implements ModelListener {

// Hidden constants.

	private static final int GAP = 10;

// Hidden data members.

	private JFrame frame;
	private JBoard board;
	private JTextField messageField;
	private JButton newGameButton;

	private ViewListener viewListener;

// Hidden constructors.

	/**
	 * Construct a new game UI.
	 *
	 * @param  name  Player's name.
	 */
	private BnsUI
		(String name)
		{
		frame = new JFrame ("B & S -- "+name);
		JPanel panel = new JPanel();
		panel.setLayout (new BoxLayout (panel, BoxLayout.Y_AXIS));
		panel.setBorder (BorderFactory.createEmptyBorder (GAP, GAP, GAP, GAP));
		frame.add (panel);

		board = new JBoard();
		board.setFocusable (false);
		panel.add (board);
		panel.add (Box.createVerticalStrut (GAP));

		messageField = new JTextField (1);
		panel.add (messageField);
		messageField.setEditable (false);
		messageField.setFocusable (false);
		panel.add (Box.createVerticalStrut (GAP));

		newGameButton = new JButton ("New Game");
		newGameButton.setAlignmentX (0.5f);
		newGameButton.setFocusable (false);
		panel.add (newGameButton);

		frame.pack();
		frame.setVisible (true);
		
		// Handling newGameButton
		newGameButton.setEnabled(false);
		newGameButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                try {
					doClearBoard();
				} catch (IOException e1) {
					// Should never reach here
				}
            }
        });
		
		// Handling boardListener
		board.setBoardListener(new BoardListener() {
			public void ballClicked(int i) {
				try {
					doBallClick(i);
				} catch (IOException e) {
					// Should never reach here
				}
			}
			public void stickClicked(int i) {
				try {
					doStickClick(i);
				} catch (IOException e) {
					// Should never reach here
				}
			}
			
		});
		
		// Handle window closing
		frame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				try {
					windClosed();
				} catch (IOException e1) {
					// Should never reach here
				}
			}
			// Unused methods
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
		});
	}

	// Exported operations.

    /**
     * An object holding a reference to a BnsUI.
     */
    private static class BnsUIRef {
        public BnsUI ui;
    }

    /**
     * Construct a new BnsUI.
     *
     * @param  name     player name.
     */
    public static BnsUI create (final String name) {
        final BnsUIRef ref = new BnsUIRef();
        onSwingThreadDo (new Runnable()
        {
            public void run()
            {
                ref.ui = new BnsUI(name);
            }
        });
        return ref.ui;
    }

    /**
     * Set the view listener for this BnsUI.
     *
     * @param  viewListener  View listener.
     */
    public void setViewListener (final ViewListener viewListener) {
    	
        onSwingThreadDo (new Runnable()
        {
            public void run()
            {
                BnsUI.this.viewListener = viewListener;
            }
        });
    }
	
	/**
	 * Report that a ball was set
	 * 
	 * @param i		The ball to set
	 * @param b		The value to set the ball to
	 */
	public void setBall(int i, boolean b) throws IOException {
		onSwingThreadDo(new Runnable() {
			public void run() {
				board.setBallVisible(i, b);
			}
		});
	}

	/**
	 * Report that a stick was set
	 * 
	 * @param i		The stick to set
	 * @param b		The value to set the stick to
	 */
	public void setStick(int i, boolean b) throws IOException {
		onSwingThreadDo(new Runnable() {
			public void run() {
				board.setStickVisible(i, b);
			}
		});
	}
	
	
	
	/**
	 * Report that the window was closed
	 * @throws IOException 
	 */
	public void windClosed() throws IOException {
		viewListener.windClosed();
	}
	
	/**
	 * Ends the client's game
	 */
	public void endGame() throws IOException {
		System.exit(1);
	}
	
	/**
	 * Changes the message in the players message field.
	 * Also handles turn setting and starting the game.
	 * 
	 * @param s		The message code
	 * @param name	The name of the other player, if necessary
	 */
	public void setMessage(int c, String s) throws IOException {
		
		onSwingThreadDo(new Runnable() {
			public void run() {
				// Message code 1
				if(c == 1) {
					messageField.setText("Waiting for partner");
				}
				
				// Message code 2
				if(c == 2) {
					messageField.setText("Your turn");
				}
				
				// Message code 3
				if(c == 3) {
					messageField.setText(s + "'s turn");
				}
				
				// Message code 4
				if(c == 4) {
					messageField.setText("You win!");
				}
				
				// Message code 5
				if(c == 5) {
					messageField.setText(s + " wins!");
				}
			}
		});
	}
	
	/**
	 * Enables the new game button
	 */
	public void enableNGButton() throws IOException {
		
		onSwingThreadDo(new Runnable() {
			public void run() {
				newGameButton.setEnabled(true);
			}
		});
	}
	
	/**
	 * Report that a ball was clicked on the board
	 * 
	 * @param i		The ball that was clicked
	 * @throws IOException
	 */
	public void doBallClick(int i) throws IOException {
		viewListener.ballClick(i, null);
	}

	/**
	 * Report that a stick was clicked on the board
	 * 
	 * @param i		The stick that was clicked
	 * @throws IOException
	 */
	public void doStickClick(int i) throws IOException {
		viewListener.stickClick(i, null);
		
	}

	/**
	 * Report that the new game button was pressed
	 * 
	 * @throws IOException
	 */
	public void doClearBoard() throws IOException {
		viewListener.clearBoard();
	}

    /**
     * Execute the given runnable object on the Swing thread.
     */
    private static void onSwingThreadDo
    (Runnable task)
    {
        try
        {
            SwingUtilities.invokeAndWait(task);
        }
        catch (Throwable exc)
        {
            exc.printStackTrace (System.err);
            System.exit (1);
        }
    }
}
