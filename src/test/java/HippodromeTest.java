import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    //2. Класс Hippodrome:
    //Конструктор
    //Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException;
    @Test
    public void checkIfHorsesNull(){
        assertThrows(IllegalArgumentException.class, ()->new Hippodrome(null));
    }
    //Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение "Horses cannot be null.";
    @Test
    public void checkMessageIfHorsesNull(){
        try {
            new Hippodrome(null);
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Horses cannot be null.", e.getMessage());
        }
    }
    //Проверить, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;
    @Test
    public void checkIfHorsesListEmpty(){
        assertThrows(IllegalArgumentException.class, ()->new Hippodrome(new ArrayList<>()));
    }
    //Проверить, что при передаче в конструктор пустого списка, выброшенное исключение будет содержать сообщение "Horses cannot be empty.";
    @Test
    public void checkMessageIfHorsesListEmpty(){
        try {
            new Hippodrome(new ArrayList<>());
            fail();
        } catch (IllegalArgumentException e){
            assertEquals("Horses cannot be empty.", e.getMessage());
        }
    }

    //метод getHorses
    //Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности,
    // что и список который был передан в конструктор.
    // При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;
    @Test
    public void getHorses(){
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horseList.add(new Horse("horse"+i,i));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        assertEquals(horseList,hippodrome.getHorses());
    }

    //метод move
    //Проверить, что метод вызывает метод move у всех лошадей.
    // При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и воспользуйся методом verify.
    @Test
    public void move(){
        List<Horse> horseList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horseList.add(mock(Horse.class));
        }
        
        new Hippodrome(horseList).move();

        for (int i = 0; i < horseList.size(); i++) {
            verify(horseList.get(i)).move();
        }
    }

    //метод getWinner
    //Проверить, что метод возвращает лошадь с самым большим значением distance.
    @Test
    public void getWinner(){
        Horse horse1 = new Horse("horse1",1,1);
        Horse horse2 = new Horse("horse2",1,0.999);
        Horse horse3 = new Horse("horse3",1,1.0001);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1,horse2,horse3));
        assertSame(horse3,hippodrome.getWinner());
    }

}
