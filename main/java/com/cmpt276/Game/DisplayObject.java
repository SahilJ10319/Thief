package com.cmpt276.Game;

import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

/**
 * interface used by all objects that are drawn to the screen
 */
public interface DisplayObject {
    /**
     * updates the ui positions of the object
     *
     * @param sprite the sprite to update by the object
     */
    public void updateUIPos(ImageView sprite);
    /**
     * draws the inital graphics of the objects
     *
     * @param root the screen to draw to
     */
    public void drawInitial(Pane root);
}
