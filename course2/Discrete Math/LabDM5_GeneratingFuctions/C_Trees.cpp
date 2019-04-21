#include <iostream>
#include <vector>
#include <cmath>

#define ll long long

using namespace std;

const ll MOD = 1000000007;

int m;
vector<bool> weights;

void parse() {
    int k;
    cin >> k >> m;
    weights.assign(m + 1, false);
    for (int i = 0; i < k; ++i) {
        int weight;
        cin >> weight;
        weights[weight] = true;
    }
}

int main() {
    std::ios_base::sync_with_stdio(false);
    parse();
    vector<ll> trees(m + 1, 0);
    vector<ll> prefix(m + 1, 0);
    trees[0] = 1;
    prefix[0] = 1;
    for (int treeWeight = 1; treeWeight <= m; ++treeWeight) {
        for (int rootWeight = 1; rootWeight <= treeWeight; ++rootWeight) {
            if (!weights[rootWeight]) {
                continue;
            }
            trees[treeWeight] = (trees[treeWeight] + prefix[treeWeight - rootWeight]) % MOD;
        }
        cout << trees[treeWeight] << " ";

        for (int prefixWeight = 0; prefixWeight <= treeWeight; ++prefixWeight) {
            prefix[treeWeight] = (prefix[treeWeight] + trees[prefixWeight] * trees[treeWeight - prefixWeight]) % MOD;
        }
    }

    return 0;
}

