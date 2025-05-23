// 322394545 Nimrod Netzer
package game;

/**
 * The Counter class manages a numerical count, with methods to increase, decrease, and retrieve the count.
 */
public class Counter {
    private int count;

    /**
     * Constructs a Counter with a specified initial count.
     *
     * @param number the initial count value
     */
    public Counter(int number) {
        this.count = number;
    }

    /**
     * Adds the specified number to the current count.
     *
     * @param number the number to add to the current count
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Subtracts the specified number from the current count.
     *
     * @param number the number to subtract from the current count
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Gets the current count value.
     *
     * @return the current count value
     */
    public int getValue() {
        return this.count;
    }
}
