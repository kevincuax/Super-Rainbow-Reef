import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Katch extends GameObject implements KeyListener {
    private int x;
    private int y;
    private double speedIncrease;
    protected float velX = 0, velY = 0;
    private  ID id;
    Image img;
    Handler handler;
    private boolean canShoot;
    public Katch(int x, int y, ID id, Image img, Handler handler) {
        super(x, y, id);
        this.x=x;
        this.y=y;
        this.speedIncrease = 0.2;
        this.id = id;
        this.img=img;
        this.handler = handler;
        this.canShoot = true;

    }

    public void update(){
        y+=velY;
        x+=velY;

    }
    public void updatePos(Pop pop){
        int dx = (int) Math.abs(Math.cos(pop.getAngle()) * pop.getSpeed());
        int dy = (int) (Math.sin(pop.getAngle()) * pop.getSpeed());
        pop.setX(pop.getX() + dx);
        pop.setY(pop.getY() - dy);
    }



    @Override
    public void draw(Graphics g){
        this.setWidth(img.getWidth(null));
        this.setHeight(img.getHeight(null));
        this.checkBorder();
        g.drawImage(img, this.getX(), this.getY(), null);

    }

    public void outOfBounds() {
        if(this.getX() < 20)
            this.setX(20);
        if(this.getX() > 540)
            this.setX(540);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }



    public void checkBorder() {
        if(this.getX() < 20)
            this.setX(20);
        if(this.getX() > 540)
            this.setX(540);
    }


    public double getSpeedIncrease() {
        return speedIncrease;
    }

    public void setSpeedIncrease(double speedIncrease) {
        this.speedIncrease = speedIncrease;
    }

    public void collision(Pop pop) {
        // change in x and y coordinate
//        int dx = (int) Math.abs(Math.cos(pop.getAngle()) * pop.getSpeed());
//        int dy = (int) (Math.sin(pop.getAngle()) * pop.getSpeed());
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
//        if(dy < -4){
//            if(pop.getSpeed() < -6.99){
//                pop.setSpeed(-6.5);
//            }
//        }
//        System.out.println("dx: " + dx + "dy: " +  dy + "speed: " + pop.getSpeed() + "angle: "+ Math.toDegrees(pop.getAngle()) + "direction: " + pop.getDirection());

        if(this.getId() == ID.Katch) {
            // pop hits katch, up
            // pop hit left side of katch
//            if((pop.getX() > this.getX() - pop.getWidth()) && (pop.getX() < this.getX() + (this.getWidth()/2)) && (pop.getY() > this.getY() - pop.getHeight())){
//                System.out.println("LEFT OF KATCH");
//
//                if(pop.getDirection() == "upDown_leftRight"){
//                    System.out.println("LEFT OF KATCH LEFT TO RIGHT");
//                    pop.setX(pop.getX() + dx);
//                    pop.setY(pop.getY() - dy);
//                    pop.setAngle(Math.PI - pop.getAngle());
//
//                    pop.setDirection("downUp_rightLeft");
//                }
//                if(pop.getDirection() == "upDown_rightLeft"){
//                    System.out.println("LEFT OF KATCH RIGHT TO LEFT");
//                    pop.setX(pop.getX() + dx);
//                    pop.setY(pop.getY() - dy);
//                    pop.setAngle(Math.PI - pop.getAngle());
//
//                    pop.setDirection("downUp_leftRight");
//                }
//            }
           if(pop.getX() > this.getX() - pop.getWidth() && pop.getX() < this.getX() + this.getWidth() && pop.getY() > this.getY() - pop.getHeight()) {

                // up to down, left to right
                if(pop.getDirection() == "upDown_leftRight") {
                    if((pop.getX() > this.getX() - pop.getWidth()) && (pop.getX() < this.getX() + (this.getWidth()/2)) && (pop.getY() > this.getY() - pop.getHeight())){
                        updatePos(pop);
                        pop.setDirection("downUp_rightLeft");
                    }

                    updatePos(pop);
                    pop.setDirection("downUp_leftRight");
                }

                // up to down, right to left
                if(pop.getDirection() == "upDown_rightLeft") {
                    updatePos(pop);
                    pop.setDirection("downUp_rightLeft");
                }

                // update angle
                pop.setAngle(Math.PI - pop.getAngle());

                // speed up
//                if(pop.getSpeed() < 0) {
//                    pop.setSpeed(-pop.getSpeed() + speedIncrease);
//                }
//                else
//                    pop.setSpeed(pop.getSpeed() + speedIncrease);

                pop.setSpeed(7);
            }
        }


    }
    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setCanShootOff(){
        canShoot = false;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShootOn(){
        canShoot = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W){
            velY = 0;
        }
    }


    public  Rectangle getBounds(){
        return new Rectangle(x, y, 80, 30);

    }

}
