package ru.aston.hometask;

import ru.aston.hometask.collection.MyHashMap;
import ru.aston.hometask.collection.MyMap;

public class Main {
    public static void main(String[] args) {
        MyMap<Integer, String> map = new MyHashMap<>();

        map.put(1, "Ann");
        map.put(7, "Mike");
        map.put(12, "Phil");
        map.put(1, "Platon");
        map.put(5, "Vika");

        System.out.println(map.size());
        System.out.println(map.get(7));
        System.out.println(map.remove(1));
        System.out.println(map.size());
    }
}
