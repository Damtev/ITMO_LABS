package parser;

import expression.*;

public class Parser {

    public Expression parse(String s) {
        s = s.replaceAll("\\s", "").replace("\n", "");
        Result result = Impl(s, 0);
        return result.acc;
    }

    private Result Impl(String s, int begin) {
        Result current = Or(s, begin);
        Expression acc = current.acc;
        while (current.rest < s.length()) {
            if (s.charAt(current.rest) != '-') {
                break;
            }
            current = Impl(s, current.rest + 2);
            acc = new Impl(acc, current.acc);
        }
        return new Result(acc, current.rest);
    }

    private Result Or(String s, int begin) {
        Result current = And(s, begin);
        Expression acc = current.acc;
        while (current.rest < s.length()) {
            if (s.charAt(current.rest) != '|') {
                break;
            }
            current = And(s, current.rest + 1);
            acc = new Or(acc, current.acc);
        }
        return new Result(acc, current.rest);
    }

    private Result And(String s, int begin) {
        Result current = Bracket(s, begin);
        Expression acc = current.acc;
        while (true) {
            if (current.rest == s.length()) {
                return current;
            }
            char sign = s.charAt(current.rest);
            if (sign != '&') {
                return current;
            }
            Result right = Bracket(s, current.rest + 1);
            acc = new And(acc, right.acc);
            current = new Result(acc, right.rest);
        }
    }

    private Result Bracket(String s, int begin) {
        char zeroChar = s.charAt(begin);
        if (zeroChar == '!') {
            Result next = Bracket(s, begin + 1);
            Expression result = new Not(next.acc);
            return new Result(result, next.rest);
        }
        if (zeroChar == '(') {
            Result r = Impl(s, begin + 1);
            if (s.charAt(r.rest) == ')') {
                r.setRest(r.rest + 1);
            }
            return r;
        }
        return Variable(s, begin);
    }

    private Result Variable(String s, int begin) {
        StringBuilder curVar = new StringBuilder();
        int i = 0;
        while (begin + i < s.length() && (Character.isLetter(s.charAt(begin + i)) ||
                Character.isDigit(s.charAt(begin + i)) || s.charAt(begin + i) == '\'')) {
            curVar.append(s.charAt(begin + i));
            ++i;
        }
        Expression result = new Variable(curVar.toString());
        return new Result(result, begin + i);
    }

    private class Result {

        private Expression acc;
        private int rest;

        Result(Expression acc, int rest) {
            this.acc = acc;
            this.rest = rest;
        }

        public void setRest(int rest) {
            this.rest = rest;
        }
    }
}