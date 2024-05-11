package ru.bgdanilov.csv;

import java.util.*;

public class CommandLineArgs {
    private final String[] args;
    private final Commons commons;
    private final ArrayList<String> warningsList = new ArrayList<>();
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

    public CommandLineArgs(String[] args, Commons commons) {
        this.args = args;
        this.commons = commons;
        loadArguments();
    }

    private void loadArguments() {
        if (args.length == 0) { // аргументы не переданы;
            warningsList.add(HELP_MESSAGE);
            return;
        }

        // Ищем дубликаты команд-ключей.
        ArrayList<String> keysDuplicates = getKeysDuplicates(args);

        if (keysDuplicates.size() != 0) {
            warningsList.add(keysDuplicates + ": команды повторяются.");
            return;
        }

        // Считаем количество переданных имен файлов (пока не начнутся ключи).
        int fileNamesAmount = 0;

        for (int i = 0; args[i].charAt(0) != '-'; i++) {
            fileNamesAmount++;
        }

        int startIndex = 0;

        if (fileNamesAmount == 0) {
            warningsList.add("Не переданы имена файлов.");
            return;
        }

        if (fileNamesAmount == 1) { // передан только csv-файл;
            setCsvFileName(args[0]);
            setHtmlFileName(commons.getHtmlExtensionFileName(csvFileName));

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
                        warningsList.add("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String separator = args[keyValueIndex];

                        if (separator.length() == 1) {
                            this.separator = separator.charAt(0);
                        } else {
                            warningsList.add("[" + args[i] + " " + separator + "] разделитель должен состоять из одного символа.");
                        }

                        i++;
                    }
                }
                case "-p" -> {
                    if (keyValueIndex == args.length || args[keyValueIndex].charAt(0) == '-') {
                        warningsList.add("[" + args[i] + "]: не указан префикс выходного файла.");
                    } else {
                        setHtmlFileNamePrefix(args[keyValueIndex]);
                        setHtmlFileName(composeHtmlFileNameWithPrefix());
                        i++;
                    }
                }
                case "-help" -> {
                    warningsList.clear(); //  не станем нагромождать другими сообщениями, справка важнее.
                    warningsList.add(HELP_MESSAGE);
                }
                default -> {
                    warningsList.add("[" + args[i] + "] не является командой.");
                    warningsList.add("Используйте команду -help для вызова справки.");
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

    private String composeHtmlFileNameWithPrefix() {
        if (htmlFileNamePrefix != null) {
            int htmlFileNameStartIndex = htmlFileName.lastIndexOf('/');

            if (htmlFileNameStartIndex == -1) {
                return htmlFileNamePrefix + htmlFileName;
            } else {
                return htmlFileName.substring(0, htmlFileNameStartIndex + 1)
                        + htmlFileNamePrefix
                        + htmlFileName.substring(htmlFileNameStartIndex + 1);
            }
        }

        return htmlFileName;
    }

    public boolean isWarnings() {
        return !warningsList.isEmpty();
    }

    public ArrayList<String> getWarningsList() {
        return warningsList;
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
}