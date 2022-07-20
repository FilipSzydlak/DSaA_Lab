package lab8;


import java.util.LinkedList;

public class BST<T extends Comparable<? super T>> {


    private class Node {
        T value;
        Node left, right, parent;

        public Node(T v) {
            value = v;
        }

        public Node(T value, Node parent) {
            this.value = value;
            this.parent = parent;
        }


        public void setRight(Node right) {
            this.right = right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node(T value, Node left, Node right, Node parent) {
            super();
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

    }

    private Node root = null;
    private int size;


    public BST() {

    }

    public T getElement(T toFind) {

        if (toFind == null) {
            return null;
        }

        if (root == null || root.value.equals(toFind)) {
            return root.value;
        }

        Node currElem = root;

        while (!(toFind.equals(currElem.value))) {
            if (toFind.compareTo(currElem.value) < 0) {
                currElem = currElem.left;
            } else if (toFind.compareTo(currElem.value) > 0) {
                currElem = currElem.right;
            }
            if ((currElem.left == null && currElem.right == null) && !(toFind.equals(currElem.value))) {
                return null;
            }
        }
        return currElem.value;
    }

    public Node getNode(T toFind) {
        if (toFind == null) {
            return null;
        }

        Node currNode = root;

        if (currNode == null) {
            return null;
        }

        while (!(currNode.value.equals(toFind))) {
            if (toFind.compareTo(currNode.value) < 0) {
                currNode = currNode.left;
            } else if (toFind.compareTo(currNode.value) > 0) {
                currNode = currNode.right;
            }

            if (currNode == null) {
                return null;
            }
        }

        return currNode;
    }


    public T successor(T elem) {


        Node currNode = getNode(elem);
        if (currNode == null) {
            return null;
        }

        if (currNode.equals(getNode(maxElem(root)))) {
            return null;
        }
        if (currNode.right != null) {
            return minElem(currNode.right);
        }

        Node nodeParent = currNode.parent;
        if (nodeParent == null) {
            return null;
        }
        while (nodeParent != null && currNode == nodeParent.right) {
            currNode = nodeParent;
            nodeParent = nodeParent.parent;
        }


        return nodeParent.value;
    }

    public T minElem(Node node) {
        Node currNode = node;

        while (currNode.left != null) {
            currNode = currNode.left;
        }
        return currNode.value;
    }

    public T maxElem(Node node) {
        Node currNode = node;

        while (currNode.right != null) {
            currNode = currNode.right;
        }
        return currNode.value;
    }


    public String toStringInOrder() {
        LinkedList<String> result = new LinkedList<String>();

        toStringInOrderRecursive(root, result);

        return String.join(", ", result);
    }

    public String toStringPreOrder() {
        LinkedList<String> result = new LinkedList<String>();

        preOrderRecursive(root, result);

        return String.join(", ", result);
    }

    public String toStringPostOrder() {
        LinkedList<String> result = new LinkedList<String>();

        postOrderRecursive(root, result);

        return String.join(", ", result);
    }


    private LinkedList<T> inOrderList() {
        LinkedList<T> result = new LinkedList<T>();

        inOrderList(root, result);

        return result;
    }

    private void inOrderList(Node node, LinkedList<T> result) {
        if (node == null)
            return;

        inOrderList(node.left, result);
        result.add(node.value);
        inOrderList(node.right, result);

    }

    private void toStringInOrderRecursive(Node node, LinkedList<String> result) {
        if (node == null)
            return;

        toStringInOrderRecursive(node.left, result);
        result.add(node.value.toString());
        toStringInOrderRecursive(node.right, result);

    }

    private void preOrderRecursive(Node node, LinkedList<String> result) {
        if (node == null)
            return;

        result.add(node.value.toString());
        preOrderRecursive(node.left, result);
        preOrderRecursive(node.right, result);

    }

    private void postOrderRecursive(Node node, LinkedList<String> output) {
        if (node == null)
            return;

        postOrderRecursive(node.left, output);
        postOrderRecursive(node.right, output);
        output.add(node.value.toString());

    }

    public boolean add(T elem) {
        Node addedNode = new Node(elem);

        if (isEmpty()) {
            root = addedNode;
            size++;
            return true;
        }


        Node currNode = root;
        Node prevNode = null;

        while (currNode != null) {
            if (currNode.value.equals(elem))
                return false;

            if (currNode.value.compareTo(elem) > 0) {
                prevNode = currNode;
                currNode = currNode.left;

            } else if (currNode.value.compareTo(elem) < 0) {
                prevNode = currNode;
                currNode = currNode.right;
            }
        }
        if (prevNode.value.compareTo(elem) > 0)
            prevNode.left = addedNode;

        else
            prevNode.right = addedNode;

        addedNode.parent = prevNode;

        size++;
        return true;
    }


    public T remove(T value) {


        Node currNode = getNode(value);
        Node successor;
        Node nodeParent;

        if (currNode == null)
            return null;

        Node remNode = new Node(currNode.value, currNode.left, currNode.right, currNode.parent);

        Node previousParent = getNode(value).parent;
        T remValue = remNode.value;
        if (currNode.left == null && currNode.right == null) {
            if (currNode == root) {
                root = null;
                size--;
                return remValue;
            }
            nodeParent = previousParent;

            if (nodeParent.left == currNode)
                nodeParent.left = null;
            else
                nodeParent.right = null;
            size--;
            return remValue;
        }
        if (currNode.right == null) {
            if (currNode == root) {
                root = root.left;
                size--;


                return remValue;
            }
            nodeParent = previousParent;

            if (nodeParent.left == currNode)
                nodeParent.left = currNode.left;
            else
                nodeParent.right = currNode.left;
            size--;
            return remValue;
        }
        if (currNode.left == null) {
            if (currNode == root) {
                root = root.right;
                size--;
                root.parent = null;
                return remValue;
            }
            nodeParent = previousParent;

            if (nodeParent.left == currNode)
                nodeParent.left = currNode.right;
            else
                nodeParent.right = currNode.right;
            size--;
            return remValue;
        }
        currNode = getNode(value);
        if (currNode == null) {
            return null;
        }
        if (currNode.right.left == null) {

            currNode.value = currNode.right.value;
            currNode.right = currNode.right.right;
            size--;
            return remValue;
        }
        successor = currNode.right;
        Node successorParent = currNode;

        while (successor.left != null) {
            successorParent = successor;
            successor = successor.left;
        }
        currNode.value = successor.value;
        successorParent.left = successor.right;
        size--;

        return remValue;
    }


    public void clear() {
        this.root = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }


}
