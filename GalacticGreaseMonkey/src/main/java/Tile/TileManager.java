package Tile;

import Game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    Tile [] tiles;
    int mapTiles[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTiles = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/wall.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("maps/map01.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String getLine = bufferedReader.readLine();

                while (col < gp.maxScreenCol ) {
                    String numbers[] = getLine.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTiles[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        }
        catch(Exception e) {

        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTiles[col][row];
            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row ++;
                y += gp.tileSize;
            }
        }
    }
}
