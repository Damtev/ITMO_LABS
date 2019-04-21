#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

const long long MOD = 998244353;
const int MAX_WEIGHT = 1000;

int n, m;
vector<long long> p, q, sum, mult, divide, reversed;

void parse() {
    cin >> n >> m;
    for (int i = 0; i <= n; ++i) {
        long long coefficient;
        cin >> coefficient;
        p.push_back(coefficient);
    }
    for (int i = 0; i <= m; ++i) {
        long long coefficient;
        cin >> coefficient;
        q.push_back(coefficient);
    }
}

void simplify() {
    while (n < m) {
        p.push_back(0);
        ++n;
    }
    while (m < n) {
        q.push_back(0);
        ++m;
    }
    while (n < MAX_WEIGHT) {
        p.push_back(0);
        ++n;
        q.push_back(0);
        ++m;
    }
}

void calcSum() {
    int deg = 0;
    for (int i = 0; i < p.size(); ++i) {
        sum.push_back((p[i] + q[i] + MOD) % MOD);
        if (sum[i] != 0) {
            deg = i;
        }
    }
    cout << deg << endl;
    for (int i = 0; i <= deg; ++i) {
        cout << sum[i] << " ";
    }
    cout << endl;
}

void calcMult(vector<long long>& p, vector<long long>& q) {
    int deg = 0;
    mult.resize(p.size() + q.size() - 1, 0);
    for (int i = 0; i < p.size(); ++i) {
        for (int j = 0; j < q.size(); ++j) {
            mult[i + j] = (mult[i + j] + p[i] * q[j] + MOD) % MOD;
        }
    }
    for (int k = 0; k < mult.size(); ++k) {
        if (mult[k] != 0) {
            deg = k;
        }
    }
    cout << deg << endl;
    for (int i = 0; i <= deg; ++i) {
        cout << mult[i] << " ";
    }
    cout << endl;
}

//q0 = 1
void calcDivide() {
    reversed.push_back(1);
    for (int i = 1; i < q.size(); ++i) {
        long long cf = 0;
        for (int k = 1; k <= i; ++k) {
            cf = (cf - q[k] * reversed[i - k] + MOD) % MOD;
        }
        reversed.push_back(cf);
    }
    divide.assign(2 * MAX_WEIGHT, 0);
    for (int i = 0; i < p.size(); ++i) {
        for (int j = 0; j < reversed.size(); ++j) {
            divide[i + j] = (divide[i + j] + p[i] * reversed[j] + MOD) % MOD;
        }
    }
    for (int l = 0; l < MAX_WEIGHT; ++l) {
        cout << divide[l] << " ";
    }
}

int main() {
    parse();
    simplify();
    calcSum();
    calcMult(p, q);
    calcDivide();
    return 0;
}