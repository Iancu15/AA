 // Copyright 2020
// Author: Matei Simtinică

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Task3
 * This being an optimization problem, the solve method's logic has to work differently.
 * You have to search for the minimum number of arrests by successively querying the oracle.
 * Hint: it might be easier to reduce the current task to a previously solved task
 */
public class Task3 extends Task {
    String task2InFilename;
    String task2OutFilename;
    private List<Relation> badRelations;
    private Integer dimensionOfExtendedFamily;

    @Override
    public void solve() throws IOException, InterruptedException {
        task2InFilename = inFilename + "_t2";
        task2OutFilename = outFilename + "_t2";
        Task2 task2Solver = new Task2();
        task2Solver.addFiles(task2InFilename, oracleInFilename, oracleOutFilename, task2OutFilename);
        readProblemData();

        // generez non-relatiile si apoi incerc toate numerele posibile de arestari de la cea
        // mai mica(1) la cea mai mare(numerOfMafias) pana imi returneaza clica o solutie
        badRelations = generateBadRelations();
        for (int size = 1; size <= numberOfMafias; size++) {
            dimensionOfExtendedFamily = numberOfMafias - size;
            reduceToTask2(task2Solver);
            task2Solver.solve();

            if (task2Solver.answer.equals(true)) {
                break;
            }
        }

        extractAnswerFromTask2();
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

    /**
     * Transforma intrarea pentru task-ul 2 si o scrie in task2InFilename
     * @param task2                         instanta de task 2 folosita
     * @throws IOException                  eroare la scriere
     * @throws InterruptedException
     */
    public void reduceToTask2(Task task2) throws IOException, InterruptedException {
        File file = new File(task2InFilename);
        Writer writer = new FileWriter(file);
        Integer numberOfRelationsTask2 = badRelations.size();

        // creez intrarea punand pe prima linie numarul de mafioti, numarul de relatii(in situatia
        // asta non-relatii) si dimensiunea familiei extinse calculata
        // si apoi pe liniile urmatoare adaug relatiile(non-relatiile task-ului 3)
        writer.append(numberOfMafias.toString() + " " + numberOfRelationsTask2 + " ");
        writer.append(dimensionOfExtendedFamily.toString() + "\n");
        for (Relation relation : badRelations) {
            writer.append(relation.getOrigin().toString() + " ");
            writer.append(relation.getDestination().toString() + "\n");
        }

        writer.close();
    }

    /**
     * 
     * @throws FileNotFoundException    Eroare in caz ca nu am reusit sa creez fisierul de intrare
     */
    public void extractAnswerFromTask2() throws FileNotFoundException {
        File file = new File(task2OutFilename);
        Scanner scanner = new Scanner(file);
        // nu avem nevoie de valoare de adevar pentru iesirea task-ului 3
        scanner.nextBoolean();

        // citesc de la solutia task-ului 2 mafiotii care nu vor fi arestati
        List<Integer> safeMafias = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            safeMafias.add(scanner.nextInt());
        }

        deciphratedResult = new ArrayList<Integer>();
        // trec prin toti mafiotii si verific daca se afla in lista safeMafias
        for (int mafiaCounter = 1; mafiaCounter <= numberOfMafias; mafiaCounter++) {
            int isDangerous = 1;
            for (Integer safeMafia : safeMafias) {
                // daca se afla inseamna ca nu trebuie arestat si resetez isDangerous
                if (safeMafia.equals(mafiaCounter)) {
                    isDangerous = 0;
                    break;
                }
            }

            // daca s-a iesit din for si nu s-a gasit in lista celor care nu trebuie arestati,
            // inseamna ca trebuie arestat si il adaug in lista cu iesirea descifrata
            if (isDangerous == 1) {
                deciphratedResult.add(mafiaCounter);
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
