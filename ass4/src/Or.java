// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class to represent the logical OR expression.
 */
public class Or extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs an OR expression with two sub-expressions.
     *
     * @param expressionOne The first sub-expression.
     * @param expressionTwo The second sub-expression.
     */
    public Or(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type of this expression.
     *
     * @return The type "OR".
     */
    public String getType() {
        return "OR";
    }

    /**
     * Evaluates the OR expression using the variable values provided in the assignment.
     *
     * @param assignment A map containing variable assignments (variable name to boolean value).
     * @return The result of the OR operation.
     * @throws Exception If the expression contains a variable not in the assignment.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return expressionOne.evaluate(assignment) || expressionTwo.evaluate(assignment);
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * A convenience method to evaluate the OR expression with an empty assignment.
     *
     * @return The result of the OR operation with an empty assignment.
     * @throws Exception If evaluation encounters an error.
     */
    public Boolean evaluate() throws Exception {
        try {
            return evaluate(Map.of());
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Returns a list of variables present in the expression.
     *
     * @return A list of variables in the expression.
     */
    public List<String> getVariables() {
        return Stream.of(expressionOne.getVariables(), expressionTwo.getVariables())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the OR expression.
     *
     * @return A string representing the OR expression in the format "(expressionOne | expressionTwo)".
     */
    public String toString() {
        return "(" + expressionOne.toString() + " | " + expressionTwo.toString() + ")";
    }

    /**
     * Creates a new expression where occurrences of a variable are replaced with a given expression.
     *
     * @param var        The variable to replace.
     * @param expression The expression to replace with.
     * @return A new expression with the variable replaced.
     */
    public Expression assign(String var, Expression expression) {
        return new Or(expressionOne.assign(var, expression), expressionTwo.assign(var, expression));
    }

    /**
     * Converts the expression tree to an equivalent tree using only NAND operations.
     *
     * @return The NAND equivalent expression.
     */
    public Expression nandify() {
        Expression nandifyExpOne = expressionOne.nandify();
        Expression nandifyExpTwo = expressionTwo.nandify();
        return new Nand(
                new Nand(nandifyExpOne, nandifyExpOne),
                new Nand(nandifyExpTwo, nandifyExpTwo)
        );
    }

    /**
     * Converts the expression tree to an equivalent tree using only NOR operations.
     *
     * @return The NOR equivalent expression.
     */
    public Expression norify() {
        Expression norifyExpOne = expressionOne.norify();
        Expression norifyExpTwo = expressionTwo.norify();
        return new Nor(
                new Nor(norifyExpOne, norifyExpTwo),
                new Nor(norifyExpOne, norifyExpTwo)
        );
    }

    /**
     * Simplifies the expression to its minimal form.
     *
     * @return The simplified expression.
     */
    public Expression simplify() {
        Expression simplifiedOne = expressionOne.simplify();
        Expression simplifiedTwo = expressionTwo.simplify();

        // Apply simplification rules
        if (isEqual(simplifiedOne, new Val(true))) {
            return new Val(true); // true | x = true
        } else if (isEqual(simplifiedTwo, new Val(true))) {
            return new Val(true); // x | true = true
        } else if (isEqual(simplifiedOne, new Val(false))) {
            return simplifiedTwo; // false | x = x
        } else if (isEqual(simplifiedTwo, new Val(false))) {
            return simplifiedOne; // x | false = x
        } else if (isEqual(simplifiedOne, simplifiedTwo)) {
            return simplifiedOne; // x | x = x
        }
        // default: return a new OR expression with simplified operands
        return new Or(simplifiedOne, simplifiedTwo);
    }
}
