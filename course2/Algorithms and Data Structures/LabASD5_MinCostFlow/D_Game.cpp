#include <iostream>

using namespace std;

int main() {
    freopen("rps2.in", "r", stdin);
    freopen("rps2.out", "w", stdout);

    int r1, s1, p1, r2, s2, p2;
    cin >> r1 >> s1 >> p1;
    cin >> r2 >> s2 >> p2;

    int minimalLoses = 0;
    int stoneDiff = r1 - p2 - r2;
    int scissorsDiff = s1 - r2 - s2;
    int paperDiff = p1 - s2 - p2;
    minimalLoses = max(minimalLoses, max(stoneDiff, max(scissorsDiff, paperDiff)));
    cout << minimalLoses;

    fclose(stdin);
    fclose(stdout);
    return 0;
}