package Game;
import Object.OBJ_Diamond;

import AI.Pathfinder;
import Entity.*;

import Tile.TileManager;
import Object.GameObject;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This subclass inherits the attributes and methods from the JPanel class.
 * Is for setting game screen.
 * @author Ryan
 * @author Zihao
 * @author Jason
 * @author Luan
 */
public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 864 pixels

    // FPS
    int FPS = 60;

    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UserInterface userInterface = new UserInterface(this);
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Pathfinder pFinder = new Pathfinder(this);

    public HUD hud= new HUD(this);

    // Entities
    public Player player = new Player(this, keyH);

    public GameObject[] spaceshipPart = new GameObject[10]; // 10 slots for object allocation
    public ArrayList<OBJ_Diamond> diamond = new ArrayList<>();
    public Alien[] alien = new Alien[5];
    public GameObject[] blackhole = new GameObject[10]; // 10 slots for object allocation
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public GameObject[] closedDoor = new GameObject[1];
    public GameObject[] openedDoor = new GameObject[1];   //Open Door after obtain 2 keys

    List<AbstractMap.SimpleEntry<Integer, Integer>> listOfRockCoords = new ArrayList<>();
    public int diamondSpawnTime = 0;
    public int alienSpawnTime = 0;

    //Game state
    public final int playingState = 1;
    public final int pausedState = 2;
    public final int loseState = 3;
    public final int winState = 4;
    public int currentGameState;

    /**
     * This method is constructor of GamePanel.
     * Is used to initializing object.
     */
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves rendering performance

        this.addKeyListener(keyH); // takes keyboard input
        this.setFocusable(true);
    }

    /**
     * This method is for setting game object.
     */
    public void setupGame() {
        aSetter.setSpaceshipPart();
        aSetter.setAlien();
        aSetter.setBlackhole();
        aSetter.setDiamond();
        aSetter.setOpenDoor(); // Open door is behind close door
        aSetter.setClosedDoor();

        currentGameState = playingState;

        //find all tiles that are walls or rocks
        for (int i=0; i<maxScreenCol; i++) {
            for (int j=0; j<maxScreenRow; j++) {
                if (tileManager.mapTileNum[i][j] == 1 || tileManager.mapTileNum[i][j] == 2) {
                    AbstractMap.SimpleEntry<Integer, Integer> rockCoords = new AbstractMap.SimpleEntry<>(i, j);
                    listOfRockCoords.add(rockCoords);
                }
            }
        }
    }

    /**
     * This method is for starting game thread.
     */
    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * This method is for Running game.
     */
    public void run()
    {
        @SuppressWarnings("IntegerDivisionInFloatingPointContext") double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {

            update(); // update information

            repaint(); // redraw screen with updated information

            try { // FPS Limiter
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                //noinspection BusyWait
                Thread.sleep((long) remainingTime) ;

                nextDrawTime += drawInterval;

            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * This method is for Updating game status.
     */
    public void update() {
        // Handle WASD movement
        if (currentGameState == playingState) {
            // Player
            player.update();

            // Projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }

            // Alien
            for (int i = 0; i < alien.length; i++) {
                if (alien[i] != null) {
                    if (alien[i].alive && !alien[i].dying) {
                        alien[i].update();
                    }
                    if(!alien[i].alive) {
                        alien[i] = null;
                    }
                }
            }

            if(alienSpawnTime > 100){
                boolean canSpawn1 = true;
                boolean canSpawn2 = true;
                int i = 0;
                while(alien[i] != null && i < alien.length-1){
                    i++;
                }

                for(int j = 0; j < alien.length; j++){
                    if (alien[j] != null){
                        if (alien[j].worldX >= 28*tileSize && alien[j].worldX <= 32*tileSize &&
                                alien[j].worldY >= 13*tileSize && alien[j].worldY <= 16*tileSize){
                            canSpawn1 = false;
                        }
                    }
                }
                for(int j = 0; j < alien.length; j++){
                    if (alien[j] != null){
                        if (alien[j].worldX >= 18*tileSize && alien[j].worldX <= 22*tileSize &&
                                alien[j].worldY >= 13*tileSize && alien[j].worldY <= 16*tileSize){
                            canSpawn2 = false;
                        }
                    }
                }

                if(canSpawn1 && (alien[i] == null || i != alien.length-1)){
                    aSetter.newAlien(i);
                    alienSpawnTime = 0;
                }
                else if (canSpawn2 && (alien[i] == null || i != alien.length-1)) {
                    aSetter.newAlien2(i);
                    alienSpawnTime = 0;
                }
            }
            alienSpawnTime++;

            //Remove diamonds every 280 frames
            for (int i=0; i<diamond.size(); i++) {
                diamond.get(i).timeSinceCreated++;
                if (diamond.get(i).timeSinceCreated > 280) {
                    diamond.get(i).timeSinceCreated = 0;
                    diamond.remove(i);
                }
            }

            //Spawn diamonds every 100 frames
            diamondSpawnTime++;
            if (diamondSpawnTime == 100) {
                boolean spawnLocationValid = false;
                int randomWorldX = 0;
                int randomWorldY = 0;
                while (!spawnLocationValid) {
                    randomWorldX = ThreadLocalRandom.current().nextInt(0, 32 + 1);
                    randomWorldY = ThreadLocalRandom.current().nextInt(0, 16 + 1);
                    AbstractMap.SimpleEntry<Integer, Integer> newCoords = new AbstractMap.SimpleEntry<>(randomWorldX, randomWorldY);
                    if( !listOfRockCoords.contains(newCoords)) {
                        spawnLocationValid = true;
                    }
                }

                OBJ_Diamond newDiamond = new OBJ_Diamond(this);
                newDiamond.worldX = randomWorldX * tileSize;
                newDiamond.worldY = randomWorldY * tileSize;

                diamond.add(newDiamond);
                diamondSpawnTime = 0;
            }
            if (player.score < 0){
                currentGameState = loseState;
            }

            if (player.score == 999999) {
                currentGameState = winState;
            }

        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TILE
        tileManager.draw(g2);

        // HUD
        hud.draw(g2);

        // PLAYER
        player.draw(g2);

        for(int i = 0; i < projectileList.size(); i++){
            if(projectileList.get(i) != null) {
                projectileList.get(i).draw(g2);
            }
        }

        for(int i = 0; i < spaceshipPart.length; i++){
            if(spaceshipPart[i] != null) {
                spaceshipPart[i].draw(g2);
            }
        }

        for (int i = 0; i < openedDoor.length; i++) {
            if (openedDoor[i] != null) {
                openedDoor[i].draw(g2);
            }
        }

        for (int i = 0; i < closedDoor.length; i++) {
            if (closedDoor[i] != null) {
                closedDoor[i].draw(g2);
            }
        }



        for(int i = 0; i < diamond.size(); i++){
            if(diamond.get(i) != null) {
                diamond.get(i).draw(g2);
            }
        }

        for(int i = 0; i < blackhole.length; i++){
            if(blackhole[i] != null) {
                blackhole[i].draw(g2);
            }
        }

        for(int i = 0; i < alien.length; i++){
            if(alien[i] != null) {
                alien[i].draw(g2);
            }
        }

        //draw UI (includes only pause screen currently)
        userInterface.draw(g2);
    }
}
