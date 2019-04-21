#include <iostream>
#include <vector>
#include <unordered_map>
#include <map>
#include <unordered_set>
#include <string>
#include <algorithm>

using namespace std;

const int ALPHABET = 27;
const int MAXSIZE = 1000000;

//unordered_set<string> diffPatterns;

struct Vertex {
	vector<int> nextVertex;
	int patternNumber = 0;
	int suffLink = -1;
	int shortSuffLink = -1;
	vector<int> go;
	int parent = 0;
	bool isTerminal = false;
	int symbol = 0;

	Vertex(const int& parent, const int& symbol) : parent(parent), symbol(symbol) {
		nextVertex.resize(ALPHABET, -1);
		go.resize(ALPHABET, -1);
	}
};

struct Aho_Corasick {

//	std::vector<Vertex*> bohr;
	Vertex* bohr[MAXSIZE];
//	std::vector<string> patterns;
	string patterns[MAXSIZE];
	// TODO: проблема в хешмапе?
	std::map<string, /*vector<int>*/int> positions;
	int bohrSize = 0;
	int patternsSize = 0;

	void initialize() {
		bohr[0] = new Vertex(0, 0);
		++bohrSize;
	}

	void add(const string& s) {
//		if (diffPatterns.find(s) == diffPatterns.end()) {
		int curVertex = 0;
		Vertex* vertex = bohr[curVertex];
		for (const char i : s) {
			const int symbol = i - 'a' + 1;
			if (vertex->nextVertex[symbol] == -1) {
				bohr[bohrSize] = new Vertex(curVertex, symbol);
				vertex->nextVertex[symbol] = bohrSize;
				++bohrSize;
			}
			curVertex = vertex->nextVertex[symbol];
			vertex = bohr[curVertex];
		}
		vertex->isTerminal = true;
		vertex->patternNumber = patternsSize;
//			diffPatterns.insert(s);
//		}
		positions.emplace(s, -1);
//		patterns[patternsSize] = s;
		++patternsSize;
	}

	int calcSuffLink(const int& v) {
		Vertex* vertex = bohr[v];
		if (vertex->suffLink == -1) {
			if (v == 0 || vertex->parent == 0) {
				vertex->suffLink = 0;
			} else {
				vertex->suffLink = calcGo(calcSuffLink(vertex->parent), vertex->symbol);
			}
		}
		return vertex->suffLink;
	}

	int calcGo(int v, int symbol) {
		Vertex* vertex = bohr[v];
		if (vertex->go[symbol] == -1) {
			if (vertex->nextVertex[symbol] != -1) {
				vertex->go[symbol] = bohr[v]->nextVertex[symbol];
			} else {
				if (v == 0) {
					vertex->go[symbol] = 0;
				} else {
					vertex->go[symbol] = calcGo(calcSuffLink(v), symbol);
				}
			}
		}
		return vertex->go[symbol];
	}

	int calcShortSuffLink(const int& v) {
		Vertex* vertex = bohr[v];
		if (vertex->shortSuffLink == -1) {
			int suff = calcSuffLink(v);
			if (suff == 0) {
				vertex->shortSuffLink = 0;
			} else {
				if (bohr[suff]->isTerminal) {
					vertex->shortSuffLink = suff;
				} else {
					vertex->shortSuffLink = calcShortSuffLink(suff);
				}
			}
		}
		return vertex->shortSuffLink;
	}

	void isSubstring(const int& v, const int& curPos) {
		for (int suff = v; suff != 0; suff = calcShortSuffLink(suff)) {
			if (bohr[suff]->isTerminal) {
				string s = patterns[bohr[suff]->patternNumber];
//				positions[s].push_back(curPos - s.length());
				positions[s] = curPos - s.length();
				bohr[suff]->isTerminal = false;
			}
		}
	}

	void findPositions(const string& text) {
		int curVertex = 0;
		for (int i = 0; i < text.length(); i++) {
			curVertex = calcGo(curVertex, text[i] - 'a' + 1);
			isSubstring(curVertex, i + 1);
		}
	}
};

Aho_Corasick ahoCorasick;
Aho_Corasick ahoCorasick_reversed;
int n;
string text;

void read() {
	cin >> n;
	for (int i = 0; i < n; ++i) {
//		string pattern;
//		cin >> pattern;
		cin >> ahoCorasick.patterns[i];
		ahoCorasick.add(ahoCorasick.patterns[i]);
	}
	cin >> text;
};

//void calcLinks(Vertex* v) {
//	for (int i = 1; i < ALPHABET; ++i) {
//		if (v->nextVertex[i] != -1) {
//			ahoCorasick.calcShortSuffLink(v->nextVertex[i]);
//			calcLinks(ahoCorasick.bohr[v->nextVertex[i]]);
//		}
//	}
//}

void printAnswers() {
	for (int i = 0; i < n; ++i) {
		string s = ahoCorasick.patterns[i];
		int posLeft = ahoCorasick.positions.find(s)->second;
		reverse(s.begin(), s.end());
		int posRight = ahoCorasick_reversed.positions.find(s)->second;
		if (posRight != -1) {
			posRight = text.length() - posRight - s.length();
		}
		cout << posLeft << " " << posRight << "\n";
	}
}

void G_SearchSubstrings() {
	freopen("search6.in", "r", stdin);
	ios_base::sync_with_stdio(false);
	cin.tie(nullptr);
	cout.tie(nullptr);

	ahoCorasick.initialize();
	read();

//	calcLinks(ahoCorasick.bohr[0]);

	freopen("search6.out", "w", stdout);
	ahoCorasick.findPositions(text);
	ahoCorasick_reversed.initialize();
	for (int i = 0; i < n; ++i) {
		string s = ahoCorasick.patterns[i];
		reverse(s.begin(), s.end());
		ahoCorasick_reversed.patterns[i] = s;
		ahoCorasick_reversed.add(ahoCorasick_reversed.patterns[i]);
	}
	reverse(text.begin(), text.end());
	ahoCorasick_reversed.findPositions(text);
	printAnswers();
}


int main() {
	G_SearchSubstrings();
	return 0;
}