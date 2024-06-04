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
        loadArguments();
    }

    public void loadArguments() throws IOException {
        if (args.length == 0) { // аргументы не переданы;
            throw new IOException("Аргументы не переданы.");
            System.out.println(HE);
            //warnings.add(HELP_MESSAGE);
        }

        // Ищем дубликаты команд-ключей.
        ArrayList<String> keysDuplicates = getKeysDuplicates(args);

        if (keysDuplicates.size() != 0) {
            //warnings.add(keysDuplicates + ": команды повторяются.");
            return;
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
            //warnings.add("Не переданы имена файлов.");
            return;
        }

        if (fileNamesAmount > 2) { // написали перед командами больше двух имен файлов;
           // warnings.add("Передано более двух имен файлов.");
            return;
        }

        int startIndex = 0;

        if (fileNamesAmount == 1) { // передан только csv-файл;
            converterArgs.setCsvFileName(args[0]);
            converterArgs.setHtmlFileName(FileNameUtilities.getNewExtensionFileName(converterArgs.getCsvFileName(), "html"));

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
                        //warnings.add("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String separator = args[keyValueIndex];

                        if (separator.length() == 1) {
                            converterArgs.setSeparator(separator.charAt(0));
                        } else {
                           // warnings.add("[" + args[i] + " " + separator + "] разделитель должен состоять из одного символа.");
                        }

                        i++;
                    }
                }
                case "-p" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        //warnings.add("[" + args[i] + "]: не указан префикс выходного файла.");
                    } else {
                        converterArgs.setHtmlFileNamePrefix(args[keyValueIndex]);
                        converterArgs.setHtmlFileName(FileNameUtilities.composeHtmlFileNameWithPrefix(converterArgs.getHtmlFileNamePrefix(), converterArgs.getHtmlFileName()));
                        i++;
                    }
                }
                case "-help" -> {
                   // warnings.clear(); //  не станем нагромождать другими сообщениями, справка важнее.
                    //warnings.add(HELP_MESSAGE);
                }
                default -> {
                    //warnings.add("[" + args[i] + "] не является командой.");
                    //warnings.add("Используйте команду -help для вызова справки.");
                }
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
