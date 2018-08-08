#include <iostream>
#include <vector>

using namespace std;

struct Treap {
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
            } else if (data > node->data) {
                node = node->right;
            } else {
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

    Node *remove(Node *node, Node *old_node) {
        if (node != nullptr) {
            if (old_node->data < node->data) {
                node->left = remove(node->left, old_node);
            } else if (old_node->data > node->data) {
                node->right = remove(node->right, old_node);
            } else {
                node = merge(node->left, node->right);
            }
        }
        return node;
    }

    Node *remove(Node *old_node) {
        root = remove(root, old_node);
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

    Node *next(int x) {
        Node *current = root;
        Node *parent = nullptr;
        while (current != nullptr) {
            if (current->data > x) {
                parent = current;
                current = current->left;
            } else {
                current = current->right;
            }
        }
        return parent;
    }
};

struct Node {
    int priority;
    int tree_size;
    int data;
    Node *left_child, *right_child;

    explicit Node(int data) {
        priority = rand();
        tree_size = 1;
        this->data = data;
        left_child = nullptr;
        right_child = nullptr;
    }
};

//    Node *root = nullptr;

int get_tree_size(Node *tree) {
    if (tree == nullptr) {
        return 0;
    }
    return tree->tree_size;
}

void update_tree_size(Node *tree) {
    if (tree != nullptr) {
        tree->tree_size = 1 + get_tree_size(tree->left_child) + get_tree_size(tree->right_child);
    }
}

Node *merge(Node *t1, Node *t2) {
    if (t1 == nullptr) {
        return t2;
    }
    if (t2 == nullptr) {
        return t1;
    }
    if (t1->priority < t2->priority) {
        t1->right_child = merge(t1->right_child, t2);
        update_tree_size(t1);
        return t1;
    } else {
        t2->left_child = merge(t1, t2->left_child);
        update_tree_size(t2);
        return t2;
    }
}

void split(Node *tree, int pos, Node *&t1, Node *&t2) {
    if (tree == nullptr) {
        t1 = nullptr;
        t2 = nullptr;
        return;
    }
    if (get_tree_size(tree->left_child) < pos) {
        split(tree->right_child, pos - get_tree_size(tree->left_child) - 1, tree->right_child, t2);
        t1 = tree;
    } else {
        split(tree->left_child, pos, t1, tree->left_child);
        t2 = tree;
    }
    update_tree_size(tree);
}

Node *add(Node *tree, int pos, int data) {
    Node *t1, *t2;
    split(tree, pos, t1, t2);
    auto *new_node = new Node(data);
    return merge(merge(t1, new_node), t2);
}

//    Node *add(int pos, int data) {
//        root = add(root, pos, data);
//    }

Node *remove(Node *t, int pos) {
    Node *t1, *t2, *t3, *t4;
    split(t, pos, t1, t2);
    split(t2, /*pos + */1, t3, t4);
    t = merge(t1, t4);
    delete t3;
    return t;
}

/*Node *remove(int pos) {
    root = remove(root, pos);
}*/

Node *make_tree(const vector<int> &v) {
    Node *result = nullptr;
    for (int i : v) {
        result = merge(result, new Node(i));
//            root = merge(root, new Node(i));
    }
    return result;
}

Node *get_value(Node *t, int pos) {
    int left_tree_size = get_tree_size(t->left_child);
    if (pos < left_tree_size) {
        return get_value(t->left_child, pos);
    } else if (pos == left_tree_size) {
        return t;
    } else {
        return get_value(t->right_child, pos - left_tree_size - 1);
    }
}

int m = 0;
Treap *zeroes = new Treap;

void print(Node *tree) {
    if (tree != nullptr) {
        print(tree->left_child);
        cout << tree->data << " ";
        print(tree->right_child);
    }
}

Node *insert(Node *tree, int pos, int data) {
    Node *search_node = get_value(tree, pos);
    if (search_node->data == 0) {
        search_node->data = data;
        zeroes->remove(new Treap::Node(pos));
    } else {
        if (pos + 1 >= m) {
            tree = add(tree, pos, data);
            return tree;
        } else {
            Treap::Node *min = zeroes->next(pos);
            if (min != nullptr) {
                int index_of_min_zero = min->data;
                zeroes->remove(min);
                tree = remove(tree, index_of_min_zero);
                tree = add(tree, pos, data);
            } else {
                tree = add(tree, pos, data);
            }
        }
    }
    return tree;
}

void to_vector(Node *tree, vector<int> &vector) {
    if (tree != nullptr) {
        to_vector(tree->left_child, vector);
        vector.push_back(tree->data);
        to_vector(tree->right_child, vector);
    }
}

Node *move_to_front(Node *t, int l, int r) {
    Node *t1, *t2, *t3, *t4;
    split(t, r + 1, t1, t2); //почти как remove
    split(t1, l, t3, t4);
    return merge(merge(t4, t3), t2);
}

int main() {
    int n;
    cin >> n >> m;
    vector<int> numbers;
    for (int i = 0; i <= m - 1; ++i) {
        numbers.push_back(0);
        zeroes->add(new Treap::Node(i));
    }
    Node *tree = make_tree(numbers);
    for (int j = 1; j <= n; ++j) {
        int index;
        cin >> index;
        tree = insert(tree, index - 1, j);
    }
    vector<int> answer;
    to_vector(tree, answer);
    int count_numbers = 0;
    int size = 0;
    for (int k : answer) {
        ++size;
        if (k != 0) {
            ++count_numbers;
        }
        if (count_numbers == n) {
            break;
        }
    }
    cout << size << endl;
    for (int l = 0; l < size; ++l) {
        cout << answer[l] << "  ";
    }
    return 0;
}