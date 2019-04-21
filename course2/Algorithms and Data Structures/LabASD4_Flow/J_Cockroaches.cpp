#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

struct Item {
    long long x1;
    long long y1;
    long long x2;
    long long y2;

    Item(long long x1, long long y1, long long x2, long long y2) : x1(x1), y1(y1), x2(x2), y2(y2) {}
};

const long long INF = INT64_MAX - 1;

long long n, w;

vector<Item> items;
vector<vector<long long>> graph;

vector<long long> dijkstra() {
    vector<long long> d(n + 2, INF);
    vector<bool> used(n + 2, false);
    d[0] = 0;
    for (long long i = 0; i < n + 2; ++i) {
        long long v = -1;
        for (long long j = 0; j < n + 2; ++j) {
            if (!used[j] && (v == -1 || d[j] < d[v])) {
                v = j;
            }
        }
        used[v] = true;
        for (long long u = 0; u < n + 2; ++u) {
            if (v != u) {
                long long curDist = graph[v][u];
                if (d[v] + curDist < d[u]) {
                    d[u] = d[v] + curDist;
                }
            }
        }
    }
    return d;
}

void readInput() {
    cin >> n >> w;
    graph = vector<vector<long long>>(n + 2, vector<long long>(n + 2));
    for (long long i = 0; i < n; ++i) {
        long long x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        items.emplace_back(x1, y1, x2, y2);
    }
}

int main() {
    readInput();
    graph[0][n + 1] = graph[n + 1][0] = w;
    for (long long i = 0; i < n; ++i) {
        Item curItem = items[i];
        long long maxY = max(curItem.y1, curItem.y2);
        long long minY = min(curItem.y1, curItem.y2);
        graph[0][i + 1] = graph[i + 1][0] = w - maxY;
        graph[n + 1][i + 1] = graph[i + 1][n + 1] = minY;
        for (long long j = 0; j < n; ++j) {
            Item other = items[j];
            long long lengthDiff = (curItem.x1 <= other.x1) ? other.x1 - curItem.x2 : curItem.x1 - other.x2;
            long long widthDiff = (curItem.y1 <= other.y1) ? other.y1 - curItem.y2 : curItem.y1 - other.y2;
            long long distance = max(lengthDiff, widthDiff);
            if (distance < 0) {
                distance = 0;
            }
            graph[i + 1][j + 1] = graph[j + 1][i + 1] = distance;
        }
    }
    vector<long long> d = dijkstra();
    cout << (d[n + 1] == INF ? 0 : d[n + 1]);

    return 0;
}