package ru.bgdanilov.csv;

import java.util.*;

public class Settings {
    private final String utilityHome = System.getProperty("user.dir"); // путь, где лежит утилита; там лежит исходный файл csv;
    private final String userHome = System.getProperty("user.home"); // домашний каталог пользователя;
    private String csvFileName;
    private String htmlFileNamePrefix; // -p указание префикса имени выходного файла;
    private String htmlFileUserPath; // -o указание пути для выходного файла, относительно userHome;
    private char userSeparator = ','; // -s символ-разделитель;
    private final Messages messages; // класс-аккумулятор сообщений процесса работы утилиты;

    public Settings(Messages messages) {
        this.messages = messages;
    }

    public void loadSettings(String[] args) {
        String helpMessage = """
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

        List<String> settings = Arrays.asList(args); // список аргументов;

        if (settings.size() == 0) {                  // аргументы не переданы;
            messages.addMessage(helpMessage);
            return;
        }

        // Ищем дубликаты команд.
        ArrayList<String> settingsDuplicates = getKeysDuplicates(args);

        if (settingsDuplicates.size() != 0) {
            messages.addMessage(settingsDuplicates + ": команды повторяются.");
            return;
        }

        // Имя csv-файла всегда передается с нулевым индексом аргумента.
        int startIndex = 0;

        if (args[startIndex].charAt(0) == '-') {
            if (args[startIndex].equals("-help")) {
                messages.addMessage(helpMessage);
                return;
            }

            messages.addMessage("Не указано имя CSV-файла.");
        } else {
            setCsvFileName(args[0]);
            startIndex = 1; // имя csv-файла прыгаем дальше;
        }

        for (int i = startIndex; i < settings.size(); i++) {
            int keyValueIndex = i + 1;

            switch (args[i]) {
                case "-s" -> {
                    if (keyValueIndex == settings.size() || args[keyValueIndex].charAt(0) == '-') {
                        messages.addMessage("[" + args[i] + "]: не указан символ-разделитель.");
                    } else {
                        String userSeparator = args[keyValueIndex];

                        if (userSeparator.equals(";") || userSeparator.equals(",")) {
                            setUserSeparator(userSeparator.charAt(0));
                        } else {
                            messages.addMessage("[" + args[i] + " " + userSeparator + "] не верный разделитель.");
                        }

                        i++;
                    }
                }
                case "-o" -> {
                    if (keyValueIndex == settings.size() || args[keyValueIndex].charAt(0) == '-') {
                        messages.addMessage("[" + args[i] + "]: не указан путь выходного файла.");
                    } else {
                        setHtmlFileUserPath(args[keyValueIndex] + "/");
                        i++;
                    }
                }
                case "-p" -> {
                    if (keyValueIndex == settings.size() || args[keyValueIndex].charAt(0) == '-') {
                        messages.addMessage("[" + args[i] + "]: не указан префикс выходного файла.");
                    } else {
                        setHtmlFileNamePrefix(args[keyValueIndex]);
                        i++;
                    }
                }
                case "-help" -> {
                    messages.clearMessages(); //  не станем нагромождать другими сообщениями, справка важнее.
                    messages.addMessage(helpMessage);
                }
                default -> {
                    messages.addMessage("[" + args[i] + "] не является командой.");
                    messages.addMessage("Используйте команду -help для вызова справки.");
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

    public String composeHtmlFileName() {
        String htmlFileName = csvFileName.replace(".csv", ".html");

        if (htmlFileNamePrefix != null) {
            return htmlFileNamePrefix + htmlFileName;
        } else {
            return htmlFileName;
        }
    }

    // По-умолчанию - в каталог с программой.
    // Или домашняя папка пользователя + указанный путь.
    public String composeHtmlFilePath() {
        if (htmlFileUserPath != null) {
            return userHome + htmlFileUserPath;
        } else {
            return utilityHome + "/";
        }
    }

/*
    Не используется.
    public String getUserHome() {
        return userHome;
    }
*/

    public String getUtilityHome() {
        return utilityHome;
    }

/*
 Не используется.
    public String getHtmlFileUserPath() {
        return htmlFileUserPath;
    }
*/

    public void setHtmlFileUserPath(String htmlFileUserPath) {
        this.htmlFileUserPath = htmlFileUserPath;
    }

    public char getUserSeparator() {
        return userSeparator;
    }

    public void setUserSeparator(char userSeparator) {
        this.userSeparator = userSeparator;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = "/" + csvFileName;
    }

/*
 Не используется.
    public String getHtmlFileNamePrefix() {
        return htmlFileNamePrefix;
    }
*/

    public void setHtmlFileNamePrefix(String htmlFileNamePrefix) {
        this.htmlFileNamePrefix = htmlFileNamePrefix;
    }
}