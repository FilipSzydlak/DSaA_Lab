package lab11;


import java.util.*;
import java.util.Map.Entry;

public class Graph {
    int arr[][];
    //TODO? Collection to map Document to index of vertex
    // You can change it
    HashMap<String, Integer> name2Int = new HashMap<>();
    @SuppressWarnings("unchecked")
    //TODO? Collection to map index of vertex to Document
    // You can change it
    Entry<String, Document>[] arrDoc = (Entry<String, Document>[]) new Entry[0];

    // The argument type depend on a selected collection in the Main class
    @SuppressWarnings("unchecked")
    public Graph(SortedMap<String, Document> internet) {
        int size = internet.size();
        arr = new int[size][size];


        int index = 0;
        for (String iter : internet.keySet()) {// map document to index of vertex
            assert name2Int != null;
            name2Int.put(iter, index);
            index++;
        }
        arrDoc = (Entry<String, Document>[]) new Entry[size];// map index to document
        index = 0;
        Iterator<Entry<String, Document>> iter = internet.entrySet().iterator();
        while (iter.hasNext()) {
            arrDoc[index] = iter.next();
            index++;
        }


        // matrix creation
        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr[i].length; j++) {
                if (i == j) {
                    arr[i][j] = 0;
                } else if (arrDoc[i].getValue().link.containsKey(arrDoc[j].getKey())) {
                    arr[i][j] = arrDoc[i].getValue().link.get(arrDoc[j].getKey()).weight;
                } else {
                    arr[i][j] = -1;
                }


            }
        }


    }

    public String bfs(String start) {
        if (!name2Int.containsKey(start)) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        boolean visited[] = new boolean[arrDoc.length];
        Queue<String> bfs = new LinkedList<>();
        visited[name2Int.get(start)] = true;
        bfs.add(start);
        int tempNodeIndex = -10;
        int index = -10;
        int counter = 0;

        while (bfs.size() != 0) {
            String added = bfs.poll();
            if (counter == 0) {
                output.append(added);
                index = name2Int.get(added);
            } else {
                output.append(", " + added);
                index = name2Int.get(added);
            }
            for (int i = 0; i < arr[index].length; i++) {
                tempNodeIndex = i;
                if (!visited[tempNodeIndex] && arr[index][i] > 0) {
                    visited[tempNodeIndex] = true;
                    bfs.add(arrDoc[tempNodeIndex].getKey());
                }
            }


            counter++;


        }


        return output.toString();
    }

    public String dfs(String start) {
        if (!name2Int.containsKey(start)) {
            return null;
        }
        StringBuilder output = new StringBuilder();
        boolean visited[] = new boolean[arrDoc.length];
        Stack<String> stack = new Stack<>();
        int tempNodeIndex = -10;
        int index = -10;
        int counter = 0;

        stack.push(start);

        while (!stack.empty()) {
            String added = stack.peek();
            stack.pop();
            index = name2Int.get(added);

            if (!visited[name2Int.get(added)]) {
                if (counter == 0) {
                    output.append(added);
                    visited[name2Int.get(added)] = true;
                } else {
                    output.append(", " + added);
                    visited[name2Int.get(added)] = true;
                }
            }
            for (int i = arr[index].length - 1; i >= 0; i--) {
                tempNodeIndex = i;
                if (!visited[tempNodeIndex] && arr[index][i] > 0) {

                    stack.push(arrDoc[tempNodeIndex].getKey());
                }
            }
            counter++;
        }


        return output.toString();
    }

    public int connectedComponents() {
        DisjointSetForest forest = new DisjointSetForest(arr.length);

        for (int i = 0; i < arr.length; i++) {
            forest.makeSet(i);
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] > 0 && forest.findSet(i) != forest.findSet(j)) {
                    forest.union(i, j);
                }
            }
        }

        return forest.countSets();
    }

    public class Vertex {
        int pred;
        int d;
        String id;

        public Vertex(int pred, int d, String id) {
            this.pred = pred;
            this.d = d;
            this.id = id;
        }


    }

    public String DijkstraSSSP(String startVertexStr) {


        if (!name2Int.containsKey(startVertexStr)) return null;


        boolean[] done = new boolean[arr.length];
        Vertex[] vertex = new Vertex[arr.length];
        int VertexFirst = name2Int.get(startVertexStr);

        for (int i = 0; i < arr.length; i++) {
            vertex[i] = new Vertex(-1, Integer.MAX_VALUE, arrDoc[i].getKey());
        }


        int index = -1;
        vertex[VertexFirst].d = 0;
        int shortestEdge = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length; j++) {
                if (vertex[j].d < shortestEdge && !done[j]) {
                    shortestEdge = vertex[j].d;
                    index = j;
                }
            }

            for (int j = 0; j < arr.length; j++) {
                if (arr[index][j] == -1) {
                    continue;
                }
                if (vertex[j].d > (vertex[index].d + arr[index][j])) {
                    vertex[j].d = vertex[index].d + arr[index][j];
                    vertex[j].pred = index;
                }

            }


            shortestEdge = Integer.MAX_VALUE;
            done[index] = true;
        }

        StringBuffer[] str = new StringBuffer[arr.length];
        StringBuffer output = new StringBuffer();
        Vertex currVertex;

        for (int i = 0; i < arr.length; i++) {
            str[i] = new StringBuffer("");

            if (i == VertexFirst) {
                str[VertexFirst] = new StringBuffer("0=" + arrDoc[VertexFirst].getKey());

            } else if (vertex[i].pred == -1) {
                str[i] = new StringBuffer(arrDoc[i].getKey() + " ot htap on");

            } else {
                currVertex = vertex[i];
                str[i].append(currVertex.d).append("=");

                while (currVertex.pred != -1) {
                    str[i].append(currVertex.id).append(">-");
                    currVertex = vertex[currVertex.pred];
                }
                str[i].append(arrDoc[VertexFirst].getKey());
            }

        }

        for (StringBuffer l : str) {
            output.append(l.reverse()).append("\n");
        }

        return output.toString();
    }


}

