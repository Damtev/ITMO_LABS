#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

struct Node {
    int l;
    int r;
    long long int sum;
    long long int  hash_sum;
};

struct Pair {
    long long int sum;
    long long int hash_sum;

    Pair(long long int sum, long long int hash_sum) {
        this->sum = sum;
        this->hash_sum = hash_sum;
    }
};

int size;
vector<Node> tree;
vector<int> leaves;

long long int calc_hash(int value) {
    if (value == 0) {
        return 0;
    } else {
//        return (1 << value);
        return (pow(value, 3));
    }
}

Pair sum(Pair first, Pair second) {
    if (first.sum == LONG_LONG_MAX) {
        return second;
    } else if (second.sum == LONG_LONG_MAX) {
        return first;
    } else {
        return {first.sum + second.sum, first.hash_sum + second.hash_sum};
    }
}

void treeBuild(int i, int tl, int tr) {
    if (tl == tr) {
        return;
    }
    if (tr - tl == 1) {
        tree[i].sum = leaves[tl];
//            tree[i].hashSum = (1 << leaves[tl]);
        tree[i].hash_sum = calc_hash(leaves[tl]);
    } else {
        int tm = (tl + tr) >> 1;
        int i2 = i << 1;
        treeBuild(i2 + 1, tl, tm);
        treeBuild(i2 + 2, tm, tr);
        tree[i].sum = tree[i2 + 1].sum + tree[i2 + 2].sum;
        tree[i].hash_sum = tree[i2 + 1].hash_sum + tree[i2 + 2].hash_sum;
    }
    tree[i].l = tl;
    tree[i].r = tr;
}

void update(int i, int tl, int tr, int pos, int newVal) {
    if (tl == tr - 1) {
        tree[i].sum = newVal;
        tree[i].hash_sum = calc_hash(newVal);
    }
    else {
        int tm  =(tl + tr) >> 1;
        if (pos < tm) {
            update(i * 2 + 1, tl, tm, pos, newVal);
        }
        else {
            update(i * 2 + 2, tm, tr, pos, newVal);
        }
        tree[i].sum = tree[i * 2 + 1].sum + tree[i * 2 + 2].sum;
        tree[i].hash_sum = tree[i * 2 + 1].hash_sum + tree[i * 2 + 2].hash_sum;
    }
}

Pair query(int node, int a, int b) {
    int l = tree[node].l;
    int r = tree[node].r;
    if (l >= b || r <= a) {
        return {LONG_LONG_MAX, LONG_LONG_MAX};
    }
    if (l >= a && r <= b) {
        return {tree[node].sum, tree[node].hash_sum};
    }
    return sum(query(node * 2 + 1, a, b), query(node * 2 + 2, a, b));
}

int main() {
    ios_base::sync_with_stdio(false);
    freopen("permutation.in", "r", stdin);
    freopen("permutation.out", "w", stdout);
    int n;
    cin >> n;
    size = 1;
    while (size < n) {
        size <<= 1;
    }
    for (int i = 0; i < (size << 1) - 1; ++i) {
        tree.emplace_back();
    }
    int p = 3;
    vector<long long int> sum;
    vector<long long int> hash_sum;
    sum.push_back(0);
    hash_sum.push_back(0);
    for (int i = 1; i <= n; i++) {
        sum.push_back(sum.back() + i);
        hash_sum.push_back(hash_sum.back() + calc_hash(i));
//        hashSum[i] = hashSum[i - 1] + (1 << i);
//                pr.println(i + "    " + sum[i] + "  " + hashSum[i]);
    }
    for (int j = 0; j < n; ++j) {
        int number;
        cin >> number;
        leaves.push_back(number);
    }
    for (int k = n; k < size; ++k) {
        leaves.push_back(0);
    }
    treeBuild(0, 0, size);
    int m;
    cin >> m;
    int t, x, y;
    for (int l = 0; l < m; ++l) {
        cin >> t >> x >> y;
        if (t == 1) {
            update(0, 0, size, x - 1, y);
        } else {
            Pair ans = query(0, x - 1, y);
            if (ans.sum == sum[y - x + 1] && ans.hash_sum == hash_sum[y - x + 1]) {
                cout << "YES" << endl;
            } else {
                cout << "NO" << endl;
            }
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}