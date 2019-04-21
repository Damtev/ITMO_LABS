#include <iostream>
#include <vector>
#include <cmath>

#define ll long long

using namespace std;

const unsigned ll MAX_WEIGHT = 6;
unsigned ll SIZE = MAX_WEIGHT + 1;

std::string line;
int pos;

ll calcComb(ll weight, ll n) {
    if (weight < 0) {
        weight = 0;
    }
    ll comb = 1;
    for (ll i = weight - n + 1; i <= weight; ++i) {
        comb *= i;
    }
    for (int i = 2; i <= n; ++i) {
        comb /= i;
    }
    return comb;
}

vector<ll> parse() {
    vector<ll> result(SIZE, 0);
    vector<ll> weightsA;
    vector<ll> weightsB;
    switch (line[pos]) {
        case 'B':
            result[1] = 1;
            ++pos;
            break;
        case 'L': // Seq
            pos += 2;
            weightsA = parse();
            ++pos;
            result[0] = 1;
            for (int n = 1; n < SIZE; ++n) {
                for (int i = 1; i <= n; ++i) {
                    result[n] += weightsA[i] * result[n - i];
                }
            }

            break;
        case 'S': // MSet
        {
            pos += 2;
            weightsA = parse();
            ++pos;
            vector<vector<ll>> m(SIZE, vector<ll>(SIZE, 0));
            for (int i = 0; i < SIZE; ++i) {
                m[0][i] = 1;
            }
            result[0] = 1;

            for (int n = 1; n < SIZE; ++n) {
                for (int k = 1; k < SIZE; ++k) {
                    for (int i = 0; i <= n / k; ++i) {
                        m[n][k] += calcComb(weightsA[k] + i - 1, i) * m[n - i * k][k - 1];
                    }
                }
                result[n] = m[n][n];
            }
        }

            break;
        case 'P': // Pair
            pos += 2;
            weightsA = parse();
            ++pos;
            weightsB = parse();
            ++pos;
            for (int n = 0; n < SIZE; ++n) {
                for (int i = 0; i <= n; ++i) {
                    result[n] += weightsA[i] * weightsB[n - i];
                }
            }

            break;
        default:
            cerr << "Вот это поворот" << endl;
            exit(EXIT_FAILURE);
    }
    return result;
}

int main() {
    getline(cin, line);
    auto result = parse();
    for (ll number : result) {
        cout << number << " ";
    }

    return 0;
}

