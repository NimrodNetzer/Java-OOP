// 322394545 Nimrod Netzer
package sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Game;
import geometry.Collidable;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;

/**
 * The sprites.Paddle class represents the paddle in the Arkanoid game.
 * It is responsible for the movement and collision behavior of the paddle.
 */
public class Paddle implements Sprite, Collidable {
    private final KeyboardSensor keyboard;
    private final Color color;
    private final int speed;
    private Rectangle paddleRect;

    /**
     * Constructor for the sprites.Paddle class.
     *
     * @param rect     the initial rectangle representing the paddle
     * @param color    the color of the paddle
     * @param keyboard the keyboard sensor to control the paddle
     * @param speed    the speed of the paddle
     */
    public Paddle(Rectangle rect, Color color, KeyboardSensor keyboard, int speed) {
        this.paddleRect = rect;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
    }

    /**
     * Moves the paddle to the left.
     * If the paddle reaches the left edge, it wraps around to the right edge.
     */
    public void moveLeft() {
        if (paddleRect.getUpperLeft().getX() > -paddleRect.getWidth()) {
            paddleRect = new Rectangle(new Point(paddleRect.getUpperLeft().getX() - speed,
                    paddleRect.getUpperLeft().getY()), paddleRect.getWidth(), paddleRect.getHeight());
        } else {
            paddleRect = new Rectangle(new Point(800, paddleRect.getUpperLeft().getY()), paddleRect.getWidth(),
                    paddleRect.getHeight());
        }
    }

    /**
     * Moves the paddle to the right.
     * If the paddle reaches the right edge, it wraps around to the left edge.
     */
    public void moveRight() {
        if (paddleRect.getUpperLeft().getX() < 800) {
            paddleRect = new Rectangle(new Point(paddleRect.getUpperLeft().getX() + speed,
                    paddleRect.getUpperLeft().getY()), paddleRect.getWidth(), paddleRect.getHeight());
        } else {
            paddleRect = new Rectangle(new Point(-paddleRect.getWidth(), paddleRect.getUpperLeft().getY()),
                    paddleRect.getWidth(), paddleRect.getHeight());
        }
    }

    /**
     * Updates the paddle's position based on user input.
     * Moves the paddle left or right depending on the keys pressed.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the DrawSurface on which to draw the paddle
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) paddleRect.getUpperLeft().getX(), (int) paddleRect.getUpperLeft().getY(),
                (int) paddleRect.getWidth(), (int) paddleRect.getHeight());
    }

    /**
     * Returns the rectangle representing the paddle.
     *
     * @return the paddle's rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddleRect;
    }

    /**
     * Handles the collision of the ball with the paddle.
     * Adjusts the ball's velocity based on the collision point.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the ball's velocity at the time of collision
     * @return the new velocity after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double paddleRegionWidth = this.paddleRect.getWidth() / 5;
        double x = collisionPoint.getX() - this.paddleRect.getUpperLeft().getX();
        int region = (int) (x / paddleRegionWidth);
        double speed = currentVelocity.getSpeed();
        // Small margin for edge handling
        double epsilon = 1e-5;
        double leftEdge = this.paddleRect.getUpperLeft().getX();
        double rightEdge = leftEdge + this.paddleRect.getWidth();

        // If the collision is within a small margin near the left or right edge, adjust accordingly
        if (Math.abs(collisionPoint.getX() - leftEdge) < epsilon) {
            return Velocity.fromAngleAndSpeed(230, speed);
        } else if (Math.abs(collisionPoint.getX() - rightEdge) < epsilon) {
            return Velocity.fromAngleAndSpeed(60, speed);
        }

        // Adjust based on the region hit
        switch (region) {
            case 0:
                return Velocity.fromAngleAndSpeed(230, speed);
            case 1:
                return Velocity.fromAngleAndSpeed(250, speed);
            case 2:
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy()); // Straight bounce
            case 3:
                return Velocity.fromAngleAndSpeed(-60, speed);
            case 4:
                return Velocity.fromAngleAndSpeed(-30, speed);
            default:
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy()); // Default straight bounce
        }
    }

    /**
     * Adds the paddle to the game as both a sprite and a collidable.
     *
     * @param g the game to which the paddle is added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
