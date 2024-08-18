package Structure.LinkedList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class List {

    private static final int MIN_TIM_SORT_GROUP = 5;

    private Node start;
    private Node end;

    public List() {
        this.start = null;
        this.end = null;
    }

    public void insertAtTheEnd(Node node) {

        if (start == null) {
            start = end = node;
        } else {
            end.setNext(node);
            node.setPrev(end);

            end = node;
        }
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public void showList() {
        Node node = start;

        while (node != null) {
            System.out.print(node.getInfo() + " ");

            node = node.getNext();
        }

        System.out.println();
    }

    public int getNodeValue(int index) {
        Node current = start;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getInfo();
    }

    public Node getNodeByIndex(int index) {
        Node current = this.start;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current;
    }

    public int getIndexByNode(Node node) {
        Node current = start;
        int index = 0;

        while (current != node) {
            index++;
            current = current.getNext();
        }

        return index;
    }

    public int[] initializeCountingSortArray(int arraySize) {

        int[] array = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = 0;
        }

        return array;
    }

    public int[] buildCountingSortArray(int arraySize, Node nodeStart) {
        Node nodeAux = nodeStart;
        int[] array = initializeCountingSortArray(arraySize);

        while (nodeAux != null) {
            array[nodeAux.getInfo() - 1]++;

            nodeAux = nodeAux.getNext();
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

    public Node getHighestNode() {
        Node nodeAux = start, highestNode = start;

        while (nodeAux.getNext() != null) {
            if (nodeAux.getInfo() > highestNode.getInfo()) {
                highestNode = nodeAux;
            }

            nodeAux = nodeAux.getNext();
        }

        return highestNode;
    }

    public int listSize() {
        Node nodeAux = start;
        int size = 0;

        while (nodeAux != null) {
            size++;
            nodeAux = nodeAux.getNext();
        }

        return size;
    }

    public Node binarySearch(int key, int TL) {
        int start = 0;
        int end = TL - 1;
        int mid = end / 2;

        int value = getNodeValue(mid);

        while (start < end && key != value) {
            if (key < value) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }

            mid = (start + end) / 2;

            value = getNodeValue(mid);
        }

        value = getNodeValue(mid);

        if (key > value) {
            return getNodeByIndex(mid + 1);
        }

        return getNodeByIndex(mid);
    }

    public void insertionSort() {
        int auxValue;
        Node index = start.getNext(), nodeAux;

        while (index != null) {
            auxValue = index.getInfo();
            nodeAux = index;

            while (nodeAux.getPrev() != null && auxValue < nodeAux.getPrev().getInfo()) {
                nodeAux.setInfo(nodeAux.getPrev().getInfo());
                nodeAux = nodeAux.getPrev();
            }

            nodeAux.setInfo(auxValue);
            index = index.getNext();
        }
    }

    public void insertionSortPreSet(Node nodeStart) {
        int auxValue;
        Node index = nodeStart.getNext(), nodeAux;

        while (index != null) {
            auxValue = index.getInfo();
            nodeAux = index;

            while (nodeAux.getPrev() != null && auxValue < nodeAux.getPrev().getInfo()) {
                nodeAux.setInfo(nodeAux.getPrev().getInfo());
                nodeAux = nodeAux.getPrev();
            }

            nodeAux.setInfo(auxValue);
            index = index.getNext();
        }
    }

    public void binarySort() {
        Node i = start.getNext(), j, current;
        int nodeAuxValue;

        while (i != null) {
            nodeAuxValue = i.getInfo();

            current = binarySearch(nodeAuxValue, getIndexByNode(i));

            j = i;

            while (j != current) {

                j.setInfo(j.getPrev().getInfo());

                j = j.getPrev();
            }

            current.setInfo(nodeAuxValue);
            i = i.getNext();
        }
    }

    public void selectionSort() {
        Node smallerNode, i = start, j;
        int nodeAux;

        while (i.getNext() != null) {
            smallerNode = i;

            j = i.getNext();

            while (j != null) {
                if (j.getInfo() < smallerNode.getInfo()) {
                    smallerNode = j;
                }

                j = j.getNext();
            }

            nodeAux = smallerNode.getInfo();
            smallerNode.setInfo(i.getInfo());
            i.setInfo(nodeAux);

            i = i.getNext();
        }
    }

    public void bubbleSort() {
        Node nodeAux, current = end;
        int aux;
        boolean change = true;

        while (current != start && change) {
            change = false;
            nodeAux = start;
            while (nodeAux.getNext() != null) {
                if (nodeAux.getInfo() > nodeAux.getNext().getInfo()) {
                    change = true;
                    aux = nodeAux.getInfo();
                    nodeAux.setInfo(nodeAux.getNext().getInfo());
                    nodeAux.getNext().setInfo(aux);
                }

                nodeAux = nodeAux.getNext();
            }

            current = current.getPrev();
        }
    }

    public void shakeSort() {
        Node nodeStart = start, nodeEnd = end, startIndex, endIndex;
        int valueAux;
        boolean change = true;

        while (nodeStart != nodeEnd && change) {
            change = false;

            startIndex = nodeStart;
            while (startIndex.getNext() != null) {
                if (startIndex.getInfo() > startIndex.getNext().getInfo()) {
                    change = true;
                    valueAux = startIndex.getInfo();
                    startIndex.setInfo(startIndex.getNext().getInfo());
                    startIndex.getNext().setInfo(valueAux);
                }

                startIndex = startIndex.getNext();
            }

            nodeEnd = nodeEnd.getPrev();

            if (change) {
                change = false;

                endIndex = nodeEnd;

                while (endIndex.getPrev() != null) {
                    if (endIndex.getInfo() < endIndex.getPrev().getInfo()) {
                        change = true;
                        valueAux = endIndex.getInfo();
                        endIndex.setInfo(endIndex.getPrev().getInfo());
                        endIndex.getPrev().setInfo(valueAux);
                    }
                    endIndex = endIndex.getPrev();
                }

                nodeStart = nodeStart.getNext();
            }
        }
    }

    public void heapSort() {
        Node parent, left, right, bigger, nodeStart = start, nodeEnd = end;
        int aux, TL = listSize(), parentIndex;

        System.out.println(TL);

        while (TL != 1) {
            parentIndex = (TL / 2) - 1;
            parent = getNodeByIndex(parentIndex);

            while (parent != null) {

                left = getNodeByIndex(2 * parentIndex + 1);
                right = getNodeByIndex(2 * parentIndex + 2);

                if (getIndexByNode(right) < TL) {
                    if (left.getInfo() > right.getInfo()) {
                        bigger = left;
                    } else {
                        bigger = right;
                    }
                } else {
                    bigger = left;
                }

                if (bigger.getInfo() > parent.getInfo()) {
                    aux = bigger.getInfo();
                    bigger.setInfo(parent.getInfo());
                    parent.setInfo(aux);
                }

                parentIndex--;
                parent = parent.getPrev();
            }

            aux = nodeStart.getInfo();
            nodeStart.setInfo(nodeEnd.getInfo());
            nodeEnd.setInfo(aux);

            nodeEnd = nodeEnd.getPrev();

            TL--;
        }
    }

    public void coutingSort() {
        Node nodeStart = start, highestNode = getHighestNode();
        int[] arrayValues = new int[listSize()];
        int k = 0, listIndex;

        while (nodeStart != null) {
            arrayValues[k] = nodeStart.getInfo();

            k++;

            nodeStart = nodeStart.getNext();
        }

        int[] countArray = buildCountingSortArray(highestNode.getInfo(), start);

        int[] sumArray = countCoutingSortArray(countArray);

        for (int i = arrayValues.length - 1; i >= 0; i--) {
            listIndex = sumArray[arrayValues[i] - 1] - 1;
            getNodeByIndex(listIndex).setInfo(arrayValues[i]);

            sumArray[arrayValues[i] - 1] = sumArray[arrayValues[i] - 1] - 1;
        }
    }

    public void applyCountingSortToRadix(int exp) {
        Node nodeStart = start;
        int index = 0, valueAux;
        int[] numbersAux = new int[listSize()], numbers = new int[listSize()];

        while (nodeStart != null) {
            numbers[index] = nodeStart.getInfo();

            nodeStart = nodeStart.getNext();
            index++;
        }

        index = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < numbers.length; j++) {
                valueAux = numbers[j];

                if (i == (valueAux / exp) % 10) {
                    numbersAux[index] = numbers[j];

                    index++;
                }
            }
        }

        nodeStart = start;

        for (int i = 0; i < numbersAux.length; i++) {
            nodeStart.setInfo(numbersAux[i]);
            nodeStart = nodeStart.getNext();
        }
    }

    public void bucketSort() {
        Node[] arrayNode = new Node[6];
        Node current, previous, highestNode = getHighestNode();

        int limit = highestNode.getInfo() / 5;

        for (int i = 0; i < 6; i++) {
            current = start;

            while (current != null) {
                if (current.getInfo() <= limit) {
                    addToBucket(arrayNode, i, current);

                    previous = current.getPrev();
                    if (previous != null) {
                        previous.setNext(current.getNext());
                    } else {
                        start = current.getNext();
                    }

                    if (current.getNext() != null) {
                        current.getNext().setPrev(previous);
                    }
                }

                current = current.getNext();
            }

            limit += highestNode.getInfo() / 5;

            insertionSortPreSet(arrayNode[i]);
        }

        printBuckets(arrayNode);
    }

    private void addToBucket(Node[] arrayNode, int index, Node node) {
        if (arrayNode[index] == null) {
            arrayNode[index] = new Node(node.getInfo());
        } else {
            Node nodeAux = arrayNode[index];

            while (nodeAux.getNext() != null) {
                nodeAux = nodeAux.getNext();
            }

            Node newNode = new Node(node.getInfo());

            nodeAux.setNext(newNode);
            newNode.setPrev(nodeAux);
        }
    }

    private void printBuckets(Node[] arrayNode) {
        for (int i = 0; i < arrayNode.length; i++) {

            while (arrayNode[i] != null) {
                System.out.print(arrayNode[i].getInfo() + " ");

                arrayNode[i] = arrayNode[i].getNext();
            }

            System.out.println();
        }
    }

    public void combSort() {
        int gap = (int) (listSize() / 1.3), previousIndex, currentIndex, nodeValueAux;
        Node previous, current;

        while (gap >= 1) {

            previousIndex = 0;
            currentIndex = gap;

            previous = getNodeByIndex(previousIndex);
            current = getNodeByIndex(currentIndex);

            while (current != null) {

                if (previous.getInfo() > current.getInfo()) {
                    nodeValueAux = current.getInfo();
                    current.setInfo(previous.getInfo());
                    previous.setInfo(nodeValueAux);
                }

                previousIndex++;
                currentIndex++;
                previous = getNodeByIndex(previousIndex);
                current = getNodeByIndex(currentIndex);
            }

            gap = (int) (gap / 1.3);
        }
    }

    public void gnomeSort() {
        Node currentMarked, previousMarked, current = start.getNext(), previous = start;
        int nodeValueAux;
        boolean check = false;

        while (current != null) {

            if (previous.getInfo() > current.getInfo()) {
                nodeValueAux = previous.getInfo();
                previous.setInfo(current.getInfo());
                current.setInfo(nodeValueAux);

                check = true;
            }

            currentMarked = current;
            previousMarked = previous;

            while (check) {

                if (previous.getPrev() != null) {
                    current = previous;
                    previous = previous.getPrev();

                    if (previous.getInfo() > current.getInfo()) {
                        nodeValueAux = previous.getInfo();
                        previous.setInfo(current.getInfo());
                        current.setInfo(nodeValueAux);
                    } else {
                        check = false;

                        previous = previousMarked;
                        current = currentMarked;
                    }
                } else {
                    check = false;

                    previous = previousMarked;
                    current = currentMarked;
                }
            }

            previous = current;
            current = current.getNext();
        }

    }

    public void shellSort() {
        int valueAux, index, gap = 1;

        while (gap < listSize()) {
            gap = (gap * 3) + 1;
        }

        gap = gap / 3;

        while (gap > 0) {

            for (int i = gap; i < listSize(); i++) {

                valueAux = getNodeByIndex(i).getInfo();

                index = i;

                while ((index - gap) >= 0
                        && valueAux < getNodeByIndex(index - gap).getInfo()) {

                    getNodeByIndex(index).setInfo(getNodeByIndex(index - gap).getInfo());

                    index -= gap;
                }

                getNodeByIndex(index).setInfo(valueAux);
            }

            gap = gap / 3;
        }
    }

    public void radixSort() {
        int maxNumber = getHighestNode().getInfo();

        for (int i = 1; maxNumber / i > 0; i *= 10) {
            applyCountingSortToRadix(i);
        }
    }

    public void quickSortWithoutPivot() {
        quickSortWOPivot(0, listSize() - 1);
    }

    public void quickSortWOPivot(int start, int end) {

        int startIndex = start, endIndex = end, valueAux;
        boolean flag = true;

        while (startIndex < endIndex) {

            if (flag) {
                while (startIndex < endIndex
                        && getNodeValue(startIndex) <= getNodeValue(endIndex)) {
                    startIndex++;
                }
            } else {
                while (startIndex < endIndex
                        && getNodeValue(startIndex) <= getNodeValue(endIndex)) {
                    endIndex--;
                }
            }

            valueAux = getNodeValue(startIndex);
            getNodeByIndex(startIndex).setInfo(getNodeValue(endIndex));
            getNodeByIndex(endIndex).setInfo(valueAux);

            flag = !flag;
        }

        if (start < startIndex - 1) {
            quickSortWOPivot(start, startIndex - 1);
        }

        if (endIndex + 1 < end) {
            quickSortWOPivot(endIndex + 1, end);
        }
    }

    public void quickSortWithPivot() {
        quickSortWPivot(0, listSize() - 1);
    }

    public void quickSortWPivot(int start, int end) {

        int startIndex = start, endIndex = end, valueAux;
        int pivot = getNodeValue((start + end) / 2);

        while (startIndex < endIndex) {
            while (getNodeValue(startIndex) < pivot) {
                startIndex++;
            }

            while (getNodeValue(endIndex) > pivot) {
                endIndex--;
            }

            if (startIndex <= endIndex) {

                valueAux = getNodeValue(startIndex);
                getNodeByIndex(startIndex).setInfo(getNodeValue(endIndex));
                getNodeByIndex(endIndex).setInfo(valueAux);

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

    public void mergeSortFirstImplementation() {
        List firstList = new List(), secondList = new List();
        int sequence = 1;

        while (sequence < listSize()) {

            firstList = new List();
            secondList = new List();
            partitionOnFirstMerge(firstList, secondList);
            fusionOnFirstMerge(firstList, secondList, sequence);

            showList();

            System.out.println();

            sequence = sequence * 2;
        }
    }

    public void partitionOnFirstMerge(List firstList, List secondList) {
        int size = listSize() / 2;
        Node current = getStart();

        for (int i = 0; i < size; i++) {
            Node newNodeFirstList = new Node(current.getInfo());
            Node newNodeSecondList = getNodeByIndex(i + size);

            firstList.insertAtTheEnd(newNodeFirstList);
            secondList.insertAtTheEnd(newNodeSecondList);

            current = current.getNext();
        }
    }

    public void fusionOnFirstMerge(List firstList, List secondList, int sequence) {
        int sequenceAux = sequence, i = 0, j = 0, k = 0;
        Node nodeAux = getNodeByIndex(k);

        while (k < listSize()) {
            while (i < sequence && j < sequence) {

                nodeAux = getNodeByIndex(k);

                if (firstList.getNodeValue(i) < secondList.getNodeValue(j)) {
                    // System.out.println(firstList.getNodeByIndex(i).getInfo());
                    nodeAux.setInfo(firstList.getNodeByIndex(i).getInfo());
                    k++;
                    i++;
                } else {
                    // System.out.println(secondList.getNodeByIndex(j).getInfo());
                    nodeAux.setInfo(secondList.getNodeByIndex(j).getInfo());
                    k++;
                    j++;
                }
            }

            while (i < sequence) {
                // System.out.println(firstList.getNodeByIndex(i).getInfo());
                nodeAux = getNodeByIndex(k);
                nodeAux.setInfo(firstList.getNodeByIndex(i).getInfo());
                k++;
                i++;
            }
            while (j < sequence) {
                // System.out.println(secondList.getNodeByIndex(j).getInfo());
                nodeAux = getNodeByIndex(k);
                nodeAux.setInfo(secondList.getNodeByIndex(j).getInfo());
                k++;
                j++;
            }

            sequence += sequenceAux;
        }
    }

    public void mergeSortSecondImplementation() {
        List listAux = new List();
        merge(listAux, 0, listSize() - 1);
    }

    public void merge(List listAux, int left, int right) {
        int mid;

        if (left < right) {
            mid = (left + right) / 2;
            merge(listAux, left, mid);
            merge(listAux, mid + 1, right);

            listAux = new List();
            fusionOnSecondMerge(listAux, left, mid, mid + 1, right);
        }
    }

    private void fusionOnSecondMerge(List listAux, int start1, int end1, int start2, int end2) {
        listAux = new List();
        Node nodeAux;
        int i, j, k = 0;
        i = start1;
        j = start2;

        while (i <= end1 && j <= end2) {
            if (getNodeValue(i) < getNodeValue(j)) {
                nodeAux = new Node(getNodeValue(i));
                listAux.insertAtTheEnd(nodeAux);
                i++;
            } else {
                nodeAux = new Node(getNodeValue(j));
                listAux.insertAtTheEnd(nodeAux);
                j++;
            }
            k++;
        }

        while (i <= end1) {
            nodeAux = new Node(getNodeValue(i));
            listAux.insertAtTheEnd(nodeAux);
            i++;
            k++;
        }

        while (j <= end2) {
            nodeAux = new Node(getNodeValue(j));
            listAux.insertAtTheEnd(nodeAux);
            j++;
            k++;
        }

        for (int w = 0; w < k; w++) {
            getNodeByIndex(w + start1).setInfo(listAux.getNodeByIndex(w).getInfo());
        }
    }

    private void insertionSortToTimSort(int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Node node = new Node(getNodeValue(i));
            int j = i - 1;

            while (j >= left && getNodeValue(j) > node.getInfo()) {
                getNodeByIndex(j + 1).setInfo(getNodeValue(j));
                j--;
            }

            getNodeByIndex(j + 1).setInfo(node.getInfo());
        }
    }

    private int calcMinRun() {
        return listSize() / MIN_TIM_SORT_GROUP;
    }

    public void timSort() {
        List listAux = new List();
        int MIN_RUN_SIZE = calcMinRun();

        for (int i = 0; i < listSize(); i += MIN_RUN_SIZE) {
            int end = Math.min(i + MIN_RUN_SIZE - 1, listSize() - 1);
            insertionSortToTimSort(i, end);
        }

        for (int runSize = MIN_RUN_SIZE; runSize < listSize(); runSize += 2) {
            for (int left = 0; left < listSize(); left += 2 * runSize) {
                int mid = left + runSize - 1;
                int right = Math.min((left + 2 * runSize - 1), (listSize() - 1));
                if(mid < right) {
                    merge(listAux, left, right);
                }
            }
        }
    }
}
