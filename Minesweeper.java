// File authored by Jorden Magnuson
// If you make a contribution, don't forget to add your name above.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Minesweeper {
    // the frame for the starter.
    public static enum Difficulty {EASY,MEDIUM,HARD}
    public static JFrame frame = new JFrame("MINESWEEPER");
    public static Button[][] buttonGrid; // this works now, but no action attached yet.
    public static int bombs; // The value is now tied to difficulty enum.

    /*  This function builds the grid and decides if they are bombs or numbers.
        They will be assigned to their matching buttons elsewhere.
        This function should not be called until after the first click to guarantee the player doesn't auto lose. 
    */
    public static void buildGrid(int rows, int cols) {
        while (bombs > 0) {
            int x = (int)Math.random()*10;
            int y = (int)Math.random()*10;
            //build the grid. Currently does nothing.
        }
    }

    public static void startPanel() {
        // I make-a-da-frame-a
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.PINK);
        frame.add(startPanel);
        
        // Creating 3 difficulty options for the player to choose from.
        Button easyButton = new Button("Easy");
        easyButton.setPreferredSize(new Dimension(160, 40));
        easyButton.setBackground(Color.green);
        easyButton.addActionListener(e -> newBttnClicked(Difficulty.EASY));

        Button mediumButton = new Button("Medium");
        mediumButton.setPreferredSize(new Dimension(160, 40));
        mediumButton.setBackground(Color.yellow);
        mediumButton.addActionListener(e -> newBttnClicked(Difficulty.MEDIUM));

        Button hardButton = new Button("Hard");
        hardButton.setPreferredSize(new Dimension(160, 40));
        hardButton.setBackground(Color.red);
        hardButton.addActionListener(e -> newBttnClicked(Difficulty.HARD));
        
        // Bundling it all up neatly into one menu.
        startPanel.add(easyButton);
        startPanel.add(mediumButton);
        startPanel.add(hardButton);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /*
     * This function creates a new game frame once a difficulty is selected.
     * The frame/grid/#bombs are based on the difficulty chosen.
     */
    public static void newBttnClicked(Difficulty level) {
        frame.dispose();
        int rows = 0, cols = 0;

        // how many sides/bombs per difficulty, and pass that to the buttongrid.
        switch (level) {
            case EASY:
                rows = 9;
                cols = 9;
                bombs = 12;
                break;
            case MEDIUM:
                rows = 12;
                cols = 12;
                bombs = 28;
                break;
            case HARD:
                rows = 16;
                cols = 16;
                bombs = 65;
                break;
        }  

        GridLayout gameGrid = new GridLayout(rows,cols);
        buttonGrid = new Button[rows][cols];
        
        Dimension gameFrameDimension = new Dimension(1280,720);

        // the gameFrame needs some more tweaks but that comes after the grid is finalized.
        JFrame gameFrame = new JFrame("MINESWEEPER");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setPreferredSize(gameFrameDimension);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(gameGrid);
        gamePanel.setPreferredSize(gameFrameDimension);
        gameFrame.add(gamePanel);

        // populate the grid panel with buttons.
        for(int row = 0; row < rows; row++) {
            Dimension bombButtonSizeDimension = new Dimension(20,20);
            for (int col = 0; col < cols; col++) {
                buttonGrid[row][col] = new Button("O");
                buttonGrid[row][col].setPreferredSize(bombButtonSizeDimension);
                gamePanel.add(buttonGrid[row][col]);
                // TODO: add an action per button that calls the check function.
                // Also need a way to differentiate right/left mouse.
            }
        }  
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }
    public static void main(String[] args) {startPanel();}
}
