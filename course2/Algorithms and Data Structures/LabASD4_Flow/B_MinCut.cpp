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

void readUndirectedGraph() {
    cin >> n >> m;
    s = 0;
    t = n - 1;
    graph.resize(n);
    for (int i = 1; i <= m; ++i) {
        int from, to;
        ll capacity;
        cin >> from >> to >> capacity;
        --from, --to;
        addEdge(from, to, capacity);
        addEdge(to, from, capacity);
    }
    fclose(stdin);
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

void findCut(vector<bool>& leftPart) {
    vector<bool> used(n);
    queue<int> q;
    q.push(s);
    leftPart[s] = true;
    used[s] = true;
    while (!q.empty()) {
        int from = q.front();
        q.pop();
        for (int id : graph[from]) {
            int to = edges[id].to;
            if (!used[to] && edges[id].flow < edges[id].capacity) {
                q.push(to);
                leftPart[to] = true;
                used[to] = true;
            }
        }
    }
}

int main() {
    readUndirectedGraph();
    dinitz();
    ll flow = 0;
    for (int id : graph[s]) {
        flow += edges[id].flow;
    }

    vector<bool> leftPart(n);
    findCut(leftPart);
    vector<int> cutEdges;
    for (int i = 0; i < edges.size(); i += 4) {
        if (leftPart[edges[i].from] != leftPart[edges[i].to]) {
            cutEdges.push_back((i / 4) + 1);
        }
    }
    cout << cutEdges.size() << " " << flow << endl;
    for (int edge : cutEdges) {
        cout << edge << " ";
    }
    return 0;
}