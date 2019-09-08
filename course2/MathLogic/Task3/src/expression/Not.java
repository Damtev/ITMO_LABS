package expression;

public class Not extends Expression {

    public Expression operand;

    public Not(Expression operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "!" + operand;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Not) {
            return operand.equals(((Not) obj).operand);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return operand.hashCode();
    }
}