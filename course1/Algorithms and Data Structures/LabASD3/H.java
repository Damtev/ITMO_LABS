import java.util.ArrayList;
import java.util.Scanner;

class Node {

    Node parent;
    ArrayList<Node> child;
//    ArrayList<Node> grandChild;
    int power;

    public Node() {
        parent = null;
        child = new ArrayList<>();
//        grandChild = new ArrayList<>();
        power = 0;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public ArrayList<Node> getChild() {
        return child;
    }

    public void addChild(Node child) {
        this.child.add(child);
    }

//    public ArrayList<Node> getGrandChild() {
//        return grandChild;
//    }
//
//    public void addGrandchild(Node grandChild) {
//        this.grandChild.add(grandChild);
//    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}

public class H {

    static int solve(Node parent) {
        if (parent.getPower() > 0) {
            return parent.getPower();
        }
        int childSum = 0;
        int grandGhildSum = 0;
        for (int i = 0; i < parent.getChild().size(); i++) {
            Node child = parent.getChild().get(i);
            childSum += solve(child);
            for (int j = 0; j < child.getChild().size(); j++) {
                grandGhildSum += solve(child.getChild().get(j));
            }
        }
//        for (int i = 0; i < parent.getGrandChild().size(); i++) {
//            grandGhildSum += solve(parent.getGrandChild().get(i));
//        }
//        for (int i = 0; i < parent.getChild().size(); i++) {
//            Node child = parent.getChild().get(i);
//            for (int j = 0; j < child.getChild().size(); j++) {
//                grandGhildSum += solve(child.getChild().get(j));
//            }
//        }
        parent.setPower(Math.max(1 + grandGhildSum, childSum));
        return parent.getPower();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int[] p = new int[n];
        Node[] tree = new Node[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new Node();
        }
        for (int i = 0; i < n; i++) {
            p[i] = Integer.parseInt(in.nextLine());
        }
        int rootIndex = 0;
        for (int i = 0; i < n; i++) {
            if (p[i] != 0) {
                tree[i].setParent(tree[p[i] - 1]); //tree[p[i]]
                tree[p[i] - 1].addChild(tree[i]);
//                for (int j = 0; j < tree[i].getGrandChild().size(); j++) {
//                    tree[p[i] - 1].addGrandchild(tree[i].getChild().get(j));
//                }
            }
            else {
                rootIndex = i;
            }
        }
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < tree[i].getChild().size(); j++) {
//                Node child = tree[i].getChild().get(j);
//                for (int k = 0; k < child.getChild().size(); k++) {
//                    tree[i].addGrandchild(child.getChild().get(k));
//                }
//            }
//        }
        System.out.println(solve(tree[rootIndex]));
//        for (int i = 0; i < n; i++) {
//            System.out.println(tree[i].getParent() + " " + tree[i].getChild().toString() + /*" " + tree[i].getGrandChild().toString() + */" " + tree[i].getPower());
//        }
    }
}
