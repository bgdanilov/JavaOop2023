package ru.bgdanilov.csv;

public class CsvToHtmlConverterArgs {
    private String csvFileName; // имя файла с путем или без него;
    private String htmlFileName; // имя файла с путем или без него;

    private String htmlFileNamePrefix; // -p указание префикса имени выходного файла;
    private char separator = ','; // -s символ-разделитель;

    private final String helpMessage = """
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


    public CsvToHtmlConverterArgs() {
    }
//    public CsvToHtmlConverterArgs(String csvFileName, String htmlFileName, String htmlFileNamePrefix, char separator) {
//        this.csvFileName = csvFileName;
//        this.htmlFileName = htmlFileName;
//        this.htmlFileNamePrefix = htmlFileNamePrefix;
//        this.separator = separator;
//    }

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

    public String getHtmlFileNamePrefix() {
        return htmlFileNamePrefix;
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

    public String getHelpMessage() {
        return helpMessage;
    }
}