package com.cmpt276.Map;

import com.cmpt276.Character.*;
import com.cmpt276.Game.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * MapGenerator to generate a random map
 */
public class RandomMazeGenerator implements MapGenerator {
    /**
     * class to represent a 2d array of walls shared on the left and right
     */
    private class WallMap {
        private int width;
        private int height;
        private boolean[][] walls;
        /**
         * Constructor
         *
         * @param width width of the map
         * @param height height of the map
         */
        public WallMap(int width, int height) {
            walls = new boolean[width * height][2];
            this.width = width;
            this.height = height;

            for (int i = 0; i < this.width * this.height; i++) {
                this.walls[i][0] = false;
                this.walls[i][1] = false;
            }
        }
        /**
         * @param x the x coordinate of the position
         * @param y the y coordinate of the position
         * @return whether or not the position is inside the map
         */
        public boolean inBounds(int x, int y) {
            return x >= 0 && x < this.width && y >= 0 && y < this.height;
        }
        /**
         * @param x the x position of the tile
         * @param y the y position of the tile
         * @param dir the direction of the wall to get
         * @return whether or not there is a wall in that position
         */
        public boolean getWall(int x, int y, Direction dir) {
            switch (dir) {
            case West: if (inBounds(x, y) && x > 0) {
                return this.walls[x + y * this.width][0];
            } return true;
            case North: if (inBounds(x, y) && y > 0) {
                return this.walls[x + y * this.width][1];
            } return true;
            case East: return this.getWall(x + 1, y, Direction.West);
            case South: return this.getWall(x, y + 1, Direction.North);
            default: return true;
            }
        }
        /**
         * @param x the x position of the tile
         * @param y the y position of the tile
         * @param dir the direction of the wall to get
         * @param newWall whether to add a wall or remove it
         */
        public void setWall(int x, int y, Direction dir, boolean newWall) {
            switch (dir) {
            case West: if (inBounds(x, y)) {
                this.walls[x + y * this.width][0] = newWall;
            } break;
            case North: if (inBounds(x, y)) {
                this.walls[x + y * this.width][1] = newWall;
            } break;
            case East: this.setWall(x + 1, y, Direction.West, newWall); break;
            case South: this.setWall(x, y + 1, Direction.North, newWall); break;
            }
        }
    }
    /**
     * POD class to store a rectangle
     */
    private class Rect {
        public int x;
        public int y;
        public int w;
        public int h;
        /*
         * Constructor
         * @param x the x position of the rectangle
         * @param y the y position of the rectangle
         * @param w the width of the rectangle
         * @param h the height of the rectangle
         */
        public Rect(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }
    /**
     * POD class used to store the parameters to the mapGenerator
     */
    public static class Options {
        public int width;
        public int height;
        public int maxPathLen;
        public int numCoins;
        public int numGuards;
        public int numMuggins;
        public int numMovingGuards;

        /**
         * @param width the width of the map
         * @param height the height of the map
         */
        public Options(int width, int height) {
            this.width = width;
            this.height = height;
            this.maxPathLen = (width + height) / 4;
            this.numCoins = (width + height) / 2;
            this.numGuards = (width + height) / 4;
            this.numMuggins = (width + height) / 8;
            this.numMovingGuards = (width + height) / 8;
        }
    }
    private Options generationOptions;

    private WallMap wallMap;
    private int[] tileTypes;
    private int[] distMap;
    private List<Position> guardPositions;
    /**
     * Constructor
     *
     * @param generationOptions the settings for the generation of this map
     */
    public RandomMazeGenerator(Options generationOptions) {
        this.generationOptions = generationOptions;
        wallMap = new WallMap(this.generationOptions.width, this.generationOptions.height);
        tileTypes = new int[this.generationOptions.width * this.generationOptions.height];
        distMap = new int[this.generationOptions.width * this.generationOptions.height];
        guardPositions = new ArrayList<Position>();
    }
    /**
     * @return the size of the map
     */
    public Position getSize() {
        return new Position(this.generationOptions.width, this.generationOptions.height);
    }
    /**
     * converts an integer to the direction enum
     *
     * @param d the integer representation of a direction
     * @return the direction represented by the integer
     */
    private Direction intToDir(int d)
    {
        switch(d % 4) {
        case 0: return Direction.West;
        case 1: return Direction.North;
        case 2: return Direction.East;
        case 3: return Direction.South;
        default: return Direction.West;
        }
    }
    /**
     * Clears the distance map stored by the class
     */
    private void ClearDistMap() {
        for (int i = 0; i < this.generationOptions.width * this.generationOptions.height; i++) {
            distMap[i] = this.generationOptions.width * this.generationOptions.height;
        }
    }
    /**
     * Generates a distance map from the position
     *
     * @param start the start position of the distance map
     */
    private void GenDistMap(Position start) {
        Stack<Integer> stack = new Stack<Integer>();

        stack.push(new Integer(start.getX() + start.getY() * this.generationOptions.width));

        int a = this.generationOptions.width;
        int b = this.generationOptions.height;
        int c = a * b;

        while(stack.isEmpty() == false) {
            int data = stack.remove(0);
            int xPos = data % a;
            int yPos = data / a % b;
            int depth = data / c;
            int index = xPos + yPos * a;

            if (xPos >= 0 && xPos < a && yPos >= 0 && yPos < b)
            {
                if (distMap[index] > depth)
                {
                    distMap[index] = depth;
                    if (!wallMap.getWall(xPos, yPos, Direction.West)) stack.push(new Integer((xPos - 1) + (yPos) * a + (depth + 1) * c));
                    if (!wallMap.getWall(xPos, yPos, Direction.North)) stack.push(new Integer((xPos + 1) + (yPos) * a + (depth + 1) * c));
                    if (!wallMap.getWall(xPos, yPos, Direction.East)) stack.push(new Integer((xPos) + (yPos - 1) * a + (depth + 1) * c));
                    if (!wallMap.getWall(xPos, yPos, Direction.South)) stack.push(new Integer((xPos) + (yPos + 1) * a + (depth + 1) * c));
                }
            }
        }
    }
    /**
     * Generates a map given the generationOptions and the seed
     * <p>
     * Generation is done using the recursive division algorithm
     *
     * @param seed the random seed used by the generator
     * @return an array of tiles used for the map
     */
    public List<Tile> generateMap(int seed) {
        for (int i = 0; i < this.generationOptions.width * this.generationOptions.height; i++) {
            tileTypes[i] = 0;
        }

        // DO GENERATION
        Random random = new Random(seed);
        Stack<Rect> workList = new Stack<Rect>();
        workList.push(new Rect(0, 0, this.generationOptions.width, this.generationOptions.height));

        while (workList.isEmpty() == false) {
            Rect rect = workList.pop();

            if (rect.w > rect.h && rect.w > 1) {
                int mid = random.nextInt(rect.w - 1) + rect.x + 1;
                workList.push(new Rect(rect.x, rect.y, mid - rect.x, rect.h));
                workList.push(new Rect(mid, rect.y, rect.w - (mid - rect.x), rect.h));

                for (int i = rect.y; i < rect.y + rect.h; i++) {
                    wallMap.setWall(mid, i, Direction.West, true);
                }
                int pos = random.nextInt(rect.h) + rect.y;
                wallMap.setWall(mid, pos, Direction.West, false);
            } else if (rect.h > 1) {
                int mid = random.nextInt(rect.h - 1) + rect.y + 1;
                workList.push(new Rect(rect.x, rect.y, rect.w, mid - rect.y));
                workList.push(new Rect(rect.x, mid, rect.w, rect.h - (mid - rect.y)));

                for (int i = rect.x; i < rect.x + rect.w; i++) {
                    wallMap.setWall(i, mid, Direction.North, true);
                }
                int pos = random.nextInt(rect.w) + rect.x;
                wallMap.setWall(pos, mid, Direction.North, false);
            }
        }

        // MAKE MAX PATH LENGTH

        Stack<Integer> startList = new Stack<Integer>();

        for (int y = 0; y < this.generationOptions.height; y++) {
            for (int x = 0; x < this.generationOptions.width; x++) {
                for (int d = 0; d < 4; d++) {
                    if (
                        (x > 0 || d != 0) &&
                        (y > 0 || d != 1) &&
                        (x < this.generationOptions.width - 1 || d != 2) &&
                        (y < this.generationOptions.height - 1 || d != 3)) {
                        startList.push(new Integer(x + y * this.generationOptions.width + d * this.generationOptions.width * this.generationOptions.height));
                    }
                }
            }
        }

        for (int i = 0; i < startList.size(); i++) {
            int a = random.nextInt(startList.size());
            int b = random.nextInt(startList.size());
            Integer tmp = startList.elementAt(a);
            startList.setElementAt(startList.elementAt(b), a);
            startList.setElementAt(tmp, b);
        }

        while (startList.isEmpty() == false) {
            int data = startList.pop();
            Position start = new Position(data % this.generationOptions.width, data / this.generationOptions.height % this.generationOptions.height);
            Direction dir = intToDir(data / this.generationOptions.height / this.generationOptions.height);
            Position end = start.move(dir);

            if (wallMap.inBounds(end.getX(), end.getY()) && wallMap.inBounds(end.getX(), end.getY())) {
                ClearDistMap();
                GenDistMap(end);
                int dist = distMap[start.getX() + start.getY() * this.generationOptions.width];
                if (dist > this.generationOptions.maxPathLen) {
                    this.wallMap.setWall(start.getX(), start.getY(), dir, false);
                }
            }
        }

        // GENERATE TILE TYPES

        List<Position> corners = new ArrayList<Position>();
        List<Position> hallways = new ArrayList<Position>();

        for (int y = 1; y < this.generationOptions.height - 1; y++) {
            for (int x = 1; x < this.generationOptions.width - 1; x++) {
                int count = 0;
                for (int i = 0; i < 4; i++) {
                    if (wallMap.getWall(x, y, intToDir(i)))
                        count++;
                }
                if (count == 3)
                    corners.add(new Position(x, y));
                else if (count == 2 && this.wallMap.getWall(x, y, Direction.West) == this.wallMap.getWall(x, y, Direction.East))
                    hallways.add(new Position(x, y));
            }
        }

        Collections.shuffle(corners);
        Collections.shuffle(hallways);

        if (hallways.size() >= this.generationOptions.numCoins + this.generationOptions.numGuards) {
            for (int i = 0; i < this.generationOptions.numCoins; i++) {
                Position pos = hallways.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 1;
            }
            for (int i = 0; i < this.generationOptions.numGuards; i++) {
                Position pos = hallways.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 2;
            }
        } else {
            int numCoins = this.generationOptions.numCoins * hallways.size() / (this.generationOptions.numCoins + this.generationOptions.numGuards);
            int numGuards = hallways.size() - numCoins;

            for (int i = 0; i < numCoins; i++) {
                Position pos = hallways.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 1;
            }
            for (int i = 0; i < numGuards; i++) {
                Position pos = hallways.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 2;
            }
        }

        if (corners.size() >= this.generationOptions.numMuggins + this.generationOptions.numMovingGuards){
            for (int i = 0; i < this.generationOptions.numMuggins; i++) {
                Position pos = corners.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 3;
            }
            for (int i = 0; i < this.generationOptions.numMovingGuards; i++) {
                Position pos = corners.remove(0);
                this.guardPositions.add(pos);
            }
        } else {
            int numMuggins = this.generationOptions.numMuggins * corners.size() / (this.generationOptions.numMuggins + this.generationOptions.numMovingGuards);
            int numMovingGuards = corners.size() - numMuggins;

            for (int i = 0; i < numMuggins; i++) {
                Position pos = corners.remove(0);
                this.tileTypes[pos.getX() + pos.getY() * this.generationOptions.width] = 3;
            }
            for (int i = 0; i < numMovingGuards; i++) {
                Position pos = corners.remove(0);
                this.guardPositions.add(pos);
            }
        }


        // CREATE TILE ARRAY

        List<Tile> tiles = new ArrayList<Tile>();
        TileGenerator gen = new TileGenerator();

        for (int y = 0; y < this.generationOptions.height; y++) {
            for (int x = 0; x < this.generationOptions.width; x++) {
                int tile = 0;

                tile |= (wallMap.getWall(x, y, Direction.West) ? 1 : 0) << 0;
                tile |= (wallMap.getWall(x, y, Direction.North) ? 1 : 0) << 1;
                tile |= (wallMap.getWall(x, y, Direction.East) ? 1 : 0) << 2;
                tile |= (wallMap.getWall(x, y, Direction.South) ? 1 : 0) << 3;
                tile |= tileTypes[x + y * this.generationOptions.width] << 4;

                tiles.add(gen.CreateTile(tile, new Position(x, y)));
            }
        }

        return tiles;
    }

    /**
     * @return a list of the moving guards on the map
     */
    public List<Guard> getGuards(TileMap map, Thief thief) {
        List<Guard> guards = new ArrayList<Guard>();
        for (Position pos : this.guardPositions) {
            guards.add(new Guard(pos, Direction.West, map, thief));
        }
        return guards;
    }
}