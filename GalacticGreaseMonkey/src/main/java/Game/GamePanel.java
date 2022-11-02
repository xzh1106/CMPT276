package Game;

import Entity.*;

import Tile.TileManager;
import Object.SuperObject;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

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

    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    ArrayList<Entity> entityList = new ArrayList<>();
    Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10]; // 10 slots for object allocation
    public Alien alien[] = new Alien[10];


    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true); // improves rendering performance

        this.addKeyListener(keyH); // takes keyboard input
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();

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
        player.update();
        for (int i = 0; i < alien.length; i++) {
            if (alien[i] != null) {
                alien[i].update();
            }
        }
    }
}
