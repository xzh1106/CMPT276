package Game;

import AI.Pathfinder;
import Entity.*;

import Tile.TileManager;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;

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
    public Alien alien[] = new Alien[10];
    public Entity blackhole[] = new Entity[10]; // 10 slots for object allocation
    //public Entity rocks[] = new Entity[150];
    ArrayList<Entity> entityList = new ArrayList<>();

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
        //aSetter.setRocks();
        currentGameState = playingState;
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
