#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

struct Position {
    double x;
    double y;

    Position(double x, double y) : x(x), y(y) {}
};

const double INF = 1000000000;
const double EPS = 1e-5;

int n;

vector<pair<Position, double>> items;
vector<Position> positions;
vector<vector<double>> distances;
vector<bool> used;

void readInput() {
    cin >> n;
    distances.resize(n);
    for (int i = 0; i < n; ++i) {
        double x, y, speed;
        cin >> x >> y >> speed;
        items.emplace_back(make_pair(Position(x, y), speed));
    }
    for (int j = 0; j < n; ++j) {
        double x, y;
        cin >> x >> y;
        positions.emplace_back(x, y);
    }
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            double distance = sqrt(pow(items[i].first.x - positions[j].x, 2) + pow(items[i].first.y - positions[j].y, 2));
            distances[i].push_back(distance);
        }
    }
}

bool try_kuhn (int v, vector<vector<int>>& edges, vector<int>& matching) {
    if (used[v]) {
        return false;
    }
    used[v] = true;
    for (int u : edges[v]) {
        if (matching[u] == -1 || try_kuhn(matching[u], edges, matching)) {
            matching[u] = v;
            return true;
        }
    }
    return false;
}

bool tryTime(double time) {
    vector<vector<int>> tryEdge(n);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (distances[i][j] / items[i].second <= time) {
                tryEdge[i].push_back(j);
            }
        }
    }
    vector<int> matching;
    matching.assign(n, -1);
    int size = 0;
    for (int k = 0; k < n; ++k) {
        used.assign(n, false);
        if (try_kuhn(k, tryEdge, matching)) {
            ++size;
        }
    }
    return size == n;
}

int main() {
    readInput();
    double l = 0;
    double r = INF;
    while (r - l > EPS) {
        double m = (l + r) / 2;
        if (tryTime(m)) {
            r = m;
        } else {
            l = m;
        }
    }
    cout.precision(5);
    cout << l;

    return 0;
}