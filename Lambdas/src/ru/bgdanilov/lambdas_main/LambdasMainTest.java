package ru.bgdanilov.lambdas_main;

import ru.bgdanilov.lambdas.Person;

import java.util.ArrayList;
import java.util.function.Consumer;

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
        ArrayList<Person> persons = new ArrayList<>();

        persons.add(new Person("Петр", 30));
        persons.add(new Person("Иван", 34));
        persons.add(new Person("Сергей", 25));
        persons.add(new Person("Дмитрий", 34));
        persons.add(new Person("Борис", 42));
        persons.add(new Person("Иван", 55));
        persons.add(new Person("Петр", 17));
        persons.add(new Person("Елена", 12));

        for (Person person : persons) {
            System.out.println(person.getName());
        }

        System.out.println("----------");

        persons.forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println(person.getName());
            }
        });

        persons.forEach((Person person) -> System.out.println(person.getName()));

        System.out.println("----------");

        persons.forEach(person -> {
            person.setName(person.getName() + " человек");
            System.out.println(person.getName());
        });
    }
}