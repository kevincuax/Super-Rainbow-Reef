import javax.swing.*;

public class Main extends JFrame{
        private Gameplay gameplay;

        public void start(){
            Gameplay gameplay = new Gameplay();
//            Thread thread = new Thread();
//            thread.start();

            this.add(gameplay);
            this.addKeyListener(gameplay);

            this.setTitle("dank memes");
            this.setSize(640, 480);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);
        }
    public static void main(String[] args){
//        JFrame obj = new JFrame();

//        obj.setBounds(10, 10, 700, 600);
//        obj.setTitle("Breakout Ball");
//        obj.setResizable(false);
//        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        obj.add(gameplay);
//        obj.setVisible(true);

        new Main().start();

    }
}
