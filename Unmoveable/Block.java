import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block extends GameObject {
    private Image img;
    private int x, y;


    public Block(int x, int y, ID id, Image img){
        super(x, y, id);
        this.x = x;
        this.y=y;
        this.img = img;


    }

    @Override
    public void draw(Graphics g){
//        try{
//            image = ImageIO.read(new File("Resources/Block1.gif"));
//        }
//        catch (IOException ex) {
//            System.out.println(ex.getMessage());
//
//        }

//
//        this.setImg(img);
//        this.setWidth(img.getWidth());
//        this.setHeight(img.getHeight());

        this.setWidth(img.getWidth(null));
        this.setHeight(img.getHeight(null));
        g.drawImage(img, this.getX(), this.getY(), null);

    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 32, 32);
    }
}
