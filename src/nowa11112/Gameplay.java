package nowa11112;

import com.sun.org.apache.regexp.internal.RECompiler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.Key;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;
    private int playerY = 550;

    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private MapGenerator map;


    public Gameplay() {
        map = new MapGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();


    }

    public void paint(Graphics g) {
        //map
        map.draw((Graphics2D)g);

        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //map
        map.draw((Graphics2D)g);


        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(692, 0, 3, 592);
        // the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, playerY, 100, 5);
        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,playerY,100,8))){
                ballYdir=-ballYdir;

            }
            A: for(int i=0; i<map.map.length;i++){
                for(int j=0; j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickWidth+80;
                        int brickY = i * map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19 <=brickRect.x|| ballposX+1>= brickRect.x+brickRect.width) {
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir=-ballYdir;
                            }
                                break A;
                        }
                    }
                }
            }
            //akcja lotu
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }


        if (e.getKeyCode() == KeyEvent.VK_W) {
            if (playerY < 10) {
                playerY = 10;
            } else {
                moveTop();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
    public void moveTop() {
        play = true;
        playerY -= 20;
    }
}
