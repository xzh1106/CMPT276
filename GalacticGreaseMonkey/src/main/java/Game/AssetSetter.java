package Game;
import Objects.*;
import Entity.*;

import java.util.AbstractMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is for setting location of object.
 * @author Jason
 * @author Ryan
 */
public class AssetSetter {

    GamePanel gp;

    /**
     * This method is constructor of AssetSetter.
     * @param gp GamePanel object.
     */
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * This method is for making a key object and save into obj array,
     * and Set location for object on map.
     */
    public void setSpaceshipPart() {
//        gp.spaceshipPart[0] = new OBJ_SpaceshipPart(gp); // Make a key object and save into obj array
//        gp.spaceshipPart[0].worldX = 7 * gp.tileSize; // Set location for obj on map
//        gp.spaceshipPart[0].worldY = 10 * gp.tileSize;
//
//        gp.spaceshipPart[1] = new OBJ_SpaceshipPart(gp); // Make a key object and save into obj array
//        gp.spaceshipPart[1].worldX = 18 * gp.tileSize; // Set location for obj on map
//        gp.spaceshipPart[1].worldY = gp.tileSize;
        int numShipPart = 2;

        for (int i = 0; i < numShipPart; i++) {
            gp.spaceshipPart[i] = new OBJ_SpaceshipPart(gp);
            boolean validLocation = false;
            int randomX = 0;
            int randomY = 0;

            while (!validLocation) {
                randomX = ThreadLocalRandom.current().nextInt(3, 30);
                randomY = ThreadLocalRandom.current().nextInt(3, 15);
                AbstractMap.SimpleEntry<Integer, Integer> newCoords = new AbstractMap.SimpleEntry<>(randomX, randomY);
                if (!gp.listOfRockCoords.contains(newCoords)) {
                    validLocation = true;
                }
            }
            gp.spaceshipPart[i].worldX = randomX * gp.tileSize;
            gp.spaceshipPart[i].worldY = randomY * gp.tileSize;
        }
    }

    /**
     * This method is for making a Diamond object and save into obj array,
     * and Set location for object on map.
     */
    public void setDiamond() {

        gp.diamond.add(new OBJ_Diamond(gp));
        gp.diamond.get(gp.diamond.size()-1).worldX = 20 * gp.tileSize;
        gp.diamond.get(gp.diamond.size()-1).worldY = 4 * gp.tileSize;

        gp.diamond.add(new OBJ_Diamond(gp));
        gp.diamond.get(gp.diamond.size()-1).worldX = 7 * gp.tileSize;
        gp.diamond.get(gp.diamond.size()-1).worldY = 5 * gp.tileSize;

    }

    /**
     * This method is for making Blackhole object and save into obj array,
     * and Set location for object on map.
     */
    public void setBlackhole() {

//        gp.blackhole[0] = new OBJ_Blackhole(gp); // Make a key object and save into obj array
//        gp.blackhole[0].worldX = 20 * gp.tileSize; // Set location for obj on map
//        gp.blackhole[0].worldY = 5 * gp.tileSize;
//
//        gp.blackhole[1] = new OBJ_Blackhole(gp); // Make a key object and save into obj array
//        gp.blackhole[1].worldX = 8 * gp.tileSize; // Set location for obj on map
//        gp.blackhole[1].worldY = 7 * gp.tileSize;
//
//        gp.blackhole[2] = new OBJ_Blackhole(gp); // Make a key object and save into obj array
//        gp.blackhole[2].worldX = 27 * gp.tileSize; // Set location for obj on map
//        gp.blackhole[2].worldY = 9 * gp.tileSize;

        int numBlackholes = ThreadLocalRandom.current().nextInt(2,7);

        for (int i = 0; i < numBlackholes; i++) {
            gp.blackhole[i] = new OBJ_Blackhole(gp);
            boolean validLocation = false;
            int randomX = 0;
            int randomY = 0;

            while (!validLocation) {
                randomX = ThreadLocalRandom.current().nextInt(3, 30);
                randomY = ThreadLocalRandom.current().nextInt(3, 15);
                AbstractMap.SimpleEntry<Integer, Integer> newCoords = new AbstractMap.SimpleEntry<>(randomX, randomY);
                if (!gp.listOfRockCoords.contains(newCoords)) {
                    validLocation = true;
                }
            }
            gp.blackhole[i].worldX = randomX * gp.tileSize;
            gp.blackhole[i].worldY = randomY * gp.tileSize;
        }
    }

    /**
     * This method is for making ClosedDoor object and save into obj array,
     * and Set location for object on map.
     */
    public void setDoor() {
        gp.closedDoor[0] = new OBJ_ClosedDoor(gp);
        gp.openedDoor[0] = new OBJ_OpenedDoor(gp);
        boolean validLocation = false;
        int randomX = 0;
        int randomY = 0;

        while (!validLocation) {
            randomX = ThreadLocalRandom.current().nextInt(3, 30);
            randomY = ThreadLocalRandom.current().nextInt(3, 15);
            AbstractMap.SimpleEntry<Integer, Integer> newCoords = new AbstractMap.SimpleEntry<>(randomX, randomY);
            if (!gp.listOfRockCoords.contains(newCoords)) {
                validLocation = true;
            }
        }
        gp.openedDoor[0].worldX = randomX * gp.tileSize;
        gp.openedDoor[0].worldY = randomY * gp.tileSize;
        gp.closedDoor[0].worldX = randomX * gp.tileSize;
        gp.closedDoor[0].worldY = randomY * gp.tileSize;
    }

    /**
     * This method is for making Alien object and save into obj array,
     * and Set location for object on map.
     */
    public void setAlien() {
        gp.alien[0] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[0].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[0].worldY = 14 * gp.tileSize;

        gp.alien[1] = new Alien(gp);
        gp.alien[1].worldX = 12 * gp.tileSize;
        gp.alien[1].worldY = 13 * gp.tileSize;

        gp.alien[2] = new Alien(gp);
        gp.alien[2].worldX = 11 * gp.tileSize;
        gp.alien[2].worldY = 6 * gp.tileSize;

        gp.alien[3] = new Alien(gp);
        gp.alien[3].worldX = 21 * gp.tileSize;
        gp.alien[3].worldY = gp.tileSize;
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = 30 * gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = 14 * gp.tileSize;
        gp.alien[index].direction = "left";
    }

    /**
     * This method is for making new Alien object and save into obj array,
     * Set location for object on map.
     * @param index array position.
     */
    public void newAlien2(int index) {
        gp.alien[index] = new Alien(gp); // Make a key object and save into obj array
        gp.alien[index].worldX = 20 * gp.tileSize; // Set location for obj on map
        gp.alien[index].worldY = 14 * gp.tileSize;
        gp.alien[index].direction = "left";
    }

}
