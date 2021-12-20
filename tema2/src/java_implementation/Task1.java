// Copyright 2020
// Author: Matei Simtinică

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * Task1
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task1 extends Task {
    private Integer numberOfSpies;

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
        numberOfSpies = scanner.nextInt();
        getRelations(scanner);
        scanner.close();
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        File file = new File(oracleInFilename);
        Writer writer = new FileWriter(file);
        Integer numberOfVariables = numberOfMafias * numberOfSpies;
        
        // primul set de clauze
        Integer numberOfClauses = numberOfMafias;

        // al doilea set de clauze
        Integer numberOfCominations = combinationsOf2(numberOfSpies) * numberOfMafias;
        if (numberOfSpies > 1) {
            numberOfClauses += numberOfCominations;
        }

        // al treilea set de clauze
        numberOfClauses += numberOfRelations * numberOfSpies;

        writer.append("p cnf " + numberOfVariables + " " + numberOfClauses + "\n");

        // adaug clauzele care au grija ca fiecare mafiot sa aiba cel putin un spion
        minimumOneSpyClauses(writer);

        writer.append("\n");

        // adaug clauzele care au grija ca fiecare mafiot sa aiba cel mult un spion
        maximumOneSpyClauses(writer);

        writer.append("\n");

        // adaug clauzele care au grija ca mafiotii care se inteleg sa aiba spioni diferiti
        differentSpiesForRelatedMafiasClauses(writer);

        writer.close();
    }

    private void minimumOneSpyClauses(Writer writer) throws IOException {
        minimumOneClauses(writer, numberOfMafias, numberOfSpies);
    }
    
    public void maximumOneSpyClauses(Writer writer) throws IOException {
        maximumOneClauses(writer, numberOfMafias, numberOfSpies);
    }

    /**
     * Pentru fiecare relatie creeaza clauze care au grija ca cei doi mafioti din relatie nu
     * au acelasi spion
     * @param writer            writer-ul ce are atasat fisierul de iesire
     * @throws IOException      exceptie de I/O in cazul unei probleme in writer.append()
     */
    private void differentSpiesForRelatedMafiasClauses(Writer writer) throws IOException {
        for (Relation relation : goodRelations) {
            Integer firstMafia = relation.getOrigin();
            Integer secondMafia = relation.getDestination();
            for (int spyCounter = 1; spyCounter <= numberOfSpies; spyCounter++) {
                Integer firstVariable = -(numberOfSpies * (firstMafia - 1) + spyCounter);
                Integer secondVariable = -(numberOfSpies * (secondMafia - 1) + spyCounter);
                writer.append(firstVariable.toString() + " " + secondVariable.toString() + " 0\n");
            }
        }
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        File file = new File(oracleOutFilename);
        Scanner scanner = new Scanner(file);
        answer = scanner.nextBoolean();

        if (scanner.hasNext()) {
            scanner.nextInt();
        }

        assignSpies(scanner);

        scanner.close();
    }

    private void assignSpies(Scanner scanner) {
        getDecipheredResult(scanner, numberOfSpies);
    }
}
