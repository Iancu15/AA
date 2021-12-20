/*
 * algo.cpp
 *
 *  Created on: Nov 26, 2020
 *      Author: Iancu Alexandru-Gabriel 324CD
 */

#include "algo.h"

// Verifica daca nodul v are vreun vecin cu culoarea color
// daca are intoarce fals, daca nu intoarce adevarat
bool isSafe(GraphList* graph, int v, int color) {
	list<Node> nodeList = graph->glist[v];
	// daca nodul nu are vecini intoarce adevarat
	if (nodeList.size() == 1)
			return true;

	list<Node>::iterator iter = nodeList.begin();
	// sare peste nodul care se testeaza
	advance(iter, 1);
	for( ; iter != nodeList.end(); iter++) {
		if (graph->glist[(*iter).vertex].front().color == color) {
			return false;
		}
	}

	return true;
}

// O functie recursiva ce va realiza algoritmul de backtracking
bool Backtracking(GraphList* graph, int v, int numberOfColors) {
	// nodul graph->nodes nu exista, se termina iteratia curenta
	if (v == graph->nodes)
		return true;

	int color;
	for (color = 1; color <= numberOfColors; color++) {
		if (isSafe(graph, v, color)) {
			graph->glist[v].front().color = color;
			if(Backtracking(graph, v + 1, numberOfColors) == true) {
				return true;
			}

			// daca nu s-a gasit nicio combinatie in care nodul v
			// are culoarea color, se reseteaza campul
			graph->glist[v].front().color = 0;
		}
	}

	// daca nu s-a gasit o culoarea potrivita pentru v se intoarce fals
	return false;
}

int Greedy(GraphList* graph) {
	int chromaticNumber = 1;

	bool available[graph->nodes];
	for (int i = 1; i <= graph->nodes; i++) {
		available[i] = true;
	}

	for (int v = 0; v < graph->nodes; v++) {
		list<Node>::iterator iter;
		list<Node> listNode = graph->glist[v];
		// se actualizeaza vectorul de culori libere pentru iteratia curenta
		for (iter = listNode.begin(); iter != listNode.end(); iter++) {
			if (graph->glist[(*iter).vertex].front().color != 0)
				available[graph->glist[(*iter).vertex].front().color] = false;
		}

		// caut o culoare nefolosita de vecini
		int color;
		for (color = 1; color <= graph->nodes; color++) {
			if (available[color] == true)
				break;
		}

		graph->glist[v].front().color = color;

		if (color > chromaticNumber) {
			chromaticNumber = color;
		}

		// resetez vectorul pentru urmatoarea iteratie
		for (int i = 1; i < graph->nodes; i++) {
			available[i] = true;
		}
	}

	return chromaticNumber;
}

int WelshPowell(GraphList* graph) {
	Degree degrees[graph->nodes];
	for (int i = 0; i < graph->nodes; i++) {
		degrees[i].degree = graph->glist[i].size() - 1;
		degrees[i].vertex = i;
	}

	qsort(degrees, graph->nodes, sizeof(Degree), compare);
	int color = 1;
	int nextNode = 0;
	while(1) {
		int i;
		// caut primul nod necolorat in vectorul degrees
		// se parcurge secvential asa ca nu mai verific ultimul nod ales
		for (i = nextNode; i < graph->nodes; i++) {
			int vertex = degrees[i].vertex;
			if (graph->glist[vertex].front().color == 0) {
				graph->glist[vertex].front().color = color;
				break;
			}
		}
		nextNode = i;

		// daca s-a iesit din for fara break inseamna ca toate nodurile sunt
		// colorate
		if (i == graph->nodes)
			break;

		// caut noduri ramase necolorate in degrees
		int j;
		for (j = i; j < graph->nodes; j++) {
			int v = degrees[j].vertex;
			if (graph->glist[v].front().color != 0)
				continue;

			int isNeighbor = 0;
			list<Node>::iterator iter;
			list<Node> listNode = graph->glist[v];
			// daca nodul n-are vecini care sa fi fost colorati in iteratia
			// curenta il colorez
			for(iter = listNode.begin(); iter != listNode.end(); iter++) {
				if (graph->glist[(*iter).vertex].front().color == color) {
					isNeighbor = 1;
					break;
				}
			}

			if (isNeighbor == 0) {
				graph->glist[v].front().color = color;
			}
		}

		// schimb culoarea
		color++;
	}

	// culoarea incepe de la 1 asa ca scad acel 1
	return color - 1;
}
