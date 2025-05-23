package listeners;

/**
 * The listeners.HitNotifier interface defines methods for adding and removing hit listeners.
 * Implementing classes can notify listeners about hit events.
 */
public interface HitNotifier {
    /**
     * Adds a hit listener to the list of listeners.
     *
     * @param hl the hit listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a hit listener from the list of listeners.
     *
     * @param hl the hit listener to remove
     */
    void removeHitListener(HitListener hl);
}
