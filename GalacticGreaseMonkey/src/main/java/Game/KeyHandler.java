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
            if (code == KeyEvent.VK_W && gp.userInterface.commandNumTitleScreen == 1) {
                gp.userInterface.commandNumTitleScreen--;
                gp.playSE(0);
            }
            if (code == KeyEvent.VK_S && gp.userInterface.commandNumTitleScreen == 0) {
                gp.userInterface.commandNumTitleScreen++;
                gp.playSE(0);
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.userInterface.commandNumTitleScreen == 0) {
                    gp.playSE(9);
                    gp.currentGameState = gp.playingState;
                } else {
                    gp.playSE(10);
                    System.exit(0);
                }
            }
        }


        //retry when in lost state
        if(gp.currentGameState == gp.loseState || gp.currentGameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                if (gp.currentGameState == gp.loseState) {
                    gp.setupGame();
                    gp.currentGameState = gp.playingState;
                } else {
                    gp.setupGame();
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

        //Pause State
        if(code == KeyEvent.VK_P || code == KeyEvent.VK_ESCAPE) {
            if (gp.currentGameState == gp.playingState) {
                gp.currentGameState = gp.pausedState;
            }
            else if (gp.currentGameState == gp.pausedState){
                gp.currentGameState = gp.playingState;
            }
        }

        if (gp.currentGameState == gp.pausedState) {
            if (code == KeyEvent.VK_W && gp.userInterface.commandNumPauseScreen > 0) {
                gp.userInterface.commandNumPauseScreen--;
            }
            if (code == KeyEvent.VK_S && gp.userInterface.commandNumPauseScreen < 2) {
                gp.userInterface.commandNumPauseScreen++;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.userInterface.commandNumPauseScreen) {
                    case 0 -> gp.currentGameState = gp.playingState;
                    case 1 -> {
                        gp.setupGame();
                        gp.currentGameState = gp.playingState;
                    }
                    case 2 -> System.exit(0);
                }
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
