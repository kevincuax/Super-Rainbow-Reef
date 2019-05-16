import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;

    public KeyInput(Handler handler){ // constructor takes in handler
        this.handler = handler; // use this so we keep the initial hanlder
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        for(int i =0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Katch){ // loop through handler objects until find player
                if(key==KeyEvent.VK_W) handler.setUp(true); // WASD key movement
                if(key==KeyEvent.VK_A) handler.setLeft(true);
                if(key==KeyEvent.VK_S) handler.setDown(true);
                if(key==KeyEvent.VK_D) handler.setRight(true);

            }
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for(int i =0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Katch){ // loop through handler objects until find player
                if(key==KeyEvent.VK_W) handler.setUp(false); // WASD key movement
                if(key==KeyEvent.VK_A) handler.setLeft(false);
                if(key==KeyEvent.VK_S) handler.setDown(false);
                if(key==KeyEvent.VK_D) handler.setRight(false);

            }
        }
    }
}
