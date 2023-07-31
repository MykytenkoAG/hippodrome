import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HorseTest {

    //1. Класс Horse:
    //конструктор
    //Проверить, что при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException. Для этого нужно воспользоваться методом assertThrows;
    @Test
    public void checkIfNameIsNull(){
        assertThrows(IllegalArgumentException.class, ()->new Horse(null,99,99));
    }

    //Проверить, что при передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение "Name cannot be null.". Для этого нужно получить сообщение из перехваченного исключения и воспользоваться методом assertEquals;
    @Test
    public void checkMessageIfNameIsNull(){
        try {
            new Horse(null,99,99);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException. Чтобы выполнить проверку с разными вариантами пробельных символов, нужно сделать тест параметризованным;
    @ParameterizedTest
    @ValueSource(strings = { "", "\s\t", "\n" })
    public void checkIfNameIsEmpty(String name){
        assertThrows(IllegalArgumentException.class, ()->new Horse(name,99,99));
    }

    //Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), выброшенное исключение будет содержать сообщение "Name cannot be blank.";
    @ParameterizedTest
    @ValueSource(strings = { "", "\s\t", "\n" })
    public void checkMessageIfNameIsEmpty(String name){
        try {
            new Horse(name, 99, 99);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Name cannot be blank.",e.getMessage());
        }
    }

    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, будет выброшено IllegalArgumentException;
    @ParameterizedTest
    @ValueSource(doubles = { -1, -99, -1000 })
    public void checkNegativeSpeed(double value){
        assertThrows(IllegalArgumentException.class, ()->new Horse("new Horse",value,99));
    }

    //Проверить, что при передаче в конструктор вторым параметром отрицательного числа, выброшенное исключение будет содержать сообщение "Speed cannot be negative.";
    @ParameterizedTest
    @ValueSource(doubles = { -1, -99, -1000 })
    public void checkMessageNegativeSpeed(double value){
        try {
            new Horse("new Horse", value, 99);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Speed cannot be negative.",e.getMessage());
        }
    }

    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException;
    @ParameterizedTest
    @ValueSource(doubles = { -1, -99, -1000 })
    public void checkNegativeDistance(double value){
        assertThrows(IllegalArgumentException.class, ()->new Horse("new Horse",value,99));
    }

    //Проверить, что при передаче в конструктор третьим параметром отрицательного числа, выброшенное исключение будет содержать сообщение "Distance cannot be negative.";
    @ParameterizedTest
    @ValueSource(doubles = { -1, -99, -1000 })
    public void checkMessageNegativeDistance(double value){
        try {
            new Horse("new Horse", 99, value);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Distance cannot be negative.",e.getMessage());
        }
    }

    //метод getName
    //Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор;
    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("newHorse", 99, 99);
        Field field = Horse.class.getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("newHorse",(String)field.get(horse));
    }

    //метод getSpeed
    //Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор;
    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("newHorse", 99, 99);
        Field field = Horse.class.getDeclaredField("speed");
        field.setAccessible(true);
        assertEquals(99,(double)field.get(horse));
    }

    //метод getDistance
    //Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор;
    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("newHorse", 99, 100);
        Field field = Horse.class.getDeclaredField("distance");
        field.setAccessible(true);
        assertEquals(100,(double)field.get(horse));
    }
    //Проверить, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами;
    @Test
    public void getZeroDistance() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("newHorse", 99);
        Field field = Horse.class.getDeclaredField("distance");
        field.setAccessible(true);
        assertEquals(0,(double)field.get(horse));
    }

    //метод move
    //Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9. Для этого нужно использовать MockedStatic и его метод verify;
    @Test
    public void moveGetRandomDouble(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("newHorse", 99, 100).move();
            mockedStatic.verify(()->Horse.getRandomDouble(0.2,0.9));
        }
    }

    //Проверить, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9).
    //Для этого нужно замокать getRandomDouble, чтобы он возвращал определенные значения, которые нужно задать параметризовав тест.
    @ParameterizedTest
    @ValueSource(doubles = {0.1,0.2,0.5,100})
    public void moveGetRandomDoubleParameterized(double value){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("newHorse", 99, 100);
            mockedStatic.when(()->Horse.getRandomDouble(0.2,0.9)).thenReturn(value);
            horse.move();
            assertEquals(100+99*value,horse.getDistance());
        }
    }

}
