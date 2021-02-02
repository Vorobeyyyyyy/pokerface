package by.bsuir.pokerface.warehouse;

import by.bsuir.pokerface.entity.board.Board;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class BoardWarehouse {
    private final static BoardWarehouse instance = new BoardWarehouse();

    private final List<Board> boards = new ArrayList<>();

    private BoardWarehouse() {}

    public static BoardWarehouse getInstance() {
        return instance;
    }

    public int size() {
        return boards.size();
    }

    public boolean isEmpty() {
        return boards.isEmpty();
    }

    public boolean contains(Object o) {
        return boards.contains(o);
    }

    public Iterator<Board> iterator() {
        return boards.iterator();
    }

    public Object[] toArray() {
        return boards.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return boards.toArray(a);
    }

    public boolean add(Board board) {
        return boards.add(board);
    }

    public boolean remove(Object o) {
        return boards.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return boards.containsAll(c);
    }

    public boolean addAll(Collection<? extends Board> c) {
        return boards.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Board> c) {
        return boards.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return boards.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return boards.retainAll(c);
    }

    public void replaceAll(UnaryOperator<Board> operator) {
        boards.replaceAll(operator);
    }

    public void sort(Comparator<? super Board> c) {
        boards.sort(c);
    }

    public void clear() {
        boards.clear();
    }

    @Override
    public boolean equals(Object o) {
        return boards.equals(o);
    }

    @Override
    public int hashCode() {
        return boards.hashCode();
    }

    public Board get(int index) {
        return boards.get(index);
    }

    public Board set(int index, Board element) {
        return boards.set(index, element);
    }

    public void add(int index, Board element) {
        boards.add(index, element);
    }

    public Board remove(int index) {
        return boards.remove(index);
    }

    public int indexOf(Object o) {
        return boards.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return boards.lastIndexOf(o);
    }

    public ListIterator<Board> listIterator() {
        return boards.listIterator();
    }

    public ListIterator<Board> listIterator(int index) {
        return boards.listIterator(index);
    }

    public List<Board> subList(int fromIndex, int toIndex) {
        return boards.subList(fromIndex, toIndex);
    }

    public Spliterator<Board> spliterator() {
        return boards.spliterator();
    }

    public static <E> List<E> of() {
        return List.of();
    }

    public static <E> List<E> of(E e1) {
        return List.of(e1);
    }

    public static <E> List<E> of(E e1, E e2) {
        return List.of(e1, e2);
    }

    public static <E> List<E> of(E e1, E e2, E e3) {
        return List.of(e1, e2, e3);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4) {
        return List.of(e1, e2, e3, e4);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5) {
        return List.of(e1, e2, e3, e4, e5);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6) {
        return List.of(e1, e2, e3, e4, e5, e6);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return List.of(e1, e2, e3, e4, e5, e6, e7);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return List.of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    @SafeVarargs
    public static <E> List<E> of(E... elements) {
        return List.of(elements);
    }

    public static <E> List<E> copyOf(Collection<? extends E> coll) {
        return List.copyOf(coll);
    }

    public <T> T[] toArray(IntFunction<T[]> generator) {
        return boards.toArray(generator);
    }

    public boolean removeIf(Predicate<? super Board> filter) {
        return boards.removeIf(filter);
    }

    public Stream<Board> stream() {
        return boards.stream();
    }

    public Stream<Board> parallelStream() {
        return boards.parallelStream();
    }

    public void forEach(Consumer<? super Board> action) {
        boards.forEach(action);
    }
}
