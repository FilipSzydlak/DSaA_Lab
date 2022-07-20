package lab10;


public class DisjointSetForest implements DisjointSetDataStructure {
    private int size;
    private class Element {
        int rank;
        int parent;
    }

    Element[] arr;

    public DisjointSetForest(int size) {
        this.size = size;
        arr = new Element[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Element();
        }
    }

    @Override
    public void makeSet(int item) {
        arr[item].parent = item;
        arr[item].rank = 0;
    }

    @Override
    public int findSet(int item) {
        if (item < 0 || item >= size) {
            return -1;
        }
        if (item != arr[item].parent) {
            arr[item].parent = findSet(arr[item].parent);
        }


        return arr[item].parent;
    }

    @Override
    public boolean union(int itemA, int itemB) {
        if ((itemA  >= size  || itemB >= size)){
            return false;
        }
        if (findSet(arr[itemA].parent) == findSet(arr[itemB].parent)){
            return false;
        }
        if(itemA < 0 || itemB < 0){
            return false;
        }
        link(findSet(itemA),findSet(itemB));
        return true;
    }

    private void link(int itemA, int itemB){
        if (arr[itemA].rank > arr[itemB].rank){
            arr[itemB].parent = itemA;
        }else {
            arr[itemA].parent = itemB;

            if (arr[itemA].rank == arr[itemB].rank){
                arr[itemB].rank++;
            }
        }


    }

    @Override
    public String toString() {
        if(size == 0){
            return "Disjoint sets as forest:";
        }
        StringBuilder output = new StringBuilder();
        output.append("Disjoint sets as forest:\n");

        for (int i = 0; i < arr.length; i++) {
            if(i == arr.length - 1){
                output.append(i + " -> " + arr[i].parent);
            }else {
                output.append(i + " -> " + arr[i].parent + "\n");
            }
        }

        return output.toString();
    }
    @Override
    public int countSets() {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].parent == i){
                counter++;
            }
        }
        return counter;
    }
}
