package pong;

import javax.swing.*;//JFrame Timer
import java.awt.*; //Graphics
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Pong implements ActionListener, KeyListener {
    //static = tylko w klasie mozna uzywac nie w instancji
    public static Pong pong;
    public int width=1100, height=700; //Wymiary ekranu
    public Renderer renderer; //Plansza
    public Paddle p1; //player 1
    public Paddle p2; //player 2
    public boolean bot = false; //przy wyborze
    public boolean w, s, up, down; //przyciski czy wcisniete
    public boolean theme=false;
    public Ball ball;
    public int gameStatus = 0; //0 = Stoped, 1 = Paused, 2 = Playing

    public Pong()
    {
        Timer timer = new Timer(20, this);
        JFrame jf = new JFrame("Pong");

        renderer = new Renderer();

        jf.setSize(width+14, height+36); //+15 bo inaczej skraca ekran z prawej i dolu
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(renderer);
        jf.addKeyListener(this);
//        start();

        timer.start();
    }

    public void start(){ //rozpoczecie gry
        gameStatus = 2;
        p1 = new Paddle(this, 1);
        p2 = new Paddle(this, 2);
        ball = new Ball(this);
    }

    public void update() {

        if (w)
            p1.move(true);//jesli p1 wcisnie w

        if (s)
            p1.move(false);

        if (up)
            p2.move(true);//jesli p2 wcisnie up

        if (down)
            p2.move(false);
        ball.update(p1, p2);
    }
    public void render(Graphics2D g){
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(gameStatus == 0){ //if stoped
            g.setColor(Color.white);
            //dokonczyc napisy
            g.setFont(new Font("Courier",  Font.PLAIN, 50));//Helvetica Monaco Plain
            g.drawString("The Pong Game", width/2 -160, 70); //-75


            g.setFont(new Font("Courier",  Font.PLAIN, 30));
            g.drawString("Press Enter to play", width/2 -130, height/2 - 70);
            g.drawString("Press Shift to play with Bot", width/2 -180, height/2 - 20);
            JButton b=new JButton("Click Here");
            b.setBounds(50,100,95,30);
        }

        if (gameStatus == 2 || gameStatus == 1){ //if playing
            System.out.println("tu");
            if(theme) {

                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                g.setColor(Color.black);
                //score
                g.setFont(new Font("Courier",  Font.PLAIN, 50));
                g.drawString(String.valueOf(p1.score), width/2 -160, 70); //-75
                g.drawString(String.valueOf(p2.score), width/2 +160, 70); //-75
                //przerywana linia :
                g.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{11 }, 0));
                g.drawLine(width/2, 0, width/2, height);

            } else{
                g.setColor(Color.BLACK);
                g.fillRect(0,0, width, height);
                g.setColor(Color.WHITE);

                //score
                g.setFont(new Font("Courier",  Font.PLAIN, 50));
                g.drawString(String.valueOf(p1.score), width/2 -160, 70); //-75
                g.drawString(String.valueOf(p2.score), width/2 +160, 70); //-75

//                g.setStroke(new BasicStroke(5f));  //grubosc linii
                //przerywana linia :
                g.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{11 }, 0));
                g.drawLine(width/2, 0, width/2, height);}


                p1.render(g);
                p2.render(g);
                ball.render(g);
            }
            if(gameStatus == 1){ //PAUZA
                g.setColor(Color.white);
                g.setFont(new Font("Courier",  Font.PLAIN,50));
                g.drawString("PAUSED", width/2 - 103, height/2 -25);
            }


    }

    //gdy zostanie wygenerowane zdarzenie na obiekcie powiązanym z danym słuchaczem to sie wywola
    //czyli caly czas
    @Override//nie trzeba wywolywac
    public void actionPerformed(ActionEvent e)
    {
        if (gameStatus == 2){
            update(); //game on between 2 players
        }
        renderer.repaint();
    }

    public static void main(String[] args)
    {
	    pong = new Pong();
    }
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode(); //id of key
        if (key == KeyEvent.VK_W)
            w = true;
        if (key == KeyEvent.VK_S )
            s = true;
        if (key == KeyEvent.VK_UP)
            up=true;
        if (key == KeyEvent.VK_DOWN)
            down=true;

        if (key == KeyEvent.VK_1){
            if(theme)
                theme = false;
            else
                theme = true;
        }
        //wybory z MENU bot
        if (key == KeyEvent.VK_SHIFT && gameStatus== 0){
            bot = true;
            start();
        }

        //wybory z MENU p2
        if (key == KeyEvent.VK_ENTER){
            if (gameStatus == 0){ //jesli manu to gra
                start();
                bot = false;
            } else if (gameStatus == 1){ //jesli pauza to wznow
                gameStatus = 2;}
//            if (gameStatus == 2)
//                gameStatus = 1;
        }
        if(key ==KeyEvent.VK_SPACE){
            if(gameStatus == 2)//pauza jesli gra
                gameStatus = 1;
            else if(gameStatus == 1)
                gameStatus = 2;
        }

    }
    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode(); //id of key
        if (key == KeyEvent.VK_W)
            w = false;
        if (key == KeyEvent.VK_S )
            s = false;
        if (key == KeyEvent.VK_UP)
            up = false;
        if (key == KeyEvent.VK_DOWN)
            down = false;

    }
    @Override
    public void keyTyped(KeyEvent e){
    }
}
