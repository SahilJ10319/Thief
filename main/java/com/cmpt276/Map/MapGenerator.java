package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import java.util.List;

/**
 * Interface used to generate TileMaps
 */
public interface MapGenerator {
    /**
     * @return the size of the map
     */
    public Position getSize();
    /**
     * @param seed the random seed used by the generator
     * @return a list of the tiles in the map
     */
    public List<Tile> generateMap(int seed);
    /**
     * @return a list of moving guards on the map
     */
    public List<Guard> getGuards(TileMap map, Thief thief);
}