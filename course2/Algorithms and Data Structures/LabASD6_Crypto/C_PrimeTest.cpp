#include <iostream>
#include <vector>
#include <math.h>
#include <time.h>

#define ll long long

using namespace std;

ll mul(ll a, ll n, ll m) {
    ll r = 0;
    while (n > 0) {
        if (n % 2 == 1)
            r = (r + a) % m;
        a = (a + a) % m;
        n /= 2;
    }
    return r;
}

ll pow(ll a, ll n, ll m) {
    ll res = 1;
    while (n > 0) {
        if ((n & 1) > 0)
            res = mul(res, a, m);
        a = mul(a, a, m);
        n >>= 1;
    }
    return res;
}

bool is_prime(ll n) {
    if (n == 2) {
        return true;
    }

    if (n < 2 || n % 2 == 0) {
        return false;
    }

    ll t = n - 1;
    ll s = 0;
    while (t % 2 == 0) {
        ++s;
        t /= 2;
    }

    for (int i = 0; i < (log(n) / 4) + 1; ++i) {
        ll a = (rand() % (n - 2)) + 2;
        ll x = pow(a, t, n);
        if (x == 1 || x == n - 1) {
            continue;
        }

        for (int j = 0; j < s - 1; ++j) {
            x = pow(x, 2, n);
            if (x == 1) {
                return false;
            }

            if (x == n - 1) {
                break;
            }
        }

        if (x != n - 1) {
            return false;
        }
    }

    return true;
}

int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);
    srand(time(0));
    int n;
    cin >> n;

    for (int i = 0; i < n; ++i) {
        ll number;
        cin >> number;
        cout << (is_prime(number) ? "YES" : "NO") << "\n";
    }

    return 0;
}