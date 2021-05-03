package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

/**
 * Tile interface used in the TileMap
 */
public interface Tile extends UpdateObject, DisplayObject {
    /**
     * @param dir the direction you are trying to exit to
     * @return whether or not you can exit this tile in that direction
     */
    public boolean canEnter(Direction dir);
    /**
     * @param dir the direction you are trying to enter from
     * @return whether or not you can enter this tile in that direction
     */
    public boolean canExit(Direction dir);
    /**
     * Causes whatever behavior is meant to occur apon an entity entering the tile
     * @param entity the entity that just entered the tile
     */
    public void enterEffect(Entity entity);
}