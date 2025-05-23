// 322394545 Nimrod Netzer
import biuoop.DrawSurface;

/**
 * The Sprite interface represents objects that can be drawn on a DrawSurface
 * and can update themselves over time.
 */
public interface Sprite {

    /**
     * Draws the sprite on a given DrawSurface.
     *
     * @param d the DrawSurface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed.
     * This allows the sprite to update its state or perform any actions it needs.
     */
    void timePassed();
}
