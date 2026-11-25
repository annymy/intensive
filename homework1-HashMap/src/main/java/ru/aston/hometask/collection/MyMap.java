package ru.aston.hometask.collection;

public interface MyMap<K, V>{
    void put(K key, V value);
    V get(K key);
    V remove(K key);

    int size();
    void clear();
    boolean containsKey(K key);
    boolean isEmpty();
}
