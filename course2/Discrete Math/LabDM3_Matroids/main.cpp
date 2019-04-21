#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>
#include <cmath>

using namespace std;

set<pair<int, int>> findSchedule(int m, const vector<pair<int, int>>& works) {
	int t = 0;
	set<pair<int, int>> schedule;
	for (int i = 0; i < m; ++i) {
		schedule.emplace(works[i].second, works[i].first);
		if (works[i].first > t) {
			++t;
		} else {
			schedule.erase(schedule.begin());
		}
	}
	return schedule;
}

void A_Schedule() {
	freopen("schedule.in", "r", stdin);
	freopen("schedule.out", "w", stdout);

	int n;
	cin >> n;
	vector<pair<int, int>> works;
	long long answer = 0;
	int m = 0;

	for (int i = 0; i < n; ++i) {
		int d, w;
		cin >> d >> w;
		if (d == 0) {
			answer += w;
			continue;
		}
		works.emplace_back(d, w);
		++m;
	}

	sort(works.begin(), works.end());
	set<pair<int, int>> schedule = findSchedule(m, works);

	for (pair<int, int> work : works) {
		pair<int, int> inSet(work.second, work.first);
		auto iter = schedule.find(inSet);
		if (iter != schedule.end()) {
			schedule.erase(iter);
		} else {
			answer += work.second;
		}
	}

	cout << answer << endl;
}

bool compare(pair<int, int> first, pair<int, int> second) {
	return first.first > second.first;
}

bool kuhn(int v, vector<vector<int>>& edges, vector<bool>& used, vector<int>& matching) {
	if (used[v]) {
		return false;
	}
	used[v] = true;
	for (int i = 0; i < edges[v].size(); ++i) {
		int u = edges[v][i];
		if (matching[u] == 0 || kuhn(matching[u], edges, used, matching)) {
			matching[u] = v;
			return true;
		}
	}
	return false;
}

void C_Matching() {
	freopen("matching.in", "r", stdin);
	freopen("matching.out", "w", stdout);

	int n;
	vector<pair<int, int>> weights;
	vector<vector<int>> leftEdges;

	cin >> n;
	leftEdges.resize(n + 1);
	for (int v = 1; v <= n; ++v) {
		int w;
		cin >> w;
		weights.emplace_back(w, v);
	}
	for (int v = 1; v <= n; ++v) {
		int m;
		cin >> m;
		for (int i = 0; i < m; ++i) {
			int u;
			cin >> u;
			leftEdges[v].push_back(u);
		}
	}

	vector<int> order;
	sort(weights.begin(), weights.end(), compare);
	order.reserve(n);
	for (int i = 0; i < n; ++i) {
		int v = weights[i].second;
		order.push_back(v);
	}

	vector<int> matching;
	matching.resize(n + 1, 0);
	vector<bool> used(n + 1);
	for (int j = 0; j < n; ++j) {
		int v = order[j];
		used.assign(n + 1, false);
		kuhn(v, leftEdges, used, matching);
	}

	vector<int> matchingLeft;
	matchingLeft.resize(n + 1, 0);
	for (int l = 1; l <= n; ++l) {
		matchingLeft[matching[l]] = l;
	}

	for (int k = 1; k <= n; ++k) {
		cout << matchingLeft[k] << " ";
	}
}

bool checkSecondAxiom(const vector<int>& I) {
	for (int set : I) {
		for (int curSet = 0; curSet < set; ++curSet) { // перебираем возможные подмножества
			int intersection = curSet & set;
			if (intersection == curSet) { // это подмножество
				if (find(I.begin(), I.end(), intersection) == I.end()) { // не является независимым
					return false;
				}
			}
		}
	}
	return true;
}

bool checkThirdAxiom(int n, const vector<int>& I, const vector<int>& sizes) {
	for (int i = 0; i < I.size(); ++i) {
		int setA = I[i];
		for (int j = 0; j < I.size(); ++j) {
			if (i == j) {
				continue;
			}
			int setB = I[j];
			if (sizes[i] > sizes[j]) {
				int diff = setA & (~setB);
				bool existsX = false;
				for (int k = 0; k < pow(2, n); ++k) {
					int kElement = (diff >> k) & 1;
					if (kElement == 1) {
						int unionSet = setB | (1 << k);
						if (find(I.begin(), I.end(), unionSet) != I.end()) {
							existsX = true;
							break;
						}
					}
				}
				if (!existsX) {
					return false;
				}
			}
		}
	}
	return true;
}

void D_Check() {
	freopen("check.in", "r", stdin);
	freopen("check.out", "w", stdout);

	int n, m;
	cin >> n >> m;
	vector<int> I;
	vector<int> sizes;
	bool hasFirstAxiom = false;
	for (int i = 0; i < m; ++i) {
		int size;
		cin >> size;
		sizes.push_back(size);
		int curSet = 0;
		for (int j = 0; j < size; ++j) {
			int element;
			cin >> element;
			curSet |= 1 << (element - 1);
		}
		if (size == 0) {
			hasFirstAxiom = true;
		}
		I.push_back(curSet);
	}
	bool hasSecondAxiom = checkSecondAxiom(I);
	bool hasThirdAxiom = checkThirdAxiom(n, I, sizes);
	bool answer = hasFirstAxiom & hasSecondAxiom & hasThirdAxiom;
	cout << (answer ? "YES" : "NO") << endl;
}

long long findMaxBase(vector<pair<long long, int>>& weights, vector<int>& cycles) {
	sort(weights.begin(), weights.end());
	int curBase = 0;
	long long curWeight = 0;
	for (auto iter = weights.rbegin(); iter != weights.rend(); ++iter) {
		int curElement = (*iter).second;
		long long elementWeight = (*iter).first;
		curBase += 1 << (curElement - 1);
		bool isDependent = false;
		for (int cycle : cycles) { // проверяем на надмножество цикла
			int intersection = cycle & curBase;
			if (intersection == cycle) {
				isDependent = true;
				break;
			}
		}
		if (isDependent) {
			curBase -= 1 << (curElement - 1);
		} else {
			curWeight += elementWeight;
		}
	}
	return curWeight;
}


void E_Cycles() {
	freopen("cycles.in", "r", stdin);
	freopen("cycles.out", "w", stdout);

	int n, m;
	cin >> n >> m;

	vector<pair<long long, int>> weights;
	for (int i = 1; i <= n; ++i) {
		long long weight;
		cin >> weight;
		weights.emplace_back(weight, i);
	}

	vector<int> cycles;
	cycles.resize(m);
	for (int j = 0; j < m; ++j) {
		int size;
		cin >> size;
		int curCycle = 0;
		for (int i = 0; i < size; ++i) {
			int element;
			cin >> element;
			curCycle |= 1 << (element - 1);
		}
		cycles[j] = curCycle;
	}

	cout << findMaxBase(weights, cycles) << endl;
}

int main() {
//	A_Schedule();
//	C_Matching();
	D_Check();
//	E_Cycles();
	return 0;
}