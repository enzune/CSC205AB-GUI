import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class Main {
    public static void main(String[] args) {
        JFrame startPoint = new JFrame();
        startPoint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startPoint.setVisible(true);
        startPoint.setPreferredSize(new Dimension(600,400));
        startPoint.setMinimumSize(new Dimension(600,400)); // initializing the frame and setting base operations.

        JPanel Grid = new JPanel();
        startPoint.add(Grid); // Grid needs to be built so that we can display the game space

        JButton reset = new JButton("New Game");
        startPoint.add(reset); //button needs to be moved off the frame because it is taking up the whole space.
        reset.setPreferredSize(new Dimension(100,80));
        reset.addMouseListener(new MouseAdapter() {
            //add something here for the mouse press to do.
        });
    }
}
