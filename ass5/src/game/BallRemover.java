// 322394545 Nimrod Netzer
package game;

import listeners.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * A game.BallRemover class that implements listeners.HitListener.
 * It is responsible for removing balls from the game when they hit the death-region block.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;

    /**
     * Constructs a game.BallRemover with a reference to the game and the counter for remaining balls.
     *
     * @param game          the game object
     * @param remainingBalls the counter for remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Handles the event when a ball hits the death region.
     *
     * @param beingHit the block that is being hit (the death region)
     * @param hitter   the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}
