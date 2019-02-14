import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Main {
    Main(){
        JFrame frame = new JFrame();

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();
        frame.setTitle("Snake Game");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        new Main();
    }
}
