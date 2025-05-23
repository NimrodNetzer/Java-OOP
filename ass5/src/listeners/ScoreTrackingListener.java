// 322394545 Nimrod Netzer
package listeners;

import game.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * A listener class that tracks the score in the game.
 * Implements the listeners.HitListener interface to respond to hit events.
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore; // game.Counter to keep track of the current score

    /**
     * Constructs a listeners.ScoreTrackingListener with the specified score counter.
     *
     * @param scoreCounter the counter to use for tracking the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Responds to a hit event by increasing the score.
     * Adds points for hitting a block and additional points for clearing all blocks.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Add points for hitting a block
        currentScore.increase(5);

        // Check if all blocks are removed by comparing the current score
        // When the total score is 5 (points for each block) * 57 (number of blocks), meaning all the block removed
        if (currentScore.getValue() == (5 * 57)) {
            // All the block were removed, add 100 points as required
            currentScore.increase(100);
            // Note: The score update will not be seen due to the game ending at the exact moment, the blocks removed
        }
    }
}
