package ru.bgdanilov.lambdas;

import java.util.Arrays;
import java.util.List;


/* Это файл для экспериментов.
* ----------------------------
1. Иногда нам нужно передать функцию в функцию.
2. Но в Java функции не могут быть типом данных.
3. Передать функцию можно только внутри объекта некого класса.
4. List<?> - ? значит, что мы не ограничиваем тип Generic, можно передавать разные типы.
5. Метод filter ожидает аргумент: интерфейс Predicate<T> {boolean test(T t)}
   мы туда передаем лямбду: x -> x > 0
   получаем boolean
 */

public class LambdasMainTest {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Петр", 30),
                new Person("Иван", 34),
                new Person("Сергей", 25),
                new Person("Дмитрий", 34),
                new Person("Борис", 45),
                new Person("Иван", 55),
                new Person("Петр", 17),
                new Person("Елена", 12));

        for (Person person : persons) {
            System.out.println(person.getName());
        }

        System.out.println("----------");

        /* Закомментирую, чтобы не было Warning. Это для понимания сути.
         persons.forEach(new Consumer<Person>() {
             @Override
             public void accept(Person person) {
                 System.out.println(person.getName());
             }
         });
        */

        persons.forEach((Person person) -> System.out.println(person.getName()));

        System.out.println("----------");

        persons.forEach(person -> {
            person.setName(person.getName() + " человек");
            System.out.println(person.getName());
        });
    }
}