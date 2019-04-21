#include <iostream>
#include <vector>
#include <queue>
#include <map>

using namespace std;

struct Edge {
    int from;
    int to;
    int capacity;
    int flow;

    Edge(int from, int to, int capacity, int flow) : from(from), to(to), capacity(capacity),
                                                                         flow(flow) {}
};

const int INF = 30000;
const int MOUNT = 1; //#
const int MBWALL = 2; //.
const int NOTWALL = 3; //-
const int A = 4; //A
const int B = 5; //B
const int BUILD = 6;
const int MAX = 110;

map<char, int> fromCharToInt;
map<int, char> fromIntToChar;
int n, s, t, a, b;
vector<Edge> edges;
vector<vector<int>> graph;
vector<int> level, deletedEdges, restEdges;
vector<vector<int>> countryMap;

int getIndex(int i, int j) {
    return i * b + j;
}

int getCircleIndex(int i, int j) {
    return getIndex(i, j) + a * b;
}

void addEdge(int from, int to, int capacity) {
    graph[from].push_back(edges.size());
    edges.emplace_back(from, to, capacity, 0);
    graph[to].push_back(edges.size());
    edges.emplace_back(to, from, 0, 0);
}

void readMap() {
    cin >> a >> b;
    n = a * b;
    countryMap.resize(a);
    graph.resize(MAX * MAX);
    for (int i = 0; i < a; ++i) {
        char cell;
        for (int j = 0; j < b; ++j) {
            cin >> cell;
            countryMap[i].push_back(fromCharToInt[cell]);
            /*if (cell == '#') {
                continue;
            }*/
            if (cell == 'A') {
                s = getCircleIndex(i, j);
            } else if (cell == 'B') {
                t = i * b + j;
            } else {
                int cap;
                if (cell == '-') {
                    cap = INF;
                } else if (cell == '.') {
                    cap = 1;
                } else {
                    cap = 0;
                }
                addEdge(getIndex(i, j), getCircleIndex(i, j), cap);
            }
        }
    }
}

void makeGraph() {
    for (int i = 0; i < a - 1; ++i) {
        for (int j = 0; j < b - 1; ++j) {
            addEdge(getCircleIndex(i, j), getIndex(i + 1, j), INF);
            addEdge(getCircleIndex(i + 1, j), getIndex(i, j), INF);
            addEdge(getCircleIndex(i, j), getIndex(i, j + 1), INF);
            addEdge(getCircleIndex(i, j + 1), getIndex(i, j), INF);
        }
    }
    for (int i = 0; i < a - 1; ++i) {
        addEdge(getCircleIndex(i, b - 1), getIndex(i + 1, b - 1), INF);
        addEdge(getCircleIndex(i + 1, b - 1), getIndex(i, b - 1), INF);
    }
    for (int i = 0; i < b - 1; ++i) {
        addEdge(getCircleIndex(a - 1, i), getIndex(a - 1, i + 1), INF);
        addEdge(getCircleIndex(a - 1, i + 1), getIndex(a - 1, i), INF);
    }
}

bool bfs() {
    level.assign(MAX * MAX, -1);
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

int dinitz() {
    restEdges.assign(MAX * MAX, 0);
    int flow = 0;
    while (true) {
        if (!bfs()) {
            break;
        }
        deletedEdges.assign(MAX * MAX, 0);
        while (true) {
            int pushed = dfs(s, INF);
            if (pushed == 0) {
                break;
            }
            flow += pushed;
        }
    }
    return flow;
}

void mark(int v, vector<bool>& inLeft, vector<int>& left) {
    inLeft[v] = true;
    left.push_back(v);
    for (int edgeId : graph[v]) {
        Edge edge = edges[edgeId];
        if (!inLeft[edge.to] && edge.flow < edge.capacity) {
            mark(edge.to, inLeft, left);
        }
    }
}

void fundCut() {
    vector<bool> inLeft(MAX * MAX, false);
    vector<int> left;
    mark(s, inLeft, left);
    for (int v : left) {
        for (int edgeId : graph[v]) {
            Edge& edge = edges[edgeId];
            if (!inLeft[edge.to] && edge.flow == 1) {
                int line = edge.from / b;
                int column = edge.from % b;
                countryMap[line][column] = BUILD;
                break;
            }
        }
    }
}

int main() {
    fromCharToInt = {
            {'#', MOUNT},
            {'.', MBWALL},
            {'-', NOTWALL},
            {'A', A},
            {'B', B},
            };
    fromIntToChar = {
            {MOUNT, '#'},
            {MBWALL, '.'},
            {NOTWALL, '-'},
            {A, 'A'},
            {B, 'B'},
            {BUILD, '+'}
            };
    readMap();
    makeGraph();
    int flow = dinitz();
    if (flow >= INF) {
        cout << (-1);
    } else {
        cout << flow << endl;
        fundCut();
        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < b; ++j) {
                cout << fromIntToChar[countryMap[i][j]];
            }
            cout << endl;
        }
    }

    return 0;
}