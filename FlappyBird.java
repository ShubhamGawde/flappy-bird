import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;


public class FlappyBird extends JPanel implements ActionListener, KeyListener {

    int boardWidth = 360;
    int boardHeight = 640;

    //images
    Image bgImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Image gameOverImg;


    // bird configuration
    int birdheight = 34;
    int birdwidht = 24;
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;

    // pipe configuration
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;


    // game logic
    Bird bird;
    Pipe pipe;
    int velocityY = 0;
    int velocityX = -4;
    int gravity = 1;
    Timer gameLoop;
    Timer placePipeTimer;
    double score;
    boolean gameover;
    ArrayList<Pipe> pipes;


    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        bgImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("flappybirdbg.png"))).getImage();
        birdImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("flappybird.png"))).getImage();
        topPipeImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("toppipe.png"))).getImage();
        bottomPipeImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("bottompipe.png"))).getImage();
        gameOverImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("gameover.png"))).getImage();

        bird = new Bird(birdImg);
        gameover = false;
        score = 0;
        pipes = new ArrayList<>();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        placePipeTimer = new Timer(1500, e -> placePipe());
        placePipeTimer.start();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(bgImg, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(birdImg, bird.x, bird.y, birdwidht, birdheight, null);

        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameover) {
            g.drawImage(gameOverImg,boardWidth/4, boardHeight/2, 150, 70,null);
//            g.drawString("Score : " + String.valueOf((int) score), boardWidth/6, boardHeight/2);
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if(!pipe.isPassed && bird.x > pipe.x + pipe.width){

                pipe.isPassed = true;
                score += 0.5;
            }
            if (collision(bird, pipe))
                gameover = true;
        }

        if (bird.y > boardHeight) {
            gameover = true;
        }


    }

    public void placePipe() {
        int randomPipeY = (int) ((pipeY - pipeHeight / 4) - (Math.random() * pipeHeight / 2));
        int openSpace = boardHeight / 6;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openSpace;
        pipes.add(bottomPipe);

    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
                a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameover) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            velocityY = -9;
        if (gameover) {
            //restart game by resetting conditions
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            gameover = false;
            score = 0;
            gameLoop.start();
            placePipeTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    class Bird {
        int x = birdX;
        int y = birdY;

        int width = birdwidht;
        int height = birdheight;
        Image img;

        public Bird(Image img) {
            this.img = img;
        }
    }

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        boolean isPassed = false;
        Image img;

        public Pipe(Image img) {
            this.img = img;
        }
    }

}
