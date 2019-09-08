import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Coras {
    private static final int ALPHABET = 27;

    ArrayList<Vertex> bohr = new ArrayList<>();
    ArrayList<String> patterns = new ArrayList<>();
    HashMap<String, ArrayList<Integer>> positions = new HashMap<>();

    void initialize() {
        bohr.add(new Vertex(0, 0));
    }

    void add(String s) {
        int curVertex = 0;
        Vertex vertex = bohr.get(curVertex);
        for (int i = 0; i < s.length(); i++) {
            int symbol = s.charAt(i) - 'a' + 1;
            if (vertex.nextVertex[symbol] == -1) {
                bohr.add(new Vertex(curVertex, /*(char)*/ symbol));
                vertex.nextVertex[symbol] = bohr.size() - 1;
            }
            curVertex = vertex.nextVertex[symbol];
            vertex = bohr.get(curVertex);
        }
        vertex.isTerminal = true;
        patterns.add(s);
        positions.put(s, new ArrayList<>());
        vertex.patternNumber = patterns.size() - 1;
    }

    boolean contains(String s) {
        int curVertex = 0;
        for (int i = 0; i < s.length(); i++) {
            int symbol = s.charAt(i) - 'a';
            if (bohr.get(curVertex).nextVertex[symbol] == -1) {
                return false;
            }
            curVertex = bohr.get(curVertex).nextVertex[symbol];
        }
        return true;
    }

    int calcSuffLink(int v) {
        Vertex vertex = bohr.get(v);
        if (vertex.suffLink == -1) {
            if (v == 0 || vertex.parent == 0) {
                vertex.suffLink = 0;
            } else {
                vertex.suffLink = calcGo(calcSuffLink(vertex.parent), vertex.symbol);
            }
        }
        return vertex.suffLink;
    }

    int calcGo(int v, /*char ch*/int symbol) {
//            int symbol = ch - 'a';
        Vertex vertex = bohr.get(v);
        if (vertex.go[symbol] == -1) {
            if (vertex.nextVertex[symbol] != -1) {
                vertex.go[symbol] = bohr.get(v).nextVertex[symbol];
            } else {
                if (v == 0) {
                    vertex.go[symbol] = 0;
                } else {
                    vertex.go[symbol] = calcGo(calcSuffLink(v), /*ch*/symbol);
                }
            }
        }
        return vertex.go[symbol];
    }

    int calcShortSuffLink(int v) {
        Vertex vertex = bohr.get(v);
        if (vertex.shortSuffLink == -1) {
            int suff = calcSuffLink(v);
            if (suff == 0) {
                vertex.shortSuffLink = 0;
            } else {
                if (bohr.get(suff).isTerminal) {
                    vertex.shortSuffLink = suff;
                } else {
                    vertex.shortSuffLink = calcShortSuffLink(suff);
                }
            }
        }
        return vertex.shortSuffLink;
    }

    void isSubstring(int v, int curPos) {
        for (int suff = v; suff != 0; suff = calcSuffLink(suff)) {
            if (bohr.get(suff).isTerminal) {
                String s = patterns.get(bohr.get(suff).patternNumber);
                positions.get(s).add(curPos - s.length());
            }
        }
    }

    void findPositions(String text) {
        int curVertex = 0;
        for (int i = 0; i < text.length(); i++) {
            curVertex = calcGo(curVertex, text.charAt(i) - 'a' + 1);
            isSubstring(curVertex, i + 1);
        }
    }

    private static class Vertex {
        int[] nextVertex = new int[ALPHABET];
        int patternNumber;
        int suffLink = -1;
        int shortSuffLink = -1;
        int[] go = new int[ALPHABET];
        int parent;
        boolean isTerminal = false;
        int symbol;

        public Vertex(int parent, int symbol) {
            this.parent = parent;
            this.symbol = symbol;
            Arrays.fill(nextVertex, -1);
            Arrays.fill(go, -1);
        }
    }
}
