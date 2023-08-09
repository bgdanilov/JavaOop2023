package ru.bgdanilov.deminer_main;

import ru.bgdanilov.deminer.Cell;
import ru.bgdanilov.deminer.Game;
import ru.bgdanilov.deminer.MineField;

public class DeminerMain {
    public static void main(String[] args) {
        // Создаем мины.
        //Cell mine1 = new Cell(0,0, "m");
        //Cell mine2 = new Cell(2,2, "m");
        //Cell mine3 = new Cell(2,3, "m");
        //Cell[] cells = {mine1, mine2, mine3};

        // Это массив объектов клеток.
        Cell[][] field = new Cell[7][5];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = new Cell(i, j, "e"); // e - empty (пусто).
            }
        }

        // Расставляем мины вручную.
        field[1][4].setName("m"); // m - mine (мина).
        field[2][2].setName("m");
        field[2][3].setName("m");

        // Печатаем минное поле.
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(field[i][j].getName() + " ");
            }

            System.out.println();
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