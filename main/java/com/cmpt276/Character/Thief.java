package com.cmpt276.Character;

import java.util.Stack;

import com.cmpt276.Game.*;
import com.cmpt276.Map.*;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

/**
 * Class holds implementation for the Thief Character
 */
public class Thief extends Entity {
    private int count;
    private Game games;
    private int[] distMap;

    private Image ThiefUp1Img, ThiefUp2Img, ThiefUp3Img, ThiefUp4Img,
    ThiefDown1Img, ThiefDown2Img, ThiefDown3Img, ThiefDown4Img,
    ThiefLeft1Img, ThiefLeft2Img, ThiefLeft3Img, ThiefLeft4Img,
    ThiefRight1Img, ThiefRight2Img, ThiefRight3Img, ThiefRight4Img,
    ThiefStationaryLeft1Img, ThiefStationaryLeft2Img, ThiefStationaryLeft3Img, ThiefStationaryLeft4Img;

    /**
     * loads all necessary Images for UpdateUIPos' animations and movement.
     */
    private void loadImages(){
        ThiefUp1Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveUp1.png"));
        ThiefUp2Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveUp2.png"));
        ThiefUp3Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveUp3.png"));
        ThiefUp4Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveUp4.png"));

        ThiefDown1Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveDown1.png"));
        ThiefDown2Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveDown2.png"));
        ThiefDown3Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveDown3.png"));
        ThiefDown4Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveDown4.png"));

        ThiefLeft1Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveLeft1.png"));
        ThiefLeft2Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveLeft2.png"));
        ThiefLeft3Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveLeft3.png"));
        ThiefLeft4Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveLeft4.png"));

        ThiefRight1Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveRight1.png"));
        ThiefRight2Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveRight2.png"));
        ThiefRight3Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveRight3.png"));
        ThiefRight4Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/MoveRight4.png"));

        ThiefStationaryLeft1Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/LeftStationary1.png"));
        ThiefStationaryLeft2Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/LeftStationary2.png"));
        ThiefStationaryLeft3Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/LeftStationary3.png"));
        ThiefStationaryLeft4Img = new Image(this.getClass().getResourceAsStream("/Images/ThiefImages/LeftStationary4.png"));
    }

    /** 
     * Class used for pathfinding for storing depth currently travelled away from the thief
    */
    private static class DistPosition extends Position {
        private int dist;

        /**
         * Constructor
         *
         * @param x x position
         * @param y y position
         * @param dist depth searched
         */
        public DistPosition(int x, int y, int dist) {
            super(x, y);
            this.dist = dist;
        }

        /**
         * used to get depth travelled
         *
         * @return depth travelled
         */
        public int getDist() {
            return this.dist;
        }
    }

    /**
     * Constructor
     * 
     * @param pos   Position the Thief is at.
     * @param facingDirection   Direction the Thief is facing.
     * @param map   Tilemap the game is using.
     * @param game  Game used for Key Events and to set up Thief
     */
    public Thief(Position pos, Direction facingDirection, TileMap map, Game game) {
        super(pos, facingDirection, map);
        games = game;
        loadImages();
        Position mapSize = map.getSize();
        distMap = new int[mapSize.getX() * mapSize.getY()];
    }

    /**
     * Uses Key Code to Take user input for movement
     * generates the distance every tile is away from thief so guard can make
     * smart movements to pursue thief
     */
    public void update() {
        KeyEvent e = games.getNextEvent();
        if (e != null) {
            switch (e.getCode()) {
            case A: this.move(Direction.West); break;
            case W: this.move(Direction.North); break;
            case D: this.move(Direction.East); break;
            case S: this.move(Direction.South); break;
            }
        }

        for (int i = 0; i < distMap.length; i++) {
            distMap[i] = distMap.length;
        }

        Stack<DistPosition> stack = new Stack<DistPosition>();
        stack.push(new DistPosition(this.getPos().getX(), this.getPos().getY(), 0));
        Position mapSize = map.getSize();

        while (!stack.isEmpty()) {
            DistPosition pos = stack.pop();
            int index = pos.getX() + pos.getY() * mapSize.getX();

            if (pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < mapSize.getX() && pos.getY() < mapSize.getY() &&
                distMap[index] > pos.getDist()) {
                distMap[index] = pos.getDist();

                if (map.canMove(pos, Direction.West)) stack.push(new DistPosition(pos.getX() - 1, pos.getY(), pos.getDist() + 1));
                if (map.canMove(pos, Direction.North)) stack.push(new DistPosition(pos.getX(), pos.getY() - 1, pos.getDist() + 1));
                if (map.canMove(pos, Direction.East)) stack.push(new DistPosition(pos.getX() + 1, pos.getY(), pos.getDist() + 1));
                if (map.canMove(pos, Direction.South)) stack.push(new DistPosition(pos.getX(), pos.getY() + 1, pos.getDist() + 1));
            }
        }
    }

    
    /** 
     * Calls game's addScore method
     * 
     * @param number integer for score to added to.
     */
    public void addScore(int number){
        games.addscore(number);
    }

    /**
     * Updates the UI position of the ImageView thief to match the current state of the thief class.
     * updates ImageView thief's Image to create movement animation.
     * <p>
     * thief's position needs to adjusted to match scaling of the maze to the UI.
     * 
     * @param thief ImageView of the guard.
     * @see ImageView
     */
    public void updateUIPos(ImageView thief) {
        // Neutral
        if (this.facingDirection == Direction.Neutral) {
            if (count >= 0 && count < 2 || count == 8){
                thief.setImage(ThiefStationaryLeft1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                thief.setImage(ThiefStationaryLeft2Img);
            else if (count >= 4 && count < 6 )
                thief.setImage(ThiefStationaryLeft3Img);
            else if (count >= 6 && count < 8 )
                thief.setImage(ThiefStationaryLeft4Img);
            count++;
        }
        // South
        else if (this.facingDirection == Direction.South){
            if (count >= 0 && count < 2 || count == 8){
                thief.setImage(ThiefDown1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                thief.setImage(ThiefDown2Img);
            else if (count >= 4 && count < 6)
                thief.setImage(ThiefDown3Img);
            else if (count >= 6 && count < 8)
                thief.setImage(ThiefDown4Img);
            count++;
        }
        // West
        else if (this.facingDirection == Direction.West){
            if (count >= 0 && count < 2 || count == 8){
                thief.setImage(ThiefLeft1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                thief.setImage(ThiefLeft2Img);
            else if (count >= 4 && count < 6)
                thief.setImage(ThiefLeft3Img);
            else if (count >= 6 && count < 8)
                thief.setImage(ThiefLeft4Img);
            count++;
        }
        // East
        else if (this.facingDirection == Direction.East){
            if (count >= 0 && count < 2 || count == 8){
                thief.setImage(ThiefRight1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                thief.setImage(ThiefRight2Img);
            else if (count >= 4 && count < 6)
                thief.setImage(ThiefRight3Img);
            else if (count >= 6 && count < 8)
                thief.setImage(ThiefRight4Img);
            count++;
        }
        // North
        else if (this.facingDirection == Direction.North){
            if (count >= 0 && count < 2 || count == 8){
                thief.setImage(ThiefUp1Img);
                if (count == 8){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                thief.setImage(ThiefUp2Img);
            else if (count >= 4 && count < 6)
                thief.setImage(ThiefUp3Img);
            else if (count >= 6 && count < 8)
                thief.setImage(ThiefUp4Img);
            count++;
        }
        thief.setX(this.pos.getX()*60+60);
        thief.setY(this.pos.getY()*60+60);
    }

    /**
     * DOES NOTHING ON THIS CLASS
     */
    @Override
    public void drawInitial(Pane root) {
        // nothing should get done here.

    }

    /**
     * Helps return how far a position is from the Thief
     * 
     * @param pos position needed to calculate placement away from thief
     * @return position away from thief
     */
    private int getDistAt(Position pos) {
        Position mapSize = map.getSize();
        if (pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < mapSize.getX() && pos.getY() < mapSize.getY()) {
            return distMap[pos.getX() + pos.getY() * mapSize.getX()];
        } else {
            return mapSize.getX() * mapSize.getY();
        }
    }

    /**
     * Helps return direction to move in order to reduce distance to thief
     * 
     * @param pos takes needed position for creating movement to thief
     * @return direction to move to pursue thief
     */
    public Direction getNextDir(Position pos) {
        int dist = getDistAt(pos);

        if (getDistAt(new Position(pos.getX() - 1, pos.getY())) == dist - 1 && map.canMove(pos, Direction.West)) {
            return Direction.West;
        } else if (getDistAt(new Position(pos.getX(), pos.getY() - 1)) == dist - 1 && map.canMove(pos, Direction.North)) {
            return Direction.North;
        } else if (getDistAt(new Position(pos.getX() + 1, pos.getY())) == dist - 1 && map.canMove(pos, Direction.East)) {
            return Direction.East;
        } else if (getDistAt(new Position(pos.getX(), pos.getY() + 1)) == dist - 1 && map.canMove(pos, Direction.South)) {
            return Direction.South;
        }

        return Direction.Neutral;
    }

    /**
     * Closes game and sets score to 0 for gameover.
     */
    public void kill() {
        games.close();
        games.setScore(-10);
    }
}