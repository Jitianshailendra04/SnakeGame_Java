

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;

public class GamePanel extends JPanel implements ActionListener{
//-->
    static final int SCREEN_WIDTH=700;
    static final int SCREEN_HEIGHT=700;
    static final int UNIT_SIZE =20;
    static final int GAME_UNITS =(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=90;
    final int []x =new int [GAME_UNITS];
    final int []y =new int [GAME_UNITS];
    int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction= 'R';
    boolean running=false;
    Timer timer ;
    Random random;

    //change made by me//
    GamePanel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    /// GAME START///
    public void startGame(){
        newApple();
        running= true;
        timer=new Timer(DELAY,this);
        timer.start();
    }

    //PAINT COMPOnNET//
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    // DRAW GRAPHICS///
    public void draw(Graphics g){
        if (running){
            g.setColor(Color.red);
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            for(int i=0;i<bodyParts;i++){
                  if(i==0){
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(168,88,9));
                      g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));

                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }

        }
        else{
            gameOver(g);
        }
    }
/// NEW APPLE//
        public void newApple() {
            appleX=random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
            appleY=random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        }
    /// MOVE///
    public void move ()
    {
        for (int i= bodyParts;i>0;i--)
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }


    }
    public void checkApple(){
if((x[0]==appleX)&&(y[0]==appleY)){
    bodyParts++;
    applesEaten++;
    newApple();
}

    }
    /// CHECK COLLISION///
    public void checkCollisions()
    {
//checks if head collied with the body//
        for(int i= bodyParts;i>0;i--){
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;

                break;
            }
        }
        //Check head collide with left Border;
        if (x[0] < 0){
            running=false;
        }
        //Check head collide with right Border;
        if (x[0] > SCREEN_WIDTH){
            running=false;
        }
        //Check head collide with top Border;
        if (y[0] < 0){
            running=false;
        }
        //Check head collide with Bottom Border;
        if (y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if(!running){
            timer.stop();
        }
    }


    ////GAME OVER
    public void gameOver(Graphics g){
//score
        g.setColor(Color.red);
        g.setFont(new Font("Verdana",Font.BOLD,18));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());

// Game over text
        g.setColor(Color.red);
        g.setFont(new Font("INK FREE",Font.BOLD,55));
        FontMetrics metrics2= getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }
    @Override

/// ACTION PERFORMED
    public void actionPerformed (ActionEvent e){
        if (running){
            move();
            checkApple ();
            checkCollisions();

        }
        repaint();
    }

    /// kEY ADAPTER
    public  class MyKeyAdapter extends KeyAdapter{
         @Override
        public void keyPressed(KeyEvent e) {
             switch (e.getKeyCode()) {
                 case KeyEvent.VK_LEFT -> {
                     if (direction != 'R') {
                         direction = 'L';
                     }
                 }
                 case KeyEvent.VK_RIGHT -> {
                     if (direction != 'L') {
                         direction = 'R';

                     }
                 }
                 case KeyEvent.VK_UP -> {
                     if (direction != 'D') {
                         direction = 'U';

                     }
                 }
                 case KeyEvent.VK_DOWN -> {
                     if (direction != 'U') {
                         direction = 'D';

                     }
                 }
             }
        }
    }}