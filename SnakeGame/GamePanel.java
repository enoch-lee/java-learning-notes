import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    static final int HEIGHT = 500, WIDTH = 500;
    static final int BOX_SIZE = 20;
    private LinkedList<Point> snake;
    private Point fruit;
    private Thread thread;
    private int direction = Direction.NO_DIRECTION;
    private Boolean running;
    private int score;

    GamePanel(){
        setFocusable(true);
        addKeyListener(this);
        setPreferredSize(new Dimension(HEIGHT, WIDTH));
        snake = new LinkedList<>();
        fruit = new Point();
        score = 0;
        generateDefaultSnake();
        generateFruit();
        start();
    }

    private void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop(){
        running = false;
        try{
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void paint(Graphics g){
        draw(g);
    }

    private void draw(Graphics g){
        drawGrid(g);
        drawFruit(g);
        drawSnake(g);
        drawScore(g);
    }

    private void drawScore(Graphics g){
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 0, HEIGHT + BOX_SIZE);
    }

    private void drawGrid(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT + BOX_SIZE);
        g.setColor(Color.BLACK);
        // g.fillRect(0, 0, WIDTH, HEIGHT);
        for(int x = 0; x <= WIDTH; x += BOX_SIZE){
            g.drawLine(x, 0, x, HEIGHT);
        }
        for(int y = 0; y <= HEIGHT; y += BOX_SIZE){
            g.drawLine(0, y, WIDTH, y);
        }
    }

    private void drawFruit(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(fruit.x * BOX_SIZE, fruit.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
    }

    private void drawSnake(Graphics g){
        g.setColor(Color.GREEN);
        for(Point p : snake){
            g.fillRect(p.x * BOX_SIZE, p.y * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        }

    }

    private void generateDefaultSnake(){
        score = 0;
        snake.clear();
        snake.add(new Point(5, 5));
        snake.add(new Point(5, 6));
        direction = Direction.NO_DIRECTION;
    }

    private void generateFruit(){
        fruit.x = (int)(Math.random() * (WIDTH / BOX_SIZE));
        fruit.y = (int)(Math.random() * (HEIGHT / BOX_SIZE));
        if(snake.contains(fruit)) generateFruit();
    }

    private void move(){
        if(direction == Direction.NO_DIRECTION) return;
        snake.removeFirst();
        Point head = snake.peekLast();
        int x = head.x, y = head.y;
        switch (direction){
            case Direction.RIGHT :
                ++x;
                break;
            case Direction.LEFT:
                --x;
                break;
            case Direction.DOWN:
                ++y;
                break;
            case Direction.UP:
                --y;
                break;
        }
        Point next = new Point(x, y);

        if(next.equals(fruit)){
            ++score;
            generateFruit();
            snake.add((Point)next.clone());
        }else if(x < 0 || y < 0 || x * BOX_SIZE >= WIDTH || y * BOX_SIZE >= HEIGHT || snake.contains(next)){
            // check if head goes over boundaries or collides with body parts
            generateDefaultSnake();
            generateFruit();
            return;
        }

        snake.add(next);
    }

    @Override
    public void run() {
        while(running){
            try{
                Thread.currentThread();
                Thread.sleep(100);
                repaint();
                move();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT && direction != Direction.LEFT){
            direction = Direction.RIGHT;
        }else if(key == KeyEvent.VK_LEFT && direction != Direction.RIGHT){
            direction = Direction.LEFT;
        }else if(key == KeyEvent.VK_UP && direction != Direction.DOWN){
            direction = Direction.UP;
        }else if(key == KeyEvent.VK_DOWN && direction != Direction.UP){
            direction = Direction.DOWN;
        }else if(key == KeyEvent.VK_SPACE){
            if(running){
                stop();
            }else{
                start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
