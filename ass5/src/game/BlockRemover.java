// 322394545 Nimrod Netzer
package game;

import listeners.HitListener;
import sprites.Ball;
import sprites.Block;

/**
 * The game.BlockRemover class is a listeners.HitListener that is responsible for removing blocks from the game.
 * It also maintains a counter for the remaining blocks in the game.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;

    /**
     * Constructs a game.BlockRemover with a reference to the game and the counter for remaining blocks.
     *
     * @param game the game object
     * @param remainingBlocks the counter for remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit should be removed from the game.
     * Also removes this listener from the block that is being removed.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hit the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(game);
        beingHit.removeHitListener(this);
        remainingBlocks.decrease(1);
    }
}
