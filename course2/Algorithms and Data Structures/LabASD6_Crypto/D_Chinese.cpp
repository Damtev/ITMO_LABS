#include <iostream>
#include <vector>
#include <math.h>
#include <time.h>

#define ll long long
#define SIZE 2

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

ll chinese(vector<ll>& a, vector<ll>& p, size_t size) {
    vector<vector<ll>> r(size, vector<ll>(size));
    for (int i = 0; i < size; ++i) {
        for (int j = i + 1; j < size; ++j) {
            ll x, y;
            gcd(p[i], p[j], x, y);
            r[i][j] = (x % p[j] + p[j]) % p[j];
        }
    }

    ll result = 0, mult = 1;
    vector<ll> x(size);
    for (int i = 0; i < size; ++i) {
        x[i] = a[i];
        for (int j = 0; j < i; ++j) {
            ll cur = (x[i] - x[j]) * r[j][i];
            x[i] = (cur % p[i] + p[i]) % p[i];

        }

        result += mult * x[i];
        mult *= p[i];
    }

    return result;
}

int main() {
    ll a0, a1, p0, p1;
    cin >> a0 >> a1 >> p0 >> p1;
    vector<ll> a{a0, a1};
    vector<ll> p{p0, p1};
    cout << chinese(a, p, SIZE);

    return 0;
}