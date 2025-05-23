// 322394545 Nimrod Netzer

import java.awt.Color;

/**
 * Ass3Game is the main class to initialize and run the Arkanoid game.
 */
public class Ass3Game {
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

        // Define the screen width and the dimensions of the blocks
        int screenWidth = 800;
        int blockWidth = 56;
        int blockHeight = 23;

        // Define the initial y-coordinate for the first row of blocks
        int initialY = 100;

        // Define block colors in the order they appear in each row
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN};

        // Array defining the number of blocks in each row
        int[] blocksPerRow = {12, 11, 10, 9, 8, 7};

        // Create the blocks and add them to the game
        for (int row = 0; row < blocksPerRow.length; row++) {
            int numBlocks = blocksPerRow[row];
            int startX = screenWidth - numBlocks * blockWidth - 10;
            for (int col = 0; col < numBlocks; col++) {
                Block block = new Block(new Point(startX + col * blockWidth, initialY + row * blockHeight),
                        blockWidth, blockHeight, colors[row]);
                block.addToGame(game);
            }
        }

        // Create the paddle and add it to the game
        Paddle paddle = new Paddle(new Rectangle(new Point(350, 573), 120, 17), Color.ORANGE,
                game.getGui().getKeyboardSensor(), 6);
        paddle.addToGame(game);

        // Run the game
        game.run();
    }
}
