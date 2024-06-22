package ru.bgdanilov.csv;

public class FileNameUtilities {
    // 1. Получить только имя файла (без расширения).
    public static String getFileNameOnly(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        return (dotIndex == -1)
                ? fileName
                : fileName.substring(0, dotIndex);
    }

    // 2. Создает имя файла с переданным расширением вместо текущего, или добавляет его при отсутствии.
    public static String composeFileNameWithNewExtension(String fileName, String newExtension) {
        return getFileNameOnly(fileName) + "." + newExtension;
    }

    // 3. Создает имя файла с переданным префиксом или возвращает исходное имя при отсутствии префикса.
    public static String composeFileNameWithPrefix(String fileNamePrefix, String fileName) {
        if (fileNamePrefix == null) {
            return fileName;
        }

        int htmlFileNameStartIndex = fileName.lastIndexOf('/');

        if (htmlFileNameStartIndex == -1) {
            return fileNamePrefix + fileName;
        }

        return fileName.substring(0, htmlFileNameStartIndex + 1)
                + fileNamePrefix
                + fileName.substring(htmlFileNameStartIndex + 1);
    }
}