package lab7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class HashTable {
    LinkedList[] arr; // use pure array
    private final static int defaultInitSize = 8;
    private final static double defaultMaxLoadFactor = 0.7;
    private int size;
    private final double maxLoadFactor;

    public HashTable() {
        this(defaultInitSize);
    }

    public HashTable(int size) {
        this(size, defaultMaxLoadFactor);
    }

    public HashTable(int initCapacity, double maxLF) {
        this.size = initCapacity;
        this.arr = new LinkedList[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }

        this.maxLoadFactor = maxLF;
    }

    @SuppressWarnings("unchecked")
    public boolean add(Object elem) {
        int key = elem.hashCode() % size;

        if (arr[key] != null) {
            for (Object elem1 : arr[key]) {
                if (elem1.equals(elem)) {
                    return false;
                }
            }
        }

        arr[key].add(elem);

        if (calcLoadFactor() > defaultMaxLoadFactor) {
            doubleArray();
        }

        return true;
    }

    private void doubleArray() {
        this.size *= 2;
        LinkedList[] coppiedValues = arr;
        this.arr = new LinkedList[size];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }

        for (LinkedList array : coppiedValues) {
            for (Object elem : array) {
                this.add(elem);
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();


        for (int i = 0; i < arr.length; i++) {
            output.append(i).append(":");
            for (int k = 0; k < arr[i].size(); k++) {
                if (k == arr[i].size() - 1) {
                    IWithName xName = (IWithName) arr[i].get(k);
                    String name = xName.getName();
                    output.append(" ").append(name);

                } else {
                    IWithName xName = (IWithName) arr[i].get(k);
                    String name = xName.getName();
                    output.append(" ").append(name).append(",");
                }
            }
            output.append("\n");
        }

        return output.toString();
    }

    public Object get(Object toFind) {
        int key = toFind.hashCode() % size;

        if (!arr[key].isEmpty()) {
            for (Object o : arr[key]) {
                if (o.equals(toFind)) {
                    return o;
                }
            }
        }

        return null;
    }

    private double calcLoadFactor() {
        double ArraySlotsInUsage = 0;

        for (LinkedList list : arr) {
            ArraySlotsInUsage += list.size();
        }

        return ArraySlotsInUsage / size;
    }
}