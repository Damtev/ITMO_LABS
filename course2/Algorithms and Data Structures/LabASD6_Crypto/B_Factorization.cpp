#include <iostream>
#include <vector>

#define N 1000000ll
#define ll long long

using namespace std;

vector<ll> prime_divider;
vector<ll> primes;

void sieve() {
    prime_divider.resize(N + 1, 0);
    for (ll i = 2; i <= N; ++i) {
        if (prime_divider[i] == 0) {
            prime_divider[i] = i;
            primes.push_back(i);
        }
        for (ll j = 0; j < primes.size() && primes[j] <= prime_divider[i]; ++j) {
            ll index = i * primes[j];
            if (index > N) {
                break;
            }
            prime_divider[index] = primes[j];
        }
    }
}

int main() {
    cin.tie(nullptr);
    ios_base::sync_with_stdio(false);
    sieve();
    int n;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        ll number;
        cin >> number;
        cout << prime_divider[number] << " ";
        number = number / prime_divider[number];
        while (prime_divider[number] != 0) {
            cout << prime_divider[number] << " ";
            number = number / prime_divider[number];
        }
        cout << "\n";
    }
}