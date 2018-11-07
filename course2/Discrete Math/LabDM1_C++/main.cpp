#include <iostream>
#include <deque>
#include <vector>
#include <deque>
#include <fstream>
#include <assert.h>

using namespace std;

vector<vector<bool>> getUndirectedGraph(int n, vector<int>& degrees) {
	vector<vector<bool>> graph;
    graph.resize(n + 1);
	for (int i = 0; i <= n; ++i) {
		graph[i].resize(n + 1);
	}
    for (int v = 2; v <= n; v++) {
        string edges;
		cin >> edges;
		for (int u = 0; u < edges.size(); ++u) {
			if (edges[u] == '1') {
				graph[v][u + 1] = (edges[u] == '1');
				graph[u + 1][v] = graph[v][u + 1];
				++degrees[v];
				++degrees[u + 1];
			}

        }
    }
	return graph;
}

void swapSub(int k, deque<int>* cycle) {
	deque<int> queue;
	int first = cycle->front();
	cycle->pop_front();
	for (int i = 0; i < k; ++i) {
		queue.push_back(cycle->front());
		cycle->pop_front();
	}
	for (int i = 0; i < k; ++i) {
		cycle->push_front(queue.front());
		queue.pop_front();
	}
	cycle->push_front(first);
}

void swapSub(int firstElements, int k, deque<int>* cycle) {
	deque<int> queue;
	deque<int> first;
	for (int j = 0; j < firstElements; ++j) {
		first.push_back(cycle->front());
		cycle->pop_front();
	}
	for (int i = 0; i < k; ++i) {
		queue.push_back(cycle->front());
		cycle->pop_front();
	}
	for (int i = 0; i < k; ++i) {
		cycle->push_front(queue.front());
		queue.pop_front();
	}
	for (int l = 0; l < firstElements; ++l) {
		cycle->push_front(first.back());
		first.pop_back();
	}
}

deque<int>* findCycle(vector<vector<bool>>& graph, int n) {
	auto * cycle = new deque<int>;
	for (int v = 1; v <= n; ++v) {
		cycle->push_back(v);
	}
	for (int j = 0; j < n * (n - 1); ++j) {
		if (!graph[cycle->at(0)][cycle->at(1)]) {
			int k = 2;
			while (!(graph[cycle->at(0)][cycle->at(k)] && graph[cycle->at(1)][cycle->at(k + 1)])) {
				++k;
			}

			swapSub(k, cycle);
		}
		cycle->push_back(cycle->front());
		cycle->pop_front();
	}
	return cycle;
}

void mainA(const char *fileIn, const char *fileOut) {
	ios_base::sync_with_stdio(false);
	freopen(fileIn, "r", stdin);
	int n;
	cin >> n;
	vector<int> degrees;
	degrees.assign(n + 1, 0);
	vector<vector<bool>> graph = getUndirectedGraph(n, degrees);
	deque<int>* cycle = findCycle(graph, n);
	ofstream fout(fileOut);
	for (int i = 0; i < n; ++i) {
		fout << cycle->front() << " ";
		cycle->pop_front();
	}
	fclose(stdin);
	fout.close();
}

void cycleSwap(unsigned long n, unsigned long i, unsigned long j, deque<int>* cycle) {
	int temp = cycle->at(i % n);
	cycle->at(i % n) = cycle->at(j % n);
	cycle->at(j % n) = temp;
}

deque<int>* findCycle(vector<vector<bool>>& graph, int n, int task) {
	auto * cycle = new deque<int>;
	for (int v = 1; v <= n; ++v) {
		cycle->push_back(v);
	}
	unsigned long first = 0;
	for (int j = 0; j < n * (n - 1); ++j) {
		if (!graph[cycle->at(first % n)][cycle->at((first + 1) % n)]) {
			unsigned long k = first + 2;
			while (k - first != n && !(graph[cycle->at(first % n)][cycle->at(k % n)]
			&& graph[cycle->at((first + 1) % n)][cycle->at((k + 1) % n)])) {
				++k;
			}

			if (k - first == n) {
				k = first + 2;
				while (!graph[cycle->at(first % n)][cycle->at(k % n)]) {
					++k;
				}
			}

			unsigned long l = first + 1;
			while (l <= k) {
				cycleSwap(static_cast<unsigned long>(n), l, k, cycle);
				++l;
			}

		}
		++first;
	}
	return cycle;
}

void mainB(const char *fileIn, const char *fileOut) {
	ios_base::sync_with_stdio(false);
	freopen(fileIn, "r", stdin);
	int n;
	cin >> n;
	vector<int> degrees;
	degrees.assign(n + 1, 0);
	vector<vector<bool>> graph = getUndirectedGraph(n, degrees);
	deque<int>* cycle = findCycle(graph, n, 2);
	ofstream fout(fileOut);
	for (int i = 0; i < n; ++i) {
		fout << cycle->front() << " ";
		cycle->pop_front();
	}
	fclose(stdin);
	fout.close();
}

string makeRequest(int v, int u) {
	cout << 1 << " " << v << " " << u << endl;
	cout.flush();
	string answer;
	cin >> answer;
	return answer;
}

void binSearch(int n, vector<int>& cycle) {
	for (int v = 2; v <= n; ++v) {
		int l = 0;
		int r = cycle.size();
		int mid = (l + r) / 2;
		while (l < r) {
			string answer = makeRequest(v, cycle[mid]);
			if (answer == "NO") { // v > cycle[mid]
				l = mid + 1;
			} else {
				r = mid;
			}
			mid = (l + r) / 2;
		}
		auto insertPlace = cycle.begin() + mid;
		cycle.insert(insertPlace, v);
	}
}

void mainC() {
	ios_base::sync_with_stdio(false);
	int n;
	cin >> n;
	vector<int> initCycle;
	initCycle.push_back(1);
	binSearch(n, initCycle);

	cout << 0 << " ";
	for (int i = 0; i < n; ++i) {
		cout << initCycle[i] << " ";
	}
}


vector<vector<bool>> getDirectedGraph(int n) {
	vector<vector<bool>> graph;
	graph.resize(n + 1);
	for (int i = 0; i <= n; ++i) {
		graph[i].resize(n + 1);
	}
	for (int v = 2; v <= n; v++) {
		string edges;
		cin >> edges;
		for (int u = 0; u < edges.size(); ++u) {
			if (edges[u] == '1') {
				graph[v][u + 1] = true;
			} else {
				graph[u + 1][v] = true;
			}
		}
	}
	return graph;
}

vector<int> findHamiltonianPath (int n, vector<vector<bool>>& graph) {
	vector<int> path;
	path.push_back(1);

	for (int v = 2; v <= n; ++v) {
		auto it = path.begin();
		while (it != path.end() && graph[*it][v]) {
			++it;
		}
		path.insert(it, v);
	}
	return path;
}

vector<int> findHamiltonianCycle (int n, vector<vector<bool>>& graph, vector<int>& path ) {
	vector<int> cycle;
	int i;
	for (i = (int) path.size() - 1; i >= 2; --i) {
		if (graph[path[i]][path[0]]) {
			break;
		}
	}
	cycle.insert(cycle.begin(), path.begin(), path.begin() + i + 1);
	path.erase(path.begin(), path.begin() + i + 1);

	for (auto st = path.begin(); st != path.end(); ) {
		auto it = cycle.begin();
		while (it != cycle.end() && graph[*it][*st]) {
			++it;
		}
		if (it != cycle.end()) {
			cycle.insert(it, path.begin(), st + 1);
			path.erase(path.begin(), st + 1);
			st = path.begin();
		} else {
			st++;
		}
	}

	return cycle;
}

void mainD(const char *fileIn, const char *fileOut) {

	ios_base::sync_with_stdio(false);
	freopen(fileIn, "r", stdin);
	int n;
	cin >> n;

	vector<vector<bool>> graph = getDirectedGraph(n);

	vector<int> path = findHamiltonianPath(n, graph);

	vector<int> cycle = findHamiltonianCycle(n, graph, path);

	ofstream fout(fileOut);
	for (int i = 0; i < n; ++i) {
		fout << cycle[i] << " ";
	}
	fclose(stdin);
	fout.close();
}


int main() {
	mainA("fullham.in", "fullham.out");

	mainB("chvatal.in", "chvatal.out");

	mainC();

	mainD("guyaury.in", "guyaury.out");
	return 0;

}