package ru.bgdanilov.csv;

import java.util.Objects;

public class FileNameUtilities {
    // 1. Получить расширение файла.
    public static String getFileNameExtension (String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

    // 2. Получить только имя файла (без расширения).
    public static String getFileNameOnly (String fileName) {
        int dotIndex = fileName.lastIndexOf('.');

        return (dotIndex == -1 || dotIndex <= fileName.length() - 1)
                ? fileName : fileName.substring(0, dotIndex);
    }

    // 3. Создает имя файла с переданным расширением вместо текущего, или добавляет его при отсутствии.
    public static String getNewExtensionFileName(String fileName, String newExtension) {
        return getFileNameOnly(fileName) + newExtension;
    }

    public static String composeHtmlFileNameWithPrefix(String htmlFileNamePrefix, String htmlFileName) {
        if (htmlFileNamePrefix == null) {
            return htmlFileName;
        }

        int htmlFileNameStartIndex = htmlFileName.lastIndexOf('/');

        if (htmlFileNameStartIndex == -1) {
            return htmlFileNamePrefix + htmlFileName;
        }

        return htmlFileName.substring(0, htmlFileNameStartIndex + 1)
                + htmlFileNamePrefix
                + htmlFileName.substring(htmlFileNameStartIndex + 1);

    }

//    public static String getCheckedExtensionFileName(String fileName, String extension) {
//        int dotIndex = fileName.lastIndexOf('.');
//        String fileExtension = null;
//        String fileNameOnly = null;
//
//        if (dotIndex != -1) {
//            fileExtension = fileName.substring(dotIndex).toLowerCase();
//            fileNameOnly = fileName.substring(0, dotIndex);
//        }
//
//        if (Objects.equals(fileExtension, extension)) {
//            return fileNameOnly + fileExtension;
//        }
//
//        return null;
//    }
}