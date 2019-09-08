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
    int reversedId;

    Edge(int from, int to, ll capacity, ll cost, ll flow) : from(from), to(to), capacity(capacity), cost(cost),
                                                            flow(flow) {}
};

const ll INF = INT64_MAX;

int n, m, s, t;
vector<vector<Edge>> graph;
vector<ll> d;
vector<Edge*> path;

void addEdge(int from, int to, ll capacity, ll cost) {
    graph[from].emplace_back(from, to, capacity, cost, 0);
    graph[to].emplace_back(to, from, 0, -cost, 0);
    graph[from].back().reversedId = graph[to].size() - 1;
    graph[to].back().reversedId = graph[from].size() - 1;
}

void read() {
    cin >> n >> m;
    s = 0;
    t = n * 2 + 1;
    graph.resize(t + 1);
    for (int i = 1; i <= n; ++i) {
        ll townCost;
        cin >> townCost;
        addEdge(i, n + i, INF, townCost);
        addEdge(n + i, i, INF, 0);
        addEdge(s, i, 1, 0);
        addEdge(n + i, t, 1, 0);
    }

    for (int i = 1; i <= m; ++i) {
        int from, to;
        ll wayCost;
        cin >> from >> to >> wayCost;
        addEdge(from, n + to, INF, wayCost);
    }
    n = t + 1;
}

void levit() {
    vector<int> id(n);
    d.assign(n, INF);
    path.assign(n, nullptr);
    deque<int> q;

    q.push_back(s);
    d[s] = 0;
    while (!q.empty()) {
        int from = q.front();
        q.pop_front();
        id[from] = 1;
        for (Edge& edge : graph[from]) {
            if (edge.flow < edge.capacity) {
                int to = edge.to;
                if (d[to] > d[from] + edge.cost) {
                    d[to] = d[from] + edge.cost;
                    if (id[to] == 0) {
                        q.push_back(to);
                    } else if (id[to] == 1) {
                        q.push_front(to);
                    }
                    path[to] = &edge;
                    id[to] = 1;
                }
            }
        }
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);
    read();
    ll minCost = 0;
    while (true) {
        levit();
        if (d[t] == INF) {
            break;
        }
        ll delta = INF;
        for (int v = t; v != s; v = path[v]->from) {
            delta = min(delta, path[v]->capacity - path[v]->flow);
        }

        for (int v = t; v != s; v = path[v]->from) {
            Edge* edge = path[v];
            Edge* reversed = &graph[edge->to][edge->reversedId];
            edge->flow += delta;
            reversed->flow -= delta;
            minCost += edge->cost * delta;
        }
    }

    cout << minCost;

    return 0;
}