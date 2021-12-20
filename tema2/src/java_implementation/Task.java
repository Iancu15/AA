// Copyright 2020
// Author: Matei Simtinică

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the abstract base class for all tasks that have to be implemented.
 */
public abstract class Task {
    String inFilename;
    String oracleInFilename;
    String oracleOutFilename;
    String outFilename;
    protected Integer numberOfMafias;
    protected Integer numberOfRelations;
    protected List<Relation> goodRelations;
    protected Boolean answer;
    protected List<Integer> deciphratedResult;

    /**
     * Adauga in lista goodRelations relatiile de intelegere dintre mafioti
     * @param scanner   scanner-ul aferent fisierului de intrare
     */
    public void getRelations(Scanner scanner) {
        goodRelations = new ArrayList<>();
        while (scanner.hasNextInt()) {
            int origin = scanner.nextInt();
            int destination = scanner.nextInt();
            goodRelations.add(new Relation(origin, destination));
        }
    }

    /**
     * Se uita care sunt relatiile din fisiere si construieste o lista cu restul de posibilitati
     * @return      lista cu non-relatiile
     */
    public List<Relation> generateBadRelations() {
        List<Relation> badRelations = new ArrayList<>();
        // la intrare relatiile sunt doar intr-o directie si crescatoare astfel incat destinatia
        // sa fie mai mare decat originea, la fel imi fac si non-relatiile
        for (int origin = 1; origin < numberOfMafias; origin++) {
            for (int destination = origin + 1; destination <= numberOfMafias; destination++) {
                if (!containsRelation(origin, destination)) {
                    badRelations.add(new Relation(origin, destination));
                }
            }
        }
        
        return badRelations;
    }

    /**
     * Citeste numarul de mafioti si de relatii din fisier-ul aferent scannerului
     * @param scanner   scannerul aferent fisierului de intrare
     */
    public void getNumbers(Scanner scanner) {
        numberOfMafias = scanner.nextInt();
        numberOfRelations = scanner.nextInt();
    }

    /**
     * Calculeaza combinari de numberOfObjects luate cate 2
     * @param numberOfObjects   Numarul de elemente dupa care se fac combinari cate 2
     * @return
     */
    public int combinationsOf2(Integer numberOfObjects) {
        return ((numberOfObjects - 1) * numberOfObjects)/2;
    }

    /**
     * Cauta relatia primita ca parametru si intoarce adevarat daca a gasit-o si respectiv fals
     * in caz contrar
     * @param origin        originea relatiei
     * @param destination   destinatia relatiei
     * @return              valoarea de adevar a cautarii
     */
    public boolean containsRelation(Integer origin, Integer destination) {
        for (Relation relation : goodRelations) {
            if (relation.getOrigin().equals(origin) && relation.getDestination().equals(destination)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Creeaza clauzele care au grija ca exista cel putin un obiect propertyTwo asignat tuturor
     * obiectelor de tipul propertyOne.
     * @param writer            obiectul writer ce contine fisierul de iesire
     * @param propertyOne       numarul de obiecte de tipul propertyOne
     * @param propertyTwo       numarul de obiecte de tipul propertyTwo
     * @throws IOException      in cazul erorilor de la apelul lui writer.append()
     */
    public void minimumOneClauses(Writer writer, int propertyOne, int propertyTwo) throws IOException {
        for (int counterOne = 1; counterOne <= propertyOne; counterOne++) {
            for (int counterTwo = 1; counterTwo <= propertyTwo; counterTwo++) {
                Integer variable = propertyTwo * (counterOne - 1) + counterTwo;
                writer.append(variable.toString() + " ");
            }

            writer.append("0\n");
        }
    }

    /**
     * Creeaza clauzele care au grija ca exista maxim un obiect de tipul propertyTwo asignat
     * obiectelor de tipul propertyOne
     * @param writer            obiectul writer ce contine fisierul de iesire
     * @param propertyOne       numarul de obiecte de tipul propertyOne
     * @param propertyTwo       numarul de obiecte de tipul propertyTwo
     * @throws IOException      in cazul erorilor de la apelul lui writer.append()
     */
    public void maximumOneClauses(Writer writer, int propertyOne, int propertyTwo) throws IOException {
        for (int counterOne = 1; counterOne <= propertyOne; counterOne++) {
            for (int iCounterTwo = 1; iCounterTwo < propertyTwo; iCounterTwo++) {
                for (int jCounterTwo = iCounterTwo + 1; jCounterTwo <= propertyTwo; jCounterTwo++) {
                    Integer firstVariable = -(propertyTwo * (counterOne - 1) + iCounterTwo);
                    Integer secondVariable = -(propertyTwo * (counterOne- 1) + jCounterTwo);
                    writer.append(firstVariable.toString() + " " + secondVariable.toString() + " 0\n");
                }
            }
        }
    }

    /**
     * Metoda se uita in fisierul .sol si interpreteaza semnul variabilelor
     * @param scanner       scanner-ul aferent fisierului de intrare
     * @param property      numarul de obiecte asignate
     */
    public void getDecipheredResult(Scanner scanner, Integer property) {
        deciphratedResult = new ArrayList<>();
        while(scanner.hasNextInt()) {
            // pentru fiecare obiect se uita dupa obiectul asignat
            for (int counter = 1; counter <= property; counter++) {
                // daca s-a gasit o variabila pozitiva(adica se intampla acel lucru), o salvez
                // in lista
                if(scanner.nextInt() > 0) {
                    deciphratedResult.add(counter);
                }
            }
        }
    }

    public abstract void solve() throws IOException, InterruptedException;

    public abstract void readProblemData() throws IOException;

    public void formulateOracleQuestion() throws IOException {}

    public void decipherOracleAnswer() throws IOException {}

    /**
     * Se foloseste de lista descifrata pentru a afisa la iesire
     * @throws IOException  In cazul in care a fost o eroare la scriere
     */
    public void writeAnswer() throws IOException {
        File file = new File(outFilename);
        Writer writer = new FileWriter(file);
        
        // Java are "true", python are "True", asa ca pentru a ma conforma cu ref-urile a trebuit
        // sa fac uppercase la "t"
        writer.append(answer.toString().substring(0, 1).toUpperCase() + answer.toString().substring(1)  + "\n");
        for (Integer assignedElement : deciphratedResult) {
            writer.append(assignedElement.toString() + " ");
        }
        
        writer.close();
    }

    /**
     * Stores the files paths as class attributes.
     *
     * @param inFilename         the file containing the problem input
     * @param oracleInFilename   the file containing the oracle input
     * @param oracleOutFilename  the file containing the oracle output
     * @param outFilename        the file containing the problem output
     */
    public void addFiles(String inFilename, String oracleInFilename, String oracleOutFilename, String outFilename) {
        this.inFilename = inFilename;
        this.oracleInFilename = oracleInFilename;
        this.oracleOutFilename = oracleOutFilename;
        this.outFilename = outFilename;
    }

    /**
     * Asks the oracle for an answer to the formulated question.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void askOracle() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.redirectErrorStream(true);
        builder.command("python3", "sat_oracle.py", oracleInFilename, oracleOutFilename);
        Process process = builder.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String buffer;
        StringBuilder output = new StringBuilder();

        while ((buffer = in.readLine()) != null) {
            output.append(buffer).append("\n");
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Error encountered while running oracle");
            System.err.println(output.toString());
            System.exit(-1);
        }
    }
}
