package ru.bgdanilov.lambdas_main;

import ru.bgdanilov.lambdas.Person;

import java.util.*;
import java.util.stream.Collectors;

public class LambdasMain {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Петр", 30));
        persons.add(new Person("Иван", 34));
        persons.add(new Person("Сергей", 25));
        persons.add(new Person("Дмитрий", 34));
        persons.add(new Person("Борис", 42));
        persons.add(new Person("Иван", 55));
        persons.add(new Person("Петр", 17));
        persons.add(new Person("Елена", 12));

        // А. Получить список уникальных имен.
        List<String> uniqueNames = persons.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        // Б. Вывести список уникальных имен в формате Имена: Имя1, Имя2.
        System.out.println("Б. Вывести список уникальных имен в формате Имена: Имя1, Имя2.");
        System.out.print("Имена: "
                + uniqueNames.stream().collect(Collectors.joining(", "))
                + ".\n");

        // IDE предлагает так сделать.
        System.out.print("Имена: "
                + String.join(", ", uniqueNames)
                + ".\n");
        System.out.println();

        // В. Получить список людей младше 18, посчитать для них средний возраст.
        System.out.println("В. Получить список людей младше 18, посчитать для них средний возраст.");
        List<Person> underEighteenPersons = persons.stream()
                .filter(person -> person.getAge() < 18)
                .toList();

        System.out.println(underEighteenPersons);

        System.out.println("Средний возраст персон младше 18 лет: "
                + underEighteenPersons.stream()
                .mapToInt(Person::getAge)
                .average().orElse(0)
                + " лет.");
        System.out.println();

        // Г. При помощи группировки получить Map, в котором ключи – имена, а значения – средний возраст.
        System.out.println("Г. При помощи группировки получить Map, в котором ключи – имена, а значения – средний возраст.");
        Map<String, Double> averageAgeByNames = persons.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println(averageAgeByNames);
        System.out.println();

        // Д. Получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста.
        System.out.println("Д. Получить людей, возраст которых от 20 до 45, вывести в консоль их имена в порядке убывания возраста.");
        List<Person> personsAgedBetween20And45 = persons.stream()
                .filter(person -> person.getAge() < 45)
                .filter(person -> person.getAge() > 20)
                .toList();

        System.out.println(personsAgedBetween20And45);

        System.out.print("Имена порядке убывания возраста: " + personsAgedBetween20And45.stream()
                .sorted((person2, person1) -> person1.getAge() - person2.getAge())
                .map(Person::getName)
                .collect(Collectors.joining(", "))
                + ".\n");
    }
}