package Game;
import Object.OBJ_Diamond;

import AI.Pathfinder;
import Entity.*;

import Tile.TileManager;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 24; // 24x24 tile
    final int scale = 2;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 32;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 864 pixels

    // FPS
    int FPS = 60;

    public TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public UserInterface userInterface = new UserInterface(this);
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Pathfinder pFinder = new Pathfinder(this);

    public HUD hud= new HUD(this);

    // Entities
    public Player player = new Player(this, keyH);
    public Entity spaceshipPart[] = new Entity[10]; // 10 slots for object allocation
    public ArrayList<OBJ_Diamond> diamond = new ArrayList<>();
    public Alien alien[] = new Alien[10];
    public Entity blackhole[] = new Entity[10]; // 10 slots for object allocation
    ArrayList<Entity> entityList = new ArrayList<>();

    List<AbstractMap.SimpleEntry<Integer, Integer>> listOfRockCoords = new ArrayList<AbstractMap.SimpleEntry<Integer, Integer>>();
    public int diamondSpawnTime = 0;

    //Game state
    public final int playingState = 1;
    public final int pausedState = 2;
    public final int loseState = 3;
    public int currentGameState;

    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // improves rendering performance

        this.addKeyListener(keyH); // takes keyboard input
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setSpaceshipPart();
        aSetter.setAlien();
        aSetter.setBlackhole();
        aSetter.setDiamond();
        //aSetter.setRocks();
        currentGameState = playingState;

        //find all tiles that are walls or rocks
        for (int i=0; i<maxScreenCol; i++) {
            for (int j=0; j<maxScreenRow; j++) {
                if (tileManager.mapTileNum[i][j] == 1 || tileManager.mapTileNum[i][j] == 6) {
                    AbstractMap.SimpleEntry<Integer, Integer> rockCoords = new AbstractMap.SimpleEntry<Integer, Integer>(i, j);
                    listOfRockCoords.add(rockCoords);
                }
            }
        }
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }
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

    public void update()
    {

//        gameTime++;
        // Handle WASD movement
        if (currentGameState == playingState) {
            // Player
            player.update();

            // Alien
            for (int i = 0; i < alien.length; i++) {
                if (alien[i] != null) {
                    alien[i].update();
                }
            }

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
                while (spawnLocationValid == false) {
                    randomWorldX = ThreadLocalRandom.current().nextInt(0, 32 + 1);
                    randomWorldY = ThreadLocalRandom.current().nextInt(0, 16 + 1);
                    AbstractMap.SimpleEntry<Integer, Integer> newCoords = new AbstractMap.SimpleEntry<Integer, Integer>(randomWorldX, randomWorldY);
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
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TILE
        tileManager.draw(g2);

        // HUD
        hud.draw(g2);

        entityList.add(player);

        for(int i = 0; i < spaceshipPart.length; i++){
            if(spaceshipPart[i] != null){
                entityList.add(spaceshipPart[i]);
            }
        }

        for(int i = 0; i < diamond.size(); i++){
            if(diamond.get(i) != null){
                entityList.add(diamond.get(i));
            }
        }

//        for(int i = 0; i < rocks.length; i++){
//            if(rocks[i] != null){
//                entityList.add(rocks[i]);
//            }
//        }

        for(int i = 0; i < blackhole.length; i++){
            if(blackhole[i] != null){
                entityList.add(blackhole[i]);
            }
        }

        for(int i = 0; i < alien.length; i++){
            if(alien[i] != null){
                entityList.add(alien[i]);
            }
        }



        // Sort entities by their Y coordinate
        entityList.sort(new Comparator<>() {
            @Override
            public int compare(Entity e1, Entity e2) {

                int result = Integer.compare(e1.worldY, e2.worldY);
                return 0;
            }
        });

        // Draw Entities
        for(int i = 0; i < entityList.size(); i++){
            entityList.get(i).draw(g2);
        }
        entityList.clear();

        //draw UI (includes only pause screen currently)
        userInterface.draw(g2);
    }
}
