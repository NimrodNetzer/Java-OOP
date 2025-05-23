// Nimrod Netzer 322394545

import java.util.Map;

/**
 * Abstract class representing a binary expression.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression expressionOne;
    private Expression expressionTwo;

    /**
     * Constructs a binary expression with two sub-expressions.
     *
     * @param expressionOne The first sub-expression.
     * @param expressionTwo The second sub-expression.
     */
    public BinaryExpression(Expression expressionOne, Expression expressionTwo) {
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Retrieves the first sub-expression.
     *
     * @return The first sub-expression.
     */
    public Expression getExpressionOne() {
        return expressionOne;
    }

    /**
     * Retrieves the second sub-expression.
     *
     * @return The second sub-expression.
     */
    public Expression getExpressionTwo() {
        return expressionTwo;
    }

    /**
     * Sets the first sub-expression.
     *
     * @param expressionOne The new first sub-expression.
     */
    public void setExpressionOne(Expression expressionOne) {
        this.expressionOne = expressionOne;
    }

    /**
     * Sets the second sub-expression.
     *
     * @param expressionTwo The new second sub-expression.
     */
    public void setExpressionTwo(Expression expressionTwo) {
        this.expressionTwo = expressionTwo;
    }

    /**
     * Evaluates the binary expression with an empty assignment map.
     *
     * @return The result of the evaluation.
     * @throws Exception If an error occurs during evaluation.
     */
    public Boolean evaluate() throws Exception {
        try {
            return evaluate(Map.of());
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }
}
