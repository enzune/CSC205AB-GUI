//// File authored by Jorden Magnuson
// If you make a contribution, don't forget to add your name above.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Minesweeper {

    // the frame for the starter.
    public static JFrame frame = new JFrame("MINESWEEPER");
    public static Button bombGrid[][]; // this might need to be tweaked insofar as type but for now it's fine.
    public static int bombs = 16; // The value was decided to be about 20% of board size.

    // This function builds the grid of buttons and decides if they are bombs.
    public static void buildGrid() {
        bombGrid = new Button[9][9];
    }

    public static void startPanel() {
        // I make-a-da-frame-a
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startPanel = new JPanel();
        frame.add(startPanel);
        
        Button newGame = new Button("New Game");
        newGame.setPreferredSize(new Dimension(160, 40));
        newGame.setBackground(Color.green);
        newGame.addActionListener(e -> newBttnClicked());

        /*
         * leaving this quit button here even though it's kinda useless
         * because i want to keep the potential for an 'options section open'
         */
        Button quitButton = new Button("Quit");
        quitButton.setPreferredSize(new Dimension(160, 40));
        quitButton.setBackground(Color.red);
        quitButton.addActionListener(e -> quitGame());
        
        startPanel.add(newGame);
        startPanel.add(quitButton);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /*
     * This function creates a new game frame once new game is clicked. 
     * It then generates a grid of buttons and hides bombs among them.
     */
    public static void newBttnClicked() {
        frame.dispose();
        GridLayout gameGrid = new GridLayout(16,16);
        
        // the gameFrame needs some more tweaks but that comes after the grid is finalized.
        JFrame gameFrame = new JFrame("MINESWEEPER");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(gameGrid);

        JPanel gamePanel = new JPanel();
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public static void quitGame() {System.exit(0);}
    public static void main(String[] args) {startPanel();}
}
