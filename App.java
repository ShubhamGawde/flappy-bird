import javax.swing.*;
import java.awt.*;

public class App {
    static int width = 360;
    static int height = 640;

    public static void main(String[] args){
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Flappy Bird");
        jFrame.setSize(width, height);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBird flappyBird = new FlappyBird();
        flappyBird.setRequestFocusEnabled(true);
        jFrame.add(flappyBird);
        jFrame.pack();
        jFrame.setVisible(true);

    }
}
