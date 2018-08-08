#include <iostream>

using namespace std;

struct Node {
    int64_t data;
    int priority;
    int64_t sum;
    int64_t min;
    int64_t max;
    Node *left, *right;

    explicit Node(int64_t data) {
        this->data = sum = min = max = data;
        priority = rand();
        left = right = nullptr;
    }
};

Node *root = nullptr;

void recalc(Node *node) {
    node->sum = node->data;
    node->min = node->data;
    node->max = node->data;
    if (node->left != nullptr) {
        node->sum += node->left->sum;
        node->min = node->left->min;
    }
    if (node->right != nullptr) {
        node->sum += node->right->sum;
        node->max = node->right->max;
    }
}

pair<Node *, Node *> split(Node *node, int64_t key) {
    if (node == nullptr) {
        return {nullptr, nullptr};
    }
    if (key <= node->data) {
        pair<Node *, Node *> split1 = split(node->left, key);
        node->left = split1.second;
        recalc(node);
//        if (split1.first) {
//            recalc(split1.first);
//        }
        return {split1.first, node};
    } else {
        pair<Node *, Node *> split1 = split(node->right, key);
        node->right = split1.first;
        recalc(node);
//        if (split1.second) {
//            recalc(split1.second);
//        }
        return {node, split1.second};
    }
}

Node *merge(Node *t1, Node *t2) {
    if (t1 == nullptr) {
        return t2;
    }
    if (t2 == nullptr) {
        return t1;
    }
    if (t1->priority > t2->priority) {
        t1->right = merge(t1->right, t2);
        recalc(t1);
        return t1;
    } else {
        t2->left = merge(t1, t2->left);
        recalc(t2);
        return t2;
    }
}

Node *find(int64_t data) {
    Node *node = root;
    while (node != nullptr) {
        if (data < node->data) {
            node = node->left;
        }
        else if (data > node->data) {
            node = node->right;
        }
        else {
            return node;
        }
    }
    return nullptr;
}

Node *add(Node *node, Node *newNode) {
    if (node == nullptr) {
        return newNode;
    }
    if (find(newNode->data)) {
        return node;
    }
    auto split1 = split(node, newNode->data);
    Node *left = split1.first;
    Node *right = split1.second;
    return merge(merge(left, newNode), right);
}

void add(Node *newNode) {
    root = add(root, newNode);
}

int64_t sum(Node *node, int l, int r) {
    if (node == nullptr) {
        return 0;
    }
    if (node->min > r || node->max < l) {
        return 0;
    }
    if (node->min >= l && node->max <= r) {
        return node->sum;
    }
    return sum(node->left, l, r) +
           (node->data >= l && node->data <= r ? node->data : 0) +
           sum(node->right, l, r);
}

int main() {
    int n;
    cin >> n;
    string operation;
    int64_t answer = -1;
    for (int i = 0; i < n; ++i) {
        cin >> operation;
        if (operation == "+") {
            int val;
            cin >> val;
            if (answer >= 0) {
                add(new Node((val + answer) % 1000000000));
            } else {
                add(new Node(val));
            }
            answer = -1;
        } else {
            int a, b;
            cin >> a >> b;
            answer = sum(root, a, b);
            cout << answer << "\n";
        }
    }
    return 0;
}