package simon;

//import java.awt.Color;
import java.awt.*;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// 
// Created by Sleepyhead08
// 
public class Simon extends JPanel implements KeyListener, MouseListener, MouseMotionListener, ActionListener {

    public static int tick;
    public static final int TICK = 20;
    public static final int FPS = 1000 / TICK;
    public int counter = 0;
    public int counter2 = 0;
    public int playerCounter = 0;
    public int playerNumber = 0;
    public static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public JFrame frame;
    public boolean computerTurn = true;
    public Random randy = new Random();
    Timer timer = new Timer(20/*change to vary frequency*/, this);
    public ArrayList<Integer> colorNumberList = new ArrayList();
    public boolean playerTurn = false;
    public boolean firstTurn = true;
    public boolean startScreen = true;
    public boolean gameScreen = false;
    public boolean firstGameScreen = true;
    public Image simonPic;
    public Font font = new Font("Arial", Font.BOLD, 30);
    public Color blueClicked = new Color(0, 191, 255);
    public Color redClicked = new Color(255, 106, 106);
    public Color greenNormal = new Color(0, 128, 0);
    public Color yellowNormal = new Color(255, 165, 0);
    public int level = 1;
    public boolean blueDifferent = false;
    public boolean redDifferent = false;
    public boolean greenDifferent = false;
    public boolean yellowDifferent = false;
    public boolean gameOver = false;

    public static void main(String[] args) {
        Simon project = new Simon("Title");
    }

    public Simon(String title) {
        try {
            simonPic = ImageIO.read(getClass().getResource("Simon.png"));
        } catch (IOException ex) {
            Logger.getLogger(Simon.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame = new JFrame(title);
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(this);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        //timer.start();
        colorNumberList.add(randy.nextInt(4));
        colorNumberList.add(randy.nextInt(4));
        colorNumberList.add(randy.nextInt(4));

    }

    public void paint(Graphics g) {
        super.paint(g);
        if (startScreen) {
            g.setColor(Color.red);
            g.fillRect(0, 0, 800, 800);
            g.drawImage(simonPic, 300, 0, 150, 200, this);
            g.setColor(Color.green);
            g.setFont(font);
            g.drawString("Simon", 300, 225);
            g.drawString("Press enter to continue", 200, 400);
        }
        if (gameScreen) {
            if (firstGameScreen) {
                timer.start();
                firstGameScreen = false;
            }
            g.drawString("Level: " + level, 650, 100);
            gameColors(g);
            if (computerTurn) {
                g.setColor(Color.black);
                g.drawString("Watch the colors and pay attention to the order they light up", 50, 50);
                for (int i = colorNumberList.size(); i < level + 2; i++) {
                    colorNumberList.add(randy.nextInt(4));
                }
            }
            if (playerTurn) {
                g.setColor(Color.black);
                g.drawString("Press the colors in the same order", 50, 50);
            }

        }
        if (gameOver) {
            g.drawString("YOU LOSE", 350, 350);
        }
        if (playerCounter == colorNumberList.size()) {
            playerTurn = false;
            computerTurn = true;
            level++;
            playerCounter = 0;
            blueDifferent = false;
            redDifferent = false;
            greenDifferent = false;
            yellowDifferent = false;
        }

        repaint();
    }

    public void gameColors(Graphics g) {
        if (!blueDifferent) {
            g.setColor(Color.blue);
        } else {
            g.setColor(blueClicked);
        }
        g.fillRect(100, 100, 300, 300); //Draw blue in top left
        if (!redDifferent) {
            g.setColor(Color.red);
        } else {
            g.setColor(redClicked);
        }
        g.fillRect(400, 100, 300, 300); //Draw red in top right
        if (!greenDifferent) {
            g.setColor(greenNormal);
        } else {
            g.setColor(Color.green);
        }
        g.fillRect(100, 400, 300, 300); //Draw green in bottom left
        if (!yellowDifferent) {
            g.setColor(yellowNormal);
        } else {
            g.setColor(Color.yellow);
        }
        g.fillRect(400, 400, 300, 300); //Draw yellow in bottom right
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(69);
        }

        if (startScreen) {
            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                startScreen = false;
                gameScreen = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (me.getX() >= width / 2 - 100 && me.getX() <= width / 2 + 100 && me.getY() >= height / 2 - 100 && me.getY() <= height / 2 + 100) {
            //when the mouse clicks the center, execute this code
        }
        if (playerTurn) {
            if (me.getX() > 100 && me.getX() < 700 && me.getY() > 100 && me.getY() < 700) {
                if (me.getX() > 100 && me.getX() < 400 && me.getY() > 100 && me.getY() < 400) {
                    playerNumber = 0;
                    blueDifferent = true;
                    redDifferent = false;
                    greenDifferent = false;
                    yellowDifferent = false;
                }
                if (me.getX() > 400 && me.getX() < 700 && me.getY() > 100 && me.getY() < 400) {
                    playerNumber = 1;
                    blueDifferent = false;
                    redDifferent = true;
                    greenDifferent = false;
                    yellowDifferent = false;
                }
                if (me.getX() > 100 && me.getX() < 400 && me.getY() > 400 && me.getY() < 700) {
                    playerNumber = 2;
                    blueDifferent = false;
                    redDifferent = false;
                    greenDifferent = true;
                    yellowDifferent = false;
                }
                if (me.getX() > 400 && me.getX() < 700 && me.getY() > 400 && me.getY() < 700) {
                    playerNumber = 3;
                    blueDifferent = false;
                    redDifferent = false;
                    greenDifferent = false;
                    yellowDifferent = true;
                }
                if (playerNumber != colorNumberList.get(playerCounter)) {
                    gameOver = true;
                    gameScreen = false;
                }

                playerCounter++;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent me) {
        //runs whenever the mouse is moved across the screen, same methods as mouseClicked
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick++;
        if (gameScreen) {
            if (computerTurn) {
                if (tick % (FPS * 1) == 0) {
                    counter2++;
                    blueDifferent = false;
                    redDifferent = false;
                    greenDifferent = false;
                    yellowDifferent = false;
                    if (counter2 % 2 == 0) {
                        if (counter < colorNumberList.size()) {
                            if (colorNumberList.get(counter) == 0) {
                                blueDifferent = true;
                            }
                            if (colorNumberList.get(counter) == 1) {
                                redDifferent = true;
                            }
                            if (colorNumberList.get(counter) == 2) {
                                greenDifferent = true;
                            }
                            if (colorNumberList.get(counter) == 3) {
                                yellowDifferent = true;
                            }
                            counter++;
                        } else {
                            counter = 0;
                            computerTurn = false;
                            playerTurn = true;
                        }
                    }
                }

            }
        }
    }

}
