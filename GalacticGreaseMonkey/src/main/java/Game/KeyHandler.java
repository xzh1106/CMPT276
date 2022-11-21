package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class is for setting game keys.
 * @author Jason
 * @author Ryan
 */
public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    /**
     * This method is constructor of KeyHandler class.
     * @param gp GamePanel object
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Title State
        if(gp.currentGameState == gp.titleState) {
            if (code == KeyEvent.VK_W && gp.userInterface.commandNum == 1) {
                gp.userInterface.commandNum--;
            }
            if (code == KeyEvent.VK_S && gp.userInterface.commandNum == 0) {
                gp.userInterface.commandNum++;
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.userInterface.commandNum == 0) {
                    gp.currentGameState = gp.playingState;
                } else {
                    System.exit(0);
                }
            }
        }

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if(code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            if (gp.currentGameState == gp.playingState) {
                gp.currentGameState = gp.pausedState;
            }
            else if (gp.currentGameState == gp.pausedState){
                gp.currentGameState = gp.playingState;
            }
        }
    }

    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
