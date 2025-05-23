// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;

/**
 * Represents a variable in a logical expression.
 */
public class Var implements Expression {

    private String variable;

    /**
     * Constructs a Var object with the given variable name.
     *
     * @param variable The name of the variable.
     */
    Var(String variable) {
        this.variable = variable;
    }

    /**
     * Returns the type identifier for this expression (VAR for variable).
     *
     * @return The type identifier "VAR".
     */
    public String getType() {
        return "VAR";
    }

    /**
     * Checks if this Var object is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Var other = (Var) obj;
        return variable.equals(other.variable);
    }

    /**
     * Returns a hash code value for this Var object.
     *
     * @return A hash code value for this Var object.
     */
    @Override
    public int hashCode() {
        return variable.hashCode();
    }

    /**
     * Returns the name of the variable.
     *
     * @return The variable name.
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Sets a new name for the variable.
     *
     * @param variable The new variable name.
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * Evaluates the expression using the provided variable assignments.
     *
     * @param assignment A map of variable names to their boolean values.
     * @return The evaluated boolean value of this variable.
     * @throws Exception If the variable is not assigned in the provided map.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        try {
            return assignment.get(variable);
        } catch (Exception e) {
            throw new Exception("Variable '" + variable + "' is not assigned");
        }
    }

    /**
     * Evaluates the expression using an empty assignment map.
     *
     * @return The evaluated boolean value of this variable.
     * @throws Exception If the variable is not assigned in the empty map.
     */
    public Boolean evaluate() throws Exception {
        return evaluate(Map.of()); // empty assignment
    }

    /**
     * Returns a list containing the variable name.
     *
     * @return A list containing the variable name as a single element.
     */
    public List<String> getVariables() {
        return List.of(variable); // just one variable in this case
    }

    /**
     * Returns a string representation of the variable name.
     *
     * @return The variable name as a string.
     */
    public String toString() {
        return variable; // just the variable name itself as a string expression
    }

    /**
     * Returns a new expression in which all occurrences of the current variable
     * are replaced with the provided expression (if the variable matches).
     *
     * @param var        The variable name to be replaced.
     * @param expression The expression to replace with.
     * @return The new expression after substitution, or the original variable if no match.
     */
    public Expression assign(String var, Expression expression) {
        if (variable.equals(var)) {
            return expression;
        } else {
            return new Var(variable);
        }
    }

    /**
     * Returns the expression tree resulting from converting all operations to the logical Nand operation.
     *
     * @return This Var object, as logical Nand operation doesn't change Var.
     */
    public Expression nandify() {
        return this;
    }

    /**
     * Returns the expression tree resulting from converting all operations to the logical Nor operation.
     *
     * @return This Var object, as logical Nor operation doesn't change Var.
     */
    public Expression norify() {
        return this;
    }

    /**
     * Simplifies the variable expression.
     *
     * @return A new Var object with the same variable name (no simplification for Var).
     */
    @Override
    public Expression simplify() {
        if (variable == null) {
            return null;
        }
        return new Var(variable);
    }
}
