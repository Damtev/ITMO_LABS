package expression;

public class Variable extends Expression {

    public String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return variable.equals(obj.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return variable.hashCode();
    }
}