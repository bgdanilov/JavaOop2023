package ru.bgdanilov.csv;

import java.util.ArrayList;
import java.util.HashSet;

public class CsvToHtmlConverterArgsLoader {
    public CsvToHtmlConverterArgs loadArguments(String[] args) throws IllegalArgumentException {
        if (args.length == 0) { // аргументы не переданы;
            throw new IllegalArgumentException("Аргументы не переданы.");
        }

        // Ищем дубликаты команд-ключей.
        ArrayList<String> keysDuplicates = getKeysDuplicates(args);

        if (!keysDuplicates.isEmpty()) {
            throw new IllegalArgumentException(keysDuplicates + ": команды повторяются.");
        }

        // Считаем количество переданных имен файлов (пока не начнутся ключи).
        int fileNamesAmount = 0;

        for (String arg : args) {
            if (arg.charAt(0) != '-') {
                fileNamesAmount++;
            } else {
                break;
            }
        }

        if (fileNamesAmount == 0) {
            throw new IllegalArgumentException("Не переданы имена файлов.");
        }

        if (fileNamesAmount > 2) { // написали перед командами больше двух имен файлов;
            throw new IllegalArgumentException("Передано более двух имен файлов.");
        }

        int startIndex = 0;

        // Создаем объект под аргументы.
        CsvToHtmlConverterArgs converterArgs = new CsvToHtmlConverterArgs();

        if (fileNamesAmount == 1) { // передан только csv-файл;
            converterArgs.setCsvFileName(args[0]);
            converterArgs.setHtmlFileName(FileNameUtilities.composeFileNameWithNewExtension(converterArgs.getCsvFileName(), "html"));

            startIndex = 1;
        }

        if (fileNamesAmount == 2) { // передан еще и html-файл;
            converterArgs.setCsvFileName(args[0]);
            converterArgs.setHtmlFileName(args[1]);

            startIndex = 2;
        }

        // Разбор параметров, аргументов, передаваемых через ключи.
        ArrayList<String> warnings = new ArrayList<>();

        for (int i = startIndex; i < args.length; i++) {
            int keyValueIndex = i + 1;

            switch (args[i]) {
                case "-s" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        warnings.add("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String separator = args[keyValueIndex];

                        if (separator.length() == 1) {
                            converterArgs.setSeparator(separator.charAt(0));
                        } else {
                            warnings.add("[" + args[i] + " " + separator + "] разделитель должен состоять из одного символа.");
                        }

                        i++;
                    }
                }
                case "-p" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        warnings.add("[" + args[i] + "]: не указан префикс выходного файла.");
                    } else {
                        converterArgs.setHtmlFileNamePrefix(args[keyValueIndex]);
                        converterArgs.setHtmlFileName(FileNameUtilities.composeFileNameWithPrefix(converterArgs.getHtmlFileNamePrefix(), converterArgs.getHtmlFileName()));
                        i++;
                    }
                }
                case "-help" -> {
                    warnings.clear();
                    warnings.add(converterArgs.getHelpMessage());
                }

                default ->
                        warnings.add("[" + args[i] + "] не является командой. Используйте команду -help для вызова справки.");
            }
        }

        if (!warnings.isEmpty()) {
            throw new IllegalArgumentException(Utilities.composeMessage(warnings));
        }

        return converterArgs;
    }

    private static ArrayList<String> getKeysDuplicates(String[] settings) {
        // HashSet дубликаты не примет,
        // поэтому дубликаты осядут в settingsDuplicates.
        ArrayList<String> settingsDuplicates = new ArrayList<>();
        HashSet<String> verificationSet = new HashSet<>();

        for (String item : settings) {
            if (item.charAt(0) == '-' && !verificationSet.add(item)) {
                settingsDuplicates.add(item);
            }
        }

        return settingsDuplicates;
    }
}

/* Описание класса.
 * Основной рабочий метод loadArguments класса принимает args[], считывает массив аргументов,
 * создает аргументы для Конвертера.
 * В результате класс создает объект другого класса CsvToHtmlConverterArgs,
 * в который записывает аргументы Конвертера, там они хранятся.
 * В процессе считывания args[], бросаются исключения.
 * Также, некоторые исключительные ситуации (связанные со считыванием ключей),
 * накапливаются в переменной-списке warnings загрузчика
 * и бросаются одним исключением в конце процесса считывания args[].
 */