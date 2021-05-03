package com.cmpt276.Character;

import com.cmpt276.Game.*;
import com.cmpt276.Map.*;

/**
 * Entity class is a super class that holds move method and getPos method commonly used by
 * subclasses Thief and Guard
 */
public abstract class Entity implements UpdateObject, DisplayObject {
    protected Position pos;
    protected Direction facingDirection;
    protected TileMap map;

    /**
     * Constructor
     * 
     * @param pos   Position the Entity is at.
     * @param facingDirection   Direction the entity is facing.
     * @param map   Tilemap the game is using.
     */
    public Entity(Position pos, Direction facingDirection, TileMap map) {
        this.pos = pos;
        this.facingDirection = facingDirection;
        this.map = map;
    }

    
    /** 
     * Moves entity in the direction that is passed in as parameter.
     * Checks if possible to move, checks what direction entity wants to move,
     * sets entity's new position and enters any possible effects on the entity
     * 
     * @param facingDirection   Direction the entity is facing.
     * @see Direction
     */
    public void move(Direction facingDirection) {
        this.facingDirection = facingDirection;
        if (this.map.canMove(this, facingDirection)) {
            if (this.facingDirection == Direction.West) {
                this.pos.setX(this.pos.getX() - 1);
                this.map.enterEffect((Entity)this);
            }
            else if (this.facingDirection == Direction.East) {
                this.pos.setX(this.pos.getX() + 1);
                this.map.enterEffect((Entity)this);
            }
            else if (this.facingDirection == Direction.North) {
                this.pos.setY(this.pos.getY() - 1);
                this.map.enterEffect((Entity)this);
            }
            else if (this.facingDirection == Direction.South) {
                this.pos.setY(this.pos.getY() + 1);
                this.map.enterEffect((Entity)this);
            }
        }
    }

    /**
     * Gets position for whatever entity created
     *
     * @return Position
     */
    public Position getPos() {
        return pos;
    }
}