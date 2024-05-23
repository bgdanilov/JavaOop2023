package ru.bgdanilov.csv;

import java.util.*;

public class CommandLineArgs {
    private final String[] args;
    private final ArrayList<String> warnings = new ArrayList<>();
    private String csvFileName; // имя файла с путем или без него;
    private String htmlFileName; // имя файла с путем или без него;
    private String htmlFileNamePrefix; // -p указание префикса имени выходного файла;
    private char separator = ','; // -s символ-разделитель;

    private static final String HELP_MESSAGE = """
            Справка.
            ------------------
            Утилита принимает исходный csv-файл и создает результирующий html-файл.
            ---
            Для работы достаточно передать имя исходного файла, который должен находиться в папке с утилитой.
            Результатом будет html-файл с тем же именем, расположенный также в папке с утилитой.
            ---
            Допускается указание расширенного имени (пути) исходного файла.
            Например: /Users/user/Documents/csvFile.csv
            Результирующий файл будет расположен по тому же пути с тем же именем,
            если не указан его иной путь или имя.
            ---
            Если файл с таким именем уже существует, он будет удален и создан новый.
            ---------
            Пример вызова утилиты:
            ---------
            CsvToHtml.jar csvFile.csv htmlFile.html -s ; p prefix-
            ---
            Результат:
            - файл prefix-htmlFile.html в папке с утилитой.
            - использована точка с запятой в качестве разделителя.
            ---------
            Дополнительные команды утилиты:
            ---------
            Порядок следования команд значения не имеет.
            ---
            [-s <значение>] - указание разделителя. <,> или <;>
                              по-умолчанию <,>
            [-p <значение>] - указание префикса html-файла.
            [-help]         - вызов справки об утилите.
            ---------""";

    public CommandLineArgs(String[] args) {
        this.args = args;
        loadArguments();
    }

    public ArrayList<String> getWarnings() {
        return warnings;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }

    public void setHtmlFileName(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }

    public void setHtmlFileNamePrefix(String htmlFileNamePrefix) {
        this.htmlFileNamePrefix = htmlFileNamePrefix;
    }

    public char getSeparator() {
        return separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    private void loadArguments() {
        if (args.length == 0) { // аргументы не переданы;
            warnings.add(HELP_MESSAGE);
            return;
        }

        // Ищем дубликаты команд-ключей.
        ArrayList<String> keysDuplicates = getKeysDuplicates(args);

        if (keysDuplicates.size() != 0) {
            warnings.add(keysDuplicates + ": команды повторяются.");
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

        int startIndex = 0;

        if (fileNamesAmount == 0) {
            warnings.add("Не переданы имена файлов.");
            return;
        }

        if (fileNamesAmount > 2) { // написали перед командами больше двух имен файлов;
            warnings.add("Передано более двух имен файлов.");
            return;
        }

        if (fileNamesAmount == 1) { // передан только csv-файл;
            setCsvFileName(args[0]);
            setHtmlFileName(FileNameUtilities.getHtmlExtensionFileName(csvFileName));

            startIndex = 1;
        }

        if (fileNamesAmount == 2) { // передан еще и html-файл;
            setCsvFileName(args[0]);
            setHtmlFileName(args[1]);

            startIndex = 2;
        }

        // Считываем ключи.
        for (int i = startIndex; i < args.length; i++) {
            int keyValueIndex = i + 1;

            switch (args[i]) {
                case "-s" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        warnings.add("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String separator = args[keyValueIndex];

                        if (separator.length() == 1) {
                            this.separator = separator.charAt(0);
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
                        setHtmlFileNamePrefix(args[keyValueIndex]);
                        setHtmlFileName(FileNameUtilities.composeHtmlFileNameWithPrefix(htmlFileNamePrefix, htmlFileName));
                        i++;
                    }
                }
                case "-help" -> {
                    warnings.clear(); //  не станем нагромождать другими сообщениями, справка важнее.
                    warnings.add(HELP_MESSAGE);
                }
                default -> {
                    warnings.add("[" + args[i] + "] не является командой.");
                    warnings.add("Используйте команду -help для вызова справки.");
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

    public boolean hasWarnings() {
        return !warnings.isEmpty();
    }
}