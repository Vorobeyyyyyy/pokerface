package by.bsuir.pokerface.warehouse;

import by.bsuir.pokerface.entity.room.Room;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class RoomWarehouse {
    private final static RoomWarehouse instance = new RoomWarehouse();

    private final List<Room> rooms = new ArrayList<>();

    private RoomWarehouse() {}

    public static RoomWarehouse getInstance() {
        return instance;
    }

    public int size() {
        return rooms.size();
    }

    public boolean isEmpty() {
        return rooms.isEmpty();
    }

    public boolean contains(Object o) {
        return rooms.contains(o);
    }

    public Iterator<Room> iterator() {
        return rooms.iterator();
    }

    public Object[] toArray() {
        return rooms.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return rooms.toArray(a);
    }

    public boolean add(Room room) {
        return rooms.add(room);
    }

    public boolean remove(Object o) {
        return rooms.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return rooms.containsAll(c);
    }

    public boolean addAll(Collection<? extends Room> c) {
        return rooms.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Room> c) {
        return rooms.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return rooms.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return rooms.retainAll(c);
    }

    public void replaceAll(UnaryOperator<Room> operator) {
        rooms.replaceAll(operator);
    }

    public void sort(Comparator<? super Room> c) {
        rooms.sort(c);
    }

    public void clear() {
        rooms.clear();
    }

    @Override
    public boolean equals(Object o) {
        return rooms.equals(o);
    }

    @Override
    public int hashCode() {
        return rooms.hashCode();
    }

    public Room get(int index) {
        return rooms.get(index);
    }

    public Room set(int index, Room element) {
        return rooms.set(index, element);
    }

    public void add(int index, Room element) {
        rooms.add(index, element);
    }

    public Room remove(int index) {
        return rooms.remove(index);
    }

    public int indexOf(Object o) {
        return rooms.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return rooms.lastIndexOf(o);
    }

    public ListIterator<Room> listIterator() {
        return rooms.listIterator();
    }

    public ListIterator<Room> listIterator(int index) {
        return rooms.listIterator(index);
    }

    public List<Room> subList(int fromIndex, int toIndex) {
        return rooms.subList(fromIndex, toIndex);
    }

    public Spliterator<Room> spliterator() {
        return rooms.spliterator();
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
        return rooms.toArray(generator);
    }

    public boolean removeIf(Predicate<? super Room> filter) {
        return rooms.removeIf(filter);
    }

    public Stream<Room> stream() {
        return rooms.stream();
    }

    public Stream<Room> parallelStream() {
        return rooms.parallelStream();
    }

    public void forEach(Consumer<? super Room> action) {
        rooms.forEach(action);
    }
}
