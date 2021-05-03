package com.cmpt276.Character;

import com.cmpt276.Game.*;
import com.cmpt276.Map.*;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * This class holds the implementation for the Guard Characters
 */
public class Guard extends Entity {
    private Thief thief;
    private int count;

    private Image MovGuardUp1Img, MovGuardUp2Img, MovGuardUp3Img, MovGuardUp4Img,
    MovGuardDown1Img, MovGuardDown2Img, MovGuardDown3Img, MovGuardDown4Img,
    MovGuardLeft1Img, MovGuardLeft2Img, MovGuardLeft3Img, MovGuardLeft4Img,
    MovGuardRight1Img, MovGuardRight2Img, MovGuardRight3Img, MovGuardRight4Img,
    MovGuardStationaryLeft1Img, MovGuardStationaryLeft2Img, MovGuardStationaryLeft3Img, MovGuardStationaryLeft4Img;

    /**
     * loads all necessary Images for UpdateUIPos' animations and movement.
     */
    private void loadImages(){
        MovGuardUp1Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingUp1.png"));
        MovGuardUp2Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingUp2.png"));
        MovGuardUp3Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingUp3.png"));
        MovGuardUp4Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingUp4.png"));

        MovGuardDown1Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingDown1.png"));
        MovGuardDown2Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingDown2.png"));
        MovGuardDown3Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingDown3.png"));
        MovGuardDown4Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingDown4.png"));

        MovGuardLeft1Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingLeft1.png"));
        MovGuardLeft2Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingLeft2.png"));
        MovGuardLeft3Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingLeft3.png"));
        MovGuardLeft4Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingLeft4.png"));

        MovGuardRight1Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingRight1.png"));
        MovGuardRight2Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingRight2.png"));
        MovGuardRight3Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingRight3.png"));
        MovGuardRight4Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/MovingRight4.png"));

        MovGuardStationaryLeft1Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/LeftStationary1.png"));
        MovGuardStationaryLeft2Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/LeftStationary2.png"));
        MovGuardStationaryLeft3Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/LeftStationary3.png"));
        MovGuardStationaryLeft4Img = new Image(this.getClass().getResourceAsStream("/Images/MovingGuardImages/LeftStationary4.png"));
    }

    /**
     * Constructor
     * 
     * @param pos   Position the guard is at.
     * @param facingDirection   Direction the guard is facing.
     * @param map   Tilemap the game is using.
     * @param thief Thief that will determine Guards movement
     */
    public Guard(Position pos, Direction facingDirection, TileMap map, Thief thief) {
        super(pos, facingDirection, map);
        this.thief = thief;
        loadImages();
    }

    /**
     * Updates Guard's position
     * Every 15 frames, the guard moves ~1
     * Check is made to see Thief's general direction so that Guard may follow
     * Also has Check that if Guard and Thief have same position, Thief Dies.
     */
    public void update() {
        if (Game.getGameRandom().nextInt(10) == 0) {
            this.move(thief.getNextDir(this.getPos()));
        }
        if (thief.getPos().getX() == this.getPos().getX() && thief.getPos().getY() == this.getPos().getY()) {
            thief.kill();
        }
    }

    /**
     * Updates the UI position of the ImageView guard to match the current state of the guard class.
     * updates ImageView guard's Image to create movement animation.
     * <p>
     * guard's position needs to adjusted to match scaling of the maze to the UI.
     * 
     * @param guard ImageView of the guard.
     * @see ImageView
     */
    public void updateUIPos(ImageView guard) {
        // Neutral
        if (this.facingDirection == Direction.Neutral) {
            if (count >= 0 && count < 2 || count == 8){
                guard.setImage(MovGuardStationaryLeft1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                guard.setImage(MovGuardStationaryLeft2Img);
            else if (count >= 4 && count < 6 )
                guard.setImage(MovGuardStationaryLeft3Img);
            else if (count >= 6 && count < 8 )
                guard.setImage(MovGuardStationaryLeft4Img);
            count++;
        }
        // South
        else if (this.facingDirection == Direction.South){
            if (count >= 0 && count < 2 || count == 8){
                guard.setImage(MovGuardDown1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                guard.setImage(MovGuardDown2Img);
            else if (count >= 4 && count < 6)
                guard.setImage(MovGuardDown3Img);
            else if (count >= 6 && count < 8)
                guard.setImage(MovGuardDown4Img);
            count++;
        }
        // West
        else if (this.facingDirection == Direction.West){
            if (count >= 0 && count < 2 || count == 8){
                guard.setImage(MovGuardLeft1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                guard.setImage(MovGuardLeft2Img);
            else if (count >= 4 && count < 6)
                guard.setImage(MovGuardLeft3Img);
            else if (count >= 6 && count < 8)
                guard.setImage(MovGuardLeft4Img);
            count++;
        }
        // East
        else if (this.facingDirection == Direction.East){
            if (count >= 0 && count < 2 || count == 8){
                guard.setImage(MovGuardRight1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                guard.setImage(MovGuardRight2Img);
            else if (count >= 4 && count < 6)
                guard.setImage(MovGuardRight3Img);
            else if (count >= 6 && count < 8)
                guard.setImage(MovGuardRight4Img);
            count++;
        }
        // North
        else if (this.facingDirection == Direction.North){
            if (count >= 0 && count < 2 || count == 8){
                guard.setImage(MovGuardUp1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                guard.setImage(MovGuardUp2Img);
            else if (count >= 4 && count < 6)
                guard.setImage(MovGuardUp3Img);
            else if (count >= 6 && count < 8)
                guard.setImage(MovGuardUp4Img);
            count++;
        }
        guard.setX(this.pos.getX()*60+60);
        guard.setY(this.pos.getY()*60+60);
    }
    
    /**
     * DOES NOTHING ON THIS CLASS
     */
    @Override
    public void drawInitial(Pane root) {
        // nothing should get done here
    }
}