package com.cmpt276.Game;

/**
 * class to store an x and y position
 */
public class Position {
    private int x;
    private int y;
    /**
     *
     * @param x the new x position
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     *
     * @param y the new y position
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     *
     * @return the current x position
     */
    public int getX(){
        return this.x;
    }
    /**
     *
     * @return the current y position
     */
    public int getY(){
        return this.y;
    }
    /**
     * Constructor to create a postion
     *
     * @param x the inital x position
     * @param y the inital y position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
     * returns a position one tile in the direction
     *
     * @param dir the direction to move
     * @return the new position one tile in the direction
     */
    public Position move(Direction dir) {
        switch (dir) {
        case West: return new Position(this.x - 1, this.y);
        case North: return new Position(this.x, this.y - 1);
        case East: return new Position(this.x + 1, this.y);
        case South: return new Position(this.x, this.y + 1);
        default: return new Position(this.x, this.y);
        }
    }
}
