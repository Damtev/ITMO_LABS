#include <iostream>
#include <vector>

using namespace std;

struct Edge {
    long long from;
    long long to;
    long long capacity;
    long long flow;

    Edge(long long from, long long to, long long capacity, long long flow) : from(from), to(to), capacity(capacity),
                                                                             flow(flow) {}
};

const long long INF = 100000;

long long n, s, t, k, m;
vector<Edge> edges;
vector<vector<size_t>> graph;
vector<long long> level, deletedEdges, restEdges;
vector<pair<long long, long long>> tunnels;

void addEdge(long long from, long long to, long long capacity) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0);
}

void readInput() {
    cin >> n >> m >> k >> s >> t;
    --s, --t;
    for (long long i = 0; i < m; ++i) {
        long long from, to;
        cin >> from >> to;
        --from, --to;
        tunnels.emplace_back(from, to);
    }
}

long long dfs(long long v, long long flow, vector<bool> &used) {
    if (flow == 0) {
        return 0;
    }
    if (v == t) {
        return flow;
    }
    used[v] = true;
    for (unsigned long id : graph[v]) {
        long long to = edges[id].to;
        if (used[to]) {
            continue;
        }
        long long delta = dfs(to, min(flow, edges[id].capacity - edges[id].flow), used);
        if (delta > 0) {
            edges[id].flow += delta;
            edges[id ^ 1].flow -= delta;
            return delta;
        }
    }
    return 0;
}

long long fordFalkerson(long long size, long long last) {
    long long flow = 0;
    vector<bool> used;
    while (true) {
        used.assign(size, false);
        long long pushed = dfs(s, last - flow, used);
        flow += pushed;
        if (pushed == 0 || flow == last) {
            break;
        }
    }
    return flow;
}

long long getNewVertex(long long curDay, long long v) {
    return n * curDay + v;
}

long long solve() {
    long long count = 0;
    long long curDay = 0;
    while (count < k) {
        for (long long v = 0; v < n; ++v) {
            addEdge(getNewVertex(curDay, v), getNewVertex(curDay + 1, v), INF);
        }
        for (long long i = 0; i < m; ++i) {
            addEdge(getNewVertex(curDay, tunnels[i].first), getNewVertex(curDay + 1, tunnels[i].second), 1);
            addEdge(getNewVertex(curDay, tunnels[i].second), getNewVertex(curDay + 1, tunnels[i].first), 1);
        }
        long long temp = t;
        t = getNewVertex(curDay, t);
        count += fordFalkerson(getNewVertex(curDay + 1, n), k - count);
        t = temp;
        ++curDay;
    }
    return curDay;
}

int main() {
    freopen("bring.in", "r", stdin);
    freopen("bring.out", "w", stdout);
    readInput();
    graph.resize(INF);
    long long ans = solve() - 1;

    vector<long long> ships(k + 5, s);
    cout << ans << endl;
    for (long long day = 1; day <= ans; ++day) {
        string curTravels;
        long long numberShips = 0;
        for (long long ship = 1; ship <= k; ++ship) {
            for (size_t edgeId : graph[ships[ship]]) {
                Edge &edge = edges[edgeId];
                if (edge.flow >= 1) {
                    --edge.flow;
                    ships[ship] = edge.to;
                    if (edge.capacity != INF) {
                        ++numberShips;
                        curTravels.append("\t").append(to_string(ship)).append(" ").append(
                                to_string((edge.to % n) + 1));
                    }
                    break;
                }
            }
        }
        cout << numberShips << curTravels << endl;
    };

    fclose(stdin);
    fclose(stdout);
    return 0;
}
