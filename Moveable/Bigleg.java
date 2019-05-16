import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bigleg extends GameObject {
    int x, y;
    Image image;

    public Bigleg(int x, int y, ID id, Image image){
        super(x, y, id);
        this.x=x;
        this.y=y;
        this.id=id;
        this.image=image;

    }
    @Override
    public void draw(Graphics g){

        this.setWidth(image.getWidth(null));
        this.setHeight(image.getHeight(null));

        g.drawImage(image, this.getX(), this.getY(), null);

    }

    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), image.getWidth(null), image.getHeight(null));
    }
}



