// Nimrod Netzer 322394545

import java.util.List;
import java.util.Map;

/**
 * Represents a boolean value in a logical expression.
 */
public class Val implements Expression {

    private Boolean value;

    /**
     * Constructs a Val object with the given boolean value.
     *
     * @param value The boolean value to be represented.
     */
    public Val(Boolean value) {
        this.value = value;
    }

    /**
     * Returns the type identifier for this expression (VAL for value).
     *
     * @return The type identifier "VAL".
     */
    public String getType() {
        return "VAL";
    }

    /**
     * Checks if this Val object is equal to another object.
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
        Val other = (Val) obj;
        return value.equals(other.value);
    }

    /**
     * Returns a hash code value for this Val object.
     *
     * @return A hash code value for this Val object.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Evaluates the expression using the provided variable assignments.
     *
     * @param assignment A map of variable names to their boolean values.
     * @return The evaluated boolean value of this Val.
     * @throws Exception If evaluation encounters an undefined variable.
     */
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return value;
    }

    /**
     * Evaluates the expression using an empty assignment map.
     *
     * @return The evaluated boolean value of this Val.
     * @throws Exception If evaluation encounters an undefined variable.
     */
    public Boolean evaluate() throws Exception {
        return evaluate(Map.of());
    }

    /**
     * Returns an empty list since Val does not contain any variables.
     *
     * @return An empty list of variables.
     */
    public List<String> getVariables() {
        return List.of();
    }

    /**
     * Returns a string representation of the Val object.
     *
     * @return "T" if value is true, "F" if value is false, otherwise null.
     */
    public String toString() {
        if (value == null) {
            return null;
        }
        if (value) {
            return "T";
        }
        return "F";
    }

    /**
     * Returns a new Val object with the same value as this Val (immutable assignment).
     *
     * @param var        The variable name to be replaced (not used in Val).
     * @param expression The expression to replace with (not used in Val).
     * @return A new Val object with the same boolean value.
     */
    public Expression assign(String var, Expression expression) {
        return new Val(value);
    }

    /**
     * Returns the current boolean value of this Val.
     *
     * @return The boolean value of this Val.
     */
    public Boolean getValue() {
        return value;
    }

    /**
     * Sets a new boolean value for this Val.
     *
     * @param value The new boolean value to be set.
     */
    public void setValue(Boolean value) {
        this.value = value;
    }

    /**
     * Returns the expression tree resulting from converting all operations to the logical Nand operation.
     *
     * @return This Val object, as logical Nand operation doesn't change Val.
     */
    public Expression nandify() {
        return this;
    }

    /**
     * Returns the expression tree resulting from converting all operations to the logical Nor operation.
     *
     * @return This Val object, as logical Nor operation doesn't change Val.
     */
    public Expression norify() {
        return this;
    }

    /**
     * Simplifies the Val expression.
     *
     * @return A new Val object with the same boolean value (no simplification for Val).
     */
    public Expression simplify() {
        if (value == null) {
            return this;
        }
        // Base case: if the value is valid (true or false) then return the expression
        return new Val(value);
    }
}
