import java.util.HashMap;
import java.util.Map;

/**
 * Test class for logical expressions.
 */
public class ExpressionsTest {

    /**
     * The main method to test the logical expressions.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // 1. Create an expression with at least three variables: (x ^ y) # z
        Expression x = new Var("x");
        Expression y = new Var("y");
        Expression z = new Var("z");
        Expression expr = new Xnor(new Xor(x, y), z);

        // 2. Print the expression
        System.out.println(expr);

        // 3. Print the value of the expression with an assignment to every variable
        Map<String, Boolean> assignment = new HashMap<>();
        assignment.put("x", true);
        assignment.put("y", false);
        assignment.put("z", true);
        try {
            System.out.println(expr.evaluate(assignment));
        } catch (Exception e) {
            System.out.println("Error evaluating expression: " + e.getMessage());
        }

        // 4. Print the Nandified version of the expression
        System.out.println(expr.nandify());

        // 5. Print the Norified version of the expression
        System.out.println(expr.norify());

        // 6. Print the simplified version of the expression
        System.out.println(expr.simplify());
    }
}
