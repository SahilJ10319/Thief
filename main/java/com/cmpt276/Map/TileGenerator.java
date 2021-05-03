package com.cmpt276.Map;

import com.cmpt276.Game.*;

/**
 * Factory clas for creating tiles
 */
public class TileGenerator {
    /**
     * @param type the integer representation of the tile
     * @param pos the postiion of the tile
     * @return the tile that is represented by the integer [type]
     */
    public Tile CreateTile(int type, Position pos) {
        int walls = type & 15;
        type = (type >> 4) % 4;

        switch (type) {
        case 0: return new Room(walls, pos);
        case 1: return new Coin(walls, pos);
        case 2: return new GuardStation(walls, pos);
        case 3: return new Mugging(walls, pos);
        default: return null;
        }
    }
}