import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import Structure.BinaryFile.File;
import Structure.BinaryFile.Register;
import Structure.LinkedList.List;
import Structure.LinkedList.Node;

public class Main {

    private static final int NUMBER_OF_REGISTER = 16;

    public static void listSort() {
        int counter = 20;
        List list = new List();
        Node node;

        while (counter != 0) {

            node = new Node((int) (Math.random() * 25) + 1);

            list.insertAtTheEnd(node);

            counter--;
        }

        list.showList();

        list.timSort();

        list.showList();
    }

    public static void fileSort() {
        File file = new File("./Files/random.dat");

        file.generateRandomFile();

        file.showFile();

        System.out.println();
        System.out.println();

        file.timSortFile();

        // System.out.println();
        System.out.println();
        System.out.println();

        file.showFile();
    }

    public static void initializeCreateTable() {
        try {
            createTable("sort-methods.table.txt");
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) throws IOException {
         initializeCreateTable();
    }

    @FunctionalInterface
    interface SortingAlgorithm {
        void sort(File file);
    }

    @FunctionalInterface
    interface GenerateFile {
        void generateFile(File file);
    }

    public static SortCounters sortFile(File file, int index, SortingAlgorithm sortingAlgorithms) {
        int comp = 0, mov = 0, time = 0;

        try {
            if (file.getFile() != null) {

                file.initComp();
                file.initMov();

                long initialTime = System.currentTimeMillis();

                sortingAlgorithms.sort(file);
                long finalTime = System.currentTimeMillis();

                comp = file.getComp();
                mov = file.getMov();
                time = (int) (finalTime - initialTime);
            } else {
                System.out.println("Erro ao abrir o arquivo.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SortCounters(comp, mov, time);
    }

    private static int calculateComplexity(String method, boolean compare, int number) {

        int n = NUMBER_OF_REGISTER;
        if (method.equalsIgnoreCase("Inserção Direta")) {
            if (compare) {
                if (number == 0) {
                    return n - 1;
                } else if (number == 1) {
                    return ((n * n) + n - 2) / 4;
                } else if (number == 2) {
                    return ((n * n) + n - 4) / 4;
                }
            } else {
                if (number == 0) {
                    return 3 * (n - 1);
                } else if (number == 1) {
                    return ((n * n) + (9 * n) - 10) / 4;
                } else if (number == 2) {
                    return ((n * n) + (3 * n) - 4) / 2;
                }
            }
        } else if (method.equalsIgnoreCase("Inserção Binária")) {
            if (compare) {
                return (int) (n * (Math.log(n) - Math.log(Math.E) + 0.5));
            } else {
                if (number == 0) {
                    return 3 * (n - 1);
                } else if (number == 1) {
                    return ((n * n) + (9 * n) - 10) / 4;
                } else if (number == 2) {
                    return ((n * n) + (3 * n) - 4) / 2;
                }
            }
        } else if (method.equalsIgnoreCase("Seleção Direta")) {
            if (compare) {
                return ((n * n) - n) / 2;
            } else {
                if (number == 0) {
                    return 3 * (n - 1);
                } else if (number == 1) {
                    return (int) (n * (Math.log(n) + 0.577216));

                } else if (number == 2) {
                    return (((n * n) / 4) + 3 * (n - 1));
                }
            }
        } else if (method.equalsIgnoreCase("Bolha") || method.equalsIgnoreCase("Shake")) {
            if (compare) {
                return ((n * n) - n) / 2;
            } else {
                if (number == 0) {
                    return 0;
                } else if (number == 1) {
                    return (3 * ((n * n) - 2) / 2);
                } else if (number == 2) {
                    return (3 * ((n * n) - 2) / 4);
                }
            }
        }
        return 0;
    }

    public static void createTable(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(String.format("%-20s | %-77s | %-77s | %-77s", "Métodos", "Arquivo Ordenado",
                    "Arquivo Reverso", "Arquivo Randômico"));
            writer.newLine();

            writer.write(String.format("%-20s | ", ""));

            writer.write(
                    String.format("%-13s | %-13s | %-13s | %-13s | %-13s | ", "Comp. Prog.", "Comp. Equa.",
                            "Mov. Prog.",
                            "Mov. Equa.", "Tempo"));
            writer.write(
                    String.format("%-13s | %-13s | %-13s | %-13s | %-13s | ", "Comp. Prog.", "Comp. Equa.",
                            "Mov. Prog.",
                            "Mov. Equa.", "Tempo"));
            writer.write(
                    String.format("%-13s | %-13s | %-13s | %-13s | %-13s | ", "Comp. Prog.", "Comp. Equa.",
                            "Mov. Prog.",
                            "Mov. Equa.", "Tempo"));
            writer.newLine();

            writer.write(
                    "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            writer.newLine();

            File orderedFile = new File("./Files/ordered.dat");
            File reversedFile = new File("./Files/reversed.dat");
            File randomFile = new File("./Files/random.dat");

            File auxOrderedFile = new File("./Files/aux-ordered.dat");
            File auxReversedFile = new File("./Files/aux-reversed.dat");
            File auxRandomFile = new File("./Files/aux-random.dat");

            SortingAlgorithm[] sortingAlgorithms = new SortingAlgorithm[] {
                    File::insertionSortFile,
                    File::binarySortFile,
                    File::selectionSortFile,
                    File::bubbleSortFile,
                    File::shakeSortFile,
                    File::shellSortFile,
                    File::heapSortFile,
                    File::quickSortWithoutPivotFile,
                    File::quickSortWithPivotFile,
                    File::mergeSortFileFirstImplement,
                    File::mergeSortFileSecondImplement,
                    File::countingSortFile,
                    File::bucketSortFile,
                    File::radixSortFile,
                    File::combSortFile,
                    File::gnomeSortFile,
                    File::timSortFile
            };

            String[] methods = {
                    "Inserção Direta", "Inserção Binária", "Seleção Direta", "Bolha",
                    "Shake", "Shell", "Heap", "Quick s/ pivô", "Quick c/ pivô",
                    "Merge 1ª Implement", "Merge 2ª Implement", "Counting",
                    "Bucket", "Radix", "Comb", "Gnome", "Tim"
            };

            orderedFile.generateOrderedFile();
            reversedFile.generateReversedFile();
            randomFile.generateRandomFile();

            for (int i = 0; i < methods.length; i++) {
                auxOrderedFile.copyFile(orderedFile.getFile());
                auxReversedFile.copyFile(reversedFile.getFile());
                auxRandomFile.copyFile(randomFile.getFile());

                SortCounters orderedCounters = sortFile(auxOrderedFile, 0, sortingAlgorithms[i]);
                SortCounters reversedCounters = sortFile(auxReversedFile, 1, sortingAlgorithms[i]);
                SortCounters randomCounters = sortFile(auxRandomFile, 2, sortingAlgorithms[i]);

                System.out.println(methods[i]);

                if (i < 5) {
                    writer.write(String.format(
                            "%-20s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |",
                            methods[i],
                            orderedCounters.getComp(), calculateComplexity(methods[i], true, 0),
                            orderedCounters.getMov(),
                            calculateComplexity(methods[i], false, 0), orderedCounters.getTime(),
                            reversedCounters.getComp(), calculateComplexity(methods[i], true, 2),
                            reversedCounters.getMov(),
                            calculateComplexity(methods[i], false, 2), reversedCounters.getTime(),
                            randomCounters.getComp(), calculateComplexity(methods[i], true, 1), randomCounters.getMov(),
                            calculateComplexity(methods[i], false, 1), randomCounters.getTime()));
                } else {
                    writer.write(String.format(
                            "%-20s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |",
                            methods[i],
                            orderedCounters.getComp(), "X",
                            orderedCounters.getMov(),
                            "X", orderedCounters.getTime(),
                            reversedCounters.getComp(), "X",
                            reversedCounters.getMov(),
                            "X", reversedCounters.getTime(),
                            randomCounters.getComp(), "X", randomCounters.getMov(),
                            "X", randomCounters.getTime()));
                }
                writer.newLine();
            }
        }
    }

    public static String getRandomData() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100));
    }

}