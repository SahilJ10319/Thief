package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Tile class to represent a room with four walls
 */
public class Room implements Tile {
    private boolean westWall;
    private boolean northWall;
    private boolean eastWall;
    private boolean southWall;

    protected Position pos;
    /**
     * Constructor
     *
     * @param walls the configuration of walls for the room
     * @param pos the position of the room
     */
    public Room(int walls, Position pos) {
        this.westWall = (walls & 1) == 1;
        this.northWall = (walls & 2) == 2;
        this.eastWall = (walls & 4) == 4;
        this.southWall = (walls & 8) == 8;
        this.pos = pos;
    }

    /**
     * @param dir the direction you are trying to exit to
     * @return whether or not you can exit this tile in that direction
     */
    public boolean canEnter(Direction dir) {
        switch (dir) {
        case West: return !this.eastWall;
        case North: return !this.southWall;
        case East: return !this.westWall;
        case South: return !this.northWall;
        default: return false;
        }
    }
    /**
     * @param dir the direction you are trying to enter from
     * @return whether or not you can enter this tile in that direction
     */
    public boolean canExit(Direction dir) {
        switch (dir) {
        case West: return !this.westWall;
        case North: return !this.northWall;
        case East: return !this.eastWall;
        case South: return !this.southWall;
        default: return false;
        }
    }
    /**
     * DOES NOTHING ON THIS CLASS
     */
    public void enterEffect(Entity entity) {
        // DONE does nothing
    }
    /**
     * DOES NOTHING ON THIS CLASS
     */
    public void update() {
        // DONE does nothing
    }
    /**
     * DOES NOTHING ON THIS CLASS
     */
    public void updateUIPos(ImageView sprite) {
    }

    /**
     * Draws lines for the room based off walls and adds to the Pane.
     *
     * @param root the Pane for the graphics to be added to.
     */
    @Override
    public void drawInitial(Pane root) {
        if(westWall){
            Line west = new Line(pos.getX()*60+60, pos.getY()*60+60, pos.getX()*60+60, pos.getY()*60+60+60);
            west.setStrokeWidth(3);
            root.getChildren().addAll(west);
        }
        if(eastWall){
            Line east = new Line(pos.getX()*60+60+60, pos.getY()*60+60, pos.getX()*60+60+60, pos.getY()*60+60+60);
            root.getChildren().addAll(east);
            east.setStrokeWidth(3);
        }
        if(northWall){
            Line north = new Line(pos.getX()*60+60, pos.getY()*60+60, pos.getX()*60+60+60, pos.getY()*60+60);
            root.getChildren().addAll(north);
            north.setStrokeWidth(3);
        }
        if(southWall){
            Line south = new Line(pos.getX()*60+60, pos.getY()*60+60+60, pos.getX()*60+60+60, pos.getY()*60+60+60);
            root.getChildren().addAll(south);
            south.setStrokeWidth(3);
        }
    }
};