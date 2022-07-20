package lab9;

import java.util.LinkedList;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

    private class Element {
        int representant;
        int next;
        int length;
        int last;
    }

    private static final int NULL = -1;

    Element arr[];

    public DisjointSetLinkedList(int size) {

        arr = new Element[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Element();
        }
    }

    @Override
    public void makeSet(int item) {

        arr[item].representant = item;
        arr[item].next = -1;
        arr[item].length = 1;
        arr[item].last = item;

    }

    @Override
    public int findSet(int item) {
        if (item < 0 || item > 7) {
            return -1;
        }
        return arr[item].representant;

    }

    @Override
    public boolean union(int itemA, int itemB) {
        if (itemA < 0 || itemA > 7) {
            return false;
        }

        if (itemB < 0 || itemB > 7) {
            return false;
        }
        if (arr[itemA].representant == arr[itemB].representant) {
            return false;
        }
        if (itemA == itemB) {
            return true;
        }

        if (arr[arr[itemA].representant].length > arr[arr[itemB].representant].length || arr[arr[itemA].representant].length == arr[arr[itemB].representant].length) {


            arr[arr[arr[itemA].representant].last].next = arr[itemB].representant;

            arr[arr[itemA].representant].last = arr[arr[itemB].representant].last;

            int currElem = arr[itemB].representant;
            int size = arr[arr[itemB].representant].length;
            for (int i = 0; i < size; i++) {
                arr[currElem].representant = arr[itemA].representant;
                currElem = arr[currElem].next;
            }


            arr[itemB].representant = arr[itemA].representant;

            arr[arr[itemA].representant].length += arr[arr[itemB].representant].length;

        } else {


            arr[arr[arr[itemB].representant].last].next = arr[itemA].representant;

            arr[arr[itemB].representant].last = arr[arr[itemA].representant].last;

            int currElem = arr[itemA].representant;
            int size = arr[arr[itemA].representant].length;
            for (int i = 0; i < size; i++) {
                arr[currElem].representant = arr[itemB].representant;
                currElem = arr[currElem].next;
            }

            arr[itemA].representant = arr[itemB].representant;

            arr[arr[itemB].representant].length += arr[arr[itemA].representant].length;

        }


        return true;
    }


    @Override
    public String toString() {
        int counter = 0;
        StringBuilder output = new StringBuilder();
        output.append("Disjoint sets as linked list:\n");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr[i].representant) {
                if (arr[i].length == 1) {
                    if (counter == arr.length - 1) {
                        output.append(i);
                    } else {
                        output.append(i + "\n");
                        counter++;
                        continue;
                    }
                } else {
                    if (counter == arr.length - 1) {
                        output.append(i);
                    } else {
                        output.append(i + ", ");
                        counter++;
                    }

                }

                int currElem = arr[arr[i].representant].next;
                while (currElem != -1) {
                    if (currElem == arr[arr[i].representant].last) {
                        if (counter == arr.length - 1) {
                            output.append(currElem);
                            break;
                        } else {
                            output.append(currElem + "\n");
                            counter++;
                            break;
                        }
                    }
                    output.append(currElem + ", ");
                    currElem = arr[currElem].next;
                    counter++;

                }


            }


        }


        return output.toString();
    }

}

