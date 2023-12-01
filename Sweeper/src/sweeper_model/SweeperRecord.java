package sweeper_model;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SweeperRecord {
    private List<String> recordsList = generateRecordsList();

    private String[][] matrix = new String[9][4];
    public void writeToFile(String recordLine) throws FileNotFoundException {
        try {
            FileWriter writer = new FileWriter("fileForWrite.txt", true);
            writer.write(recordLine);
           // writer.write(String.join("\n", recordLine));
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Возникла ошибка во время записи, проверьте данные.");
        }
    }

    public void getRecordsMatrix() {
        String[][] matrix = new String[9][4];

        for (String st : recordsList) {

        }
    }


    public String getBestRecord(SweeperDifficultyLevel level) {
        //System.out.println("0 = " + recordsList.get(0));
        //Stream<String> stream = recordsList.stream();

        // Получаем список рекордов по уровню сложности.
        List<String> recordsByLevels = new ArrayList<>();

        for (String st : recordsList) {
            System.out.println("st = " + st);

            if (st.contains(level.getLabel())) {
                recordsByLevels.add(st);
            }
        }

        // Находим максимальное время.
        int max = 0;

        for (String recordString : recordsByLevels) {
            String time = recordString.substring(0,recordString.indexOf(';'));
            int sec = Integer.parseInt(time);

            if (sec > max)
                max = sec;
        }

        int finalMax = max;

        // Формируем список рекордов с максимальным временем.
        List<String> bestRecord = new ArrayList<>();

        for (String st : recordsByLevels) {
            if (st.contains(finalMax + ";")) {
                bestRecord.add(st);
            }
        }

        System.out.println("Best = " + bestRecord);

        return "dfsdfsd " + max;
    }


    public List<String> generateRecordsList() {
        ArrayList<String> recordsList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("fileForWrite.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                    recordsList.add(line);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordsList;
    }

//    public String read() {
//        String output = null;
//        StringBuilder sb = new StringBuilder();
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("fileForWrite.txt"));
//            String line = reader.readLine();
//
//            while (line != null) {
//                line = reader.readLine();
//                sb.append(line).append("\n");
//                output = sb.toString();
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return output;
//    }

    public String[][] getMatrix() {
        return matrix;
    }

    public String toString(String[][] matrix) {
        StringBuilder sb = new StringBuilder().append('{');

        for (String[] row : matrix) {
            sb.append(Arrays.toString(row)).append(", ");
        }

        sb.setLength(sb.length() - 2);
        sb.append('}');


        return sb.toString();
    }
 }