// 322394545 Nimrod Netzer

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class manages a collection of Sprite objects.
 */
public class SpriteCollection {
    private final List<Sprite> sprites;

    /**
     * Constructs a new SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notifies all sprites that time has passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite sprite : new ArrayList<>(this.sprites)) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites on the given DrawSurface.
     *
     * @param d the surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }
}
