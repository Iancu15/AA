/*
 * algo_test.cpp
 *
 *  Created on: Nov 24, 2020
 *      Author: Iancu Alexandru-Gabriel 324CD
 */

#include "algo.h"
#include <chrono>
using namespace std;

// testeaza daca graful e corect
// daca se gaseste un nod care are aceeasi culoare cu un nod vecin => incorect
bool isCorrectGraph(GraphList* graph) {
	int v;
	for (v = 0; v < graph->nodes; v++) {
		if (isSafe(graph, v, graph->glist[v].front().color) == false)
			return false;
	}

	return true;
}

// reseteaza culorile din graf
void clearColors(GraphList *graph) {
	int i;
	for (i = 0; i < graph->nodes; i++) {
		graph->glist[i].front().color = 0;
	}
}

void writeGraph(ofstream &file, GraphList* graph) {
	int v;
	for (v = 0; v < graph->nodes; v++) {
		Node node = graph->glist[v].front();
		file << "Vertex " << node.vertex;
		file << " with color " << node.color << "\n";
	}

	file << "\n";
}

void writeResult(ofstream &file, GraphList* graph, double elapsedTime,
										bool corectness, int chromaticNumber) {
	file << elapsedTime << " ";
	file << corectness << " ";
	file << chromaticNumber << "\n";
}

int main(int argc, char* argv[]) {
	if (argc == 1)
		printf("ERROR -> N-ai introdus fisierul de citire!\n");

	if (argc > 2)
		printf("ERROR -> Se introduce un singur fisier de citire!\n");

	string line, outputFile, inputFile;
	inputFile.assign(argv[1]);
	ifstream input(inputFile);
	int nodes;
	if (!input.is_open())
		return 1;

	input >> nodes;
	GraphList *graph = new GraphList(nodes);
	graph->addnodes(input);

	// creez path-ul pentru fisierul de output
	outputFile.append("out");
	outputFile.append(inputFile.substr(2, inputFile.size() - 5));
	outputFile.append(".out");
	ofstream output(outputFile);
	if (!output.is_open())
		return 1;

	output << graph->nodes << "\n";
	auto start = std::chrono::high_resolution_clock::now();
	int chromaticNumberWelsh = WelshPowell(graph);
	auto finish = std::chrono::high_resolution_clock::now();
	std::chrono::duration<double> elapsed = finish - start;
	bool isCorrectWelsh = isCorrectGraph(graph);
	//output << "WelshPowell: \n";
	writeResult(output, graph, elapsed.count(), isCorrectWelsh, chromaticNumberWelsh);
	clearColors(graph);

	start = std::chrono::high_resolution_clock::now();
	int chromaticNumberGreedy = Greedy(graph);
	finish = std::chrono::high_resolution_clock::now();
	elapsed = finish - start;
	bool isCorrectGreedy = isCorrectGraph(graph);
	//output << "Greedy: \n";
	writeResult(output, graph, elapsed.count(), isCorrectGreedy, chromaticNumberGreedy);
	clearColors(graph);

	// start = std::chrono::high_resolution_clock::now();
	// bool foundNumberWelsh = Backtracking(graph, 0, chromaticNumberWelsh);
	// finish = std::chrono::high_resolution_clock::now();
	// elapsed = finish - start;
	// //output << "Backtracking with WelshPowell's chromatic number: \n";
	// writeResult(output, graph, elapsed.count(), foundNumberWelsh, chromaticNumberWelsh);
	// clearColors(graph);

	start = std::chrono::high_resolution_clock::now();
	bool foundNumberGreedy = Backtracking(graph, 0, chromaticNumberGreedy);
	finish = std::chrono::high_resolution_clock::now();
	elapsed = finish - start;
	//output << "Backtracking with Greedy's chromatic number: \n";
	writeResult(output, graph, elapsed.count(), foundNumberGreedy, chromaticNumberGreedy);
	clearColors(graph);

	delete graph;
    input.close();
    output.close();
    return 0;
}
