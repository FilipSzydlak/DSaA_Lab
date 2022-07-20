package lab2;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OneWayLinkedList<E> implements IList<E> {

    private class Element {
        E object;
        Element next = null;

        public Element(E e) {
            this.object = e;

        }

        public E getValue() {
            return object;
        }

        public void setValue(E value) {
            this.object = value;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;

        }

    }

    private class InnerIterator implements Iterator<E> {
        private Element actElem;

        public InnerIterator() {
            this.actElem = sentinel.getNext();

        }

        @Override
        public boolean hasNext() {

            return actElem != null;
        }

        @Override
        public E next() {
            Element tempElem = actElem;
            E next = tempElem.getValue(); // .toString();
            actElem = tempElem.getNext();

            return next;
        }
    }

    Element sentinel;

    int size;

    public OneWayLinkedList() {

        this.sentinel = new Element(null);// actElem is sentinel => on index=-1
        this.size = 0;

    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        if (size == 0) {
            Element newElem = new Element(e);
            this.sentinel.setNext(newElem);
            size++;
            return true;
        }

        Element prevElem = sentinel;// current is an element before
        for (int i = 0; i < size; i++) {// the slot where we want to add a new elem
            prevElem = prevElem.getNext();// prevElem => elemBefore
        }
        Element newElem = new Element(e);
        prevElem.setNext(newElem);

        size++;
        return true;
    }

    @Override
    public void add(int index, E element) throws NoSuchElementException {
        if (index < 0 || index > size) {
            throw new NoSuchElementException();
        }
        if (index == size) {
            add(element);
            return;
        }
        if (index == 0) {
            Element sentinelNext = new Element(element);
            Element shiftedElem = sentinel.getNext();
            this.sentinel.setNext(sentinelNext);
            sentinelNext.setNext(shiftedElem);

        } else {
            Element prevElem = sentinel;
            for (int i = -1; i < index - 1; i++) {// current is now the element before the index where the new element
                // will be added
                prevElem = prevElem.getNext();
            }
            Element newElem = new Element(element);

            newElem.setNext(prevElem.getNext());
            prevElem.setNext(newElem);

        }
        size++;
    }

    @Override
    public void clear() {// TO CHECK
        sentinel.setNext(null);
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
    public E get(int index) throws NoSuchElementException {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element current = sentinel;

        for (int i = 0; i < index + 1; i++) {
            current = current.getNext();

        }
        return current.getValue();
    }

    @Override
    public E set(int index, E element) throws NoSuchElementException {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element prevElem = sentinel;
        for (int i = 0; i < index; i++) {

            prevElem = prevElem.getNext();
        }
        Element toSetElem = prevElem.getNext();
        E oldElem = toSetElem.getValue();
        toSetElem.setValue(element);

        return oldElem;
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        if (size == 0) {
            return -1;

        }
        Element currentElem = sentinel.getNext();
        for (int i = 0; i < size; i++) {
            if (currentElem.getValue().equals(element)) {
                return index;
            }
            currentElem = currentElem.getNext();
            index++;
        }

        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E remove(int index) throws NoSuchElementException {

        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            E removed = sentinel.getNext().getValue();
            sentinel.setNext(null);
            size--;
            return removed;

        } else {
            Element prevElem = sentinel;
            for (int i = 0; i < index; i++) {

                prevElem = prevElem.getNext();
            }
            E removed = prevElem.getNext().getValue();
            Element removedElem = prevElem.getNext();
            Element next = removedElem.getNext();
            prevElem.setNext(next);
            size--;
            return removed;
        }

    }

    @Override
    public boolean remove(E e) {
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            if (sentinel.getNext().getValue().equals(e)) {
                sentinel.setNext(null);
                size--;
                return true;
            } else {
                return false;
            }
        }
        Element prevElem = sentinel;
        for (int i = 0; i < size; i++) {
            if ((e == null ? prevElem.getNext().getValue() == null : e.equals(prevElem.getNext().getValue()))) {
                Element toBeRemovedElem = prevElem.getNext();

                prevElem.setNext(toBeRemovedElem.getNext());

                size--;
                return true;
            }
            prevElem = prevElem.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;

    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof OneWayLinkedList))
            return false;
        OneWayLinkedList<?> that = (OneWayLinkedList<?>) o;
        return size == that.size && Objects.equals(sentinel, that.sentinel);
    }

    public int hashCode() {
        return Objects.hash(sentinel, size);
    }

}
