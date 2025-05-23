// Nimrod Netzer 322394545

/**
 * Abstract base class for expressions, implementing common methods and behaviors.
 */
public abstract class BaseExpression implements Expression {

    /**
     * Helper method to check equality between two expressions.
     *
     * @param expOne The first expression to compare.
     * @param expTwo The second expression to compare.
     * @return true if the expressions are equal, false otherwise.
     */
    public boolean isEqual(Expression expOne, Expression expTwo) {
        return expOne.equals(expTwo);
    }
}
