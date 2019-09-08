package expression;

public abstract class BinaryOperator extends Expression {

    public Expression leftOperand;
    public Expression rightOperand;
    String operator;

    BinaryOperator(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public String toString() {
        return "(" + leftOperand + " " + operator + " " + rightOperand + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperator) {
            BinaryOperator other = (BinaryOperator) obj;
            if (other.operator.equals(operator)) {
                return (leftOperand.equals(other.leftOperand) && rightOperand.equals(other.rightOperand));
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return leftOperand.hashCode() * 37 + rightOperand.hashCode() * 43 + operator.hashCode();
    }
}