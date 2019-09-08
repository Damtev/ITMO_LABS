#include <iostream>
#include <vector>
#include <math.h>
#include <time.h>

#define ll long long

using namespace std;

ll gcd(ll a, ll b, ll& x, ll& y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    ll x1, y1;
    ll d = gcd (b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}

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

int main() {
    ll c, e, n;
    cin >> n >> e >> c;

    pair<ll, ll> factorization;
    for (ll i = 2; i <= n; ++i) {
        if (n % i == 0) {
            factorization = {i, n / i};
            break;
        }
    }

    ll euler_func = (factorization.first - 1) * (factorization.second - 1);
    ll x, y;
    gcd(e, euler_func, x, y);
    ll d = (x + euler_func) % euler_func;

    cout << pow(c, d, n);
}