// 322394545 Nimrod Netzer
package game;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import geometry.Collidable;
import geometry.Point;
import geometry.Rectangle;
import listeners.ScoreTrackingListener;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.ScoreIndicator;
import sprites.Sprite;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * The main class for the Arkanoid game.
 * Manages the game's sprites, game environment, and GUI.
 */
public class Game {
    private final SpriteCollection sprites; // Collection of all sprites in the game
    private final GameEnvironment environment; // The game environment containing collidables
    private final GUI gui; // The graphical user interface for the game
    private Counter remainingBlocks; // game.Counter for the remaining blocks
    private Counter remainingBalls; // game.Counter for the remaining balls

    /**
     * Constructs a new game.Game object.
     * Initializes the sprite collection, game environment, and GUI.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
    }

    /**
     * Adds a collidable object to the game's environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite object to the game.
     *
     * @param s the sprite object to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes the game by adding boundaries, blocks, the paddle, and balls.
     * Called once before the game starts.
     */
    public void initialize() {
        // Initialize counters
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        // game.Counter for the player's score
        Counter score = new Counter(0);

        // Create and add score tracking listener
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(score);

        // Add boundaries
        Block topBoundary = new Block(new Point(0, 0), 800, 10, Color.GRAY);
        Block leftBoundary = new Block(new Point(0, 0), 10, 710, Color.GRAY);
        Block rightBoundary = new Block(new Point(790, 0), 10, 710, Color.GRAY);

        // Initialize block and ball remover
        BlockRemover blockRemover = new BlockRemover(this, this.remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);

        // Death region - bottom boundary
        Block bottomBoundary = new Block(new Point(0, 700), 800, 10, Color.GRAY);
        bottomBoundary.addHitListener(ballRemover);

        topBoundary.addToGame(this);
        bottomBoundary.addToGame(this);
        leftBoundary.addToGame(this);
        rightBoundary.addToGame(this);

        // Add blocks
        int screenWidth = 800;
        int blockWidth = 56;
        int blockHeight = 23;
        int initialY = 100;
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN};
        int[] blocksPerRow = {12, 11, 10, 9, 8, 7};

        for (int row = 0; row < blocksPerRow.length; row++) {
            int numBlocks = blocksPerRow[row];
            int startX = screenWidth - numBlocks * blockWidth - 10;
            for (int col = 0; col < numBlocks; col++) {
                Block block = new Block(new Point(startX + col * blockWidth, initialY + row * blockHeight),
                        blockWidth, blockHeight, colors[row]);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener); // Add score listener to each block

                this.remainingBlocks.increase(1);
            }
        }

        // Add paddle
        Paddle paddle = new Paddle(new Rectangle(new Point(350, 580), 120, 17), Color.ORANGE,
                this.gui.getKeyboardSensor(), 6);
        paddle.addToGame(this);

        // Add balls
        Ball ball1 = new Ball(new Point(400, 300), 10, Color.BLUE, environment);
        ball1.setVelocity(4, 4);
        ball1.addToGame(this);
        remainingBalls.increase(1);

        Ball ball2 = new Ball(new Point(450, 350), 10, Color.GREEN, environment);
        ball2.setVelocity(4, 5);
        ball2.addToGame(this);
        remainingBalls.increase(1);

        Ball ball3 = new Ball(new Point(350, 320), 10, Color.RED, environment);
        ball3.setVelocity(4, 5);
        ball3.addToGame(this);
        remainingBalls.increase(1);

        // Add score indicator
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Runs the main game loop.
     * Continuously updates and renders the game at a fixed frame rate.
     * Uses a sleeper to control the timing of each frame.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis(); // Record the start time of the frame
            DrawSurface d = gui.getDrawSurface();

            // Draw all sprites onto the draw surface
            this.sprites.drawAllOn(d);

            // Show the draw surface to display the updated frame
            gui.show(d);

            // Notify all sprites that a frame has passed (to update their state)
            this.sprites.notifyAllTimePassed();

            // Calculate the time used for processing this frame
            long usedTime = System.currentTimeMillis() - startTime;

            // Calculate how much time is left to sleep until the next frame
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;

            // Sleep for the remaining time to maintain the target frame rate
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }

            // Check if the game should end
            if (remainingBlocks.getValue() <= 0) {
                gui.close();
                return;
            }
            if (remainingBalls.getValue() <= 0) {
                gui.close();
                return;
            }
        }
    }

    /**
     * Removes a collidable object from the game's environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite object from the game.
     *
     * @param s the sprite object to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }
}
