//
// Created by damtev on 09.11.18.
//

#include <utility>
#include <iostream>
#include <vector>
#include <queue>
#include <set>
#include <climits>

using namespace std;

vector<vector<int>> getDirectedWeightedGraph(int n) {
	vector<vector<int>> graph;
	graph.resize(n + 1);
	for (int i = 0; i <= n; ++i) {
		graph[i].resize(n + 1);
	}
	for (int v = 1; v <= n; ++v) {
		for (int u = 1; u <= n; ++u) {
			cin >> graph[v][u];
		}
	}
	return graph;
}

void floyd(int n, vector<vector<int>> &graph) {
	for (int k = 1; k <= n; ++k) {
		for (int v = 1; v <= n; ++v) {
			for (int u = 1; u <= n; ++u) {
				graph[v][u] = min(graph[v][u], graph[v][k] + graph[k][u]);
			}
		}
	}
}

void A_Floyd() {
	int n;
	cin >> n;
	vector<vector<int>> graph = getDirectedWeightedGraph(n);
	floyd(n, graph);
	for (int v = 1; v <= n; ++v) {
		for (int u = 1; u <= n; ++u) {
			cout << graph[v][u] << " ";
		}
		cout << endl;
	}
}

vector<vector<pair<int, long long>>> getUndirectedWeightedGraph(int n, int m) {
	vector<vector<pair<int, long long>>> graph;
	graph.resize(n + 1);
	for (int j = 0; j < m; ++j) {
		int v, u;
		long long weight;
		cin >> v >> u >> weight;
		graph[v].emplace_back(u, weight);
		graph[u].emplace_back(v, weight);
	}
	return graph;
}

vector<long long> dijkstra(int s, int n, vector<vector<pair<int, long long>>> &graph) {
	vector<long long> d;
	vector<bool> used;
	d.assign(n + 1, INT64_MAX / 10);
	d[s] = 0;
	set<pair<long long, int>> queue;
	queue.emplace(0, s);
	while (!queue.empty()) {
		int v = queue.begin()->second;
		queue.erase(queue.begin());
		for (pair<int, long long> edge : graph[v]) {
			int u = edge.first;
			long long weight = edge.second;
			if (d[v] + weight < d[u]) {
				queue.erase({d[u], u});
				d[u] = d[v] + weight;
				queue.insert({d[u], u});
			}
		}
	}
	return d;
}

void B_Dijkstra() {
	int n, m;
	cin >> n >> m;
	vector<vector<pair<int, long long>>> graph = getUndirectedWeightedGraph(n, m);
	vector<long long> d = dijkstra(1, n, graph);

	cout << 0 << " ";
	for (int v = 2; v <= n; ++v) {
		cout << d[v] << " ";
	}
}

struct Edge {
	int from;
	int to;
	long long weight;

	Edge(int v, int u, long long weight) : from(v), to(u), weight(weight) {}
};

vector<Edge> getDirectedWeightedEdges(int n) {
	vector<Edge> edges;
	for (int v = 1; v <= n; ++v) {
		for (int u = 1; u <= n; ++u) {
			int weight;
			cin >> weight;
			if (weight != 100000) {
				edges.emplace_back(v, u, weight);
			}
		}
	}
	return edges;
}

void fordToNegativeCycle(int n, vector<Edge> &edges) {
	vector<long long> d;
	vector<int> p;
	d.assign(n + 1, 1);
	p.assign(n + 1, -1);
	d[1] = 0;
	for (int i = 0; i < n; ++i) {
		for (Edge edge : edges) {
			int v = edge.from;
			int u = edge.to;
			long long weight = edge.weight;
			if (d[v] + weight < d[u]) {
				d[u] = d[v] + weight;
				p[u] = v;
			}
		}
	}

	bool hasNegativeCycle = false;

	for (auto edge : edges) {
		int v = edge.from;
		int u = edge.to;
		long long weight = edge.weight;
		if (d[v] + weight < d[u]) {
			hasNegativeCycle = true;
			for (int i = 0; i < n; ++i) {
				v = p[v];
			}
			u = v;

			vector<int> negativeCycle;
			while (u != p[v]) {
				negativeCycle.push_back(v);
				v = p[v];
			}

			negativeCycle.push_back(v);

			cout << "YES" << endl;
			cout << negativeCycle.size() << endl;
			for (auto iter = negativeCycle.rbegin(); iter != negativeCycle.rend(); ++iter) {
				cout << *iter << " ";
			}
			break;
		}
	}

	if (!hasNegativeCycle) {
		cout << "NO";
	}
}

void C_NegativeCycle() {
	int n;
	cin >> n;
	vector<Edge> edges = getDirectedWeightedEdges(n);
	fordToNegativeCycle(n, edges);
}

void getGraph_D(int n, int m, vector<vector<pair<int, int>>> &graph) {
	graph.resize(n + 1);
	for (int i = 0; i < m; ++i) {
		int from, to, weight;
		cin >> from >> to >> weight;
		graph[from].emplace_back(to, weight);
	}
}

vector<vector<long long>> findShortestK(int n, int s, int k, vector<vector<pair<int, int>>> &graph) {
	vector<vector<long long>> d;
	d.resize(k + 1);
	for (int j = 0; j <= k; ++j) {
		d[j].resize(n + 1, LLONG_MAX / 2);
	}
	d[0][s] = 0;
	for (int i = 1; i <= k; ++i) {
		for (int v = 1; v <= n; ++v) {
			for (pair<int, int> edge : graph[v]) {
				int to = edge.first;
				int weight = edge.second; // м.б. < 0
				d[i][to] = min(d[i][to], d[i - 1][v] + weight);
			}
		}
	}
	return d;
}

void D_ShortestInKEdges() {
	int n, m, k, s;
	cin >> n >> m >> k >> s;
	vector<vector<pair<int, int>>> graph;
	getGraph_D(n, m, graph);
	vector<vector<long long>> d = findShortestK(n, s, k, graph);
	for (int v = 1; v <= n; ++v) {
		cout << (d[k][v] > INT32_MAX ? -1 : d[k][v]) << endl;
	}
}

void getEdges(int m, vector<Edge> &edges, vector<vector<int>> &verticesList) {
	for (int i = 0; i < m; ++i) {
		int from, to;
		long long weight;
		cin >> from >> to >> weight;
		edges.emplace_back(from, to, weight);
		verticesList[from].push_back(to);
	}
}

void dfs(int v, vector<vector<int>> &verticesList, vector<bool> &used, vector<bool> &inNegativeCycle) {
	used[v] = true;
	inNegativeCycle[v] = true;
	for (int u : verticesList[v]) {
		if (!used[u]) {
			dfs(u, verticesList, used, inNegativeCycle);
		}
	}
}

void dfs(int v, vector<vector<int>> &verticesList, vector<bool> &canReach) {
	canReach[v] = true;
	for (int u : verticesList[v]) {
		if (!canReach[u]) {
			dfs(u, verticesList, canReach);
		}
	}
}

void fordInE(int n, int s, vector<Edge> &edges, vector<vector<int>> &verticesList) {
	vector<long long> d;
	vector<int> p;
	vector<string> answer;
	d.assign(n + 1, LONG_LONG_MAX);
	p.assign(n + 1, -1);
	answer.resize(n + 1, "");
	d[s] = 0;
	int x = -1;

	vector<bool> canReach(n + 1, false);
	dfs(s, verticesList, canReach);
	for (auto iter = edges.begin(); iter != edges.end(); ++iter) {
		int from = (*iter).from;
		if (!canReach[from]) {
			edges.erase(iter);
			--iter;
		}
	}

	for (int i = 0; i < n; ++i) {
		x = -1;
		for (Edge edge : edges) {
			int v = edge.from;
			int u = edge.to;
			long long weight = edge.weight;
			if (d[v] < LONG_LONG_MAX) {
				if (!(weight > 0 && d[v] > 0 && d[v] + weight < 0)) {
					if (d[v] + weight < d[u]) {
						d[u] = d[v] + weight;
						p[u] = v;
						x = u;
					}
				}
			}
		}
	}

	vector<bool> inNegativeCycle(n + 1, false);

	if (x != -1) {
		for (auto edge : edges) {
			int v = edge.from;
			int u = edge.to;
			long long weight = edge.weight;
			if ((weight < 0 && d[v] + weight < d[u]) ||
				(weight >= 0 && d[v] + weight >= d[v] && d[v] + weight < d[u])) {
				for (int i = 0; i < n; ++i) {
					v = p[v];
				}
				vector<bool> used(n + 1, false);
				dfs(v, verticesList, used, inNegativeCycle);
			}
		}
	}

	for (int v = 1; v <= n; ++v) {
		if (inNegativeCycle[v]) {
			cout << "-";
		} else {
			if (d[v] == LONG_LONG_MAX) {
				cout << "*";
			} else {
				cout << d[v];
			}
		}
		cout << endl;
	}

}

void E_ShortestPaths() {
	int n, m, s;
	cin >> n >> m >> s;
	vector<Edge> edges;
	vector<vector<int>> verticesList;
	verticesList.resize(n + 1);
	getEdges(m, edges, verticesList);
	fordInE(n, s, edges, verticesList);
}

void F_Kefir() {
	int n, m, a, b, c;
	cin >> n >> m;

	vector<vector<pair<int, long long>>> graph = getUndirectedWeightedGraph(n, m);
	cin >> a >> b >> c;

	const long long INF = 2000000000000000; // 2 * (10 ^ 15)

	vector<long long> d_a = dijkstra(a, n, graph);
	long long ab = d_a[b];
	long long ac = d_a[c];

	vector<long long> d_b = dijkstra(b, n, graph);
	long long bc = d_b[c];

	long long answer = min(ab + ac, min(ab + bc, ac + bc));
	if (answer > INF) {
		answer = -1;
	}

	cout << answer;
}

void H_DwarfTower() {
	freopen("dwarf.in", "r", stdin);
	freopen("dwarf.out", "w", stdout);
	int n, m;
	cin >> n >> m;
	vector<long long> prices(n + 1);
	for (int i = 1; i <= n; ++i) {
		cin >> prices[i];
	}

	vector<vector<pair<int, int>>> graph(n + 1);
	for (int i = 0; i < m; ++i) {
		int result, first, second;
		cin >> result >> first >> second;
//		graph[result].emplace_back(first, second);
		graph[first].emplace_back(result, second);
		graph[second].emplace_back(result, first);
	}

	vector<long long> d;
	d.emplace_back(INT64_MAX);
	for (int i = 1; i <= n; ++i) {
		d.emplace_back(prices[i]);
	}
	set<pair<long long, int>> queue;
	for (int i = 1; i <= n; ++i) {
		queue.emplace(prices[i], i);
	}
	while (!queue.empty()) {
		int first = queue.begin()->second;
		queue.erase(queue.begin());
		for (pair<int, int> craft : graph[first]) {
			int result = craft.first;
			int second = craft.second;
			if (d[first] + d[second] < d[result]) {
				queue.erase({d[result], result});
				d[result] = d[first] + d[second];
				queue.insert({d[result], result});
			}
		}
	}

	cout << d[1];

	fclose(stdin);
	fclose(stdout);
}

int main() {
//	A_Floyd();
//	B_Dijkstra();
//	C_NegativeCycle();
//	D_ShortestInKEdges();
//	E_ShortestPaths();
//	F_Kefir();
	H_DwarfTower();
	return 0;
}
