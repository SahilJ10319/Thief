package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Tile class that holds a fleeting reward for the player
 */
public class Mugging extends Coin {
    private int rewardTimer;
    private ImageView muggingSprite;
    private Image muggingImage;

    /**
     * Constructor
     * 
     * @param walls the configuration of walls for the room
     * @param pos the position of the tile
     */
    public Mugging(int walls, Position pos) {
        super(walls, pos);
        this.rewardTimer = 0;
        this.hasReward = false;
    }

    /**
     * Causes whatever behavior is meant to occur apon an entity entering the tile
     * <p>
     * if the entity is a player and a reward is still availible it adds points to the score
     * the Mugging's ImageView will be set offscreen if collected.
     * 
     * @param entity the entity that just entered the tile
     */
    public void enterEffect(Entity entity) {
        if (this.hasReward && entity instanceof Thief) {
            Thief thief = (Thief)entity;
            this.hasReward = false;
            thief.addScore(30);
        }
    }
    /**
     * Updates the Muggings
     * if there is a reward
     *      checks if the reward timer has run out then removes the reward
     * else
     *      checks if it can respawn the reward
     */
    public void update() {
        if (this.hasReward) {
            if (this.rewardTimer < 0) {
                this.hasReward = false;
                muggingSprite.setX(-100);
            }
            else {
                this.rewardTimer--;
            }
        } else {
            if (Game.getGameRandom().nextInt(150) == 0) {
                this.hasReward = true;
                this.rewardTimer = 120;
            }
        }

    }
    /** 
     * Updates the position of the UI object to match the current state of the mugging.
     * 
     * @param mug the ImageView of the mugging (unused)
     */
    public void updateUIPos(ImageView mug) {
        if (hasReward){
            muggingSprite.setX(this.pos.getX()*60+60+15);
            muggingSprite.setY(this.pos.getY()*60+60+20);
            this.coinSprite.setX(-100);
        }
        else{
            muggingSprite.setX(-100);
        }
    }
    /**
     * Renders the inital graphics of the mugging and adds to the Pane
     * 
     * @param root the Pane to add the graphics to.
     */
    public void drawInitial(Pane root){
        super.drawInitial(root);
        muggingImage = new Image(this.getClass().getResourceAsStream("/Images/MuggingImages/Mugging1.png"));
        muggingSprite = new ImageView(muggingImage);
        root.getChildren().add(muggingSprite);
        muggingSprite.setX(-100);
        this.coinSprite.setX(-100);
    }
}