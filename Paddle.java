package pong;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Graphics;
public class Paddle extends Rectangle2D.Float{
    public int paddleNum;
    public int x, y, width=35, height=220;
    public int score;
    public Paddle(Pong pong, int puddleNum)
    {
        this.paddleNum=puddleNum;
        if(paddleNum == 1){
            this.x = 15; //paddle dla lewej strony ekranu 0
        }
        if(paddleNum == 2){
            this.x = pong.width - width - 15; //player2 prawa strona ekranu
        }
        this.y = pong.height / 2 - this.height / 2; //wysokosc ekranu
    }

    public void render(Graphics g){
        if(Pong.pong.theme)
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public void move(boolean up){//porysznie sie belki
        int speed = 6;
        if(up){
            if(y-speed > 0){
                y-=speed;
            }else{
                y=0;
            }
        }else{
            if(y +height + speed < Pong.pong.height){
                y+=speed;}
            else{
                y=Pong.pong.height-height;}//-15 bo znowu wyjeższa za okienko
        }
    }

}
