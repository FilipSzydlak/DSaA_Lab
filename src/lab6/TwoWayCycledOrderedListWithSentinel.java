package lab6;


import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledOrderedListWithSentinel<E extends Comparable<? super E>> implements IList<E> {

    private class Element {

        E object;


        Element next = null;


        Element prev = null;

        public Element(E e) {
            this.object = e;
        }

        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
        }

        // add element e after this
        public void addAfter(Element elem) {
            elem.setNext(this.getNext());
            this.getNext().setPrev(elem);
            elem.setPrev(this);
            this.setNext(elem);

        }

        public void addBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.prev);
            this.prev.setNext(elem);
            this.setPrev(elem);

        }

        // assert it is NOT a sentinel
        public void remove() {
            if (!(this.equals(sentinel))) {
                this.next.setPrev(this.prev);
                this.prev.setNext(this.next);
            }
        }

        public E getObject() {
            return object;
        }

        public void setObject(E object) {
            this.object = object;
        }


        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }


        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }


    }


    private class InnerIterator implements Iterator<E> {
        Element pos;
        int counter;

        public InnerIterator() {
            pos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return pos != null && counter < size;

        }

        @Override
        public E next() {
            E next = pos.object;
            pos = pos.next;
            counter++;
            return next;
        }
    }

    private class InnerListIterator implements ListIterator<E> {
        Element pos;
        Element posPrev;
        int counter;

        public InnerListIterator() {
            this.pos = sentinel.next;
            this.posPrev = sentinel.prev;
            this.counter = 0;
        }

        @Override
        public boolean hasNext() {
            return pos != null && counter < size;
        }

        @Override
        public E next() {
            E next = pos.object;
            pos = pos.next;
            counter++;
            return next;
        }

        @Override
        public void add(E arg0) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return posPrev != null && counter < size;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            E previous = posPrev.object;
            posPrev = posPrev.prev;
            counter++;
            return previous;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E arg0) {
            throw new UnsupportedOperationException();
        }
    }

    Element sentinel;
    int size;

    public TwoWayCycledOrderedListWithSentinel() {
        this.sentinel = new Element(null);
        this.size = 0;
    }

    //@SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        Element addedElem = new Element(e);
        Element currElem = sentinel.getNext();
        if (size == 0) {

            sentinel.setNext(addedElem);
            sentinel.setPrev(addedElem);
            addedElem.setPrev(sentinel);
            addedElem.setNext(sentinel);

            size++;

            return true;
        }


        while (currElem.getObject() != null && !currElem.equals(sentinel)) {
            int compared = e.compareTo(currElem.getObject());
            if (compared < 0) {

                currElem.addBefore(addedElem);
                size++;

                return true;

            } else if (compared >= 0 && currElem.getNext().getObject() != null) {
                if (e.compareTo(currElem.getNext().getObject()) < 0) {

                    currElem.addAfter(addedElem);
                    size++;

                    return true;
                }

            } else if (compared >= 0 && currElem.getNext().equals(sentinel)) {

                currElem.addAfter(addedElem);
                size++;

                return true;
            }


            currElem = currElem.getNext();
        }

        return true;
    }

    private Element getElement(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element currElem = sentinel;

        for (int i = 0; i < index + 1; i++) {
            currElem = currElem.getNext();

        }
        return currElem;
    }


    private Element getElement(E obj) {
        Element currElem = sentinel.next;
        for (int i = 0; i < size; i++) {
            if (currElem.getObject().equals(obj)) {//==
                return currElem;
            }
            currElem = currElem.next;
        }
        return null;

    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        sentinel = null;
        this.sentinel = new Element(null);
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        for (E elem : this) {
            if (element == null ? elem == null : element.equals(elem)) {
                return true;
            }
        }
        return false;

    }


    @Override
    public E get(int index) {
        Element currElem = getElement(index);
        return currElem.getObject();

    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        while (getElement(element) == null) {
            if (index == size) {
                return -1;
            }

            index++;

        }
        return index;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    @Override
    public E remove(int index) throws NoSuchElementException {
        Element remElem = getElement(index);
        if (remElem == null) {
            throw new NoSuchElementException();
        }


        remElem.remove();
        size--;
        return remElem.getObject();
    }

    @Override
    public boolean remove(E e) {
        Element remElem = getElement(e);
        if (remElem == null) {


            return false;
        }
        remElem.remove();
        size--;
        return true;
    }

    @Override
    public int size() {

        return size;
    }

    //@SuppressWarnings("unchecked")
    public void add(TwoWayCycledOrderedListWithSentinel<E> other) {
        if ((this.isEmpty() && other.isEmpty())) {
            return;
        }
        if (this.equals(other)) {
            return;
        }
        if (other.isEmpty()) {
            return;
        }

        if (this.isEmpty()) {
            this.sentinel = other.sentinel;
            this.size = other.size;
            other.clear();

            return;
        }

        for (E e : other) {
            this.add(e);
        }

        other.clear();
    }


    //@SuppressWarnings({ "unchecked", "rawtypes" })
    public void removeAll(E e) {
        while (this.contains(e)) {
            remove(e);
        }
    }

    public String toString() {
        if (isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append("\n");

        int counter = 1;
        ListIterator<E> iter = this.listIterator();
        while (iter.hasNext()) {
            if (counter % 10 != 0) {
                if (counter == size) {
                    result.append(iter.next());
                    break;
                }

                result.append(iter.next()).append(" ");

            } else {
                result.append(iter.next()).append("\n");
            }

            counter++;
        }

        return result.toString();

    }

    public String reversetoString() {
        if (isEmpty()) {
            return "";
        }
        int counter = 1;
        StringBuilder result = new StringBuilder();
        result.append("\n");
        ListIterator<E> iter = this.listIterator();
        while (iter.hasPrevious()) {
            if (counter % 10 != 0) {
                if (counter == size) {
                    result.append(iter.previous());
                    break;
                }

                result.append(iter.previous()).append(" ");

            } else {
                result.append(iter.previous()).append("\n");
            }

            counter++;
        }

        return result.toString();

    }

    public boolean equals(TwoWayCycledOrderedListWithSentinel<E> other) {
        if (this == other) return true;
        if (!(other instanceof TwoWayCycledOrderedListWithSentinel)) return false;
        Iterator iter = this.iterator();
        Iterator iter1 = other.iterator();
        int counter = 1;
        while (iter.hasNext() && iter1.hasNext()) {
            if (iter.next().equals(iter1.next())) {
                if (counter == size && size == other.size) {
                    return true;
                }
                counter++;
            }
        }
        return false;
    }
}












