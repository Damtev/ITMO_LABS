#include <iostream>
#include <vector>

using namespace std;

struct Node {
    int value;
    int set;

    Node() {
        value = INT32_MAX;
        set = 0;
    }

    explicit Node(int value) {
        this->value = value;
        set = 0;
    }

    void set_value(int x) {
        if (value < x) {
            value = x;
            set = x;
        }
    }
};

struct Pair {
    int value;
    int index;

    Pair(int value, int index) {
        this->value = value;
        this->index = index;
    }
};

int size, n, m;
vector<Node> tree;

void push(int node) {
    if (node >= tree.size() - size) {
        tree[node].set = 0;
        return;
    }
    if (tree[node].set != 0) {
        tree[(node << 1) + 1].set_value(tree[node].set);
        tree[(node << 1) + 2].set_value(tree[node].set);
        tree[node].set = 0;
    }
}

Pair min_node(Pair first, Pair second) {
    return (first.value <= second.value) ? first : second;
}

void update(int node, int tl, int tr, int l, int r, int newVal) {
    if (l >= r) {
        return;
    }
    if (tl == l && tr == r) {
        tree[node].set_value(newVal);
        return;
    }
    push(node);
    int tm = (tl + tr) >> 1;
    update((node << 1) + 1, tl, tm, l, min(r, tm), newVal);
    update((node << 1) + 2, tm, tr, max(l, tm), r, newVal);
    tree[node].value = min(tree[(node << 1) + 1].value, tree[(node << 1) + 2].value);
}

Pair find_node(int node, int tl, int tr, int l, int r) {
    if (l >= r) {
        return {INT32_MAX, -1};
    }
    if (tl == l && tr == r) {
        return {tree[node].value, node};
    }
    push(node);
    int tm = (tl + tr) >> 1;
    return min_node(find_node((node << 1) + 1, tl, tm, l, min(r, tm)),
                    find_node((node << 1) + 2, tm, tr, max(l, tm), r));
}

int find_index(int node) {
    while (node < tree.size() - size) {
        push(node);
        node = (tree[(node << 1) + 1].value == tree[node].value) ? (node << 1) + 1 : (node << 1) + 2;
    }
    return node;
}

int main() {
    cin >> n >> m;
    size = 1;
    while (size < n) {
        size <<= 1;
    }
    for (int i = 0; i < (size << 1) - 1 - size + n; i++) {
        tree.emplace_back(0);
    }
    for (int i = (size << 1) - 1 - size + n; i < (size << 1) - 1; i++) {
        tree.emplace_back(INT32_MAX);
    }
    string command;
    int l, r;
    for (int i = 0; i < m; ++i) {
        cin >> command >> l >> r;
        if (command[0] == 'd') {
            int newVal;
            cin >> newVal;
            update(0, 0, size, l - 1, r, newVal);
        }
        else {
            Pair ans = find_node(0, 0, size, l - 1, r);
            cout << ans.value << " " << (find_index(ans.index) - size + 2) << '\n';
        }
    }
    return 0;
}