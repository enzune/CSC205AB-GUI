// File authored by Jorden Magnuson
// If you make a contribution, don't forget to add your name above.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Minesweeper extends MouseAdapter {

    // Some global variables that are very important. Yes I know it's messy.
    // Minesweeper should be its own class instantiated in the main, but I'm too lazy to  redo it all now.
    public static enum Difficulty {EASY,MEDIUM,HARD}
    public static JFrame difficultySelectFrame = new JFrame("MINESWEEPER");
    public static Button[][] buttonGrid;
    public static int[][] board;
    public static boolean[][] revealed;
    public static boolean[][] flagged;
    public static int boardSize;
    public static int bombs; // The value is now tied to difficulty enum.
    public static int cellsRevealed;
    public static boolean gameOver = false;
    public static JFrame gameFrame;

    //  This function initializes and works like a constructor
    public static void initializeBoard() {
        board = new int[boardSize][boardSize];
        revealed = new boolean[boardSize][boardSize];
        flagged = new boolean[boardSize][boardSize];

        // All becomes 0.
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
                flagged[i][j] = false;
                revealed[i][j] = false;
            }
        }
        Random rand = new Random();
        int bombsPlaced = 0;

        // place bombs until we have no more.
        while (bombsPlaced < bombs) {
            int x = rand.nextInt(boardSize);
            int y = rand.nextInt(boardSize);

            if(board[x][y] != -1) { // if not a bomb, make a bomb
                board[x][y] = -1; // -1 is the bomb value
                bombsPlaced++;

                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        // update the cells adjacent to bombs to be numbers.
                        if (x + i >= 0 && x + i < boardSize && y + j >= 0 && y + j < boardSize && board[x+i][y+j] != -1)
                            board[x + i][y + j]++;
                    }
                }
            }
        }
    }

    public static void revealCell(int row, int col) {
        if (revealed[row][col] || flagged[row][col]) {
            return;
        }

        revealed[row][col] = true;
        if (board[row][col] == -1) {
            gameOver = true;
            buttonGrid[row][col].setLabel("M");
            buttonGrid[row][col].setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "Game Over! You hit a mine.");
            gameOver();
            return;
        }
        updateButton(row, col);
        if (board[row][col] == 0) {
            cellsRevealed++;
            // Reveal neighboring cells if the cell is empty
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize && !revealed[newRow][newCol]) {
                        revealCell(newRow, newCol);
                        cellsRevealed++;
                    }
                }
            }
        }
        if (cellsRevealed == (boardSize - bombs) ) {
            gameWin();
        }
    }

    public static void flagCell(int row, int col) {
        if (revealed[row][col]) {
            return;
        }
        flagged[row][col] = !flagged[row][col];
        if (flagged[row][col]) {
            buttonGrid[row][col].setLabel("F");
        } else {
            buttonGrid[row][col].setLabel("");
        }
    }

    public static void updateButton(int row, int col) {
        if (!revealed[row][col]) return;

        if (board[row][col] == 0) {
            buttonGrid[row][col].setLabel("");
            buttonGrid[row][col].setBackground(Color.LIGHT_GRAY);
        } else if (board[row][col] > 0) {
            buttonGrid[row][col].setLabel(String.valueOf(board[row][col]));
            buttonGrid[row][col].setBackground(Color.LIGHT_GRAY);
        }
    }

    // Function creates the new game interface and prompts user for difficulty.
    public static void startPanel() {
        difficultySelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.PINK);
        difficultySelectFrame.add(startPanel);
        
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
        difficultySelectFrame.pack();
        difficultySelectFrame.setLocationRelativeTo(null);
        difficultySelectFrame.setVisible(true);
    }

    /*
     * This function creates a new game frame once a difficulty is selected.
     * The frame/grid/#bombs are based on the difficulty chosen.
     */
    public static void newBttnClicked(Difficulty level) {
        try {
            // Better to do this with windowEvents, but it's a school project not a product.
            difficultySelectFrame.dispose();

            // how many sides/bombs per difficulty, and pass that to the buttongrid.
            switch (level) {
                case EASY:
                    boardSize = 9;
                    bombs = 12;
                    break;
                case MEDIUM:
                    boardSize = 12; 
                    bombs = 28;
                    break;
                case HARD:
                    boardSize = 16;
                    bombs = 65;
                    break;
            }  

            GridLayout gameGrid = new GridLayout(boardSize,boardSize);
            buttonGrid = new Button[boardSize][boardSize];
            
            Dimension gameFrameDimension = new Dimension(1280,720);

            // the gameFrame needs some more tweaks but that comes after the grid is finalized.
            gameFrame = new JFrame("MINESWEEPER");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setPreferredSize(gameFrameDimension);

            JPanel gamePanel = new JPanel();
            gamePanel.setLayout(gameGrid);
            gamePanel.setPreferredSize(gameFrameDimension);
            gamePanel.setBackground(Color.gray);
            gameFrame.add(gamePanel);

            // populate the grid panel with buttons. Each button has a mouseListener.
            for(int row = 0; row < boardSize; row++) {

                Dimension bombButtonSizeDimension = new Dimension(20,20);

                for (int col = 0; col < boardSize; col++) {
                    buttonGrid[row][col] = new Button();
                    buttonGrid[row][col].setPreferredSize(bombButtonSizeDimension);
                    buttonGrid[row][col].setFont(new Font("Arial",Font.PLAIN, 12));

                    // beginning of mouseListener definition.
                    buttonGrid[row][col].addMouseListener(new MouseAdapter() {
                        // detect which mouse button was pressed
                        @Override
                        public void mousePressed(MouseEvent e) {
                            int buttonNum = e.getButton();
                            Button sourceButton = (Button) e.getSource();
                            int row = -1, col = -1;
                            
                            for (int i = 0; i < boardSize; i++) {
                                for (int j = 0; j < boardSize; j++) {
                                    if (sourceButton == buttonGrid[i][j]) {
                                        row = i;
                                        col = j;
                                        break;
                                    }
                                }
                            }// this method for finding source button may not work, must test.

                            switch (buttonNum) {
                                // if left mouse
                                case MouseEvent.BUTTON1:
                                    revealCell(row,col);
                                    break;
                                // if right mouse
                                case MouseEvent.BUTTON3:
                                    flagCell(row, col);
                                    break;
                            }
                        }
                    }); // end mouse listener definition
                    gamePanel.add(buttonGrid[row][col]);
                } // end inside loop
            } // end outside loop
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);

            initializeBoard();
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An unexpected error occurred.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void gameWin() {
        JOptionPane.showMessageDialog(null, "Game won!");
        gameOver();
    }

    public static void gameOver() {
        gameFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {startPanel();}
}
