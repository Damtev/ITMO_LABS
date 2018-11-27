#include <iostream>
#include <cmath>
#include <vector>
#include <set>
#include <unordered_set>
#include <unordered_map>
#include <map>
#include <utility>
#include <fstream>

using namespace std;

struct Edge {
	int u;
	int v;

	Edge(int u, int v) : u(u), v(v) {}

	bool operator==(Edge edge) {
		return ((u == edge.u && v == edge.v) || (u == edge.v && v == edge.u));
	}

	bool operator<(Edge edge) {
		return v < edge.v;
	}
};


bool isK5(int n, vector<vector<bool>>& graph) {
	for (int v = 1; v < graph.size(); ++v) {
		int k = 0;
		for (int u = 1; u < graph[v].size(); ++u) {
			if (graph[v][u]) {
				++k;
			}
		}
		if (k != 4) {
			return false;
		}
	}
	return true;
}

void isBiparted(int v, int curColor, vector<vector<bool>>& graph, vector<bool>& used, vector<int>& colors) {
	if (colors[0] != -1 || used[v]) {
		return;
	}

	used[v] = true;
	colors[v] = 3 - curColor;
	for (int u = 1; u <= 6; ++u) {
		if (graph[v][u]) {
			if (!used[u]) {
				isBiparted(u, colors[v], graph, used, colors);
			} else {
				if (colors[u] == colors[v] && colors[u] != -1) {
					colors[0] = 3;
				}
			}
		}
	}
}

bool isK3_3(vector<vector<bool>>& graph) {
	vector<int> colors(7, -1);
	vector<bool> used(7, false);
	for (int i = 1; i <= 6; ++i) {
		if (!used[i]) {
			isBiparted(i, 1, graph, used, colors);
		}
	}
	if (colors[0] != -1) {
		return false;
	}

	bool isK3_3 = true;
	for (int v = 1; v <= 6; ++v) {
//		if (graph[v].size() != 3) {
//			answer = false;
//			break;
//		}
		int k = 0;
		for (int u = 1; u <= 6; ++u) {
			if (graph[v][u]) {
				++k;
			}
		}
		if (k != 3) {
			isK3_3 = false;
			break;
		}
	}
	return isK3_3;
}

void D_PlanarityCheck() {
	freopen("planaritycheck.in", "r", stdin);
	freopen("planaritycheck.out", "w", stdout);
	int t;
	string s;
	cin >> t;
	getline(cin, s);
	for (int i = 0; i < t; ++i) {
		string table;
		getline(cin, table);

		// n ^ 2 - 2 * n - 2 * table.size = 0
		int n = (1 + sqrt(1 + 8 * table.size())) / 2;
		if (n <= 4) {
			cout << "YES" << endl;
			continue;
		}
		vector<vector<bool>> graph(n + 1);
		vector<int> degrees(n + 1, 0);
		vector<Edge> edges;
		for (int k = 1; k <= n; ++k) {
			graph[k].resize(n + 1, false);
		}
		int from = 2;
		int to = 1;
		int m = 0;
		for (char j : table) {
			if (j == '1') {
//				graph[to].push_back(from);
//				graph[from].push_back(to);
				graph[to][from] = graph[from][to] = true;
				++degrees[to];
				++degrees[from];
				++m;
				edges.emplace_back(min(from, to), max(from, to));
			}
			++to;
			if (from == to) {
				++from;
				to = 1;
			}
		}
		if (m > 3 * n - 6) {
			cout << "NO" << endl;
			continue;
		}
		if (n == 5) {
			if (isK5(5, graph)) {
				cout << "NO" << endl;
			} else {
				cout << "YES" << endl;
			}
			continue;
		}

		// n = 6
		if (m == 9) {
			if (isK3_3(graph)) {
				cout << "NO" << endl;
			} else {
				cout << "YES" << endl;
			}
		} else {
			bool isPlanar = true;
			if (m < 9) {
				cout << "YES" << endl;
			} else if (m == 12) {
				if (table == "000111111111111" || table == "011001111111111" || table == "101010111111111" ||
					table == "110100111111111") {
					cout << "NO" << endl;
				} else {

					// удалим 3 ребра и чекнем на K3_3

					for (int j = 0; j < 11; ++j) {
						Edge edge1 = edges[j];
						int u1 = edge1.u;
						int v1 = edge1.v;
						for (int k = j + 1; k < 11; ++k) {
							Edge edge2 = edges[k];
							int u2 = edge2.u;
							int v2 = edge2.v;
							for (int l = k + 1; l < 11; ++l) {
								Edge edge3 = edges[l];
								int u3 = edge3.u;
								int v3 = edge3.v;
								vector<vector<bool>> newGraph(n + 1);
								copy(graph.begin(), graph.end(), newGraph.begin());
								newGraph[u1][v1] = newGraph[v1][u1] = false;
								newGraph[u2][v2] = newGraph[v2][u2] = false;
								newGraph[u3][v3] = newGraph[v3][u3] = false;
								if (isK3_3(newGraph)) {
									isPlanar = false;
									break;
								}
							}
							if (!isPlanar) {
								break;
							}
						}
						if (!isPlanar) {
							break;
						}
					}
					if (!isPlanar) {
						cout << "NO" << endl;
					} else {
						// проверим на K5, стянув какое-то ребро
						for (int j = 0; j < 11; ++j) {
							Edge edge = edges[j];
							int u = edge.u;
							int v = edge.v;
							vector<vector<bool>> newGraph(n);
							for (int k = 1; k < n; ++k) {
								newGraph[k].resize(n, false);
							}
							// вершину uv определим как 5, остальные по порядку от 1 до 4
							unordered_map<int, int> oldToNew;
							int number = 1;
							for (int i1 = 1; i1 <= 6; ++i1) {
								if (i1 == u || i1 == v) {
									continue;
								} else {
									oldToNew.insert({i1, number});
									++number;
								}
							}

							for (int l = 1; l <= n; ++l) {
								for (int k = 1; k <= n; ++k) {
									if (graph[l][k]) {
										int newFrom;
										if (l == u || l == v) {
											newFrom = 5;
										} else {
											newFrom = oldToNew.find(l).operator*().second;
										}

										int newTo;
										if (k == u || k == v) {
											newTo = 5;
										} else {
											newTo = oldToNew.find(k).operator*().second;
										}

										if (newFrom == 5 && newTo == 5) {
											continue;
										} else {
											newGraph[newFrom][newTo] = newGraph[newTo][newFrom] = true;
										}
									}
								}
							}

							if (isK5(5, newGraph)) {
								isPlanar = false;
								break;
							}
						}
						if (isPlanar) {
							cout << "YES" << endl;
						} else {
							cout << "NO" << endl;
						}
					}
				}
			} else if (m == 10) {
				// ищем K5 + изолированная вершина
				int deg4 = 0;
				int deg0 = 0;
				for (int v = 1; v <= 6; ++v) {
					if (degrees[v] == 4) {
						++deg4;
					} else if (degrees[v] == 0) {
						++deg0;
					}
				}
				if (deg4 == 5 && deg0 == 1) {
					cout << "NO" << endl;
				} else {
					// уберем 1 ребро и поищем K3_3
					for (int j = 0; j < 10; ++j) {
						Edge edge = edges[j];
						int u = edge.u;
						int v = edge.v;
//						vector<Edge> newEdges;
//						copy(edges.begin(), edges.end(), newEdges.begin());
//						newEdges.erase(newEdges.begin() + j);
						vector<vector<bool>> newGraph(n + 1);
						copy(graph.begin(), graph.end(), newGraph.begin());
						newGraph[u][v] = newGraph[v][u] = false;
//						for (int k = 1; k <= n; ++k) {
//							newGraph[k].resize(n + 1, false);
//						}
//						for (Edge edge1 : newEdges) {
//							int u = edge1.u;
//							int v = edge1.v;
//							graph[u][v] = graph[v][u] = true;
//						}
						if (isK3_3(newGraph)) {
							isPlanar = false;
							break;
						}
					}
					if (isPlanar) {
						cout << "YES" << endl;
					} else {
						cout << "NO" << endl;
					}
				}
			} else {
				// m == 11

				// удалим 2 ребра и чекнем на K3_3
				for (int j = 0; j < 11; ++j) {
					Edge edge1 = edges[j];
					int u1 = edge1.u;
					int v1 = edge1.v;
					for (int k = j + 1; k < 11; ++k) {
						Edge edge2 = edges[k];
						int u2 = edge2.u;
						int v2 = edge2.v;
						vector<vector<bool>> newGraph(n + 1);
						copy(graph.begin(), graph.end(), newGraph.begin());
						newGraph[u1][v1] = newGraph[v1][u1] = false;
						newGraph[u2][v2] = newGraph[v2][u2] = false;
						if (isK3_3(newGraph)) {
							isPlanar = false;
							break;
						}
					}
					if (!isPlanar) {
						break;
					}
				}
				if (!isPlanar) {
					cout << "NO" << endl;
				} else {
					// проверим на K5, стянув какое-то ребро
					for (int j = 0; j < 11; ++j) {
						Edge edge = edges[j];
						int u = edge.u;
						int v = edge.v;
						vector<vector<bool>> newGraph(n);
						for (int k = 1; k < n; ++k) {
							newGraph[k].resize(n, false);
						}
						// вершину uv определим как 5, остальные по порядку от 1 до 4
						unordered_map<int, int> oldToNew;
						int number = 1;
						for (int i1 = 1; i1 <= 6; ++i1) {
							if (i1 == u || i1 == v) {
								continue;
							} else {
								oldToNew.insert({i1, number});
								++number;
							}
						}

						for (int l = 1; l <= n; ++l) {
							for (int k = 1; k <= n; ++k) {
								if (graph[l][k]) {
									int newFrom;
									if (l == u || l == v) {
										newFrom = 5;
									} else {
										newFrom = oldToNew.find(l).operator*().second;
									}

									int newTo;
									if (k == u || k == v) {
										newTo = 5;
									} else {
										newTo = oldToNew.find(k).operator*().second;
									}

									if (newFrom == 5 && newTo == 5) {
										continue;
									} else {
										newGraph[newFrom][newTo] = newGraph[newTo][newFrom] = true;
									}
								}
							}
						}

						if (isK5(5, newGraph)) {
							isPlanar = false;
							break;
						}
					}
					if (isPlanar) {
						cout << "YES" << endl;
					} else {
						cout << "NO" << endl;
					}
				}
			}
		}
	}
}

#include <limits>
#include <algorithm>

double EPS = 1e-9;

struct Line {
	double A;
	double B;
	double C;

	Line(double A, double B, double C) : A(A), B(B), C(C) {}
};

struct Point {
	double x;
	double y;

	Point(double x, double y) : x(x), y(y) {}

	bool operator==(const Point& other) const {
		return (abs(other.x - x) < EPS && abs(other.y - y) < EPS);
	}

	bool operator<(const Point& other) const {
		return (x < other.x - EPS || abs(x - other.x) < EPS && y < other.y - EPS);
	}

	double angleTo(const Point& other) const {
		//TODO: в каком порядке писать?
		double fi = atan2(other.y - y, other.x - x) * (180 / M_PI);
		return (fi < 0) ? fi + 360 : fi;
	}
};

map<Point, int> pointToVertex;
vector<Point> vertices;
vector<vector<int>> graph;

struct cmpByDegree {
	int center;

	explicit cmpByDegree(int center) : center(center) {}

	bool operator()(int a, int b) const {
		Point point1 = vertices[a];
		Point point2 = vertices[b];
		Point c = vertices[center];
		double fi1 = point1.angleTo(c);
		double fi2 = point2.angleTo(c);
		return (abs(fi1 - fi2) > EPS && fi1 < fi2);
	}
};

Point intersection(pair<Point, Point>& first, pair<Point, Point>& second) {
	double x1 = first.first.x;
	double x2 = first.second.x;
	double x3 = second.first.x;
	double x4 = second.second.x;
	double y1 = first.first.y;
	double y2 = first.second.y;
	double y3 = second.first.y;
	double y4 = second.second.y;
	double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

	if (abs(denominator) < EPS) {
		return {INT32_MAX, INT32_MAX};
	}

	return {((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator,
			((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator};
}

int getNumber(/*vector<vector<int>>& graph, */Point& point) {
	if (!pointToVertex.count(point)) {
		pointToVertex[point] = (int) vertices.size();
		vertices.push_back(point);
		graph.resize(graph.size() + 1);
	}
	return pointToVertex[point];
}

void findFacesSquares(int n, vector<double>& squares) {
	vector<vector<bool>> used(n);
	for (int i = 0; i < n; ++i) {
		used[i].resize(graph[i].size(), false);
	}

	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < graph[i].size(); ++j) {
			if (!used[i][j]) {
				used[i][j] = true;
				int v = graph[i][j];
				int parent = i;
				vector<int> face;
				while (true) {
					face.push_back(v);
					auto iter = lower_bound(graph[v].begin(), graph[v].end(), parent, cmpByDegree(v));
					if (++iter == graph[v].end()) {
						iter = graph[v].begin();
					}
					if (used[v][iter - graph[v].begin()]) {
						break;
					}
					used[v][iter - graph[v].begin()] = true;
					parent = v;
					v = *iter;
				}

				double square = 0.0;
				face.push_back(face[0]);
				for (int k = 0; k < face.size() - 1; ++k) {
					square += (vertices[face[k]].x + vertices[face[k + 1]].x) *
							  (vertices[face[k]].y - vertices[face[k + 1]].y);
				}
				if (square >= EPS) {
					squares.push_back(square / 2);
				}
			}
		}
	}
}

void B_Squares() {
	int m;
	cin >> m;
	if (m == 1) {
		cout << 0 << endl;
	} else {

		vector<pair<Point, Point>> pairs;
		for (int i = 0; i < m; ++i) {
			int x1, y1, x2, y2;
			cin >> x1 >> y1 >> x2 >> y2;
			pairs.emplace_back(Point(x1, y1), Point(x2, y2));
		}

		for (int i = 0; i < m; ++i) {
			vector<Point> curPoints;
			for (int j = 0; j < m; ++j) {
				Point curPoint = intersection(pairs[i], pairs[j]);
				if (curPoint.x == INT32_MAX && curPoint.y == INT32_MAX) {
					continue;
				}
				curPoints.push_back(curPoint);
			}

			if (!curPoints.empty()) {
				sort(curPoints.begin(), curPoints.end());
				for (int k = 0; k < curPoints.size() - 1; ++k) {
					int u = getNumber(curPoints[k]);
					int v = getNumber(curPoints[k + 1]);
					if (u != v) {
						graph[v].push_back(u);
						graph[u].push_back(v);
					}
				}
			}
		}

		int n = (int) graph.size();
		for (int l = 0; l < n; ++l) {
			sort(graph[l].begin(), graph[l].end(), cmpByDegree(l));
			graph[l].erase(unique(graph[l].begin(), graph[l].end()), graph[l].end());
		}


		if (n == 0) {
			cout << 0 << endl;
		} else {
			vector<double> squares;
			findFacesSquares(n, squares);

			cout << squares.size() << endl;
			sort(squares.begin(), squares.end());

			for (double& square : squares) {
				printf("%.10lf\n", square);
			}
		}
	}
//	}
}

void generateTestsA() {
	freopen("tests.in", "w", stdout);
	unsigned rand_value = 1;
	srand(rand_value);
	for (int j = 0; j < 200; ++j) {

		int m = (rand() % 80) + 1;
		cout << m << endl;

		for (int i = 0; i < m; ++i) {
			int x1 = (rand() % 100);
			if (rand() % 2 == 0) {
				x1 *= -1;
			}
			int y1 = (rand() % 100);
			if (rand() % 2 == 0) {
				y1 *= -1;
			}
			int x2 = (rand() % 100);
			if (rand() % 2 == 0) {
				x2 *= -1;
			}
			int y2 = (rand() % 100);
			if (rand() % 2 == 0) {
				y2 *= -1;
			}

			cout << x1 << " " << y1 << " " << x2 << " " << y2 << endl;
		}
	}
}

int main() {
	B_Squares();
//	D_PlanarityCheck();
//	generateTestsA();
	return 0;
}