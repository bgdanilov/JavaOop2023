package ru.bgdanilov.lambdas;

import java.util.*;
import java.util.stream.Collectors;

public class LambdasMain {
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

        // А. Получить список уникальных имен.
        System.out.println("А. Получить список уникальных имен.");
        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println(uniqueNames);
        System.out.println();

        // Б. Вывести список уникальных имен в формате Имена: Имя1, Имя2.
        System.out.println("Б. Вывести список уникальных имен в формате Имена: Имя1, Имя2.");
        String uniqueNamesLine = "Имена: " + String.join(", ", uniqueNames) + ".";

        System.out.println(uniqueNamesLine);
        System.out.println();

        // В. Получить список людей младше 18, посчитать для них средний возраст.
        System.out.println("В. Получить список людей младше 18, посчитать для них средний возраст.");
        List<Person> under18Persons = persons.stream()
                .filter(p -> p.getAge() < 18)
                .toList();

        OptionalDouble personsUnder18average = under18Persons.stream()
                .mapToInt(Person::getAge)
                .average();

        personsUnder18average.ifPresentOrElse(
                averageAge -> System.out.println("Средний возраст персон младше 18 лет: " + averageAge + "."),
                () -> System.out.println("Людей младше 18 лет нет.")
        );
        System.out.println();

        // Г. При помощи группировки получить Map, в котором ключи – имена, а значения – средний возраст.
        System.out.println("Г. При помощи группировки получить Map, в котором ключи – имена, а значения – средний возраст.");
        Map<String, Double> averageAgesByNames = persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println(averageAgesByNames);
        System.out.println();

        // Д. Получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста.
        System.out.println("Д. Получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста.");
        String personsAgedBetween20And45Line = "Имена порядке убывания возраста: " + persons.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                // Так тоже можно.
                //.sorted((person1, person2) -> person2.getAge() - person1.getAge())
                .sorted(Collections.reverseOrder(Comparator.comparingInt(Person::getAge)))
                .map(Person::getName)
                .collect(Collectors.joining(", ")) + ".";

        System.out.println(personsAgedBetween20And45Line);
    }
}