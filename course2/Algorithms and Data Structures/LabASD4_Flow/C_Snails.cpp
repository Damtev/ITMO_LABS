#include <iostream>
#include <vector>
#include <queue>

#define ll long long

using namespace std;

struct Edge {
    int from;
    int to;
    ll capacity;
    ll flow;

    Edge(int from, int to, long long int capacity, long long int flow) : from(from), to(to), capacity(capacity),
                                                                         flow(flow) {}
};

const ll INF = INT64_MAX;

int n, m, s, t;
vector<Edge> edges;
vector<vector<int>> graph;
vector<int> level, deletedEdges, restEdges;

void addEdge(int from, int to, ll capacity) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0);
}

void readDirectedGraph() {
    cin >> n >> m >> s >> t;
    --s, --t;
    graph.resize(n);
    for (int i = 1; i <= m; ++i) {
        int from, to;
        cin >> from >> to;
        if (from == to) {
            continue;
        }
        --from, --to;
        addEdge(from, to, 1);
    }
}

bool bfs() {
    level.assign(n, -1);
    int v = 0;
    int layer = 0;
    restEdges[layer++] = s;
    level[s] = 0;
    while (v < layer && level[t] == -1) {
        int curVertex = restEdges[v++];
        for (int i = 0; i < graph[curVertex].size(); ++i) {
            int id = graph[curVertex][i];
            int to = edges[id].to;
            if (level[to] == -1 && edges[id].flow < edges[id].capacity) {
                restEdges[layer++] = to;
                level[to] = level[curVertex] + 1;
            }
        }
    }
    return level[t] != -1;
}

ll dfs(int v, ll flow) {
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
        ll delta = dfs(to, min(flow, edges[id].capacity - edges[id].flow));
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

void findPath(vector<int>& path) {
    int v = s;
    path.push_back(v);
    while (v != t) {
        for (int id : graph[v]) {
            int to = edges[id].to;
            if (edges[id].flow > 0) {
                edges[id].flow = 0;
                v = to;
                path.push_back(v);
                break;
            }
        }
    }
}

int main() {
    readDirectedGraph();
    dinitz();
    ll flow = 0;
    for (int id : graph[s]) {
        flow += edges[id].flow;
    }
    if (flow < 2) {
        cout << "NO";
    } else {
        cout << "YES" << endl;
        vector<int> masha;
        findPath(masha);
        for (int v : masha) {
            cout << v + 1 << " ";
        }
        cout << endl;

        vector<int> petya;
        findPath(petya);
        for (int v : petya) {
            cout << v + 1 << " ";
        }
    }

    return 0;
}