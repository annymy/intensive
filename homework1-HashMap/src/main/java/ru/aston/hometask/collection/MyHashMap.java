package ru.aston.hometask.collection;

import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private Node<K, V>[] table;
    private int size;
    private int currArrLen;
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public MyHashMap() {
    }

    @Override
    public void put(K key, V value) {
        int hash = hash(key);

        if (table == null || currArrLen == 0 || size >= currArrLen * DEFAULT_LOAD_FACTOR) {
            resize();
        }

        int idx = calcIdx(hash);

        if (table[idx] == null) {
            table[idx] = new Node<>(key, value, hash, null);
            size++;
        } else {
            Node<K, V> node = table[idx];
            Node<K, V> prev = node;
            while (node != null) {
                if(node.getHash() == hash && Objects.equals(node.getKey(), key)){
                    node.setValue(value);
                    return;
                }
                prev = node;
                node = node.getNext();
            }
            prev.setNext(new Node<>(key, value, hash, null));
            size++;
        }

    }

    private int hash(K key){
        if(key == null){
            throw new NullPointerException("Ключ не может быть null");
        }
        int hash = key.hashCode();
        hash = hash^(hash>>>16);
        return hash;
    }

    private int calcIdx(int hash){
        return (hash & 0x7FFFFFFF) % currArrLen;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);

        if(table == null) return null;

        int idx = calcIdx(hash);

        Node<K, V> node = table[idx];

        while (node!=null){
            if(node.getHash() == hash && Objects.equals(key, node.getKey())){
                return node.getValue();
            }
            node = node.getNext();
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        if(table == null) return null;

        int idx = calcIdx(hash);

        Node<K, V> node = table[idx];
        Node<K, V> prev = null;
        while (node!=null){
            if(node.getHash() == hash && Objects.equals(key, node.getKey())){
                size--;
                V value = node.getValue();
                if(prev!=null){
                    prev.setNext(node.getNext());
                } else {
                    table[idx] = node.getNext();
                }
                return value;
            }
            prev = node;
            node = node.getNext();
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        table = null;
        size = 0;
        currArrLen = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = hash(key);
        if(table == null) return false;

        int idx = calcIdx(hash);

        Node<K, V> node = table[idx];

        while (node!=null){
            if(node.getHash() == hash && Objects.equals(node.getKey(), key)){
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
            currArrLen = DEFAULT_INITIAL_CAPACITY;
            return;
        }

        if(currArrLen>=MAXIMUM_CAPACITY){
            throw new IllegalArgumentException("Размер таблицы достиг максимума");
        }
        int newSize = currArrLen*2;
        Node<K, V>[] newTable = new Node[newSize];

        for (int i = 0; i < currArrLen; i++) {
            Node<K, V> node = table[i];

            while(node!=null){
                Node<K,V> next = node.getNext();
                int newIdx = (node.getHash() & 0x7FFFFFFF) % newSize;
                node.setNext(newTable[newIdx]);
                newTable[newIdx] = node;

                node = next;
            }
        }

        table = newTable;
        currArrLen = newSize;
    }

    public int getCurrArrLen() {
        return currArrLen;
    }

    static class Node<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private Node<K,V> next;

        public Node(K key, V value, int hash, Node<K,V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        void setValue(V value) {
            this.value = value;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return value;
        }

        Node<K, V> getNext() {
            return next;
        }

        int getHash() {
            return hash;
        }

        void setNext(Node<K, V> next) {
            this.next = next;
        }
    }
}
