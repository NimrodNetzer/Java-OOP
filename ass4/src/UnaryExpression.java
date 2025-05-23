// Nimrod Netzer 322394545

/**
 * Abstract class representing unary expressions in a logical expression system.
 * Unary expressions operate on a single operand to produce a result.
 */
public abstract class UnaryExpression extends BaseExpression {

    /**
     * Abstract method to be implemented by subclasses to return the expression's inner operand.
     *
     * @return The inner operand of the unary expression.
     */
    public abstract Expression getExpression();
}
