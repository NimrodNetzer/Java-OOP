// 322394545 Nimrod Netzer
package sprites;

import biuoop.DrawSurface;
import game.Counter;
import game.Game;

import java.awt.Color;

/**
 * A sprite that displays the current score at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private final Counter scoreCounter; // game.Counter to keep track of the current score

    /**
     * Constructs a sprites.ScoreIndicator with the specified score counter.
     *
     * @param scoreCounter the counter to use for displaying the score
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.scoreCounter = scoreCounter;
    }

    /**
     * Draws the score on the given DrawSurface.
     * Implements the sprites.Sprite interface method.
     *
     * @param d the DrawSurface to draw the score on
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(21, 40, "Score: " + this.scoreCounter.getValue(), 25);
    }

    /**
     * Notifies the sprite that time has passed.
     * Implements the sprites.Sprite interface method.
     * No action is needed for this implementation.
     */
    @Override
    public void timePassed() {
        // No action needed on time passage
    }

    /**
     * Adds the sprites.ScoreIndicator to the game as a sprite.
     *
     * @param game the game to add the sprites.ScoreIndicator to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
