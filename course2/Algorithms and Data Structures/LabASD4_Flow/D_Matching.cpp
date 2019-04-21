#include <iostream>
#include <vector>

using namespace std;

struct Edge {
    int from;
    int to;
    int capacity;
    int flow;

    Edge(int from, int to, int capacity, int flow) : from(from), to(to), capacity(capacity),
                                                     flow(flow) {}
};

const int INF = 1000000000;

int n, m, s, t, l, r;
vector<Edge> edges;
vector<vector<int>> graph;
vector<int> level, deletedEdges, restEdges;

void addEdge(int from, int to, int capacity) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0);
}

void readInput() {
    cin >> l >> r;
    s = 0;
    t = l + r + 1;
    n = t + 1;
    graph.resize(n);
    for (int to = 1; to <= l; ++to) {
        addEdge(s, to, 1);
    }
    for (int from = l + 1; from <= l + r; ++from) {
        addEdge(from, t, 1);
    }
    for (int from = 1; from <= l; ++from) {
        int to;
        while (true) {
            cin >> to;
            if (to == 0) {
                break;
            }
            to = l + to;
            addEdge(from, to, 1);
        }
    }
}

bool bfs() {
    level.assign(n, -1);
    int v = 0;
    int curT = 0;
    restEdges[curT++] = s;
    level[s] = 0;
    while (v < curT && level[t] == -1) {
        int curVertex = restEdges[v++];
        for (int i = 0; i < graph[curVertex].size(); ++i) {
            int id = graph[curVertex][i];
            int to = edges[id].to;
            if (level[to] == -1 && edges[id].flow < edges[id].capacity) {
                restEdges[curT++] = to;
                level[to] = level[curVertex] + 1;
            }
        }
    }
    return level[t] != -1;
}

int dfs(int v, int flow) {
    if (flow == 0) {
        return 0;
    }
    if (v == t) {
        return flow;
    }
    for (; deletedEdges[v] < graph[v].size(); ++deletedEdges[v]) {
        int id = graph[v][deletedEdges[v]];
        int to = edges[id].to;
        if (level[to] != level[v] + 1) {
            continue;
        }
        int delta = dfs(to, min(flow, edges[id].capacity - edges[id].flow));
        if (delta > 0) {
            edges[id].flow += delta;
            edges[id ^ 1].flow -= delta;
            return delta;
        }
    }
    return 0;
}

void dinitz() {
    restEdges.assign(n, 0);
    while (true) {
        if (!bfs()) {
            break;
        }
        deletedEdges.assign(n, 0);
        while (true) {
            if (dfs(s, INF) == 0) {
                break;
            }
        }
    }
}

int main() {
    readInput();
    dinitz();
    int flow = 0;
    for (int id : graph[s]) {
        flow += edges[id].flow;
    }
    cout << flow << endl;

    for (int v = 1; v <= l; ++v) {
        for (int id : graph[v]) {
            Edge edge = edges[id];
            if (edge.flow > 0) {
                cout << v << " " << edge.to - l << endl;
                break;
            }
        }
    }

    return 0;
}