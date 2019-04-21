#include <queue>
#include <iostream>

#define ll long long

using namespace std;

struct Edge {
    int from;
    int to;
    ll capacity;
    ll cost;
    ll flow;

    Edge(int from, int to, ll capacity, ll cost, ll flow) : from(from), to(to), capacity(capacity), cost(cost),
                                                            flow(flow) {}
};

const ll INF = INT64_MAX;

int n, m, s, t;
vector<Edge> edges;
vector<vector<int>> graph;
vector<ll> d;
vector<pair<Edge*, int>> path;

void addEdge(int from, int to, ll capacity, ll cost) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, cost, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, -cost, 0);
}

void readMatrix() {
    cin >> n >> m;
    s = 0;
    t = n - 1;
    graph.resize(n);
    for (int i = 1; i <= m; ++i) {
        int from, to;
        ll capacity, cost;
        cin >> from >> to >> capacity >> cost;
        --from, --to;
        addEdge(from, to, capacity, cost);
    }
}

void fordBellman() {
    d.assign(n, INF);
    path.assign(n, {nullptr, -1});
    d[s] = 0;
    while (true) {
        bool exists = false;
        int id = 0;
        for (Edge &edge : edges) {
            ++id;
            if (edge.flow < edge.capacity && d[edge.from] < INF) {
                if (d[edge.to] > d[edge.from] + edge.cost) {
                    d[edge.to] = d[edge.from] + edge.cost;
                    path[edge.to] = make_pair(&edge, id);
                    exists = true;
                }
            }
        }
        if (!exists) {
            break;
        }
    }
}

void levit() {
    vector<int> id(n);
    d.assign(n, INF);
    path.assign(n, {nullptr, -1});
    deque<int> q;

    q.push_back(s);
    d[s] = 0;
    while (!q.empty()) {
        int from = q.front();
        q.pop_front();
        id[from] = 1;
        for (int edgeId : graph[from]) {
            Edge& edge = edges[edgeId];
            if (edge.flow < edge.capacity) {
                int to = edge.to;
                if (d[to] > d[from] + edge.cost) {
                    d[to] = d[from] + edge.cost;
                    if (id[to] == 0) {
                        q.push_back(to);
                    } else if (id[to] == 1) {
                        q.push_front(to);
                    }
                    path[to] = make_pair(&edge, edgeId);
                    id[to] = 1;
                }
            }
        }
    }
}

int main() {
    freopen("mincost.in", "r", stdin);
    freopen("mincost.out", "w", stdout);
    readMatrix();
    ll minCost = 0;
    while (true) {
//        fordBellman();
        levit();
        if (d[t] == INF) {
            break;
        }
        ll delta = INF;
        for (int v = t; v != s; v = path[v].first->from) {
            delta = min(delta, path[v].first->capacity - path[v].first->flow);
        }

        for (int v = t; v != s; v = path[v].first->from) {
            Edge* edge = path[v].first;
            Edge& reversed = edges[path[v].second ^ 1];
            edge->flow += delta;
            reversed.flow -= delta;
            minCost += edge->cost * delta;
        }
    }
    cout << minCost;

    fclose(stdin);
    fclose(stdout);

    return 0;
}