import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // for ball
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;
import javax.sound.sampled.AudioSystem;


public class Gameplay extends JPanel implements KeyListener, ActionListener, Runnable {
    private  boolean play = false;


    private Timer timer;
    private int delay = 8;
    private int drawStringY = 0;
    private int biglegCount;
    private int biglegCount2;
    private int biglegCount3;



    private boolean lvl1Beat;
    private  boolean resetForLvl2 = false;
    private boolean resetForLvl3 = false;
    private float radius = 200;
    private float x = 100f, y = 100f;
    private int playerX =310;
    private double newElapsedSeconds;
    private boolean wroteToScores;
    public static HashMap<String,Image> sprites;
//    private int ballPosX = 120;
//    private int ballPosY = 350;
//    private int ballXDir = -2;
//    private int ballYDir = -2;

    protected int velX = 0, velY = 0;


    Image image;

    ArrayList<Block> blocks;
    ArrayList<Block> blocks2;
    ArrayList<Block> blocks3;

    ArrayList<Wall> walls;
    ArrayList<Bigleg> biglegs;
    ArrayList<Bigleg> biglegs2;
    ArrayList<Bigleg> biglegs3;
    private Pop pop;
    private Katch katch;
    private Handler handler;
    private boolean isRunning = false;
    private Thread thread;
    private MusicPlayer player;
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    private boolean decRadius;
    private boolean glowOn;
    private Block bonusBlock;
    private boolean gameOver;
    private float alpha;
    private boolean lvl2Beat;
    private boolean gameWon;


    public Gameplay(){
        loadSprites();
        System.out.println("Here");
        blocks = new ArrayList<>();
        blocks2 = new ArrayList<>();
        blocks3 = new ArrayList<>();
        walls = new ArrayList<>();
        biglegs = new ArrayList<>();
        biglegs2 = new ArrayList<>();
        biglegs3 = new ArrayList<>();
        pop = new Pop(360, 380, ID.Pop, sprites.get("pop"), handler);


        katch = new Katch(320, 420, ID.Katch, sprites.get("katch"), handler);
        lvl1Beat = false;
        lvl2Beat = false;
        gameOver = false;
        gameWon = false;

        tStart = System.currentTimeMillis();
        tEnd = 0;
        tDelta = 0;
        alpha = 1f;
        newElapsedSeconds = 0;
        decRadius = false;
        glowOn = true;
        wroteToScores = false;

        this.addBlocks1();
        this.addBlocks2();
        this.addBlocks3();
        this.addWalls();
        this.addBiglegs();
        this.addBigleg2();
        this.addBigleg3();
        bonusBlock = new Block(0, 0, null, null );
//        getRandomBlock(blocks);

        player = new MusicPlayer("Music", "Sound_block", "Sound_katch", "what_was_that", "wow-mlg-sound-effect",
                "Sound_wall", "Sound_lost", "boo-sound", "supa-hot-fire-sound");
//        player.playSound("Resources/Music.wav");
        player.playSound("Music");

        biglegCount = biglegs.size();
        biglegCount2 = biglegs2.size();
        biglegCount3 = biglegs3.size();

        addKeyListener(this);
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false );
        timer = new Timer(delay, this);
        timer.start();


    }

    public void loadSprites(){
        sprites = new HashMap<>();
        try {
            sprites.put("background", this.getSprite("Resources/Background1.png"));
            sprites.put("background2", this.getSprite("Resources/Background2.png"));
            sprites.put("bigleg", this.getSprite("Resources/Bigleg_transparent.gif"));
            sprites.put("bigleg_small", this.getSprite("Resources/Bigleg_small_transparent.gif"));
            sprites.put("solidBlock", this.getSprite("Resources/Block_solid.gif"));
            sprites.put("splitBlock", this.getSprite("Resources/Block_split.gif"));
            sprites.put("lifeBlock", this.getSprite("Resources/Block_life.gif"));
            sprites.put("doubleBlock", this.getSprite("Resources/Block_double.gif"));
            sprites.put("purpleBlock", this.getSprite("Resources/Block1.gif"));
            sprites.put("yellowBlock", this.getSprite("Resources/Block2.gif"));
            sprites.put("redBlock", this.getSprite("Resources/Block3.gif"));
            sprites.put("greenBlock", this.getSprite("Resources/Block4.gif"));
            sprites.put("cyanBlock", this.getSprite("Resources/Block5.gif"));
            sprites.put("blueBlock", this.getSprite("Resources/Block6.gif"));
            sprites.put("grayBlock", this.getSprite("Resources/Block7.gif"));
            sprites.put("wall", this.getSprite("Resources/Wall.gif"));
            sprites.put("katch", this.getSprite("Resources/Katch_transparent.gif"));
            sprites.put("pop", this.getSprite("Resources/Pop_transparent.gif"));
            sprites.put("gameover", this.getSprite("Resources/gameover.png"));
            sprites.put("title", this.getSprite("Resources/Title.gif"));
            sprites.put("help", this.getSprite("Resources/Button_help.gif"));
            sprites.put("load", this.getSprite("Resources/Button_load.gif"));
            sprites.put("quit", this.getSprite("Resources/Button_quit.gif"));
            sprites.put("scores", this.getSprite("Resources/Button_scores.gif"));
            sprites.put("start", this.getSprite("Resources/Button_start.gif"));
            sprites.put("congrats", this.getSprite("Resources/Congratulation.gif"));

        }
        catch (Exception e){
            System.out.println("Failed to load sprites");
        }


    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setBackground(Graphics g){
        Image img;

//       try{
//           img = ImageIO.read(new File("Resources/Background2.png"));

            img = sprites.get("background");
            if(lvl1Beat)
                img = sprites.get("background2");
            g.drawImage(img, 0, 0, null);



//       }
//       catch(IOException ex){
//           System.out.println(ex.getMessage());
//       }

    }
    public int getBiglegCount2() {
        return biglegCount2;
    }

    public void setBiglegCount2(int biglegCount2) {
        this.biglegCount2 = biglegCount2;
    }

    public int getBiglegCount3() {
        return biglegCount3;
    }

    public void setBiglegCount3(int biglegCount3) {
        this.biglegCount3 = biglegCount3;
    }

    public void addBiglegs() {
        for(int i = 0; i < 3; i++) {
            Bigleg bigleg = new Bigleg(i * 160 + 140, 20, ID.Bigleg, sprites.get("bigleg_small"));
         biglegs.add(bigleg);
        }
    }

    public void addBigleg2() {
        Bigleg bigleg1 = new Bigleg(200, 20, ID.Bigleg, sprites.get("bigleg"));
        Bigleg bigleg2 = new Bigleg(400, 20, ID.Bigleg, sprites.get("bigleg"));



        biglegs2.add(bigleg1);
        biglegs2.add(bigleg2);
    }

    public void addBigleg3() {
        Bigleg bigleg1 = new Bigleg(200, 20, ID.Big leg, sprites.get("bigleg"));
        Bigleg bigleg2 = new Bigleg(400, 20, ID.Bigleg, sprites.get("bigleg"));



        biglegs3.add(bigleg1);
        biglegs3.add(bigleg2);
    }


    public void drawBigleg(Graphics g) {
        if(!lvl1Beat) {
            for (int i = 0; i < biglegs.size(); i++) {
                biglegs.get(i).draw(g);
            }
        }
    }

    public void drawBigleg2(Graphics g){
        if(lvl1Beat) {
            for (int i = 0; i < biglegs2.size(); i++) {
                biglegs2.get(i).draw(g);
            }
        }
    }


    public void drawBigleg3(Graphics g){
        if(lvl2Beat) {
            for (int i = 0; i < biglegs3.size(); i++) {
                biglegs3.get(i).draw(g);
            }
        }

    }

    public void drawBlocks(Graphics g){
//        Block block1 = new Block(0, 50, ID.Block);
//        Block block2 = new Block(5, 60, ID.Block);
//        block1.draw(g);
//        block2.draw(g);
        if(!lvl1Beat) {
//            for (Block blockObj : blocks2) {
//                blockObj.draw(g);
//            }
            for (Block blockObj : blocks) {
                blockObj.draw(g);
            }

        }
        if(lvl1Beat){
            if(!lvl2Beat) {
                for (Block blockObj : blocks2) {
                    blockObj.draw(g);
                }
            }

        }
        if(lvl2Beat){
            for(Block blockObj: blocks3){
                blockObj.draw(g);
            }
        }
    }

    public void drawScore(Graphics g){
        int playerScore = pop.getScore();

        g.drawString("Score: " + playerScore, 500, 400);

    }

    public void drawTime(Graphics g){
        tEnd = System.currentTimeMillis();
        tDelta = tEnd - tStart;
         elapsedSeconds = (tDelta / 1000.0) - 10.0;
         // round to 2 decimals
        newElapsedSeconds = Math.round(elapsedSeconds*100) / 100.0;
         if(elapsedSeconds > 0) {
             String secondsString = Double.toString(newElapsedSeconds);

             g.drawString("Time: " + secondsString, 500, 380);
         }

    }


    public void drawWalls(Graphics g){
        for(int i = 0; i < walls.size(); i++){
            if(walls.get(i).isVisible()){
                walls.get(i).draw(g);
            }

        }
    }

    public void drawPop(Graphics g){
        try {
//            pop.collision(pop);
            pop.draw(g);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void drawKatch(Graphics g){
        katch.draw(g);


    }

    public boolean isGlowOn() {
        return glowOn;
    }

    public void setGlowOn(boolean glowOn) {
        this.glowOn = glowOn;
    }

    public void getRandomBlock(ArrayList<Block> blocks){
        Random r = new Random();
        int low = 0;
        int high=0;
        if(!lvl1Beat)
            high = blocks.size();
        if(lvl1Beat && !lvl2Beat)
            high = blocks2.size();
        if(lvl2Beat)
            high = blocks3.size();
        int randomBlockIndex = r.nextInt(high - low) + low;

        if(elapsedSeconds < 30){
            bonusBlock = blocks.get(71);
//            System.out.println(bonusBlock.getBounds());
            setGlowOn(true);
        }
        else {
            if(!lvl1Beat)
                bonusBlock = blocks.get(randomBlockIndex);
            if(lvl1Beat && !lvl2Beat)
                bonusBlock = blocks2.get(randomBlockIndex);
            if(lvl2Beat && lvl1Beat)
                bonusBlock = blocks3.get(randomBlockIndex);
            setGlowOn(true);

        }
    }

    public void drawGlowBonus(Graphics g, double time, boolean glowOn, Block currentBlock){
        Graphics2D g2d = (Graphics2D) g;
//        System.out.println(glowOn);
        if(glowOn) {
            if (!decRadius) {
                if (radius == 800.0) {
                    decRadius = true;

                }
                radius += 5;
            } else if (decRadius) {
                radius -= 5;
                if (radius == 200)
                    decRadius = false;
            }



//            System.out.println(radius);

            Point2D center = new Point2D.Float(x, y);
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.white};
            RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
            g2d.setPaint(p);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .95f));

//            if(time > 30)
                g2d.fillRect(currentBlock.getX(), currentBlock.getY(), 40, 20);
//            if(time > 70 && time < 70.5){

            g2d.dispose();
//        System.out.println(glowOn);
        }
    }

    public void checkLives(){
        if(pop.getLives()<1) {
            setGameOver(true);
            player.playSound("boo-sound");
            timer.stop();
        }
    }

    public void drawGameover(Graphics g){
        Image img;
        img = sprites.get("gameover");
        readHighscores(g);
        g.drawImage(img, 200, 170, null);
    }

    private void drawLives(Graphics g) {
        int playerLives = pop.getLives();
        g.drawString("Lives: " + playerLives, 50, 400);

    }

    public void drawTitle(Graphics g){
        Image img;
        img = sprites.get("title");
        Image img2 = sprites.get("scores");
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                alpha));
        g2d.drawImage(img, 0, 0, 640, 480, null);

//        g.drawImage(img, 0, 0, null);


    }

    public void drawCongrats(Graphics g){
        Image img;
        img = sprites.get("congrats");
        this.readHighscores(g);
        g.drawImage(img, 0, 0, 640, 480, null);
    }




    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        double timePassed = getCurrentSeconds();


        if(!gameWon) {
            if (timePassed < 10) {
                this.drawTitle(g);
                g.setFont(new Font("Courier", Font.PLAIN, 24));
//            g.drawString("Loading...", 500, 440);

                if (timePassed < 2.01) {
                    g.drawString("Loading...", 500, 440);
                }
                if (timePassed > 2.01 && timePassed < 5.01) {
                    g.drawString("Loading..", 500, 440);
                }
                if (timePassed > 5.01 && timePassed < 8.01) {
                    g.drawString("Loading.", 500, 440);

                }
                if (timePassed > 8.01 && timePassed < 10.01) {
                    g.drawString("Loading...", 500, 440);

                }

            } else {
                if (!gameOver) {
                    this.setBackground(g);

                    this.resetPos();
                    this.wallCollision();
                    this.setLvl1Beat();
                    this.drawScore(g);
                    this.drawTime(g);
                    this.drawLives(g);
                    this.drawBigleg(g);
                    this.drawBigleg2(g);
                    this.drawBigleg3(g);
                    this.drawBlocks(g);
                    this.drawWalls(g);
                    this.drawPop(g);
                    this.drawKatch(g);
                    this.drawGlowBonus(g, elapsedSeconds, glowOn, bonusBlock);

                }
                if (gameOver) {
                    this.setBackground(g);


                    this.drawGameover(g);
                }
            }
        }
        if(gameWon)
            this.drawCongrats(g);

    }

    public void addWalls() {
        // up
        for(int i = 0; i < 32; i++) {
            Wall wall = new Wall(i * 20, 0, ID.Wall, sprites.get("wall"));
            walls.add(wall);
        }

        // left
        for(int i = 0; i < 23; i++) {
            Wall wall = new Wall(0, (i + 1) * 20, ID.Wall, sprites.get("wall"));
            walls.add(wall);
        }

        // right
        for(int i = 0; i < 23; i++) {
            Wall wall = new Wall(620, (i + 1) * 20, ID.Wall, sprites.get("wall"));
            walls.add(wall);
        }
        }


    public void addBlocks1(){
        //solids
        for(int i = 0; i < 12; i++) {
            Block solid1 = new Block(100, i * 20 + 20, ID.Block, sprites.get("solidBlock"));
            blocks.add(solid1);
            Block solid2 = new Block(220, i * 20 + 20, ID.Block, sprites.get("solidBlock"));
            blocks.add(solid2);
            Block solid3 = new Block(380, i * 20 + 20, ID.Block, sprites.get("solidBlock"));
            blocks.add(solid3);
            Block solid4 = new Block(500, i * 20 + 20, ID.Block, sprites.get("solidBlock"));
            blocks.add(solid4);

        }


        // life block
        for(int i = 0; i < 2; i++) {
            Block life1 = new Block(i * 40 + 20, 20, ID.Lifeblock, sprites.get("lifeBlock"));
            blocks.add(life1);
            Block life2 = new Block(640 - (i + 1) * 40 - 20, 20, ID.Lifeblock, sprites.get("lifeBlock"));
            blocks.add(life2);
        }

        //purple block
        for(int i = 0; i < 11; i++) {
            Block purple1 = new Block(260, (i + 1) * 20, ID.Block, sprites.get("purpleBlock"));
            blocks.add(purple1);
            Block purple2 = new Block(340, (i + 1) * 20, ID.Block, sprites.get("purpleBlock"));
            blocks.add(purple2);
        }

        //yellow block
        for(int i = 0; i < 9; i++) {
            Block yellow1 = new Block(140, (i + 1) * 20 + 60, ID.Block, sprites.get("yellowBlock"));
            blocks.add(yellow1);
            Block yellow2 = new Block(460, (i + 1) * 20 + 60, ID.Block, sprites.get("yellowBlock"));
            blocks.add(yellow2);
        }

        //red block
        for(int i = 0; i < 11; i++) {
            Block red1 = new Block(60, (i + 2) * 20, ID.Redblock, sprites.get("redBlock"));
            blocks.add(red1);
            Block red2 = new Block(540, (i + 2) * 20, ID.Redblock, sprites.get("redBlock"));
            blocks.add(red2);
        }

        // green block
        for(int i = 0; i < 12; i++) {
            Block green1 = new Block(180, (i + 1) * 20, ID.Block, sprites.get("greenBlock"));
            blocks.add(green1);
            Block green2 = new Block(420, (i + 1) * 20, ID.Block, sprites.get("greenBlock"));
            blocks.add(green2);
        }

        // blue block
        for(int i = 0; i < 11; i++) {
            Block blue1 = new Block(20, (i + 2) * 20, ID.Block, sprites.get("blueBlock"));
            blocks.add(blue1);

            Block blue2 = new Block(580, (i + 2) * 20, ID.Block, sprites.get("blueBlock"));
            blocks.add(blue2);
        }

        // gray block
        for(int i = 0; i < 9; i++) {
            Block gray1 = new Block(300, i * 20 + 60, ID.Block, sprites.get("grayBlock"));
            blocks.add(gray1);
        }


    }
//
    public void addBlocks2() {


        // life block
        for(int i = 0; i < 2; i++) {
            Block life1 = new Block(260, i * 20 + 160, ID.Lifeblock, sprites.get("lifeBlock"));
            blocks2.add(life1);
            Block life2 = new Block(340, i * 20 + 160, ID.Lifeblock, sprites.get("lifeBlock"));
            Block life3 = new Block(300, i * 20 + 160, ID.Lifeblock, sprites.get("lifeBlock"));

            blocks2.add(life2);
            blocks2.add(life3);
        }

        // purple block
        for(int i = 0; i < 8; i++) {
            Block purple1 = new Block(260, i * 20 + 200, ID.Block, sprites.get("purpleBlock"));
            blocks2.add(purple1);
            Block purple2 = new Block(340, i * 20 + 200, ID.Block, sprites.get("purpleBlock"));
            blocks2.add(purple2);

        }

        // yellow block
        for(int i = 0; i < 5; i++) {
            Block yellow1 = new Block(140, i * 20 + 160, ID.Block, sprites.get("yellowBlock"));
            blocks2.add(yellow1);
            Block yellow2 = new Block(460, i * 20 + 160, ID.Block, sprites.get("yellowBlock"));
            blocks2.add(yellow2);
        }

        // red block
        for(int i = 0; i < 5; i++) {
            Block block1 = new Block(60, i * 20 + 160, ID.Block, sprites.get("redBlock"));
            blocks2.add(block1);
            Block block2 = new Block(540, i * 20 + 160, ID.Block, sprites.get("redBlock"));
            blocks2.add(block2);
        }

        // green block
        for(int i = 0; i < 5; i++) {
            Block green1 = new Block(180, i * 20 + 160, ID.Block, sprites.get("greenBlock"));
            blocks2.add(green1);
            Block green2 = new Block(420, i * 20 + 160, ID.Block, sprites.get("greenBlock"));
            Block green3 = new Block(380, i * 20 + 160, ID.Block, sprites.get("greenBlock"));
            blocks2.add(green2);
            blocks2.add(green3);
        }

        // cyan block
        for(int i = 0; i < 10; i++) {

            Block cyan1 = new Block(100, i * 20 + 180, ID.Block, sprites.get("cyanBlock"));
            Block cyan2 = new Block(500, i * 20 + 180, ID.Block, sprites.get("cyanBlock"));

            blocks2.add(cyan1);
            blocks2.add(cyan2);
        }

        for(int i = 0; i < 4; i++) {
            Block cyan1 = new Block(220, i * 20 + 160, ID.Block, sprites.get("cyanBlock"));
            Block cyan2 = new Block(380, i * 20 + 160, ID.Block, sprites.get("cyanBlock"));
            blocks2.add(cyan1);
            blocks.add(cyan2);
        }

        // blue block
        for(int i = 0; i < 16; i++) {
            Block blue1 = new Block(20, i * 20 + 60, ID.Block, sprites.get("blueBlock"));
            blocks2.add(blue1);
            Block blue2 = new Block(580, i * 20 + 60, ID.Block, sprites.get("blueBlock"));
            blocks2.add(blue2);
            Block blue3 = new Block(40 * i, 60, ID.Block, sprites.get("blueBlock"));
            blocks2.add(blue3);
        }

        // gray block
        for(int i = 0; i < 5; i++) {
            Block gray1 = new Block(300, i * 20 + 160, ID.Block, sprites.get("grayBlock"));
            blocks2.add(gray1);
        }

        // split block
        for(int i = 0; i < 2; i++) {
            Block split1 = new Block(i * 400 + 100, 160, ID.Block, sprites.get("splitBlock"));
            blocks2.add(split1);
        }


    }

    public void addBlocks3(){

        // life block
        for(int i = 0; i < 2; i++) {
            Block life1 = new Block(i * 40 + 20, 180, ID.Lifeblock, sprites.get("lifeBlock"));
            blocks3.add(life1);
            Block life2 = new Block(640 - (i + 1) * 40 - 20, 180, ID.Lifeblock, sprites.get("lifeBlock"));
            blocks3.add(life2);
        }

        //purple block
        for(int i = 0; i < 24; i++) {
            Block purple1 = new Block(20 + (i*40), 200, ID.Block, sprites.get("purpleBlock"));
            blocks3.add(purple1);
//            Block purple2 = new Block(340, (i + 1) * 20, ID.Block, sprites.get("purpleBlock"));
//            blocks3.add(purple2);
        }
//
        //yellow block
        for(int i = 0; i < 24; i++) {
            Block yellow1 = new Block(20 + (i*40), 140, ID.Block, sprites.get("yellowBlock"));
            blocks3.add(yellow1);

        }
//
        //red block
        for(int i = 0; i < 24; i++)  {
            Block red1 = new Block(20 + (i*40), 80, ID.Redblock, sprites.get("redBlock"));
            blocks3.add(red1);

        }
//
        // green block
        for(int i = 0; i < 24; i++) {
            Block green1 = new Block(20 + (i*40), 260, ID.Block, sprites.get("greenBlock"));
            blocks3.add(green1);

        }
//
        // blue block
        for(int i = 0; i < 24; i++) {
            Block blue1 = new Block(20 + (i*40), 320, ID.Block, sprites.get("blueBlock"));
            blocks3.add(blue1);

        }


    }

    public void resetPos(){
//        System.out.println("tried reset");
        if(pop.getY() > 480 ) {

            System.out.println(pop.getY());
            if(pop.getLives() > 0)
                pop.setLives(pop.getLives() - 1);

            if(pop.getLives() == 0){
//                timer.stop();
            }
            if (pop.getLives() > 0) {


                    pop.setX(360);
                    pop.setY(380);
                    pop.setSpeed(5);
                    pop.setAngle(Math.PI / 4);
                    pop.setDirection("downUp_leftRight");
                    pop.setInLaunch(false);

                    //reset katch
                    katch.setX(320);
                    katch.setY(420);
                    katch.setCanShootOn();


                }

        }
        if(pop.getY() < 20)
        {
            pop.setY(40);
            pop.setSpeed(5);
            pop.setAngle(Math.PI - pop.getAngle());
            if(pop.getDirection() == "downUp_leftRight") {

                pop.setDirection("upDown_rightLeft");
            }

            //reset katch

        }



    }
    public void wallCollision() {
        if (pop.getY() < 20) {
            pop.setY(40);
            player.playSound("Sound_wall");
            pop.setSpeed(5);
            pop.setAngle(Math.PI - pop.getAngle());
            if (pop.getDirection() == "downUp_leftRight") {

                pop.setDirection("upDown_rightLeft");
            }
        }
        if(pop.isInLaunch()) {
            if (pop.getX() + pop.getWidth() < 56)
                player.playSound("Sound_wall");
            if (pop.getX()  > 580)
                player.playSound("Sound_wall");
        }
    }

    public void readHighscores(Graphics g){
        File file = new File("highscores.txt");

        try {   if(!wroteToScores) {
            String str = "\n" + "Player1 " + pop.getScore();
            BufferedWriter writer = new BufferedWriter(new FileWriter("highscores.txt", true));
            writer.append(' ');
            writer.append(str);

            writer.close();
            wroteToScores = true;
        }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            g.setFont(g.getFont().deriveFont(20f));
            g.drawString("Scores", 200, 60);

            while ((st=br.readLine())!=null){
                System.out.println(st);
//                g.drawString("Score: " + st + "%n", 500, 400);
                drawString(g, st, 200, 80);
//                drawString(g, "Scores\n" + st + "\n", 20, 20);
            }

//            while ((st = br.readLine()) != null) {
//                System.out.println(st);
//                g.drawString("Score: " + st + "%n", 500, 400);
//                drawString(g, "Scores\n" + st + "\n", 20, 20);
//
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n")) {
            y += drawStringY * g.getFontMetrics().getHeight();
            g.drawString(line, x, y);
            drawStringY += 1;
        }
    }
    public void setLvl1Beat(){


            if (biglegCount == 0 && resetForLvl2 == false) {

                blocks.removeAll(blocks);
                setGlowOn(false);
                lvl1Beat = true;

                pop.setX(360);
                pop.setY(380);
                pop.setSpeed(5);
                pop.setAngle(Math.PI / 4);
                pop.setDirection("downUp_leftRight");
                pop.setInLaunch(false);

                //reset katch
                katch.setX(320);
                katch.setY(420);
                katch.setCanShootOn();
                resetForLvl2 = true;

            }

            if(biglegCount2 == 0 && resetForLvl3 == false){
                blocks.removeAll(blocks);
                setGlowOn(false);
                lvl2Beat = true;

                pop.setX(360);
                pop.setY(380);
                pop.setSpeed(5);
                pop.setAngle(Math.PI / 4);
                pop.setDirection("downUp_leftRight");
                pop.setInLaunch(false);

                //reset katch
                katch.setX(320);
                katch.setY(420);
                katch.setCanShootOn();
                resetForLvl3 = true;

            }


    }

    public double getCurrentSeconds(){
        long tEnd2 = System.currentTimeMillis();
        long tDelta2 = tEnd2 - tStart;
        double elapsedSeconds2 = tDelta2 / 1000.0;
        return elapsedSeconds2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        alpha += -.00091f;
        if(alpha <= 0){
            alpha = 0;
        }
        update();
        repaint();


        if(play){


        }
//        repaint(); // recall paint and draw everything again

    }

    @Override
//    public void run() {
//        while(true) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            katch.tick();
//            repaint();
//
//        }
//    }

    public void run() { // creator of minecraft's function for run
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
//                katch.tick();
                //updates++;

                delta--;
            }
            frames++;



            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
        }
        stop();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void start() { // function to start thread
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }



    public void tick() { // updated every 60 sec


//        handler.tick();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(getCurrentSeconds() > 10) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                velX = -7;
            } else if (key == KeyEvent.VK_RIGHT) {
                velX = 7;
            }
            if (key == KeyEvent.VK_SPACE) {
                katch.setCanShootOff();
                pop.setInLaunch(true);
            }
        }
    }

    public void updatePop(){

        if (!katch.isCanShoot()) { // if katch is shot
            pop.collision(pop);
        }
        if (!pop.isInLaunch()) {
            pop.setX(pop.getX() + velX);
            pop.setY(pop.getY() + velY);
        }

    }

    public void playSoundKatchCollide() {

        if (pop.getX() > katch.getX() - pop.getWidth() && pop.getX() < katch.getX() + katch.getWidth() && pop.getY() > katch.getY() - pop.getHeight()) {
            player.playSound("Sound_katch");
//            System.out.println("katchcollide");
        }
    }

    public void updateBlock() {


        for (int i = 1; i < 100; i++) {
            if(elapsedSeconds > 0 && elapsedSeconds <1 )
                getRandomBlock(blocks);
            if (elapsedSeconds > i * 30 && elapsedSeconds < i * 30 + .1) {

                if(!lvl1Beat)
                    getRandomBlock(blocks);
                if(lvl1Beat && !lvl2Beat)
                    getRandomBlock(blocks2);
                if(lvl2Beat)
                    getRandomBlock(blocks3);
            }


        }
    }

    public void updateKatchNPop(){

            katch.setX(katch.getX() + velX);
            katch.setY(katch.getY() + velY);

            if (!katch.isCanShoot()) { // if katch is shot

                pop.collision(pop);
                katch.collision(pop);
            }
            if (!pop.isInLaunch()) {

                pop.setX(pop.getX() + velX);
                pop.setY(pop.getY() + velY);
            }


    }

    public void biglegCollision(){
        try {
            for (Bigleg biglegObj : biglegs) {
                if (biglegObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(biglegObj.getBounds())) {
                    biglegs.remove(biglegObj);
                    biglegCount--;
                    System.out.println(biglegCount);

                }
            }

            if(lvl1Beat) {
                for (Bigleg biglegObj : biglegs2) {
                    if (biglegObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(biglegObj.getBounds())) {
                        biglegs2.remove(biglegObj);
                        biglegCount2--;
                        System.out.println(biglegCount);

                    }
                }
            }
            if(lvl2Beat){
                for (Bigleg biglegObj : biglegs3) {
                    if (biglegObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(biglegObj.getBounds())) {
                        biglegs3.remove(biglegObj);
                        biglegCount3--;
                        System.out.println(biglegCount);
                        if(biglegCount3==0)
                            gameWon=true;

                    }
                }

            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void blockCollision(){
        try {

            if(!lvl1Beat) {
                for (Block blockObj : blocks) {
                    if (blockObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(blockObj.getBounds())) {
                        if(blockObj.getX()==bonusBlock.getX() && blockObj.getY() == bonusBlock.getY()) {
                            setGlowOn(false);
                            pop.setScore(pop.getScore() + 1000);
                            player.playSound("supa-hot-fire-sound");
                        }

                        if(blockObj.getId() == ID.Lifeblock)
                            pop.setLives(pop.getLives() + 1);;

                        blocks.remove(blockObj);
                        player.playSound("Sound_block");
                        pop.setScore(pop.getScore() + 10);
                        pop.setSpeed(-pop.getSpeed());


                    }


                }
            }

            if(lvl1Beat){
                for (Block blockObj : blocks2) {
                    if (blockObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(blockObj.getBounds())) {
                        if(blockObj.getX()==bonusBlock.getX() && blockObj.getY() == bonusBlock.getY()) {
                            setGlowOn(false);
                            pop.setScore(pop.getScore() + 1000);
                            player.playSound("supa-hot-fire-sound");
                        }

                        if(blockObj.getId() == ID.Lifeblock)
                            pop.setLives(pop.getLives() + 1);

                        blocks2.remove(blockObj);
                         player.playSound("Sound_block");
                        pop.setScore(pop.getScore()+10);
                        pop.setSpeed(-pop.getSpeed());



                    }


                }

            }
            if(lvl2Beat){
                for (Block blockObj : blocks3) {
                    if (blockObj.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(blockObj.getBounds())) {
                        if(blockObj.getX()==bonusBlock.getX() && blockObj.getY() == bonusBlock.getY()) {
                            setGlowOn(false);
                            pop.setScore(pop.getScore() + 1000);
                            player.playSound("supa-hot-fire-sound");
                        }

                        if(blockObj.getId() == ID.Lifeblock)
                            pop.setLives(pop.getLives() + 1);

                        blocks3.remove(blockObj);
                        player.playSound("Sound_block");
                        pop.setScore(pop.getScore()+10);
                        pop.setSpeed(-pop.getSpeed());



                    }


                }

            }
        }   catch(Exception e){
            System.out.println(e);
        }
    }

    public void update() {
//        katch.setX(getX()+velX);
//        katch.setY(getY()+velY);

//        if (!pop.isInLaunch()) {
//            pop.setX(pop.getX() + velX);
//            pop.setY(pop.getY() + velY);
//        }
//        updateKatch();
//        updatePop();
        checkLives();
        updateBlock();
        updateKatchNPop();
        playSoundKatchCollide();
        biglegCollision();
        blockCollision();

//        try {
//            Rectangle RPop = new Rectangle(pop.getX(), pop.getY(), pop.getWidth(), pop.getHeight());
//
//
//            for (Block blockObj : blocks) {
//                if (blockObj.getBounds().intersects(RPop) || RPop.intersects(blockObj.getBounds())) {
//
//                    blocks.remove(blockObj);
//                    pop.setScore(pop.getScore()+10);
//                    pop.setSpeed(-pop.getSpeed());
//
//
//
//                }
//
//
//            }
//        }
//        catch(Exception e){
//                System.out.println(e);
//            }


    }




    public Image getSprite(String name) {
        URL url = Gameplay.class.getResource(name);
        Image img = Toolkit.getDefaultToolkit().getImage(url);

        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception var5) {
            ;
        }

        return img;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(getCurrentSeconds() > 10) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {
                velX = 0;
            } else if (key == KeyEvent.VK_RIGHT) {
                velX = 0;
            }
        }
    }
}
