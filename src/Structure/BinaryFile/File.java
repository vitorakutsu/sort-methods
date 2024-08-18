package Structure.BinaryFile;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class File {
    private String fileName;
    private RandomAccessFile file;
    private int comp, mov;

    private static final int MIN_TIM_SORT_GROUP = 16;

    public RandomAccessFile getFile() {
        return this.file;
    }

    public File(String fileName) {
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (IOException e) {
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void truncate(long position) // desloca eof
    {
        try {
            file.setLength(position * Register.length());
        } catch (IOException exc) {
        }
    }

    public void insertAtTheEnd(Register register) {
        seekArq(filesize());
        register.writeOnFile(file);
    }

    public void showFile() {
        int i;
        Register aux = new Register(1);
        seekArq(0);
        i = 0;
        while (!this.eof()) {
            System.out.print("Posicao " + i + "  ");
            aux.readFile(file);
            aux.showRegister();

            System.out.println();

            i++;
        }
    }

    public boolean eof() {
        boolean response = false;

        try {
            if (file.getFilePointer() == file.length())
                response = true;
        } catch (IOException e) {
        }
        return (response);
    }

    public void seekArq(int position) {
        try {
            file.seek(position * Register.length());
        } catch (IOException e) {
        }
    }

    public int filesize() {
        try {
            return (int) file.length() / Register.length();
        } catch (IOException e) {
            return 0;
        }
    }

    public void initComp() {
        this.comp = 0;
    }

    public void initMov() {
        this.mov = 0;
    }

    public int getComp() {
        return this.comp;
    }

    public int getMov() {
        return this.mov;
    }

    public void copyFile(RandomAccessFile originFile) throws IOException {
        originFile.seek(0);
        file.seek(0);

        int byteRead;
        while ((byteRead = originFile.read()) != -1) {
            file.write(byteRead);
        }
    }

    public void generateOrderedFile() {
        Register register;

        int counter = 1;

        while (counter != 1025) {

            register = new Register(counter);

            register.writeOnFile(file);

            counter++;
        }
    }

    public void generateReversedFile() {

        Register register;

        int counter = 1024;

        while (counter != 0) {

            register = new Register(counter);

            register.writeOnFile(file);

            counter--;
        }
    }

    public void generateRandomFile() {
        Register register;
        int counter = 0;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 1024; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        while (counter != 1024) {

            register = new Register(numbers.get(counter));
            register.writeOnFile(file);
            counter++;
        }
    }

    // MÉTODOS DE ORDENAÇÃO

    public int[] initializeCountingSortArray(int arraySize) {

        int[] array = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = 0;
        }

        return array;
    }

    public int[] buildCountingSortArray(int arraySize) {
        Register registerAux = new Register(0);
        seekArq(0);
        registerAux.readFile(file);
        int[] array = initializeCountingSortArray(arraySize);
        boolean stop = false;

        while (!stop) {
            array[registerAux.getNumber() - 1]++;

            if (eof()) {
                stop = true;
            }

            registerAux.readFile(file);
        }

        return array;
    }

    public int[] countCoutingSortArray(int[] array) {

        int[] arrayAux = array;

        for (int i = 1; i < arrayAux.length; i++) {
            arrayAux[i] = arrayAux[i] + arrayAux[i - 1];
        }

        return arrayAux;
    }

    public int getHighestRegisterValue() {
        Register registerAux = new Register(0);
        seekArq(0);
        boolean stop = false;
        registerAux.readFile(file);

        int highestValue = 0;

        while (!stop) {

            // * COMPARAÇÃO
            comp++;
            if (registerAux.getNumber() > highestValue)
                highestValue = registerAux.getNumber();

            if (eof()) {
                stop = true;
            }

            registerAux.readFile(file);
        }

        return highestValue;

    }

    public int binarySearchFile(int value, int size) {
        int start = 0;
        int end = size - 1;
        int mid = end / 2;
        Register registerAux = new Register(0);

        seekArq(mid);
        registerAux.readFile(file);

        comp++;
        while (start < end && value != registerAux.getNumber()) {
            comp++;
            if (value < registerAux.getNumber()) {
                end = end - 1;
            } else {
                start = start + 1;
            }

            mid = (start + end) / 2;

            seekArq(mid);
            registerAux.readFile(file);
        }

        seekArq(mid);
        registerAux.readFile(file);

        comp++;
        if (value > registerAux.getNumber()) {
            return mid + 1;
        }

        return mid;
    }

    public void insertionSortFile() {
        int index = 1, position, size = filesize();
        Register registerAux = new Register(0);
        Register register2 = new Register(1);

        while (index < size) {
            seekArq(index);
            registerAux.readFile(file);

            seekArq(index - 1);
            register2.readFile(file);

            position = index;

            comp++;
            while (registerAux.getNumber() < register2.getNumber() && position > 0) {

                seekArq(position);
                register2.writeOnFile(file);
                mov++;

                position--;

                seekArq(position - 1);
                register2.readFile(file);
                comp++;
            }

            seekArq(position);
            registerAux.writeOnFile(file);
            mov++;
            index++;
        }
    }

    public void bubbleSortFile() {

        Register register1 = new Register(1);
        Register register2 = new Register(2);

        int size = filesize();
        boolean change = true;

        while (size > 1 && change) {
            change = false;

            for (int i = 0; i < size - 1; i++) {
                seekArq(i);
                register1.readFile(file);

                register2.readFile(file);

                comp++;
                if (register1.getNumber() > register2.getNumber()) {
                    change = true;
                    seekArq(i);
                    register2.writeOnFile(file);
                    mov++;
                    register1.writeOnFile(file);
                    mov++;
                }
            }
            size--;
        }
    }

    public void selectionSortFile() {

        Register registerAux = new Register(0);
        Register register1 = new Register(1);
        Register register2 = new Register(2);

        int size = filesize(), smallerPosition;

        for (int i = 0; i < size - 1; i++) {
            seekArq(i);
            registerAux.readFile(file);

            smallerPosition = i;
            for (int j = i + 1; j < size; j++) {
                seekArq(j);
                register1.readFile(file);

                seekArq(smallerPosition);
                register2.readFile(file);

                comp++;
                if (register1.getNumber() < register2.getNumber()) {
                    smallerPosition = j;
                }
            }
            seekArq(i);
            register2.writeOnFile(file);
            mov++;

            seekArq(smallerPosition);
            registerAux.writeOnFile(file);
            mov++;
        }
    }

    public void binarySortFile() {
        int previousIndex = 1, currentIndex, valueAux, indexAux;
        Register previousRegister = new Register(0), previousRegisterAux = new Register(0), registerAux;

        seekArq(previousIndex);
        previousRegister.readFile(file);

        while (previousIndex < filesize()) {

            valueAux = previousRegister.getNumber();

            currentIndex = binarySearchFile(valueAux, previousIndex);

            indexAux = previousIndex;

            while (indexAux != currentIndex) {

                seekArq(indexAux - 1);
                previousRegisterAux.readFile(file);

                seekArq(indexAux);
                previousRegisterAux.writeOnFile(file);
                mov++;

                indexAux--;
            }

            seekArq(currentIndex);
            registerAux = new Register(valueAux);

            registerAux.writeOnFile(file);
            mov++;

            previousIndex++;

            seekArq(previousIndex);
            previousRegister.readFile(file);
        }

    }

    public void shakeSortFile() {

        Register startRegister = new Register(0);
        Register endRegister = new Register(1);

        Register startRegisterAux = new Register(0), startRegisterNextAux = new Register(0);
        Register endRegisterAux = new Register(0), endRegisterPrevAux = new Register(0);

        int startIndexAux, finalIndexAux;
        int startIndex = 0, finalIndex = filesize() - 1;
        boolean change = true;

        seekArq(startIndex);
        startRegister.readFile(file);

        seekArq(finalIndex);
        endRegister.readFile(file);

        while (startIndex != finalIndex && change) {
            change = false;

            startIndexAux = startIndex;

            seekArq(startIndexAux);
            startRegisterAux.readFile(file);

            seekArq(startIndexAux + 1);
            startRegisterNextAux.readFile(file);

            while (startIndexAux != finalIndex) {

                comp++;
                if (startRegisterAux.getNumber() > startRegisterNextAux.getNumber()) {
                    change = true;

                    seekArq(startIndexAux + 1);
                    startRegisterAux.writeOnFile(file);
                    mov++;

                    seekArq(startIndexAux);
                    startRegisterNextAux.writeOnFile(file);
                    mov++;
                }

                startIndexAux++;
                seekArq(startIndexAux);
                startRegisterAux.readFile(file);

                seekArq(startIndexAux + 1);
                startRegisterNextAux.readFile(file);
            }

            finalIndex--;

            if (change) {
                finalIndexAux = finalIndex;
                change = false;

                seekArq(finalIndex);
                endRegisterAux.readFile(file);

                seekArq(finalIndexAux - 1);
                endRegisterPrevAux.readFile(file);

                while (finalIndexAux != startIndex) {

                    comp++;
                    if (endRegisterAux.getNumber() < endRegisterPrevAux.getNumber()) {
                        change = true;

                        seekArq(finalIndexAux - 1);
                        endRegisterAux.writeOnFile(file);
                        mov++;

                        seekArq(finalIndexAux);
                        endRegisterPrevAux.writeOnFile(file);
                        mov++;
                    }

                    finalIndexAux--;
                    seekArq(finalIndexAux);
                    endRegisterAux.readFile(file);

                    seekArq(finalIndexAux - 1);
                    endRegisterPrevAux.readFile(file);
                }
                startIndex++;
            }
        }

    }

    public void heapSortFile() {

        Register leftRegister = new Register(0), rightRegister = new Register(0), parentRegister = new Register(0),
                bigger, startRegister = new Register(0), endRegister = new Register(0);
        int parentIndex = filesize() - 1, fileSize = filesize(), biggerIndex;

        while (fileSize != 1) {

            parentIndex = (fileSize / 2) - 1;
            seekArq(parentIndex);

            parentRegister.readFile(file);

            while (parentIndex >= 0) {

                seekArq((2 * parentIndex) + 1);
                leftRegister.readFile(file);

                seekArq((2 * parentIndex) + 2);
                rightRegister.readFile(file);

                if ((2 * parentIndex) + 2 < fileSize) {
                    comp++;
                    if (leftRegister.getNumber() > rightRegister.getNumber()) {
                        biggerIndex = 2 * parentIndex + 1;
                        bigger = leftRegister;
                    } else {
                        biggerIndex = 2 * parentIndex + 2;
                        bigger = rightRegister;
                    }
                } else {
                    biggerIndex = 2 * parentIndex + 1;
                    bigger = leftRegister;
                }

                comp++;
                if (bigger.getNumber() > parentRegister.getNumber()) {
                    seekArq(biggerIndex);
                    parentRegister.writeOnFile(file);
                    mov++;

                    seekArq(parentIndex);
                    bigger.writeOnFile(file);
                    mov++;
                }

                parentIndex--;

                seekArq(parentIndex);
                parentRegister.readFile(file);
            }
            seekArq(0);
            startRegister.readFile(file);

            seekArq(fileSize - 1);
            endRegister.readFile(file);

            seekArq(0);
            endRegister.writeOnFile(file);
            mov++;

            seekArq(fileSize - 1);
            startRegister.writeOnFile(file);
            mov++;

            fileSize--;
        }

    }

    public void countingSortFile() {

        Register startRegister = new Register(0);
        int[] arrayValues = new int[filesize()], countArray, sumArray;
        boolean stop = false;
        int k = 0, highestValue = getHighestRegisterValue(), index;

        seekArq(0);
        startRegister.readFile(file);

        while (!stop) {
            arrayValues[k] = startRegister.getNumber();

            if (eof()) {
                stop = true;
            }

            k++;
            startRegister.readFile(file);
        }

        countArray = buildCountingSortArray(highestValue);

        sumArray = countCoutingSortArray(countArray);

        for (int i = arrayValues.length - 1; i >= 0; i--) {
            index = sumArray[arrayValues[i] - 1] - 1;
            Register registerAux = new Register(arrayValues[i]);

            seekArq(index);
            registerAux.writeOnFile(file);
            mov++;

            sumArray[arrayValues[i] - 1] = sumArray[arrayValues[i] - 1] - 1;
        }
    }

    public void bucketSortFile() {
        File[] bucketFile = new File[10];
        Register registerAux = new Register(0);
        int[] arrayValues = new int[filesize()];
        int highestValue = getHighestRegisterValue();
        boolean stop = false;
        int k = 0;

        int limit = highestValue / 10;
        seekArq(0);
        registerAux.readFile(file);

        while (!stop) {
            arrayValues[k] = registerAux.getNumber();

            if (eof()) {
                stop = true;
            }

            k++;
            registerAux.readFile(file);
        }

        for (int i = 0; i < 10; i++) {

            bucketFile[i] = new File("./Files/bucket/bucket-" + i + "-file.dat");
            for (int j = 0; j < arrayValues.length; j++) {
                // * COMPARAÇÃO
                comp++;
                if (arrayValues[j] > limit - highestValue / 10
                        && arrayValues[j] <= limit) {
                    Register registerValue = new Register(arrayValues[j]);
                    registerValue.writeOnFile(bucketFile[i].getFile());
                    // ? MOVIMENTAÇÃO
                    mov++;
                }
            }

            limit = limit + highestValue / 10;

            bucketFile[i].bubbleSortFile();
        }

        k = 0;

        for (int i = 0; i < 10; i++) {

            for (int j = 0; j < bucketFile[i].filesize(); j++) {
                bucketFile[i].seekArq(j);
                registerAux.readFile(bucketFile[i].getFile());

                seekArq(k);
                registerAux.writeOnFile(file);
                // ? MOVIMENTAÇÃO
                mov++;
                k++;
            }
        }
    }

    public void combSortFile() {
        int gap = (int) (filesize() / 1.3), startIndex = 0, endIndex = gap;
        Register startRegister = new Register(startIndex), endRegister = new Register(endIndex);

        while (gap >= 1) {

            startIndex = 0;
            endIndex = gap;

            seekArq(startIndex);
            startRegister.readFile(file);

            seekArq(endIndex);
            endRegister.readFile(file);

            while (endIndex < filesize()) {

                comp++;
                if (startRegister.getNumber() > endRegister.getNumber()) {
                    seekArq(endIndex);
                    startRegister.writeOnFile(file);
                    mov++;

                    seekArq(startIndex);
                    endRegister.writeOnFile(file);
                    mov++;
                }

                startIndex++;
                endIndex++;

                seekArq(startIndex);
                startRegister.readFile(file);

                seekArq(endIndex);
                endRegister.readFile(file);
            }

            gap = (int) (gap / 1.3);
        }
    }

    public void gnomeSortFile() {
        int previousIndex = 0, currentIndex = 1, previousIndexMarked, currentIndexMarked;
        boolean check = false;
        Register previousRegister = new Register(previousIndex), currentRegister = new Register(currentIndex);

        seekArq(previousIndex);
        previousRegister.readFile(file);

        seekArq(currentIndex);
        currentRegister.readFile(file);

        while (currentIndex < filesize()) {

            comp++;
            if (previousRegister.getNumber() > currentRegister.getNumber()) {

                seekArq(currentIndex);
                previousRegister.writeOnFile(file);
                mov++;

                seekArq(previousIndex);
                currentRegister.writeOnFile(file);
                mov++;

                check = true;
            }

            previousIndexMarked = previousIndex;
            currentIndexMarked = currentIndex;

            while (check) {

                if (previousIndexMarked != 0) {

                    currentIndexMarked = previousIndexMarked;
                    previousIndexMarked--;

                    seekArq(previousIndexMarked);
                    previousRegister.readFile(file);

                    seekArq(currentIndexMarked);
                    currentRegister.readFile(file);

                    comp++;
                    if (previousRegister.getNumber() > currentRegister.getNumber()) {
                        seekArq(currentIndexMarked);
                        previousRegister.writeOnFile(file);
                        mov++;

                        seekArq(previousIndexMarked);
                        currentRegister.writeOnFile(file);
                        mov++;

                    } else {
                        check = false;

                        previousIndex = previousIndexMarked;
                        currentIndex = currentIndexMarked;
                    }
                } else {
                    check = false;

                    previousIndex = previousIndexMarked;
                    currentIndex = currentIndexMarked;
                }
            }

            previousIndex = currentIndex;
            currentIndex++;

            seekArq(previousIndex);
            previousRegister.readFile(file);

            seekArq(currentIndex);
            currentRegister.readFile(file);
        }
    }

    public void shellSortFile() {
        int gap = 1, valueAux, index;

        Register registerAux = new Register(0), registerIndex = new Register(0);

        while (gap < filesize()) {
            gap = (3 * gap) + 1;
        }

        gap = gap / 3;

        while (gap > 0) {

            for (int i = gap; i < filesize(); i++) {

                seekArq(i);
                registerAux.readFile(file);

                valueAux = registerAux.getNumber();

                index = i;

                seekArq(index - gap);
                registerIndex.readFile(file);

                comp++;
                while ((index - gap) >= 0 && valueAux < registerIndex.getNumber()) {

                    seekArq(index);
                    registerIndex.writeOnFile(file);
                    mov++;

                    index -= gap;

                    seekArq(index - gap);
                    registerIndex.readFile(file);

                    comp++;
                }

                seekArq(index);
                Register registerValue = new Register(valueAux);

                registerValue.writeOnFile(file);
                mov++;
            }

            gap = gap / 3;
        }
    }

    public void applyCountingSortToRadix(int exp) {
        Register startRegister = new Register(0);
        int index = 0, valueAux, k = 0;
        int[] numbersAux = new int[filesize()], numbers = new int[filesize()];
        boolean stop = false;

        seekArq(0);
        startRegister.readFile(file);

        while (!stop) {
            numbers[k] = startRegister.getNumber();

            if (eof()) {
                stop = true;
            }

            k++;
            startRegister.readFile(file);
        }

        index = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numbers.length; j++) {
                valueAux = numbers[j];

                comp++;
                if (i == (valueAux / exp) % 10) {
                    numbersAux[index] = numbers[j];

                    index++;
                }
            }
        }

        for (int i = 0; i < numbersAux.length; i++) {

            seekArq(i);
            Register registerValue = new Register(numbersAux[i]);
            registerValue.writeOnFile(file);
            mov++;
        }
    }

    public void radixSortFile() {
        int maxNumber = getHighestRegisterValue();

        for (int i = 1; maxNumber / i > 0; i *= 10) {
            applyCountingSortToRadix(i);
        }
    }

    public void quickSortWithoutPivotFile() {
        quickSortWOPivot(0, filesize() - 1);
    }

    public void quickSortWOPivot(int start, int end) {

        int startIndex = start, endIndex = end;
        Register startRegister = new Register(startIndex), endRegister = new Register(endIndex);
        boolean flag = true;

        while (startIndex < endIndex) {

            seekArq(startIndex);
            startRegister.readFile(file);

            seekArq(endIndex);
            endRegister.readFile(file);

            if (flag) {
                comp++;
                while (startIndex < endIndex && startRegister.getNumber() <= endRegister.getNumber()) {
                    startIndex++;
                    seekArq(startIndex);
                    startRegister.readFile(file);

                    comp++;
                }
            } else {
                comp++;
                while (startIndex < endIndex && startRegister.getNumber() <= endRegister.getNumber()) {
                    endIndex--;
                    seekArq(endIndex);
                    endRegister.readFile(file);

                    comp++;
                }
            }

            seekArq(endIndex);
            startRegister.writeOnFile(file);
            mov++;

            seekArq(startIndex);
            endRegister.writeOnFile(file);
            mov++;

            flag = !flag;
        }

        if (start < startIndex - 1) {
            quickSortWOPivot(start, startIndex - 1);
        }

        if (endIndex + 1 < end) {
            quickSortWOPivot(endIndex + 1, end);
        }

    }

    public void quickSortWithPivotFile() {
        quickSortWPivot(0, filesize() - 1);
    }

    public void quickSortWPivot(int start, int end) {
        int startIndex = start, endIndex = end;
        Register registerPivot = new Register(0), startRegister = new Register(0), endRegister = new Register(0);
        seekArq((start + end) / 2);
        registerPivot.readFile(file);

        int pivot = registerPivot.getNumber();

        seekArq(startIndex);
        startRegister.readFile(file);

        seekArq(endIndex);
        endRegister.readFile(file);

        while (startIndex < endIndex) {

            seekArq(startIndex);
            startRegister.readFile(file);

            seekArq(endIndex);
            endRegister.readFile(file);

            comp++;
            while (startRegister.getNumber() < pivot) {
                startIndex++;
                seekArq(startIndex);
                startRegister.readFile(file);

                comp++;
            }

            comp++;
            while (endRegister.getNumber() > pivot) {
                endIndex--;
                seekArq(endIndex);
                endRegister.readFile(file);

                comp++;
            }

            if (startIndex <= endIndex) {

                seekArq(startIndex);
                endRegister.writeOnFile(file);
                mov++;

                seekArq(endIndex);
                startRegister.writeOnFile(file);
                mov++;

                startIndex++;
                endIndex--;
            }
        }

        if (start < endIndex) {
            quickSortWPivot(start, endIndex);
        }

        if (end > startIndex) {
            quickSortWPivot(startIndex, end);
        }

    }

    public void mergeSortFileFirstImplement() {
        try {
            int sequence = 1;
            File firstFile = new File("./Files/merge-sort/first-implement/first-file.dat");
            File secondFile = new File("./Files/merge-sort/first-implement/second-file.dat");

            while (sequence < filesize()) {
                firstFile.truncate(0);
                secondFile.truncate(0);

                partitionOnFirstMerge(firstFile, secondFile);

                fusionOnFirstMerge(firstFile, secondFile, sequence);

                sequence = sequence * 2;
            }
        } catch (Exception e) {
        }
    }

    public void partitionOnFirstMerge(File firstFile, File secondFile) {
        Register register = new Register(0);

        int size = filesize() / 2;

        for (int i = 0; i < size; i++) {
            seekArq(i);
            register.readFile(file);

            register.writeOnFile(firstFile.getFile());
            mov++;

            seekArq(i + size);
            register.readFile(file);

            register.writeOnFile(secondFile.getFile());
            mov++;
        }
    }

    public void fusionOnFirstMerge(File firstFile, File secondFile, int sequence) {
        int sequenceAux = sequence, i = 0, j = 0, k = 0;

        Register firstRegister = new Register(0), secondRegister = new Register(0);

        while (k < filesize()) {

            while (i < sequence && j < sequence) {
                firstFile.seekArq(i);
                firstRegister.readFile(firstFile.getFile());

                secondFile.seekArq(j);
                secondRegister.readFile(secondFile.getFile());

                comp++;
                if (firstRegister.getNumber() < secondRegister.getNumber()) {
                    seekArq(k);
                    firstRegister.writeOnFile(file);
                    mov++;

                    k++;
                    i++;
                } else {
                    seekArq(k);
                    secondRegister.writeOnFile(file);
                    mov++;

                    k++;
                    j++;
                }
            }

            while (i < sequence) {
                firstFile.seekArq(i);
                firstRegister.readFile(firstFile.getFile());

                seekArq(k);
                firstRegister.writeOnFile(file);
                mov++;

                k++;
                i++;

            }

            while (j < sequence) {
                secondFile.seekArq(j);
                secondRegister.readFile(secondFile.getFile());

                seekArq(k);
                secondRegister.writeOnFile(file);
                mov++;

                k++;
                j++;
            }

            sequence += sequenceAux;
        }

    }

    public void mergeSortFileSecondImplement() {
        File fileAux = new File("./Files/merge-sort/second-implement/file-second-implement.dat");

        mergeSecondImplement(fileAux, 0, filesize() - 1);
    }

    public void mergeSecondImplement(File fileAux, int left, int right) {
        int mid;

        if (left < right) {

            mid = (left + right) / 2;

            mergeSecondImplement(fileAux, left, mid);
            mergeSecondImplement(fileAux, mid + 1, right);

            fileAux.truncate(0);
            fusionOnSecondMerge(fileAux, left, mid, mid + 1, right);
        }
    }

    public void fusionOnSecondMerge(File fileAux, int start1, int end1, int start2, int end2) {
        Register firstRegister = new Register(0), secondRegister = new Register(0), auxRegister = new Register(0);
        int i = start1;
        int j = start2;
        int k = 0;

        while (i <= end1 && j <= end2) {
            seekArq(i);
            firstRegister.readFile(file);

            seekArq(j);
            secondRegister.readFile(file);

            comp++;
            if (firstRegister.getNumber() < secondRegister.getNumber()) {
                fileAux.seekArq(k);
                firstRegister.writeOnFile(fileAux.getFile());
                mov++;
                i++;
            } else {
                fileAux.seekArq(k);
                secondRegister.writeOnFile(fileAux.getFile());
                mov++;
                j++;
            }
            k++;
        }

        while (i <= end1) {
            seekArq(i);
            firstRegister.readFile(file);

            fileAux.seekArq(k);
            firstRegister.writeOnFile(fileAux.getFile());
            mov++;

            k++;
            i++;
        }

        while (j <= end2) {
            seekArq(j);
            secondRegister.readFile(file);

            fileAux.seekArq(k);
            secondRegister.writeOnFile(fileAux.getFile());
            mov++;

            k++;
            j++;
        }

        for (int w = 0; w < k; w++) {
            fileAux.seekArq(w);
            auxRegister.readFile(fileAux.getFile());

            seekArq(w + start1);
            auxRegister.writeOnFile(file);
            mov++;
        }
    }

    private void insertionSortToTimSort(int left, int right) {
        for (int i = left; i < right; i++) {
            Register firstRegister = new Register(0), secondRegister = new Register(0);
            int j = i - 1;
            seekArq(i);
            firstRegister.readFile(file);

            seekArq(j);
            secondRegister.readFile(file);

            comp++;
            while (j >= left && secondRegister.getNumber() > firstRegister.getNumber()) {
                seekArq(j + 1);
                secondRegister.writeOnFile(file);
                mov++;

                j--;
                seekArq(j);
                secondRegister.readFile(file);

                comp++;
            }

            seekArq(j + 1);
            firstRegister.writeOnFile(file);
            mov++;
        }
    }

    private int calcMinRun() {
        return filesize() / MIN_TIM_SORT_GROUP;
    }

    public void timSortFile() {
        File file = new File("./Files/tim/tim-file.dat");
        int MIN_RUN_SIZE = calcMinRun();

        for (int i = 0; i < filesize(); i += MIN_RUN_SIZE) {
            int end = Math.min(i + MIN_RUN_SIZE - 1, filesize() - 1);
            insertionSortToTimSort(i, end);
        }

        mergeSecondImplement(file, 0, filesize() - 1);

        // for (int runSize = MIN_RUN_SIZE; runSize < filesize(); runSize += 2) {
        // for (int left = 0; left < filesize(); left += 2 * runSize) {
        // int mid = left + runSize - 1;
        // int right = Math.min((left + 2 * runSize - 1), (filesize() - 1));
        // if (mid < right) {
        // mergeSecondImplement(file, left, right);
        // }
        // }
        // }
    }
}
