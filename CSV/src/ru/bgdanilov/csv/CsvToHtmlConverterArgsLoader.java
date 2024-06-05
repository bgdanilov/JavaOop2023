package ru.bgdanilov.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CsvToHtmlConverterArgsLoader {
    String[] args;
    CsvToHtmlConverterArgs converterArgs;

    public CsvToHtmlConverterArgsLoader(String[] args, CsvToHtmlConverterArgs arguments) {
        this.args = args;
        this.converterArgs = arguments;
    }

    public void loadArguments() throws IOException {
        if (args.length == 0) { // аргументы не переданы;
            throw new IOException(converterArgs.getHelpMessage());
        }

        // Ищем дубликаты команд-ключей.
        ArrayList<String> keysDuplicates = getKeysDuplicates(args);

        if (keysDuplicates.size() != 0) {
            throw new IOException(keysDuplicates + ": команды повторяются.");
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
            throw new IOException("Не переданы имена файлов.");
        }

        if (fileNamesAmount > 2) { // написали перед командами больше двух имен файлов;
            throw new IOException("Передано более двух имен файлов.");
        }

        int startIndex = 0;

        if (fileNamesAmount == 1) { // передан только csv-файл;
            converterArgs.setCsvFileName(args[0]);
            converterArgs.setHtmlFileName(FileNameUtilities.composeNewExtensionFileName(converterArgs.getCsvFileName(), "html"));

            startIndex = 1;
        }

        if (fileNamesAmount == 2) { // передан еще и html-файл;
            converterArgs.setCsvFileName(args[0]);
            converterArgs.setHtmlFileName(args[1]);

            startIndex = 2;
        }

        // Считываем ключи.
        for (int i = startIndex; i < args.length; i++) {
            int keyValueIndex = i + 1;

            switch (args[i]) {
                case "-s" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        throw new IOException("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String separator = args[keyValueIndex];

                        if (separator.length() == 1) {
                            converterArgs.setSeparator(separator.charAt(0));
                        } else {
                            throw new IOException("[" + args[i] + " " + separator + "] разделитель должен состоять из одного символа.");
                        }

                        i++;
                    }
                }
                case "-p" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        throw new IOException("[" + args[i] + "]: не указан префикс выходного файла.");
                    } else {
                        converterArgs.setHtmlFileNamePrefix(args[keyValueIndex]);
                        converterArgs.setHtmlFileName(FileNameUtilities.composeFileNameWithPrefix(converterArgs.getHtmlFileNamePrefix(), converterArgs.getHtmlFileName()));
                        i++;
                    }
                }
                case "-help" -> throw new IOException(converterArgs.getHelpMessage());
                default ->
                        throw new IOException("[" + args[i] + "] не является командой. Используйте команду -help для вызова справки.");
            }
        }
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