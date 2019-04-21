#include <queue>
#include <iostream>

#define ll long long

using namespace std;

const int INF = INT32_MAX;

int n, m;
vector<vector<int>> C;
vector<int> answer;

void readMatrix() {
    cin >> n;
    m = n;
    C.assign(n + 1, vector<int>(m + 1));
    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= m; ++j) {
            cin >> C[i][j];
        }
    }
}

int hungary() {
    vector<int > u(n + 1), v(m + 1), matching(m + 1), way(m + 1);
    for (int i = 1; i <= n; ++i) {
        matching[0] = i;
        int column = 0;
        vector<int> minInColumn(m + 1, INF);
        vector<bool> used(m + 1, false);
        while (true) {
            used[column] = true;
            int line = matching[column];
            int delta = INF;
            int nextColumn = 0;
            for (int j = 1; j <= m; ++j) {
                if (!used[j]) {
                    int curMin = C[line][j] - u[line] - v[j];
                    if (curMin < minInColumn[j]) {
                        minInColumn[j] = curMin;
                        way[j] = column;
                    }
                    if (minInColumn[j] < delta) {
                        delta = minInColumn[j];
                        nextColumn = j;
                    }
                }
            }
            for (int j = 0; j <= m; ++j) {
                if (used[j]) {
                    u[matching[j]] += delta;
                    v[j] -= delta;
                } else {
                    minInColumn[j] -= delta;
                }
            }
            column = nextColumn;

            if (matching[column] == 0) {
                break;
            }
        }

        while (true) {
            int nextColumn = way[column];
            matching[column] = matching[nextColumn];
            column = nextColumn;

            if (column == 0) {
                break;
            }
        }
    }

    answer.resize(n + 1);
    for (int j = 1; j <= m; ++j) {
        answer[matching[j]] = j;
    }

    return -v[0];
}

int main() {
    freopen("assignment.in", "r", stdin);
    freopen("assignment.out", "w", stdout);
    readMatrix();

    int sum = hungary();
    cout << sum << endl;
    for (int i = 1; i <= n; ++i) {
        cout <<  i << " " << answer[i] << endl;
    }

    fclose(stdin);
    fclose(stdout);

    return 0;
}