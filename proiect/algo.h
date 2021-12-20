/*
 * algo.h
 *
 *  Created on: Nov 24, 2020
 *      Author: Iancu Alexandru-Gabriel 324CD
 */

#ifndef ALGO_H_
#define ALGO_H_
#include <list>
#include <vector>
#include <fstream>
#include <sstream>
using namespace std;

class Node {
public:
	int vertex;
	int color;

	Node(int vertex) {
		this->vertex = vertex;
		this->color = 0;
	}
};

class GraphList {
public:
	vector<list<Node>> glist;
	int nodes;
	GraphList(int number_of_nodes) {
		this->nodes = number_of_nodes;
		int i;
		// adaug nodul corespunzator index-ului in fiecare lista de noduri
		for(i = 0; i < number_of_nodes; i++) {
			list<Node> new_list;
			this->glist.push_back(new_list);
			Node node(i);
			this->glist[i].push_front(node);
		}
	}

	void addnodes(ifstream &file) {
		string line;
		int neighbor;
		vector<list<Node>>::iterator iterator;
		getline(file, line);
		for(iterator = glist.begin(); iterator != glist.end(); iterator++) {
			getline(file, line);
			// transform sirul intr-un stream pentru a citii vecinii
			stringstream sline(line);
			while(sline >> neighbor) {
				// daca nu are noduri vecine iese din while
				if (neighbor == -1)
					break;

				Node node = this->glist[neighbor].front();

				(*iterator).push_back(node);
			}
		}
	}
};

bool isSafe(GraphList* graph, int v, int color);

bool Backtracking(GraphList* graph, int v, int numberOfColors);

int Greedy(GraphList* graph);

// folosita in WelshPowell
class Degree {
public:
	int degree;
	int vertex;
};

// folosita pentru a compara grad-ul intre 2 noduri de qsort in WelshPowell
// si are return-urile inversate pentru a avea o sortare descrescatoare
inline int compare(const void * a, const void * b) {
	if ((*(Degree*)a).degree <  (*(Degree*)b).degree) {
		return 1;
	} else if ((*(Degree*)a).degree == (*(Degree*)b).degree) {
		return 0;
	}

	return -1;
}

int WelshPowell(GraphList* graph);

#endif /* ALGO_H_ */
