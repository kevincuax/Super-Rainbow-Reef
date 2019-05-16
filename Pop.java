import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

import static javax.imageio.ImageIO.read;

public class Pop extends GameObject {
    private double speed = 5.0;
    private double angle = Math.PI/4;
    private int score;
    private int lives;
    private boolean inLaunch;
    private int x, y;
    Handler handler;
    private String direction = "downUp_leftRight";


    public Pop(int x, int y, ID id, Image img, Handler handler){
        super(x, y, id);
        this.img=img;
        this.x = x;
        this.handler = handler;
        this.y = y;
        this.score = 0;
        this.lives = 100;
        this.inLaunch = false;
    }

    public boolean isInLaunch() {
        return inLaunch;
    }

    public void setInLaunch(boolean inLaunch) {
        this.inLaunch = inLaunch;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    Image img;





    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), 35, 35);
    }


    @Override
    public void draw(Graphics g) throws IOException {

        this.setWidth(img.getWidth(null));
        this.setHeight(img.getHeight(null));
        this.checkBorder();
        g.drawImage(img, this.getX(), this.getY(), null);



    }

    public void update(){

    }

    public String getDirection() { return direction; }


//    public void collision(Pop pop){
//        int dx = (int)Math.abs(Math.cos(pop.getAngle()* pop.getSpeed()));
//        int dy = (int) (Math.sin(pop.getAngle())*pop.getSpeed());
//        if(pop.getDirection() == "downUp_leftRight") {
//            pop.setX(pop.getX() + dx);
//            pop.setY(pop.getY() - dy);
//        }
//    }

    public void checkBorder() {
        if(!this.isInLaunch()) {
            if (this.getX() < 55)
                this.setX(55);
            if (this.getX() > 575)
                this.setX(575);
        }
    }

    public void collision(Pop pop) {
        // change in x and y coordinate
        int dx = (int) Math.abs(Math.cos(pop.getAngle()) * pop.getSpeed());
        int dy = (int) (Math.sin(pop.getAngle()) * pop.getSpeed());
//        if(dx>4){
//            if(pop.getSpeed() < -6.9)
//                pop.setSpeed(-6.5);
//        }
//        if(dy > 4){
//            if(pop.getSpeed() > 6.8){
//                pop.setSpeed(6.5);
//            }
//
//        }
//
//        if(dy < -4){
//            if(pop.getSpeed() < -6.99){
//                pop.setSpeed(-6.5);
//            }
//        }



        // down up left right
        if(pop.getDirection() == "downUp_leftRight") {
            pop.setX(pop.getX() + dx);
            pop.setY(pop.getY() - dy);
        }

        // down up right left
        if(pop.getDirection() == "downUp_rightLeft") {
            pop.setX(pop.getX() - dx);
            pop.setY(pop.getY() - dy);
        }

        // up down left right
        if(pop.getDirection() == "upDown_leftRight") {
            pop.setX(pop.getX() + dx);
                pop.setY(pop.getY() + dy);
        }

        // up down right left
        if(pop.getDirection() == "upDown_rightLeft") {
            pop.setX(pop.getX() - dx);
                pop.setY(pop.getY() + dy);

        }
        // collision wall on the left
        if(pop.getX() < 20) {

            // up down right left
            if(pop.getDirection() == "upDown_rightLeft") {
                pop.setDirection("upDown_leftRight");
                pop.setAngle(Math.PI - pop.getAngle());
                double newSpeed = pop.getSpeed()-2;
                pop.setSpeed(newSpeed);

            }

            // down to up, right to left
            if(pop.getDirection() == "downUp_rightLeft") {
                // update direction
                pop.setDirection("downUp_leftRight");
                // update angle
                pop.setAngle(Math.PI - pop.getAngle());
                // speed down
                double newSpeed = pop.getSpeed()-2;
                    pop.setSpeed(newSpeed);
            }
        }

        //walls

        // collision wall on the right
        if(pop.getX() > 580) {

            // up down left right
            if(pop.getDirection() == "upDown_leftRight") {

                pop.setDirection("upDown_rightLeft");
                pop.setAngle(Math.PI - pop.getAngle());
                double newSpeed = pop.getSpeed()-2;
                    pop.setSpeed(newSpeed);
            }

            // down up left right
            if(pop.getDirection() == "downUp_leftRight") {
                pop.setDirection("downUp_rightLeft");
                pop.setAngle(Math.PI - pop.getAngle());
                double newSpeed = pop.getSpeed()-2;
                    pop.setSpeed(newSpeed);
            }
        }

        // collision wall on the top
        if(pop.getY() < 20) {


//            pop.setY(40);
            // down to up, left to right
            if(pop.getDirection() == "downUp_leftRight") {
                // update direction
                pop.setDirection("upDown_leftRight");
                // update angle
                pop.setAngle(Math.PI - pop.getAngle());
                // speed down
                double newSpeed = pop.getSpeed()-2;
                    pop.setSpeed(newSpeed);


            }

            // down to up, right to left
            if(pop.getDirection() == "downUp_rightLeft") {
                // update direction
                pop.setDirection("upDown_rightLeft");
                // update angle
                pop.setAngle(Math.PI - pop.getAngle());
                // speed down
                double newSpeed = pop.getSpeed()-2;
                    pop.setSpeed(newSpeed);
            }
        }
    }
}
