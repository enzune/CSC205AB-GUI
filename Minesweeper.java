// File authored by Jorden Magnuson
// If you make a contribution, don't forget to add your name above.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Minesweeper {

    public static void startPanel(){
        // I make-a-da-frame-a
        JFrame frame = new JFrame("MINESWEEPER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startPanel = new JPanel();
        frame.add(startPanel);
        
        Button newGame = new Button("New Game");
        newGame.setPreferredSize(new Dimension(160, 40));
        newGame.setBackground(Color.green);
        newGame.addActionListener(e -> newBttnClicked());
        
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

    public static void newBttnClicked() {
        System.out.println("starting new game!");
    }

    public static void quitGame() {
        System.exit(0);
    }
    
    public static void main(String[] args) {
        startPanel();
    }
}
