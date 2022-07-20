
package lab3;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E> {

    private class Element {
        E object;
        Element next = null;
        Element prev = null;

        public Element(E e) {
            this.object = e;
            this.next = null;
            this.prev = null;
        }

        @SuppressWarnings("unused")
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.next = next;
            this.prev = prev;
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

    private class InnerIterator implements Iterator<E> {// only to iterate through the list one way
        // like a regular iterator
        Element pos;
        int index;

        public InnerIterator() {
            this.pos = head;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {

            return pos != null && index < size;
        }

        @Override
        public E next() {
            E next = pos.getObject();
            pos = pos.getNext();
            index++;

            return next;
        }
    }

    private class InnerListIterator implements ListIterator<E> {// to reverse the list
        Element p;
        Element p1;
        int index;
        boolean check = false;

        public InnerListIterator() {
            this.p = head;
            this.p1 = tail;
            this.index = 0;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();

        }

        @Override
        public boolean hasNext() {

            return p != null && index < size;
        }

        @Override
        public boolean hasPrevious() {
            return p != null && index < size;
        }

        @Override
        public E next() {
            E next = p.getObject();
            p = p.getNext();
            index++;

            return next;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            E prev = p1.getObject();
            p1 = p1.getPrev();
            index++;
            check = true;

            return prev;
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
        public void set(E e) {
            if (check) {
                p1.setObject(e);
            } else {
                p.setObject(e);
            }

        }
    }

    private Element head;
    private Element tail;
    private int size;

    public TwoWayUnorderedListWithHeadAndTail() {
        // make a head and a tail
        this.head = new Element(null);

        this.tail = new Element(null);

        this.size = 0;
    }

    @Override
    public boolean add(E e) {

        if (size == 0) {
            this.head.setObject(e);

            this.tail.setObject(e);

            this.head.setNext(tail);
            this.head.setPrev(tail);

            this.tail.setNext(head);
            this.tail.setPrev(head);

            size++;
            return true;

        }
        if (size == 1) {
            this.tail.setObject(e);
            size++;
            return true;
        }

        Element newElem = new Element(e);
        newElem.setNext(head);
        newElem.setPrev(tail);
        tail.setNext(newElem);
        tail = newElem;

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

        if (index == 0 && size == 1) {
            tail.setObject(head.getObject());
            head.setObject(element);
            head.setNext(tail);
            this.head.setPrev(tail);

            this.tail.setNext(head);
            this.tail.setPrev(head);

            size++;

        } else {
            if (index == 0) {
                Element shiftedHead = new Element(head.getObject());

                shiftedHead.setNext(head.getNext());
                head.getNext().setPrev(shiftedHead);

                head.setObject(element);
                head.setNext(shiftedHead);

                shiftedHead.setPrev(head);

                size++;

            } else {
                Element newElem = new Element(element);
                if (index == 1) {
                    head.getNext().setPrev(newElem);
                    newElem.setNext(head.getNext());
                    head.setNext(newElem);
                    newElem.setPrev(head);

                    size++;
                    return;
                }
                if (index == size - 1) {
                    tail.getPrev().setNext(newElem);
                    newElem.setPrev(tail.getPrev());
                    tail.setPrev(newElem);

                    newElem.setNext(tail);

                    size++;
                    return;
                }

                Element prevElem = tail;

                for (int i = 0; i < index; i++) {
                    prevElem = prevElem.getNext();

                }
                prevElem.getNext().setPrev(newElem);
                newElem.setNext(prevElem.getNext());
                prevElem.setNext(newElem);
                newElem.setPrev(prevElem);

                size++;

            }

        }
    }

    @Override
    public void clear() {
        head = null;
        tail = null;

        this.size = 0;
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
        Element current = tail;

        for (int i = 0; i < index + 1; i++) {
            current = current.getNext();

        }
        return current.getObject();
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        Element prevElem = tail;
        for (int i = 0; i < index; i++) {

            prevElem = prevElem.getNext();
        }
        Element toSetElem = prevElem.getNext();
        E oldElem = toSetElem.getObject();
        toSetElem.setObject(element);

        return oldElem;
    }

    @Override
    public int indexOf(E element) {
        int index = 0;
        if (size == 0) {
            return -1;

        }
        Element currentElem = tail.getNext();
        for (int i = 0; i < size; i++) {
            if (currentElem.getObject().equals(element)) {
                return index;
            }
            currentElem = currentElem.getNext();
            index++;
        }

        return -1;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            E removed = head.getObject();
            head.setObject(null);
            size--;
            return removed;
        } else if (index == size - 1) {
            E removed = tail.getObject();
            tail.setPrev(tail.getPrev().getPrev());
            tail.setObject(tail.getPrev().getObject());
            size--;
            return removed;

        } else {
            if (index == 0) {
                E removed = head.getObject();
                head.setObject(head.getNext().getObject());
                head.getNext().getNext().setPrev(head);

                head.setNext(head.getNext().getNext());

                tail.setNext(head);
                size--;

                return removed;
            } else {

                if (index == 1) {
                    E removed = head.getNext().getObject();
                    head.getNext().getNext().setPrev(head);
                    head.setNext(head.getNext().getNext());
                    size--;
                    return removed;
                }
                if (index == size - 2) {
                    E removed = tail.getPrev().getObject();
                    tail.getPrev().getPrev().setNext(tail);
                    tail.setPrev(tail.getPrev().getPrev());
                    size--;
                    return removed;
                }

                Element prevElem = tail;

                for (int i = 0; i < index; i++) {
                    prevElem = prevElem.getNext();

                }
                E removed = prevElem.getNext().getObject();
                prevElem.getNext().getNext().setPrev(prevElem);
                prevElem.setNext(prevElem.getNext().getNext());

                size--;
                return removed;
            }

        }

    }

    @Override
    public boolean remove(E e) {
        if (size == 0) {
            return false;
        }

        // Index check
        int index = 0;
        Element prevElem = tail;
        for (int i = 0; i < size; i++) {
            if ((e == null ? prevElem.getNext().getObject() == null : e.equals(prevElem.getNext().getObject()))) {
                break;
            }
            prevElem = prevElem.getNext();
            index++;
            if (index == size) {
                return false;
            }
        }
        if (index == 0) {

            head.setObject(head.getNext().getObject());
            head.getNext().getNext().setPrev(head);

            head.setNext(head.getNext().getNext());

            tail.setNext(head);
            size--;
            return true;
        }
        if (index == 1) {

            head.getNext().getNext().setPrev(head);
            head.setNext(head.getNext().getNext());
            size--;
            return true;
        }
        if (index == size - 2) {

            tail.getPrev().getPrev().setNext(tail);
            tail.setPrev(tail.getPrev().getPrev());
            size--;
            return true;
        }
        if (index == size - 1) {

            tail.setPrev(tail.getPrev().getPrev());
            tail.setObject(tail.getPrev().getObject());
            size--;
            return true;

        }
        prevElem = tail;
        for (int i = 0; i < index; i++) {
            prevElem = prevElem.getNext();

        }

        prevElem.getNext().getNext().setPrev(prevElem);
        prevElem.setNext(prevElem.getNext().getNext());

        size--;
        return true;
    }

    @Override
    public int size() {

        return this.size;
    }


    boolean reverseCheck = false;

    public String toStringReverse() {

        ListIterator<E> iter = new InnerListIterator();
        String retStr = "";


        while (iter.hasPrevious()) {
            retStr += "\n" + iter.previous().toString();

        }
        reverseCheck = true;
        return retStr;

    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {//
        if (this.isEmpty() && other.isEmpty()) {
            return;
        }
        if (other.isEmpty()) {
            return;
        }

        if (this.equals(other)) {
            return;
        }
        if (this.isEmpty()) {
            this.head = other.head;
            this.tail = other.tail;
            this.size = other.size;
            other.clear();
            return;

        }

        this.tail.setNext(other.head);
        other.head.setPrev(this.tail);
        this.size += other.size;
        this.tail = other.tail;
        this.tail.setNext(head);
        this.head.setPrev(tail);

        other.clear();


    }
}
