// Copyright 2020
// Author: Matei Simtinică

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Bonus Task
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class BonusTask extends Task {
    private Integer sumOfOptionalWeights;
    private Integer numberOfVariables;
    private Map<Integer, Integer> mafiaDegrees;
    private Integer maxValue;

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
        askOracle();
        decipherOracleAnswer();
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        File inputFile = new File(inFilename);
        Scanner scanner = new Scanner(inputFile);
        getNumbers(scanner);
        getRelations(scanner);
        scanner.close();
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        File file = new File(oracleInFilename);
        Writer writer = new FileWriter(file);
        // existsa o variabila pentru fiecare mafiot
        numberOfVariables = numberOfMafias;
        
        // setul hard de clauze
        Integer numberOfClauses = numberOfRelations;

        // setul soft de clauze
        numberOfClauses += numberOfMafias;

        calculateDegreers();
        calculateSumOfWeights();
        
        writer.append("p wcnf " + numberOfVariables + " " + numberOfClauses + " ");
        writer.append(sumOfOptionalWeights + "\n");
        
        // adaug clauzele care au grija ca pentru fiecare relatie macar unul dintre mafioti sa
        // fie arestati
        keepAtLeastaMafiaPerRelation(writer);

        // ma asigur ca o sa aresteze doar minimul de mafioti necesari
        minimumNumberOfMafias(writer);

        writer.close();
    }

    /**
     * Calculeaza suma ponderilor clauzelor soft
     */
    private void calculateSumOfWeights() {
        Integer sum = 0;
        for (Integer value : mafiaDegrees.values()) {
            // +1 pentru a trata cazul special in care gradul este egal cu numberOfMafias
            sum += numberOfMafias + value + 1;
        }

        // se adauga + 1 pentru a ne asigura ca toate ponderile sunt mai mici strict decat top-ul 
        // calculat
        sumOfOptionalWeights = sum + 1;
    }

    /**
     * Calculeaza gradul mafiotilor si il pune in hashmap-ul mafiaDegrees la cheia corespunzatoare
     * index-ului mafiotului
     */
    private void calculateDegreers() {
        mafiaDegrees = new HashMap<>();
        // creez entry-urile pentru a avea la ce sa adun
        for (int i = 1; i <= numberOfMafias; i++) {
            mafiaDegrees.put(i, 0);
        }

        // pentru fiecare relatie pe care o gasesc cresc gradul cumulat cu 1 pentru destinatie
        // si sursa
        for (Relation relation : goodRelations) {
            Integer origin = relation.getOrigin();
            Integer destination = relation.getDestination();
            mafiaDegrees.replace(origin, mafiaDegrees.get(origin) + 1);
            mafiaDegrees.replace(destination, mafiaDegrees.get(destination) + 1);
        }
    }

    /**
     * Adauga clauzele soft ce ii spun solver-ului pe care mafioti ar fi bine sa nu-i aresteze
     * @param writer            Writer-ul corespondent fisierului de iesire
     * @throws IOException      Eroare la scriere
     */
    private void minimumNumberOfMafias(Writer writer) throws IOException {
        // trec prin toti mafiotii si pentru fiecare pun clauza P -Maf_i, unde ponderea P
        // este numberOfMafias minus gradul mafiotului i + 1
        for (int mafiaCounter = 1; mafiaCounter <= numberOfMafias; mafiaCounter++) {
            Integer notMafia = -mafiaCounter;
            Integer mafiaDegree = numberOfMafias - mafiaDegrees.get(mafiaCounter) + 1;

            writer.append(mafiaDegree + " " + notMafia + " 0\n");
        }
    }

    /**
     * Adauga clauzele care se asigura ca pentru fiecare relatie, unul dintre mafioti este arestat
     * @param writer            Writer-ul corespondent fisierului de iesire
     * @throws IOException      Eroare la scriere
     */
    private void keepAtLeastaMafiaPerRelation(Writer writer) throws IOException{        
        for (Relation relation : goodRelations) {
            writer.append(sumOfOptionalWeights + " ");
            writer.append(relation.getOrigin() + " " + relation.getDestination() + " 0\n");
        }
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        File file = new File(oracleOutFilename);
        Scanner scanner = new Scanner(file);

        deciphratedResult = new ArrayList<>();
        if (scanner.hasNext()) {
            numberOfVariables = scanner.nextInt();
            // nu ma folosesc de suma ponderilor satisfacute pentru afisare
            scanner.nextInt();
            for (int i = 1; i <= numberOfMafias; i++) {
                Integer variable = scanner.nextInt();
                if(variable > 0) {
                    deciphratedResult.add(variable);
                }
            }
        }

        scanner.close();
    }

    @Override
    public void writeAnswer() throws IOException {
        File file = new File(outFilename);
        Writer writer = new FileWriter(file);
        for (Integer assignedElement : deciphratedResult) {
            writer.append(assignedElement.toString() + " ");
        }
        
        writer.close();
    }
}
