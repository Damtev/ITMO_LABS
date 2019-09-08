#include <iostream>
#include <vector>

using namespace std;

const int INF = INT32_MAX;

int k, p;
vector<vector<int>> costs;
vector<vector<int>> tree, children, matrix;
vector<vector<vector<int>>> values;
vector<bool> used;

void readTree() {
    int n;
    cin >> n >> k >> p;
    costs.resize(n + 1, vector<int>(k + 1));
    tree.resize(n + 1);
    used.resize(n + 1);
    values.resize(n + 1, vector<vector<int>>(k + 1, vector<int>(k + 1)));
    matrix.resize(n + 3, vector<int>(k + 3));
    children.resize(n + 1);

    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= k; ++j) {
            cin >> costs[i][j];
        }
    }

    for (int l = 1; l <= n - 1; ++l) {
        int u, v;
        cin >> u >> v;
        tree[u].push_back(v);
        tree[v].push_back(u);
    }
}

void calcLeafs(int v) {
    used[v] = true;
    for (int u : tree[v]) {
        if (!used[u]) {
            children[v].push_back(u);
            calcLeafs(u);
        }
    }

    if (children[v].empty()) {
        for (int color = 1; color <= k; ++color) {
            for (int parent = 0; parent <= k; ++parent) {
                values[v][color][parent] = costs[v][color];
            }
        }
    }
}

int hungary(int n, int m) {
    vector<int > u(n + 1), v(m + 1), matching(m + 1), way(m + 1);
    for (int i = 1; i <= n; ++i) {
        matching[0] = i;
        int column = 0;
        vector<int> minInColumn(m + 1, INF);
        vector<bool> used(m + 1, false);
        while (true) {
            used[column] = true;
            int line = matching[column];
            int delta = INF;
            int nextColumn = 0;
            for (int j = 1; j <= m; ++j) {
                if (!used[j]) {
                    int curMin = matrix[line][j] - u[line] - v[j];
                    if (curMin < minInColumn[j]) {
                        minInColumn[j] = curMin;
                        way[j] = column;
                    }
                    if (minInColumn[j] < delta) {
                        delta = minInColumn[j];
                        nextColumn = j;
                    }
                }
            }
            for (int j = 0; j <= m; ++j) {
                if (used[j]) {
                    u[matching[j]] += delta;
                    v[j] -= delta;
                } else {
                    minInColumn[j] -= delta;
                }
            }
            column = nextColumn;

            if (matching[column] == 0) {
                break;
            }
        }

        while (true) {
            int nextColumn = way[column];
            matching[column] = matching[nextColumn];
            column = nextColumn;

            if (column == 0) {
                break;
            }
        }
    }

    return -v[0];
}

void findMinCost(int v) {
    for (int u : children[v]) {
        findMinCost(u);
    }

    for (int parentColor = 1; parentColor <= k; ++parentColor) {
        for (int color = 1; color <= k; ++color) {
            if (!children[v].empty()) {
                values[v][color][parentColor] = p + costs[v][color];
            }
            for (int u : children[v]) {
                int cost = values[u][1][color];
                for (int childColor = 2; childColor <= k; ++childColor) {
                    cost = min(cost, values[u][childColor][color]);
                }
                values[v][color][parentColor] += cost;
            }

            if (!children[v].empty() && children[v].size() < k || (v == 1 && children[v].size() <= k)) {
                for (int i = 0; i < children[v].size(); ++i) {
                    for (int childColor = 1; childColor <= k; ++childColor) {
                        if (childColor == parentColor && v != 1) {
                            matrix[i + 1][childColor] = INT32_MAX;
                        } else {
                            matrix[i + 1][childColor] = values[children[v][i]][childColor][color];
                        }
                    }
                }

                values[v][color][parentColor] = min(values[v][color][parentColor], hungary(children[v].size(), k) + costs[v][color]);
            }
        }
    }
}

int main() {
    readTree();
    calcLeafs(1);
    findMinCost(1);

    int answer = values[1][1][1];
    for (int color = 2; color <= k; ++color) {
        answer = min(answer, values[1][color][1]);
    }

    cout << answer << endl;
}