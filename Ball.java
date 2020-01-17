package pong;

import javax.print.attribute.standard.MediaSize;
import java.awt.*;
import java.util.Random;

public class Ball {
    public int x, y, width=25, height =25;//25
    public int mX, mY; //motion x motion y
    public Random ranodm;
    public int hitsNum; //co 5 hits bedzie poruszac sie szybciej kulka

    private Pong pong;

    public Ball(Pong pong){
        this.pong = pong;
        this.ranodm = new Random();

        newB();

    }
    public int checkCollision(Paddle paddle){
        if(this.x < paddle.x +  paddle.width && this.x +width >paddle.x && this.y < paddle.y +  paddle.height && this.y +height>paddle.y){
            return 1; //trafia w belke czyli odbicie
        }
        else if (paddle.x > x + 15 && paddle.paddleNum ==1 || paddle.x <x - width && paddle.paddleNum==2){
                              //+ width
            return 2; //nie trafila czyli  score++
        }
//        else if (paddle.x > x || paddle.x < x +width)
//        if()//jesli uderza w
//        {
//            if (paddle.y > + y + height || paddle.y + height < y) {
//                return 1; //belka
//            }else{
//                return 2; //sciana czyli score ++
//            }
//        }
        return 0; //nothing
    }
    public void update(Paddle paddle1, Paddle paddle2){
        int speed=5; //za wolno sie poruszala
        this.x += mX * speed;
        this.y += mY * speed;
        if(this.y+ height > pong.height || this.y <0){ //przy uderzeniu w sciany gora dol
            if(this.y < 0) { // gdy kuleczka uderzy w sciane gorna
                this.mY = ranodm.nextInt(3);// 4
                this.y = 0;
            }
            else{ // gdy kuleczka uderzy w sciane dolna
                this.mY = -ranodm.nextInt(3);
                
            }
        }


        if(checkCollision(paddle1) == 1){//trafia w belke 1
            this.mX = 1+(hitsNum/5);
            this.mY = -2 + ranodm.nextInt(3);//4
            //rzeby kuleczka gora dol randomowo odbicie
            if (mY == 0)
                mY = 1;
            hitsNum++;

        } else if(checkCollision(paddle2) == 1){
            this.mX = -1-(hitsNum/5);
//            this.mX = ranodm.nextInt(2)-1;//usunac potem
            this.mY = -2 + ranodm.nextInt(3);//4
            //rzeby kuleczka gora dol randomowo odbicie
            if (mY == 0)
                mY = 1;
            hitsNum++;

        }
        if (checkCollision(paddle1)==2){//trafia w sciane
            paddle2.score++;
            newB();
            //init nowa kulke
        }else if (checkCollision(paddle2)==2){//trafia w sciane
            paddle1.score++;
            newB();
            //init nowa kulke
        }

    }
    public void render(Graphics g){
        if(Pong.pong.theme)
            g.setColor(Color.BLACK);
        else
            g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }

    public void newB()
    {
        this.hitsNum = 0;
        this.x = pong.width / 2 - this.width / 2;
        this.y = pong.height / 2 - this.height / 2;
        this.mY = -2 + ranodm.nextInt(4); // zeby ze srodka wylatywala pod katem
        if (ranodm.nextBoolean()) //czy kuleczka od poleci w lewo czy prawo
            mX=1;
        else
            mX=-1;
//        if(this.x+ width >pong.width || this.x <0)//gdy kulka uderzy w boczne sciany
//        {
//            System.out.println("d");
//        }
    }
}


