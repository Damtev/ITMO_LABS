import expression.*;
import parser.Parser;

import java.io.*;
import java.util.*;

public class Solver {

    private static Parser parser;
    private static Expression[] axioms = new Expression[10];
    private static Expression proofingStatement;
    private static List<Expression> hypothesises = new ArrayList<>();
    private static List<Expression> trueStatements = new ArrayList<>();
    private static Map<Expression, HashSet<Expression>> rightPartsOfImpl = new HashMap<>();
    private static Map<Expression, Integer> expressionEarliestNumbers = new HashMap<>();

    private static void initAxioms() {
        axioms[0] = new Impl(new Variable("A"), new Impl(new Variable("B"), new Variable("A")));
        axioms[1] = new Impl(new Impl(new Variable("A"), new Variable("B")),
                new Impl(new Impl(new Variable("A"), new Impl(new Variable("B"), new Variable("C"))),
                        new Impl(new Variable("A"), new Variable("C"))));
        axioms[2] = new Impl(new Variable("A"),
                new Impl(new Variable("B"), new And(new Variable("A"), new Variable("B"))));
        axioms[3] = new Impl(new And(new Variable("A"), new Variable("B")), new Variable("A"));
        axioms[4] = new Impl(new And(new Variable("A"), new Variable("B")), new Variable("B"));
        axioms[5] = new Impl(new Variable("A"), new Or(new Variable("A"), new Variable("B")));
        axioms[6] = new Impl(new Variable("B"), new Or(new Variable("A"), new Variable("B")));
        axioms[7] = new Impl(new Impl(new Variable("A"), new Variable("B")),
                new Impl(new Impl(new Variable("C"), new Variable("B")),
                        new Impl(new Or(new Variable("A"), new Variable("C")), new Variable("B"))));
        axioms[8] = new Impl(new Impl(new Variable("A"), new Variable("B")),
                new Impl(new Impl(new Variable("A"), new Not(new Variable("B"))),
                        new Not(new Variable("A"))));
        axioms[9] = new Impl(new Not(new Not(new Variable("A"))), new Variable("A"));
    }

    private static boolean checkHyp(Expression expression) {
        for (Expression hyp : hypothesises) {
            boolean equals = expression.equals(hyp);
            if (equals) {
                expression.setHyp();
                expression.setHypNumber(hyp.getHypNumber());
                return true;
            }
        }
        return false;
    }

    private static boolean checkAxiom(Expression expression) {
        for (int i = 0; i < axioms.length; i++) {
            if (matchesToAxiom(expression, axioms[i])) {
                expression.setAxiom();
                expression.setAxiomNumber(i + 1);
                return true;
            }
        }
        return false;
    }

    private static boolean matchesToAxiom(Expression expression, Expression axiom) {
        HashMap<String, Expression> substitution = new HashMap<>();
        return matchesToAxiom(axiom, expression, substitution);
    }

    private static boolean matchesToAxiom(Expression left, Expression right, HashMap<String, Expression> substitution) {
        if (left instanceof BinaryOperator) {
            if (left.getClass() == right.getClass()) {
                BinaryOperator leftOperator = (BinaryOperator) left;
                BinaryOperator rightOperator = (BinaryOperator) right;
                return (matchesToAxiom(leftOperator.leftOperand, rightOperator.leftOperand, substitution) &&
                        matchesToAxiom(leftOperator.rightOperand, rightOperator.rightOperand, substitution));
            }
            return false;
        }
        if (left instanceof Not) {
            if (left.getClass() == right.getClass()) {
                return matchesToAxiom(((Not) left).operand, ((Not) right).operand, substitution);
            }
            return false;
        }
        if (left instanceof Variable) {
            String variable = ((Variable) left).variable;
            if (substitution.containsKey(variable)) {
                return substitution.get(variable).equals(right);
            } else {
                substitution.put(variable, right);
            }
        }
        return true;
    }

    private static boolean checkModusPonens(Expression expression) {
        if (!rightPartsOfImpl.containsKey(expression)) {
            return false;
        }
        HashSet<Expression> leadsTo = rightPartsOfImpl.get(expression);
        for (Expression curExpr : leadsTo) {
            if (expressionEarliestNumbers.containsKey(((Impl) curExpr).leftOperand)) {
                expression.setMP();
                expression.setFromMP(expressionEarliestNumbers.get(((Impl) curExpr).leftOperand));
                expression.setToMP(expressionEarliestNumbers.get(curExpr));
                return true;
            }
        }
        return false;
    }

    private static boolean checkNewLine(String line) {
        if (proofingStatement == null) {
            if (line.contains("|-")) {
                int index = line.indexOf("|-");
                if (index == 0) {
                    proofingStatement = parser.parse(line.substring(2));
                } else {
                    String[] hyps = line.substring(0, index).split(",");
                    for (int i = 0; i < hyps.length; i++) {
                        Expression curHyp = parser.parse(hyps[i]);
                        curHyp.setHyp();
                        curHyp.setHypNumber(i + 1);
                        hypothesises.add(curHyp);
                    }
                    proofingStatement = parser.parse(line.substring(index + 2));
                }
                return true;
            }
            return false;
        } else {
            Expression curExpr = parser.parse(line);
            boolean isTrue = checkHyp(curExpr) || checkAxiom(curExpr) || checkModusPonens(curExpr);
            if (isTrue) {
                trueStatements.add(curExpr);
                if (curExpr instanceof Impl) {
                    Impl impl = (Impl) curExpr;
                    if (!rightPartsOfImpl.containsKey(impl.rightOperand)) {
                        rightPartsOfImpl.put(impl.rightOperand, new HashSet<>());
                    }
                    rightPartsOfImpl.get(impl.rightOperand).add(impl);
                }
                expressionEarliestNumbers.putIfAbsent(curExpr, trueStatements.size() - 1);
            }
            return isTrue;
        }
    }

    private static void setUsed(Expression expression) {
        expression.setUsed();
        if (expression.isMP()) {
            int from = expression.getFromMP();
            int to = expression.getToMP();
            setUsed(trueStatements.get(from));
            setUsed(trueStatements.get(to));
        }
    }

    private static void minimize() {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < hypothesises.size(); i++) {
            line.append(hypothesises.get(i));
            if (i != hypothesises.size() - 1) {
                line.append(", ");
            } else {
                line.append(" ");
            }
        }
        line.append("|-").append(" ").append(proofingStatement).append("\n");
        System.out.print(line);
        int count = 1;
        Map<Integer, Integer> oldToNewIndex = new HashMap<>();
        for (Expression expression : trueStatements) {
            if (!expression.isUsed()) {
                continue;
            }
            oldToNewIndex.put(expressionEarliestNumbers.get(expression), count);

            System.out.print(String.format("[%d. ", count));
            if (expression.isHyp()) {
                System.out.println(String.format("Hypothesis %d", expression.getHypNumber()) + "] " + expression);
            } else if (expression.isAxiom()) {
                System.out.println(String.format("Ax. sch. %d", expression.getAxiomNumber()) + "] " + expression);
            } else {
                System.out.println(String.format("M.P. %d, %d", oldToNewIndex.get(expression.getToMP()),
                        oldToNewIndex.get(expression.getFromMP())) + "] " + expression);
            }
            ++count;
        }
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            parser = new Parser();
            String line;
            initAxioms();
            boolean isCorrect = true;
            while ((line = reader.readLine()) != null) {
                if (!checkNewLine(line)) {
                    isCorrect = false;
                }
            }
            if (!isCorrect || !trueStatements.get(trueStatements.size() - 1).equals(proofingStatement)) {
                System.out.println("Proof is incorrect");
            } else {
                setUsed(trueStatements.get(trueStatements.size() - 1));
                minimize();
            }
        }
    }
}