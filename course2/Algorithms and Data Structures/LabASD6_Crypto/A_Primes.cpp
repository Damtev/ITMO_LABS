#include <iostream>
#include <vector>

#define N 20000000ll
#define ll long long

using namespace std;

vector<bool> prime;

void sieve() {
    prime.resize(N + 1, true);
    prime[0] = prime[1] = false;
    for (ll i = 2; i * i <= N; ++i) {
        if (prime[i]) {
            for (ll j = i * i; j <= N; j += i) {
                prime[j] = false;
            }
        }
    }
}

int main() {
    cin.tie(nullptr);
    int n;
    cin >> n;
    sieve();
    for (int i = 0; i < n; ++i) {
        ll number;
        cin >> number;
        cout << (prime[number] ? "YES" : "NO") << "\n";
    }
}