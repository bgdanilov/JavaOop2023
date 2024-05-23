package ru.bgdanilov.csv;

import java.util.Objects;

public class FileNameUtilities {
    public static String getHtmlExtensionFileName(String csvFileName) {
        return csvFileName.replace(".csv", ".html");
    }

    public static String composeHtmlFileNameWithPrefix(String htmlFileNamePrefix, String htmlFileName) {
        if (htmlFileNamePrefix == null) {
            return htmlFileName;
        }

        int htmlFileNameStartIndex = htmlFileName.lastIndexOf('/');

        if (htmlFileNameStartIndex == -1) {
            return htmlFileNamePrefix + htmlFileName;
        } else {
            return htmlFileName.substring(0, htmlFileNameStartIndex + 1)
                    + htmlFileNamePrefix
                    + htmlFileName.substring(htmlFileNameStartIndex + 1);
        }
    }

    public static String getCheckedExtensionFileName(String fileName, String extension) {
        int pointIndex = fileName.lastIndexOf('.');
        String fileExtension = null;
        String fileNameOnly = null;

         if (pointIndex != -1) {
             fileExtension = fileName.substring(pointIndex).toLowerCase();
             fileNameOnly = fileName.substring(0, pointIndex);
         }

         if (Objects.equals(fileExtension, extension)) {
             return fileNameOnly + fileExtension;
         }

         return null;
    }
}
