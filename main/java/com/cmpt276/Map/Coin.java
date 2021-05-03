package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Tile class that holds a persistant reward for the player to walk on.
 */
public class Coin extends Room {
    protected boolean hasReward;
    private int count;
    public ImageView coinSprite;
    private Image coinImage1, coinImage2, coinImage3, coinImage4, coinImage5, coinImage6;

    /**
     * Loads all necessary Images for UpdateUIPos' animations and movement.
     */
    private void loadImages(){
        coinImage1 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin1.png"));
        coinImage2 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin2.png"));
        coinImage3 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin3.png"));
        coinImage4 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin4.png"));
        coinImage5 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin5.png"));
        coinImage6 = new Image(this.getClass().getResourceAsStream("/Images/CoinImages/Coin6.png"));
        coinSprite = new ImageView(coinImage1);
    }
    /**
     * Constructor
     *
     * @param walls the configuration of walls for the room
     * @param pos the position of the tile
     */
    public Coin(int walls, Position pos) {
        super(walls, pos);
        this.hasReward = true;
    }

    /**
     * Causes whatever behavior is meant to occur apon an entity entering the tile.
     * <p>
     * if the entity is a player and a reward is still availible it adds points to the score.
     * the Coin's ImageView will be set offscreen if collected.
     *
     * @param entity the entity that just entered the tile.
     */
    public void enterEffect(Entity entity) {
        if (this.hasReward && entity instanceof Thief) {
            Thief thief = (Thief)entity;
            this.hasReward = false;
            thief.addScore(10);
        }
    }
    /**
     * Updates the position of the UI object to match the current state of the coin.
     * Updates the coin's Image to create rotating coin effect.
     *
     * @param coin the ImageView of the coin
     */
    public void updateUIPos(ImageView coin) {
        if (hasReward){
            if (count >= 0 && count < 2 || count == 12){
                coinSprite.setImage(coinImage1);
                if (count == 12){
                    count = 0;
                }
            }
            else if (count >= 2 && count < 4)
                coinSprite.setImage(coinImage2);
            else if (count >= 4 && count < 6 )
                coinSprite.setImage(coinImage3);
            else if (count >= 6 && count < 8 )
                coinSprite.setImage(coinImage4);
            else if (count >= 8 && count < 10 )
                coinSprite.setImage(coinImage5);
            else if (count >= 10 && count < 12 )
                coinSprite.setImage(coinImage6);
            count++;
        }
        else{
            coinSprite.setX(-100);
        }
    }
    /**
     * Loads Images located within class and sets to location on the Pane.
     *
     * @param root the Pane used to add the coinSprites to.
     * @see Pane
     */
    public void drawInitial(Pane root){
        super.drawInitial(root);
        loadImages();
        coinSprite = new ImageView(coinImage1);
        coinSprite.setX(this.pos.getX()*60+60+20);
        coinSprite.setY(this.pos.getY()*60+60+20);
        root.getChildren().add(coinSprite);
    }
    /**
     * @return whether or not this coin still has the reward availible
     */
    public boolean stillHasReward() {
        return hasReward;
    }
    /**
     * sets hasReward to true for testing
     */
    public void setHasRewardTrue() {
        this.hasReward = true;
    }
}