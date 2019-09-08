package expression;

public class Or extends BinaryOperator {

    public Or(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
        operator = "|";
    }
}
