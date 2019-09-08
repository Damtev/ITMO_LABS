package expression;

public class Impl extends BinaryOperator{

    public Impl(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
        operator = "->";
    }
}
