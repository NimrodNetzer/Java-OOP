// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;

/**
 * Represents an expression in a logical or arithmetic context.
 */
public interface Expression {

    /**
     * Evaluate the expression using the variable values provided in the assignment.
     *
     * @param assignment A map containing variable names and their boolean values.
     * @return The result of evaluating the expression with the given assignment.
     * @throws Exception If the expression contains a variable not in the assignment.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;

    /**
     * A convenience method to evaluate the expression with an empty assignment.
     *
     * @return The result of evaluating the expression with an empty assignment.
     * @throws Exception If evaluation encounters an error.
     */
    Boolean evaluate() throws Exception;

    /**
     * Returns a list of variables (names) used in the expression.
     *
     * @return List of variable names used in the expression.
     */
    List<String> getVariables();

    /**
     * Returns a string representation of the expression.
     *
     * @return String representation of the expression.
     */
    String toString();

    /**
     * Creates a new expression by replacing occurrences of the specified variable
     * with the provided expression.
     *
     * @param var        The variable to replace.
     * @param expression The expression to replace with.
     * @return A new expression with the variable replaced by the provided expression.
     */
    Expression assign(String var, Expression expression);

    /**
     * Converts the expression to an equivalent expression using only Nand operations.
     *
     * @return Equivalent expression using only Nand operations.
     */
    Expression nandify();

    /**
     * Converts the expression to an equivalent expression using only Nor operations.
     *
     * @return Equivalent expression using only Nor operations.
     */
    Expression norify();

    /**
     * Simplifies the expression to its minimal form.
     *
     * @return Simplified expression.
     */
    Expression simplify();

    /**
     * Retrieves the type of the expression.
     *
     * @return The type of the expression as a string identifier.
     */
    String getType();
}
