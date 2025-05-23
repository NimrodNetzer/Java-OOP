// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a logical expression XOR (exclusive OR).
 */
public class Xor extends BinaryExpression {
    private final Expression expressionOne;
    private final Expression expressionTwo;

    /**
     * Constructs Xor object with two sub-expressions.
     *
     * @param expressionOne The first sub-expression.
     * @param expressionTwo The second sub-expression.
     */
    public Xor(Expression expressionOne, Expression expressionTwo) {
        super(expressionOne, expressionTwo);
        this.expressionOne = expressionOne;
        this.expressionTwo = expressionTwo;
    }

    /**
     * Returns the type identifier for this expression (XOR).
     *
     * @return The type identifier "XOR".
     */
    public String getType() {
        return "XOR";
    }

    /**
     * Evaluates the XOR expression using the variable values provided in the assignment.
     *
     * @param assignment A map of variable names to their boolean values.
     * @return The evaluated boolean result of the XOR operation.
     * @throws Exception If an error occurs during evaluation.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return expressionOne.evaluate(assignment) ^ expressionTwo.evaluate(assignment);
        } catch (Exception e) {
            throw new Exception("Error evaluating expression: " + e.getMessage());
        }
    }

    /**
     * Returns a list of variables present in the XOR expression.
     *
     * @return A list of variable names.
     */
    public List<String> getVariables() {
        return Stream.of(expressionOne.getVariables(), expressionTwo.getVariables())
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Returns a string representation of the XOR expression.
     *
     * @return A string representing the XOR expression.
     */
    public String toString() {
        return "(" + expressionOne.toString() + " ^ " + expressionTwo.toString() + ")";
    }

    /**
     * Returns a new expression where occurrences of a specific variable are replaced with the provided expression.
     *
     * @param var        The variable name to replace.
     * @param expression The expression to replace with.
     * @return A new XOR expression with substitutions.
     */
    public Expression assign(String var, Expression expression) {
        return new Xor(expressionOne.assign(var, expression), expressionTwo.assign(var, expression));
    }

    /**
     * Converts the XOR expression to an equivalent expression using only NAND operations.
     *
     * @return An equivalent expression using only NAND operations.
     */
    public Expression nandify() {
        Expression nandifyExpOne = expressionOne.nandify();
        Expression nandifyExpTwo = expressionTwo.nandify();
        return new Nand(
                new Nand(nandifyExpOne, new Nand(nandifyExpOne, nandifyExpTwo)),
                new Nand(nandifyExpTwo, new Nand(nandifyExpOne, nandifyExpTwo))
        );
    }

    /**
     * Converts the XOR expression to an equivalent expression using only NOR operations.
     *
     * @return An equivalent expression using only NOR operations.
     */
    public Expression norify() {
        Expression norifyExpOne = expressionOne.norify();
        Expression norifyExpTwo = expressionTwo.norify();
        return new Nor(
                new Nor(new Nor(norifyExpOne, norifyExpOne), new Nor(norifyExpTwo, norifyExpTwo)),
                new Nor(norifyExpOne, norifyExpTwo));
    }

    /**
     * Simplifies the XOR expression.
     *
     * @return A simplified version of the current XOR expression.
     */
    @Override
    public Expression simplify() {
        Expression simplifiedExpOne = expressionOne.simplify();
        Expression simplifiedExpTwo = expressionTwo.simplify();

        // Apply simplification rules
        if (isEqual(simplifiedExpOne, new Val(true))) {
            return new Not(simplifiedExpTwo); // True ^ x = ~x
        } else if (isEqual(simplifiedExpTwo, new Val(true))) {
            return new Not(simplifiedExpOne); // x ^ True = ~x
        } else if (isEqual(simplifiedExpOne, new Val(false))) {
            return simplifiedExpTwo; // False ^ x = x
        } else if (isEqual(simplifiedExpTwo, new Val(false))) {
            return simplifiedExpOne; // x ^ False = x
        } else if (isEqual(simplifiedExpOne, simplifiedExpTwo)) {
            return new Val(false); // x ^ x = false
        }
        // default: return a new xor expression with simplified operands
        return new Xor(simplifiedExpOne, simplifiedExpTwo);
    }
}
