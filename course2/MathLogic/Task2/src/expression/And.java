package expression;

public class And extends BinaryOperator {

    public And(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
        operator = "&";
    }
}