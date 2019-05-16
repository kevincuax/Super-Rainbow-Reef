import java.awt.*;

public class Wall extends GameObject{
    Image img;
    private int x, y;
    public Wall(int x, int y, ID id, Image img){
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.img = img;
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(img, this.getX(), this.getY(), null);
        this.setWidth(img.getWidth(null));
        this.setHeight(img.getHeight(null));

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }



}
