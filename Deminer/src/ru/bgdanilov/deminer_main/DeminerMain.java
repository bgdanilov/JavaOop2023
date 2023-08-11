package ru.bgdanilov.deminer_main;

import ru.bgdanilov.deminer.CellType;
import ru.bgdanilov.deminer.MineField;

import java.util.Scanner;

public class DeminerMain {
    public static void main(String[] args) {
        // Создаем новое поле.
        MineField field = new MineField(7, 5);
        // Заполняем созданное поле пустыми объектами-клетками *.
        field.setField();

        // Назначаем мины на поле.
        // TODO потом сделать автоматическое заполнение.
        field.getCell(1, 4).setType(CellType.MINE);
        field.getCell(2, 2).setType(CellType.MINE);
        // Назначение типа клетки цифра.
        field.getCell(0, 3).setType(CellType.DIGIT);
        field.getCell(0, 4).setType(CellType.DIGIT);
        field.getCell(1, 2).setType(CellType.DIGIT);
        field.getCell(1, 3).setType(CellType.DIGIT);
        field.getCell(2, 2).setType(CellType.DIGIT);
        field.getCell(2, 4).setType(CellType.DIGIT);
        field.getCell(3, 2).setType(CellType.DIGIT);
        field.getCell(3, 3).setType(CellType.DIGIT);
        field.getCell(3, 4).setType(CellType.DIGIT);
        // Назначаем цифры-подсказки на поле.
        field.getCell(0, 3).setName("1");
        field.getCell(0, 4).setName("1");
        field.getCell(1, 2).setName("1");
        field.getCell(1, 3).setName("2");
        field.getCell(2, 2).setName("1");
        field.getCell(2, 4).setName("2");
        field.getCell(3, 2).setName("1");
        field.getCell(3, 3).setName("1");
        field.getCell(3, 4).setName("1");

        // Выводим поле.
        field.printField();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите целое число i: ");
            int i = scanner.nextInt();

            System.out.println("Введите целое число j: ");
            int j = scanner.nextInt();

            System.out.println("Введите целое код: 1 - открыть, 2 - отметить, 0 - выход");
            int action = scanner.nextInt();

            if (action == 1) {
                field.getCell(i, j).setIsHidden(false);
            }

            if (action == 2) {
                field.getCell(i, j).setIsMarked(true);
            }

            if (action == 0) {
                break;
            }

            System.out.print("\033[H\033[J");
            // Выводим поле.
            field.printField();
        }
    }
}


/* TODO
    08.08.2023 Тут запишем некоторые мыслы, чтобы не забыть.
    ============================================
    1. Цифра в ячейки означает сколько мин находится в соседних ячейках.
    * * *   - в каких-то из ДВУХ звездочек есть по мине.
    * 2 *   - получается, максимальная цифра может быть 8,
    * * *     но польше 4 я не встречал, видимо это связано с пересечением со содедями.
    -----
    2. Если кликаешь (попадаешь) на пустую клетку, без цифры,
       то открываются все пустые клетки, соприкасающиеся с оной,
       пока волна распространения не упрется в клетки с цифрами,
       при этом первые встретившиеся клетки с цифрами тоже откроются, но не далее.
 */