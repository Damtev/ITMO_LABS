package expression;

public abstract class Expression {

    private int entity;
    private int hypNumber;
    private int axiomNumber;
    private int fromMP;
    private int toMP;
    private boolean used;

    public boolean isHyp() {
        return entity == 1;
    }

    public int getHypNumber() {
        return hypNumber;
    }

    public boolean isAxiom() {
        return entity == 2;
    }

    public int getAxiomNumber() {
        return axiomNumber;
    }

    public boolean isMP() {
        return entity == 3;
    }

    public int getFromMP() {
        return fromMP;
    }

    public int getToMP() {
        return toMP;
    }

    public void setHyp() {
        entity = 1;
    }

    public void setHypNumber(int hypNumber) {
        this.hypNumber = hypNumber;
    }

    public void setAxiom() {
        entity = 2;
    }

    public void setAxiomNumber(int axiomNumber) {
        this.axiomNumber = axiomNumber;
    }

    public void setMP() {
        entity = 3;
    }

    public void setFromMP(int fromMP) {
        this.fromMP = fromMP;
    }

    public void setToMP(int toMP) {
        this.toMP = toMP;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        this.used = true;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}