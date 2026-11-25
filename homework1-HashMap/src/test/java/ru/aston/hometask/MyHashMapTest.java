package ru.aston.hometask;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.collection.MyHashMap;
import ru.aston.hometask.collection.MyMap;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class MyHashMapTest {
    @Test
    @DisplayName("Добавление новых элементов в коллекцию увеличивает ее размер")
    void whenPutElement_thenItsAdded(){
        MyMap<String, String> map = new MyHashMap<>();
        map.put("Key1", "Val1");

        assertNotNull(map);
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("Проверка корректности добавления большого количества элементов")
    void whenPut1000Elements_thenSizeIs1000AndArrayResized(){
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }

        assertEquals(1000, map.size());
        assertNotEquals(16, map.getCurrArrLen());
    }

    @Test
    @DisplayName("Удаление элемента из коллекции возвращает удаляемое значение и уменьшает её размер")
    void whenRemove_thenGetValueAndSizeDecrease() {
        MyMap<Integer, Integer> map = new MyHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        assertThat(map.remove(1)).isEqualTo(1);
        assertThat(map.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Удаление одного элемента не меняет остальную коллекцию")
    void whenRemove_thenMustNotBeEmpty() {
        MyMap<Integer, Integer> map = new MyHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.remove(1);
        assertThat(map.get(2)).isEqualTo(2);
        assertThat(map.get(3)).isEqualTo(3);
    }

    @Test
    @DisplayName("При добавлении двух ключей с одинаковым хэш-кодом возникает коллизия")
    void whenTwoKeysWithSameHashCode_thenCollision(){
        MyMap<String, Integer> map = new MyHashMap<>();
        String str1 = "Aa", str2 = "BB";
        map.put(str1, 1);
        map.put(str2, 2);

        assertEquals(str1.hashCode(), str2.hashCode());
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("Ключи не должны повторяться")
    void whenTwoKeysTheSame_thenFirstReplacedSecond(){
        MyMap<String, Integer> map = new MyHashMap<>();
        map.put("one", 1);
        map.put("one", 2);

        assertEquals(2, map.get("one"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("При удалении уменьшается размер коллекции")
    void whenRemove_thenSizeDown(){
        MyMap<Integer, String> map = new MyHashMap<>();

        map.put(1, "Ann");
        map.put(7, "Mike");
        map.put(12, "Phil");
        map.put(5, "Vika");

        int size1 = map.size();

        map.remove(5);

        assertNotEquals(size1, map.size());
    }

    @Test
    @DisplayName("Добавление элемента с null ключом недопустимо")
    void whenPutKeyIsNull_thenNPE(){
        MyMap<Integer, String> map = new MyHashMap<>();

        assertThatThrownBy(()-> map.put(null, "lalala"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Получение элемента с null ключом недопустимо")
    void whenGetKeyIsNull_thenNPE(){
        MyMap<Integer, String> map = new MyHashMap<>();

        assertThatThrownBy(()-> map.get(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Удаление элемента с null ключом недопустимо")
    void whenRemoveKeyIsNull_thenNPE(){
        MyMap<Integer, String> map = new MyHashMap<>();

        assertThatThrownBy(()-> map.remove(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Проверка элемента с null ключом недопустимо")
    void whenContainsKeyIsNull_thenNPE(){
        MyMap<Integer, String> map = new MyHashMap<>();

        assertThatThrownBy(()-> map.containsKey(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Очищение таблицы стирает ее содержимое")
    void whenClear_thenMapIsEmpty(){
        MyMap<Integer, Integer> map = new MyHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);

        map.clear();

        assertThat(map).isNotNull();

        assertThat(map.size()).isEqualTo(0);
        assertThat(map.isEmpty()).isTrue();

        assertThat(map.get(1)).isNull();
    }

    @Test
    @DisplayName("Если ключ содержится, значит contains возвращает true")
    void whenKeyContains_thenTrue(){
        MyMap<Integer, Integer> map = new MyHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);

        assertThat(map.containsKey(1)).isTrue();
        assertThat(map.containsKey(2)).isTrue();
        assertThat(map.containsKey(3)).isTrue();
    }

    @Test
    @DisplayName("Если ключ не содержится, значит contains возвращает false")
    void whenKeyDoesntContains_thenFalse(){
        MyMap<Integer, Integer> map = new MyHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);

        assertThat(map.containsKey(10)).isFalse();
        assertThat(map.containsKey(12)).isFalse();
    }

    @Test
    @DisplayName("Пустая таблица isEmpty")
    void whenMapEmpty_thenIsEmptyTrue(){
        MyMap<Integer, Integer> map = new MyHashMap<>();

        assertThat(map.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Таблица с элементами не isEmpty")
    void whenMapDoesntEmpty_thenIsEmptyFalse(){
        MyMap<Integer, Integer> map = new MyHashMap<>();
        map.put(1, 1);

        assertThat(map.isEmpty()).isFalse();
    }
}
