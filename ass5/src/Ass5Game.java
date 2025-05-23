// 322394545 Nimrod Netzer

import game.Game;

/**
 * Ass3Game is the main class to initialize and run the Arkanoid game.
 */
public class Ass5Game {
    /**
     * The main method to create and run the Arkanoid game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the game object
        Game game = new Game();

        // Initialize the game
        game.initialize();

        // Run the game
        game.run();
    }
}
