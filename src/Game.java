// 322394545 Nimrod Netzer

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The main class for the Arkanoid game.
 * Manages the game's sprites, game environment, and GUI.
 */
public class Game {
    private final SpriteCollection sprites; // Collection of all sprites in the game
    private final GameEnvironment environment; // The game environment containing collidables
    private final GUI gui; // The graphical user interface for the game

    /**
     * Constructs a new Game object.
     * Initializes the sprite collection, game environment, and GUI.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
    }

    /**
     * Retrieves the GUI object of the game.
     *
     * @return the GUI object of the game
     */
    public GUI getGui() {
        return this.gui;
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
     * Initializes the game by adding boundaries and balls.
     * Called once before the game starts.
     */
    public void initialize() {
        // Add boundaries
        Block topBoundary = new Block(new Point(0, 0), 800, 10, Color.GRAY);
        Block bottomBoundary = new Block(new Point(0, 590), 800, 10, Color.GRAY);
        Block leftBoundary = new Block(new Point(0, 0), 10, 600, Color.GRAY);
        Block rightBoundary = new Block(new Point(790, 0), 10, 600, Color.GRAY);

        topBoundary.addToGame(this);
        bottomBoundary.addToGame(this);
        leftBoundary.addToGame(this);
        rightBoundary.addToGame(this);

        // Add balls
        Ball ball1 = new Ball(new Point(400, 300), 10, Color.BLUE, environment);
        ball1.setVelocity(5, 6);
        ball1.addToGame(this);

        Ball ball2 = new Ball(new Point(450, 350), 10, Color.GREEN, environment);
        ball2.setVelocity(4, -7);
        ball2.addToGame(this);
    }

    /**
     * Runs the main game loop.
     * Continuously updates and renders the game at a fixed frame rate.
     * Uses a sleeper to control the timing of each frame.
     */
    @SuppressWarnings("InfiniteLoopStatement")
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
        }
    }
}
