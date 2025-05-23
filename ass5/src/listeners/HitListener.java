// 322394545 Nimrod Netzer
package listeners;

import sprites.Ball;
import sprites.Block;

/**
 * The listeners.HitListener interface represents an object that listens for hit events.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that is doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}
