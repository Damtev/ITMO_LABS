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

int n, s, t, originalN;
vector<Edge> edges;
vector<vector<int>> graph;
vector<int> level, deletedEdges, restEdges;
vector<int> neededPoints;
vector<int> possiblePoints;
vector<vector<int>> willPlay;
vector<vector<char>> table;

void addEdge(int from, int to, int capacity) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0);
}

void readInput() {
    cin >> originalN;
    n = originalN + 2;

    s = 0;
    t = originalN + 1;

    neededPoints.resize(originalN + 1, 0);
    possiblePoints.resize(originalN + 1, 0);
    vector<int> curPoints(originalN + 1, 0);
    willPlay.resize(originalN + 1);
    graph.resize(originalN + 2);
    table.resize(originalN + 1, vector<char>(originalN + 1));

    for (int i = 1; i <= originalN; ++i) {
        char result;
        for (int j = 1; j <= originalN; ++j) {
            cin >> result;
            if (j >= i) {
                continue;
            }
            int point = 0;
            bool wasGame = true;
            char otherResult;
            switch (result) {
                case 'W' :
                    point = 3;
                    otherResult = 'L';
                    break;
                case 'w' :
                    point = 2;
                    otherResult = 'l';
                    break;
                case 'l' :
                    point = 1;
                    otherResult = 'w';
                    break;
                case 'L' :
                    otherResult = 'W';
                    break;
                case '.':
                    willPlay[i].push_back(j);
                    willPlay[j].push_back(i);
                    wasGame = false;
            }
            if (wasGame) {
                curPoints[i] += point;
                curPoints[j] += 3 - point;
            }
            table[i][j] = result;
            table[j][i] = otherResult;
        }
    }
    for (int i = 1; i <= originalN; ++i) {
        int points;
        cin >> points;
        neededPoints[i] = max(0, points - curPoints[i]);
    }
}

void makeGraph() {
    for (int team = 1; team <= originalN; ++team) {
        int canGet = 0;
        for (int competitor : willPlay[team]) {
            if (team < competitor) {
                canGet += 3;
                addEdge(team, competitor, 3);
            }
        }
        addEdge(s, team, canGet);
    }
    for (int team = 1; team <= originalN; ++team) {
        addEdge(team, t, neededPoints[team]);
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
    makeGraph();
    dinitz();
    for (int i = 1; i <= originalN; ++i) {
        for (int edgeId : graph[i]) {
            Edge& edge = edges[edgeId];
            if (edge.to != t && edge.to != s && i < edge.to) {
                switch (edge.flow) {
                    case 0 :
                        table[i][edge.to] = 'W';
                        table[edge.to][i] = 'L';
                        break;
                    case 1 :
                        table[i][edge.to] = 'w';
                        table[edge.to][i] = 'l';
                        break;
                    case 2 :
                        table[i][edge.to] = 'l';
                        table[edge.to][i] = 'w';
                        break;
                    case 3 :
                        table[i][edge.to] = 'L';
                        table[edge.to][i] = 'W';
                        break;
                }
            }
        }
    }

    for (int i = 1; i <= originalN; ++i) {
        for (int j = 1; j <= originalN; ++j) {
            if (i == j) {
                cout << '#';
                continue;
            }
            cout << table[i][j];
        }
        cout << endl;
    }

    return 0;
}