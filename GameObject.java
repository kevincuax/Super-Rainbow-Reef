import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public abstract class GameObject {
    private int x;
    private int y;
    protected ID id;
    private boolean isVisible = true;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    private int width, height;

    private Image img;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public void checkBorder() {
        if(this.getX() < 20)
            this.setX(20);
        if(this.getX() > 540)
            this.setX(540);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void draw(Graphics g) throws IOException;

    public abstract Rectangle getBounds(); // collision detection





}
