#include <iostream>
#include <vector>
#include <queue>

using namespace std;

struct Edge {
    int from;
    int to;
    int capacity;
    int flow;
    bool isReversed;

    Edge(int from, int to, int capacity, int flow, bool isReversed) : from(from), to(to), capacity(capacity),
                                                                      flow(flow), isReversed(isReversed) {
    }
};

const int INF = 1000000000;

int n, m, s, t;
vector<Edge> edges;
vector<vector<int>> graph;
vector<int> level, deletedEdges, restEdges;

void addEdge(int from, int to, int capacity, bool isReversed) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0, isReversed);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0, !isReversed);
}

void readUndirectedGraph() {
    cin >> n >> m;
    s = 0;
    t = n - 1;
    graph.resize(n);
    for (int i = 1; i <= m; ++i) {
        int from, to, capacity;
        cin >> from >> to >> capacity;
        --from, --to;
        addEdge(from, to, capacity, false);
        addEdge(to, from, capacity, true);
    }
}

bool bfs() {
    level.assign(n, -1);
    /*queue<int> queue;
    queue.push(s);
    level[s] = 0;
    while (!queue.empty()) {
        int v = queue.front();
        queue.pop();
        for (int i = 0; i < graph[v].size(); ++i) {
            int edgeId = graph[v][i];
            int to = edges[edgeId].to;
            if (level[to] == -1 && edges[edgeId].flow < edges[edgeId].capacity) {
                level[to] = level[v] + 1;
                queue.push(to);
            }
        }
    }*/
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
    readUndirectedGraph();
    dinitz();
    int flow = 0;
    for (int id : graph[s]) {
        flow += edges[id].flow;
    }
    cout << flow << endl;
    for (int i = 0; i < edges.size(); i += 4) {
        vector<Edge> curRealEdge;
        for (int j = 1; j < 4; ++j) {
            curRealEdge.push_back(edges[i + j]);
        }
        flow = edges[i].flow;
        bool isReversed = edges[i].isReversed;
        for (Edge edge : curRealEdge) {
            if (abs(flow) < abs(edge.flow)) {
                flow = edge.flow;
                isReversed = edge.isReversed;
            }
        }
        cout << ((isReversed) ? flow * (-1) : flow) << endl;
    }
    return 0;
}