// 322394545 Nimrod Netzer
package sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The sprites.SpriteCollection class manages a collection of sprites.Sprite objects.
 * It provides methods to add, remove, notify, and draw sprites.
 */
public class SpriteCollection {
    private final List<Sprite> sprites; // List to hold all sprites

    /**
     * Constructs a new sprites.SpriteCollection.
     * Initializes the sprites list.
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
     * Removes a sprite from the collection.
     *
     * @param sprite the sprite to remove
     */
    public void removeSprite(Sprite sprite) {
        this.sprites.remove(sprite);
    }

    /**
     * Notifies all sprites that time has passed.
     * This is used to update the state of each sprite.
     */
    public void notifyAllTimePassed() {
        // Create a new list to avoid concurrent modification exceptions
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
