// Copyright 2020
// Author: Matei Simtinică

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

/**
 * Task2
 * You have to implement 4 methods:
 * readProblemData         - read the problem input and store it however you see fit
 * formulateOracleQuestion - transform the current problem instance into a SAT instance and write the oracle input
 * decipherOracleAnswer    - transform the SAT answer back to the current problem's answer
 * writeAnswer             - write the current problem's answer
 */
public class Task2 extends Task {
    private Integer dimensionOfExtendedFamily;

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
        dimensionOfExtendedFamily = scanner.nextInt();
        getRelations(scanner);
        scanner.close();
    }

    @Override
    public void formulateOracleQuestion() throws IOException {
        File file = new File(oracleInFilename);
        Writer writer = new FileWriter(file);
        Integer numberOfVariables = numberOfMafias * dimensionOfExtendedFamily;
        
        // primul set de clauze
        Integer numberOfClauses = dimensionOfExtendedFamily;
        
        // al doilea set de clauze
        numberOfClauses += dimensionOfExtendedFamily * combinationsOf2(numberOfMafias);
        
        // al treilea set de clauze
        int numberOfNotRelatedPairs = combinationsOf2(numberOfMafias) - numberOfRelations;
        numberOfClauses += numberOfNotRelatedPairs * combinationsOf2(dimensionOfExtendedFamily) * 2;
        
        // al patrulea set de clauze
        numberOfClauses += combinationsOf2(dimensionOfExtendedFamily) * numberOfMafias;
        
        writer.append("p cnf " + numberOfVariables + " " + numberOfClauses + "\n");
        
        // adaug clauzele care au grija ca fiecare pozitie din clica sa aiba cel putin un mafiot
        minimumOneMafiaClauses(writer);

        // adaug clauzele care au grija ca fiecare pozitie din clica sa aiba cel mult un mafiot
        maximumOneMafiaClauses(writer);
        
        // adaug clauzele care au grija ca mafiotii care nu se inteleg intre ei sa nu faca parte
        // amandoi din familia extinsa
        keepAwayNotRelatedMafias(writer);
        
        // adaug clauzele care au grija ca un mafiot sa nu aiba mai mult de o pozitie in familia
        // extinsa
        maximumOnePositionClauses(writer);
        
        writer.close();
    }

    /**
     * Fac clauze care sa restrictioneze ca 2 variabile ce au acelasi mafiot si pozitii diferite
     * sa nu fie adevarate in acelasi timp (maxim o pozitie pentru un mafiot)
     * @param writer        Writer-ul aferent fisierului de iesire
     * @throws IOException  Eroare in cazul unei probleme la scriere
     */
    private void maximumOnePositionClauses(Writer writer) throws IOException {
        for (int counterOne = 1; counterOne <= numberOfMafias; counterOne++) {
            for (int iCounterTwo = 1; iCounterTwo < dimensionOfExtendedFamily; iCounterTwo++) {
                for (int jCounterTwo = iCounterTwo + 1; jCounterTwo <= dimensionOfExtendedFamily; jCounterTwo++) {
                    Integer firstVariable = -(numberOfMafias * (iCounterTwo - 1) + counterOne);
                    Integer secondVariable = -(numberOfMafias * (jCounterTwo- 1) + counterOne);
                    writer.append(firstVariable.toString() + " " + secondVariable.toString() + " 0\n");
                }
            }
        }
    }

    /**
     * Pentru fiecare non-relatie trec prin toate pozitiile diferite pe care le-ar putea dintr-o
     * familie extinsa
     * @param writer            writer-ul aferent fisierul de iesire
     * @throws IOException      exceptie in cazul unui erori la scriere
     */
    private void keepAwayNotRelatedMafias(Writer writer) throws IOException{
        List<Relation> badRelations = generateBadRelations();
        
        for (Relation relation : badRelations) {
            // merg prin toate posibilitatile de pozitii
            for (int positionOne = 1; positionOne < dimensionOfExtendedFamily; positionOne++) {
                for (int positionTwo = positionOne + 1; positionTwo <= dimensionOfExtendedFamily; positionTwo++) {
                    Integer firstVariable = -(numberOfMafias * (positionOne - 1) + relation.getOrigin());
                    Integer secondVariable = -(numberOfMafias * (positionTwo- 1) + relation.getDestination());
                    writer.append(firstVariable.toString() + " " + secondVariable.toString() + " 0\n");

                    // trebuie clauze si pentru aceleasi pozitii, dar mafiotii interschimbati
                    // pentru ca sunt variabile diferite ce trebuie luate in calcul
                    firstVariable = -(numberOfMafias * (positionOne - 1) + relation.getDestination());
                    secondVariable = -(numberOfMafias * (positionTwo- 1) + relation.getOrigin());
                    writer.append(secondVariable.toString() + " " + firstVariable.toString() + " 0\n");
                }
            }
        }
    }

    private void minimumOneMafiaClauses(Writer writer) throws IOException {
        minimumOneClauses(writer, dimensionOfExtendedFamily, numberOfMafias);
    }
    
    public void maximumOneMafiaClauses(Writer writer) throws IOException {
        maximumOneClauses(writer, dimensionOfExtendedFamily, numberOfMafias);
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        File file = new File(oracleOutFilename);
        Scanner scanner = new Scanner(file);
        answer = scanner.nextBoolean();

        if (scanner.hasNext()) {
            scanner.nextInt();
        }
        
        getExtendedFamily(scanner);
        
        scanner.close();
    }
    
    private void getExtendedFamily(Scanner scanner) {
        getDecipheredResult(scanner, numberOfMafias);
    }
}
