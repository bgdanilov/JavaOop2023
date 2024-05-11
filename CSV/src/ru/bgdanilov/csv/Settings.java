package ru.bgdanilov.csv;

import java.util.*;

public class Settings {
    private final String[] args;
    private final ArrayList<String> warningsList = new ArrayList<>();
    private String csvFileName; // имя файла с путем или без него;
    private String htmlFileName; // имя файла с путем или без него;
    private String htmlFileNamePrefix; // -p указание префикса имени выходного файла;
    private char separator = ','; // -s символ-разделитель;
    private static final String HELP_MESSAGE = """
            Справка.
            ----------
            Исходный csv-файл должен находиться в папке с утилитой.
            Необходимо указать имя csv-файла в строке вызова утилиты.
            Например: CsvToHtml.jar my_csv_file.csv
            ---
            Результирующий html-файл по-умолчанию будет иметь имя html.html
            и будет создан также в папке с утилитой.
            Если файл с таким именем уже существует, он будет удален и создан новый.
            ------
            Дополнительные команды утилиты:
            ---------
            Пример вызова утилиты:
            CsvToHtml.jar my_csv_file.csv -s ; p prefix- o /Documents/Files
            Порядок следования команд значения не имеет.
            ---
            [-s <значение>] - указание разделителя. <,> или <;>
                              по-умолчанию <,>
            [-p <значение>] - указание префикса html-файла.
            [-o <значение>] - указание пути для создания html-файла;
                              путь указывается относительно домашней папки пользователя;
                              указанный путь должен существовать.
            [-help] - вызов справки об утилите.
            ------""";

    public Settings() {
        this.args = null;
    }

    public Settings(String[] args) {
        this.args = args;
        loadSettings();
    }

    public void loadSettings() {
        if (args.length == 0) { // аргументы не переданы;
            warningsList.add(HELP_MESSAGE);
            return;
        }

        // Ищем дубликаты команд.
        ArrayList<String> settingsDuplicates = getKeysDuplicates(args);

        if (settingsDuplicates.size() != 0) {
            warningsList.add(settingsDuplicates + ": команды повторяются.");
            return;
        }

        // Считываем имена файлов (два подряд максимум) пока не начнутся ключи.
        int fileNamesAmount = 0;

        for (int i = 0; i < 2 || args[i].charAt(0) != '-'; i++) {
            fileNamesAmount++;
        }

        int startIndex = 0;

        if (fileNamesAmount >= 1) { // передан только csv-файл;
            setCsvFileName(args[0]);
            startIndex = 1;
        }

        if (fileNamesAmount == 2) { // передан еще и html-файл;
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

    public String composeHtmlFileNameWithPrefix() {
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

    public String composeHtmlFileName(String csvFileName) {
        return csvFileName.replace(".csv", ".html");
    }

    public boolean isWarnings() {
        return !warningsList.isEmpty();
    }

    public void printWarnings() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        for (String item : warningsList) {
            sb.append(item).append(lineSeparator);
        }

        sb.setLength(sb.length() - 1);

        System.out.println(sb);
    }

    public char getSeparator() {
        return separator;
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
}