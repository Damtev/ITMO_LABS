#include <iostream>
#include <vector>
#include <cmath>
#include <map>
#include <fstream>
#include <unordered_set>
#include <set>

using namespace std;

int n;
int l;
vector<vector<int>> children;
vector<vector<int>> edges;
vector<int> time_in, time_out;
int timer;
vector<vector<int>> dp_vertices;
vector<vector<int>> dp_edges;
map<pair<int, int>, int> edge_weight;
vector<int> depthes;
vector<int> parents;
vector<int> colors;
vector<unordered_set<int> > diff_colors;
vector<int> colors_number;
vector<bool> dead_dinoes;
vector<int> alive_dinoes;
vector<int> magic;
vector<int> tree;
vector<int> size;
vector<int> next_in_chain;
vector<int> chain;
vector<int> height;
vector<int> chain_size;
vector<int> top_chain;
vector<int> maxes;
vector<int> centroid_decomposition;
vector<int> numbers;


void dfs_A(int v, int p = 1) {
    time_in[v] = ++timer;
    dp_vertices[v][0] = p;
    for (int i = 1; i <= l; ++i)
        dp_vertices[v][i] = dp_vertices[dp_vertices[v][i - 1]][i - 1];
    for (size_t i = 0; i < children[v].size(); ++i) {
        int child = children[v][i];
        dfs_A(child, v);
    }
    time_out[v] = ++timer;
}

int depth = 0;

void dfs_B(int v, int p = 1) {
    time_in[v] = ++timer;
    dp_vertices[v][0] = p;
    dp_edges[v][0] = edge_weight[{v, p}];
    depthes[v] = depth;
    for (int i = 1; i <= l; ++i) {
        dp_vertices[v][i] = dp_vertices[dp_vertices[v][i - 1]][i - 1];
        dp_edges[v][i] = min(dp_edges[v][i - 1], dp_edges[dp_vertices[v][i - 1]][i - 1]);
    }
    for (size_t i = 0; i < children[v].size(); ++i) {
        ++depth;
        int child = children[v][i];
        dfs_B(child, v);
        --depth;
    }
    time_out[v] = ++timer;
}

int find_min_edge(int v, int u) {
    int answer = INT32_MAX;
    for (int i = l; i >= 0; --i) {
        if ((1 << i) <= depthes[v] - depthes[u]) {
            answer = min(answer, dp_edges[v][i]);
            v = dp_vertices[v][i];
        }
    }
    return answer;
}

bool isAncestor(int u, int v) {
    return (time_in[u] <= time_in[v] && time_out[u] >= time_out[v]);
}

int lca(int u, int v) {
    if (isAncestor(u, v)) {
        return u;
    }
    if (isAncestor(v, u)) {
        return v;
    }
    for (int i = l; i >= 0; --i) {
        if (!isAncestor(dp_vertices[u][i], v)) {
            u = dp_vertices[u][i];
        }
    }
    return dp_vertices[u][0];
}

void merge(unordered_set<int> &first, unordered_set<int> &second) {
    if (first.size() < second.size()) {
        first.swap(second);
    }
    for (auto color : second) {
        first.insert(color);
    }
    second.clear();
}

void dfs_C(int v) {
    for (int i = 0; i < children[v].size(); ++i) {
        int child = children[v][i];
        dfs_C(child);
        merge(diff_colors[v], diff_colors[child]);
    }
    colors_number[v] = diff_colors[v].size();
}

void main_A() {
    cin >> n;
    time_in.resize(n + 1);
    time_out.resize(n + 1);
    dp_vertices.resize(n + 1);
    children.resize(n + 1);
    l = (int) log2(n);
    for (int i = 1; i <= n; ++i) {
        dp_vertices[i].resize(l + 1);
    }
    for (int j = 2; j <= n; ++j) {
        int parent;
        cin >> parent;
        children[parent].push_back(j);
    }
    dfs_A(1);
    int m;
    cin >> m;
    for (int k = 0; k < m; ++k) {
        int u, v;
        cin >> u >> v;
        cout << lca(u, v) << endl;
    }
}

void main_B() {
    ios_base::sync_with_stdio(false);
    ifstream in("minonpath.in");
    ofstream out("minonpath.out");
    in >> n;
    time_in.resize(n + 1);
    time_out.resize(n + 1);
    dp_vertices.resize(n + 1);
    dp_edges.resize(n + 1);
    children.resize(n + 1);
    depthes.resize(n + 1);
    l = (int) log2(n);
    for (int i = 1; i <= n; ++i) {
        dp_vertices[i].resize(l + 1);
        dp_edges[i].resize(l + 1);
    }
    for (int j = 2; j <= n; ++j) {
        int parent, weight;
        in >> parent >> weight;
        children[parent].push_back(j);
        edge_weight[{parent, j}] = weight;
        edge_weight[{j, parent}] = weight;
    }
    dfs_B(1);
    int m;
    in >> m;
    for (int k = 0; k < m; ++k) {
        int v, u;
        in >> v >> u;
        int cur_lca = lca(u, v);
        int result = min(find_min_edge(v, cur_lca), find_min_edge(u, cur_lca));
        out << result << "\n";
    }
}

void main_H() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);
    cin >> n;
    parents.resize(n + 1), colors.resize(n + 1), children.resize(n + 1), diff_colors.resize(n + 1);
    colors_number.resize(n + 1);
    int root = 0;
    for (int i = 1; i <= n; ++i) {
        int parent, color;
        cin >> parent >> color;
        if (parent == 0) {
            root = i;
        }
        parents[i] = parent;
        colors[i] = color;
        children[parent].push_back(i);
        diff_colors[i].insert(color);
    }
    dfs_C(root);
    for (int j = 1; j <= n; ++j) {
        cout << colors_number[j] << "\n";
    }
}

void dfs_F(int v, int parent, int depth) {
    dp_vertices[v][0] = parent;
    depthes[v] = depth;
    for (int i = 1; i <= l; ++i)
        dp_vertices[v][i] = dp_vertices[dp_vertices[v][i - 1]][i - 1];
    for (auto child: children[v]) {
        dfs_F(child, v, depth + 1);
    }
}

void insert(int child, int parent) {
    depthes[child] = depthes[parent] + 1;
    dp_vertices[child][0] = parent;
    for (int i = 1; i <= l; ++i) {
        dp_vertices[child][i] = dp_vertices[dp_vertices[child][i - 1]][i - 1];
    }
}

int find_ancestor(int v) {
    if (v == parents[v]) {
        return v;
    }
    return parents[v] = find_ancestor(parents[v]);
}

void reweigh(int v, int u) {
    v = find_ancestor(v);
    u = find_ancestor(u);
    if (v != u) {
        if (magic[v] < magic[u]) {
            int temp = v;
            v = u;
            u = temp;
        }
        parents[u] = v;
        if (magic[v] == magic[u]) {
            ++magic[v];
        }
    }
}

void recalc(int v) {
    parents[v] = v;
    magic[v] = 0;
    for (int child : children[v]) {
        if (dead_dinoes[child]) {
            reweigh(child, v);
        }
    }
    alive_dinoes[find_ancestor(v)] = dp_vertices[v][0];
    if (dead_dinoes[dp_vertices[v][0]]) {
        int temp = alive_dinoes[find_ancestor(dp_vertices[v][0])];
        reweigh(v, dp_vertices[v][0]);
        alive_dinoes[find_ancestor(v)] = temp;
    }
}

int get(int v) {
    return (dead_dinoes[v]) ? alive_dinoes[find_ancestor(v)] : v;
}

int lca_F(int v, int u) {
    if (depthes[v] > depthes[u]) {
        int temp = u;
        u = v;
        v = temp;
    }
    for (int i = l; i >= 0; --i) {
        if (depthes[u] - depthes[v] >= (1 << i)) {
            u = dp_vertices[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = l; i >= 0; --i)
        if (dp_vertices[v][i] != dp_vertices[u][i] && depthes[dp_vertices[v][i]] == depthes[dp_vertices[u][i]]) {
            v = dp_vertices[v][i];
            u = dp_vertices[u][i];
        }
    return get(dp_vertices[v][0]);
}

void main_F() {
    ios_base::sync_with_stdio(false);
    cin >> n;
    l = (int) log2(n);
    parents.resize(n + 1), children.resize(n + 1), magic.resize(n + 1);
    dp_vertices.resize(n + 1), depthes.resize(n + 1);
    for (int i = 0; i <= n; ++i) {
        alive_dinoes.push_back(i);
        dead_dinoes.push_back(false);
        dp_vertices[i].resize(l + 1);
    }
    dfs_F(1, 1, 0);
    int number = 2;
    for (int i = 0; i < n; ++i) {
        string operation;
        cin >> operation;
        if (operation == "+") {
            int v;
            cin >> v;
            insert(number, v);
            children[v].push_back(number);
            ++number;
        } else if (operation == "-") {
            int v;
            cin >> v;
            dead_dinoes[v] = true;
            recalc(v);
        } else {
            int v, u;
            cin >> v >> u;
            int answer = lca_F(v, u);
            cout << answer << "\n";
        }
    }
}

int common_count, counter = 1;

void dfs_D(int v, int parent = 0) {
    parents[v] = parent;
    size[v] = 1;
    for (int to : edges[v]) {
        if (to != parent) {
            depthes[to] = depthes[v] + 1;
            dfs_D(to, v);
            size[v] += size[to];
            if (next_in_chain[v] == -1 || size[to] > size[next_in_chain[v]]) {
                next_in_chain[v] = to;
            }
        }
    }
}

void heavy_light_decomposition(int v, int parent = -1) {
    chain[v] = counter - 1;
    height[v] = common_count++;
    if (!chain_size[counter - 1]) {
        top_chain[counter - 1] = v;
    }
    ++chain_size[counter - 1];
    if (next_in_chain[v] != -1) {
        heavy_light_decomposition(next_in_chain[v], v);
    }
    for (int to : edges[v]) {
        if (to == parent || to == next_in_chain[v]) {
            continue;
        }
        ++counter;
        heavy_light_decomposition(to, v);
    }
}

void update(int v, int tl, int tr, int pos, int value) {
    if (tl == tr) {
        tree[v] = value;
        return;
    }
    int tm = (tl + tr) >> 1;
    if (pos <= tm) {
        update(v << 1, tl, tm, pos, value);
    } else {
        update((v << 1) + 1, tm + 1, tr, pos, value);
    }
    tree[v] = max(tree[v << 1], tree[(v << 1) + 1]);
}

int find_max(int v, int tl, int tr, int l, int r) {
    if (l > tr || r < tl) {
        return 0;
    }
    if (l <= tl && r >= tr) {
        return tree[v];
    }
    int tm = (tl + tr) >> 1;
    return max(find_max(v << 1, tl, tm, l, r), find_max((v << 1) + 1, tm + 1, tr, l, r));
}

int find_max(int v, int u) {
    int answer = 0;
    while (chain[v] != chain[u]) {
        if (depthes[top_chain[chain[v]]] < depthes[top_chain[chain[u]]]) {
            swap(v, u);
        }
        int begin = top_chain[chain[v]];
        if (height[v] == height[begin] + chain_size[chain[v]] - 1) {
            answer = max(answer, maxes[chain[v]]);
        } else {
            answer = max(answer, find_max(1, 0, n - 1, height[begin], height[v]));
        }
        v = parents[begin];
    }
    if (depthes[v] > depthes[u]) {
        swap(v, u);
    }
    answer = max(answer, find_max(1, 0, n - 1, height[v], height[u]));
    return answer;
}

void mod(int v, int value) {
    update(1, 0, n - 1, height[v], value);
    int begin = height[top_chain[chain[v]]];
    int end = begin + chain_size[chain[v]] - 1;
    maxes[chain[v]] = find_max(1, 0, n - 1, begin, end);
}

void main_D() {
    ios_base::sync_with_stdio(false);
    ifstream in("mail.in");
    ofstream out("mail.out");
    in >> n;
    edges.resize(n), parents.resize(n), depthes.resize(n), maxes.resize(n);
    size.resize(n), chain.resize(n), height.resize(n), chain_size.resize(n);
    top_chain.resize(n), tree.resize(n << 2);
    next_in_chain.resize(n, -1);
    vector<int> temp_values;
    temp_values.resize(n);
    for (int i = 0; i < n; ++i) {
        int origin_height;
        in >> origin_height;
        temp_values[i] = origin_height;
    }
    for (int j = 0; j < n - 1; ++j) {
        int u, v;
        in >> u >> v;
        --u, --v;
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
    dfs_D(0);
    heavy_light_decomposition(0);
    for (int i1 = 0; i1 < n; ++i1) {
        mod(i1, temp_values[i1]);
    }
    int m;
    in >> m;
    for (int k = 0; k < m; ++k) {
        string operaion;
        in >> operaion;
        if (operaion == "!") {
            int u, height;
            in >> u >> height;
            --u;
            mod(u, height);
        } else {
            int v, u;
            in >> v >> u;
            --v, --u;
            out << find_max(v, u) << endl;
        }
    }
}

int count(int v, int size, int &centroid, int parent = 0) {
    int sum = 1;
    for (int to : edges[v]) {
        if (depthes[to] == 0 && to != parent) {
            sum += count(to, size, centroid, v);
        }
    }
    if (centroid == 0 && (2 * sum >= size || parent == 0)) {
        centroid = v;
    }
    return sum;
}

void dfs_E(int v, int size, int depth, int last) {
    int centroid = 0;
    count(v, size, centroid);
    depthes[centroid] = depth;
    centroid_decomposition[centroid] = last;
    for (int to : edges[centroid]) {
        if (depthes[to] == 0) {
            dfs_E(to, size / 2, depth + 1, centroid);
        }
    }
}

void main_E() {
//    ifstream in("centroid_decomp.in");
    cin >> n;
    edges.resize(n + 1), depthes.resize(n + 1), centroid_decomposition.resize(n + 1);
    for (int i = 0; i < n - 1; ++i) {
        int v, u;
        cin >> v >> u;
        edges[v].push_back(u);
        edges[u].push_back(v);
    }
    dfs_E(1, n, 1, 0);
//    dfs_E(0, n, 0, -1);
    for (int j = 1; j <= n; ++j) {
        cout << centroid_decomposition[j] << " ";
    }
}

void push(int v) {
    if ((v << 1) + 1 < tree.size()) {
        tree[v << 1] += tree[v];
        tree[(v << 1) + 1] += tree[v];
    }
    tree[v] = 0;
}

void update_sum(int v, int tl, int tr, int l, int r, int d) {
    if (tl > r || tr < l) {
        return;
    }
    if (tl >= l && tr <= r) {
        tree[v] += d;
        return;
    }
    push(v);
    int tm = (tl + tr) >> 1;
    update_sum(v << 1, tl, tm, l, r, d);
    update_sum((v << 1) + 1, tm + 1, tr, l, r, d);
    tree[v] = tree[v << 1] + tree[(v << 1) + 1];
}

void mod_C(int v, int u, int d) {
    update_sum(1, 0, n - 1, u, v, d);
}

int get(int v, int tl, int tr, int pos) {
    if (tl == tr) {
        return tree[v];
    }
    push(v);
    int tm = (tl + tr) >> 1;
    if (pos > tm) {
        return get((v << 1) + 1, tm + 1, tr, pos);
    } else {
        return get(v << 1, tl, tm, pos);
    }
}

int find(int v) {
    return get(1, 0, n - 1, v);
}

void main_C() {
    cin >> n;
    edges.resize(n), parents.resize(n), depthes.resize(n);
    size.resize(n), chain.resize(n), height.resize(n), chain_size.resize(n);
    top_chain.resize(n), tree.resize(n << 2);
    next_in_chain.resize(n, -1);
    vector<int> temp_values;
    temp_values.resize(n);
    for (int j = 0; j < n - 1; ++j) {
        int u, v;
        cin >> u >> v;
        --u, --v;
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
    dfs_D(0);
    heavy_light_decomposition(0);
    int m;
    cin >> m;
    for (int k = 0; k < m; ++k) {
        string operaion;
        cin >> operaion;
        if (operaion == "+") {
            int v, u, d;
            cin >> v >> u >> d;
            --u, --v;
            mod_C(v, u, d);
        } else {
            int v;
            cin >> v;
            --v;
            cout << find(v) << endl;
        }
    }
}

int main() {
//    main_A();
//    main_B();
//    main_H();
//    main_F();
//    main_D();
    main_E();
//    main_C(); //Не работает
    return 0;
}