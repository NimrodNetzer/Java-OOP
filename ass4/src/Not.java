// Nimrod Netzer 322394545

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class to represent the unary expression NOT.
 */
public class Not extends UnaryExpression {

    private Expression expression;

    /**
     * Constructor to initialize the NOT expression with a given sub-expression.
     *
     * @param expression The expression to negate.
     */
    Not(Expression expression) {
        this.expression = expression;
    }

    /**
     * Returns the type of this expression.
     *
     * @return The type "NOT".
     */
    @Override
    public String getType() {
        return "NOT";
    }

    /**
     * Setter for the expression to negate.
     *
     * @param expression The expression to set.
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Getter for the expression being negated.
     *
     * @return The expression being negated.
     */
    public Expression getExpression() {
        return expression;
    }

    /**
     * Evaluates the NOT expression using the variable values provided in the assignment.
     *
     * @param assignment A map containing variable assignments (variable name to boolean value).
     * @return The result of the negation operation.
     * @throws Exception If the expression contains a variable not in the assignment.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !expression.evaluate(assignment);
    }

    /**
     * A convenience method to evaluate the NOT expression with an empty assignment.
     *
     * @return The result of the negation operation with an empty assignment.
     * @throws Exception If evaluation encounters an error.
     */
    public Boolean evaluate() throws Exception {
        return !expression.evaluate(new HashMap<>());
    }

    /**
     * Returns a list of variables present in the expression.
     *
     * @return A list of variables in the expression.
     */
    public List<String> getVariables() {
        return expression.getVariables();
    }

    /**
     * Returns a string representation of the NOT expression.
     *
     * @return A string representing the NOT expression in the format "~(expression)".
     */
    public String toString() {
        return "~(" + expression + ")";
    }

    /**
     * Creates a new expression where occurrences of a variable are replaced with a given expression.
     *
     * @param var        The variable to replace.
     * @param expression The expression to replace with.
     * @return A new expression with the variable replaced.
     */
    public Expression assign(String var, Expression expression) {
        return new Not(this.expression.assign(var, expression));
    }

    /**
     * Converts the expression tree to an equivalent tree using only NAND operations.
     *
     * @return The NAND equivalent expression.
     */
    public Expression nandify() {
        Expression nandifyExpression = expression.nandify();
        return new Nand(nandifyExpression, nandifyExpression);
    }

    /**
     * Converts the expression tree to an equivalent tree using only NOR operations.
     *
     * @return The NOR equivalent expression.
     */
    public Expression norify() {
        Expression norifyExpression = expression.norify();
        return new Nor(norifyExpression, norifyExpression);
    }

    /**
     * Simplifies the expression to its minimal form.
     *
     * @return The simplified expression.
     */
    public Expression simplify() {
        Expression simplifiedExpression = expression.simplify();

        if (simplifiedExpression == null) {
            return null;  // Propagate null simplification
        } else if (isEqual(simplifiedExpression, new Val(false))) {
            return new Val(true);  // ~(false) = true
        } else if (isEqual(simplifiedExpression, new Val(true))) {
            return new Val(false); // ~(true) = false
        } else if (isEqual(simplifiedExpression, new Not(new Not(expression)))) {
            return expression.simplify();  // Simplify double negation to the original expression
        } else if (Objects.equals(simplifiedExpression.getType(), "NOT")) {
            return ((Not) simplifiedExpression).getExpression().simplify(); // ~(~x) = x
        }

        return new Not(simplifiedExpression); // Default case: return simplified Not expression
    }

}
