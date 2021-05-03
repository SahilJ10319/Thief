package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * Class used to store a 2d map of tiles
 */
public class TileMap implements UpdateObject, DisplayObject {
    private int width;
    private int height;
    private List<Tile> tiles;
    /**
     * Constructor
     * 
     * @param generator the MapGenerator used to initialize the map
     */
    public TileMap(MapGenerator generator) {
        Position size = generator.getSize();
        this.width = size.getX();
        this.height = size.getY();
        this.tiles = generator.generateMap((int)System.currentTimeMillis());
    }
    /**
     * @param entity the entity trying to move
     * @param dir the direction the entity wants to move
     * @return whether or not the entity can move in that direction
     */
    public boolean canMove(Entity entity, Direction dir) {
        Position pos = entity.getPos();

        if (pos.getX() >= 0 && pos.getX() <= this.width && pos.getY() >= 0 && pos.getY() < this.height) {
            Tile tile = tiles.get(pos.getX() + pos.getY() * this.width);
            return tile.canExit(dir);
        } else {
            return false;
        }
    }
    /**
     * @param pos the position to move from
     * @param dir the direction you want to move
     * @return whether or not you can move in that direction
     */
    public boolean canMove(Position pos, Direction dir) {
        if (pos.getX() >= 0 && pos.getX() <= this.width && pos.getY() >= 0 && pos.getY() < this.height) {
            Tile tile = tiles.get(pos.getX() + pos.getY() * this.width);
            return tile.canExit(dir);
        } else {
            return false;
        }
    }
    /**
     * Applies the enterEffect of the tile wherever the entity is standing
     * 
     * @param entity the entity to apply the effect to
     */
    public void enterEffect(Entity entity) {
        Position pos = entity.getPos();

        Tile tile = tiles.get(pos.getX() + pos.getY() * this.width);

        tile.enterEffect(entity);
    }
    /**
     * Updates all the tiles in the map
     */
    public void update() {
        for (Tile tile : this.tiles) {
            tile.update();
        }
    }
    /**
     * updates the UI positions of all the tiles in the map
     * 
     * @param sprite the sprite used by the tiles
     */
    public void updateUIPos(ImageView sprite) {
        for (Tile tile : this.tiles){
            tile.updateUIPos(sprite);
        }
    }
    /**
     * @return the size of the map
     */
    public Position getSize() {
        return new Position(width, height);
    }

    /**
     * Draws the inital representations of all the tiles
     * @param root the screen to draw to
     */
    @Override
    public void drawInitial(Pane root) {
        for (Tile tile : this.tiles) {
            tile.drawInitial(root);
        }
    }
    /**
     * @return whether or not there are uncollected coins on the map
     */
    public boolean hasRemainingCoins() {
        for (Tile tile : tiles) {
            if (tile instanceof Coin && !(tile instanceof Mugging)) {
                Coin coin = (Coin)tile;
                if (coin.stillHasReward())
                    return true;
            }
        }
        return false;
    }
}