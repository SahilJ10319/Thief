package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** 
 * Tile class that holds a persistant punishment for the player
 */
public class GuardStation extends Room {
    private boolean hasPunishment;
    private ImageView guardStationSprite;
    private Image guardStationImage;

    /** 
     * Constructor
     * 
     * @param walls the configuration of walls for the room
     * @param pos the position of the tile
     */
    public GuardStation(int walls, Position pos) {
        super(walls, pos);
        this.hasPunishment = true;
    }

    /** 
     * Causes whatever behavior is meant to occur apon an entity entering the tile
     * <p>
     * if the entity is a player and a punishment is still availible it subtracts points to the score
     * if the Thief has a high enough score the guardStation's ImageView will be set offscreen.
     * 
     * @param entity the entity that just entered the tile
     */
    public void enterEffect(Entity entity) {
        if (this.hasPunishment && entity instanceof Thief) {
            Thief thief = (Thief)entity;
            this.hasPunishment = false;
            thief.addScore(-20);
        }
    }

    /**
     * Updates the position of the UI object to match the current state of the guardstation
     * 
     * @param guardStation the ImageView representing the guardStation (Unused)
     */
    public void updateUIPos(ImageView guardStation) {
        if (hasPunishment){
            guardStationSprite.setX(this.pos.getX()*60+60);
            guardStationSprite.setY(this.pos.getY()*60+60);
        }
        else{
            guardStationSprite.setX(-100); 
        }
    }
    /**
     * Renders the inital graphics of the guard station and adds to Pane
     * 
     * @param root the Pane to add the graphics to
     */
    public void drawInitial(Pane root){
        super.drawInitial(root);
        guardStationImage = new Image(this.getClass().getResourceAsStream("/Images/GuardStationImages/Hostile.png"));
        guardStationSprite = new ImageView(guardStationImage);
        root.getChildren().add(guardStationSprite);
    }

    /**
     * @return whether or not this guardStation still has the punishment availible
     */
    public boolean stillHasPunishment() {
        return hasPunishment;
    }
}