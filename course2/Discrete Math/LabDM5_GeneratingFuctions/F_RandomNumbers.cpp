#include <iostream>
#include <vector>
#include <cmath>

#define ll long long

using namespace std;

const ll MOD = 104857601;

ll n;
int k;
vector<ll> a, q, negQ, r;

void read() {
    cin >> k >> n;
    --n;
    a.assign(2 * k, 0);
    for (int i = 0; i < k; ++i) {
        ll value;
        cin >> value;
        a[i] = value;
    }
    q.push_back(1);
    for (int j = 0; j < k; ++j) {
        ll cf;
        cin >> cf;
        q.push_back((-cf + MOD) % MOD);
    }
}

ll get(vector<ll> &polynom, int i) {
    return (i > k ? 0 : polynom[i]);
}

int main() {
    read();
    r.assign(k + 1, 0);
    negQ.assign(k + 1, 0);
    while (n >= k) {

        for (int i = k; i <= 2 * k - 1; ++i) {
            a[i] = 0;
            for (int j = 1; j <= k; ++j) {
                a[i] = (a[i] - q[j] * a[i - j] + MOD) % MOD;
            }

            while (a[i] < 0) {
                a[i] += MOD;
            }
        }

        for (int i = 0; i <= k; ++i) {
            negQ[i] = (i % 2 == 0 ? q[i] : -q[i] + MOD) % MOD;
        }

        for (int i = 0; i <= 2 * k; i += 2) {
            r[i / 2] = 0;
            for (int j = 0; j <= i; ++j) {
                r[i / 2] = (r[i / 2] + get(q, j) * get(negQ, i - j) + MOD) % MOD;
            }
        }

        int begin = 0;
        for (int i = 0; i <= 2 * k - 1; ++i) {
            if (n % 2 == i % 2) {
                a[begin++] = a[i];
            }
        }

        for (int i = 0; i <= k; ++i) {
            q[i] = r[i];
        }

        n /= 2;
    }

    cout << a[n];

    return 0;
}

