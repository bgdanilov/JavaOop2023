package sweeper_model;

import java.awt.*;

public enum SweeperCellImage {
    CLOSED,
    EXPLODED,
    MARKED,
    NUMBER1,
    NUMBER2,
    NUMBER3,
    NUMBER4,
    NUMBER5,
    NUMBER6,
    NUMBER7,
    NUMBER8,
    OPENED;

    // Соответствующая картинка - объект в памяти.
    private Image cellImage;

    public Image getCellImage() {
        return cellImage;
    }

    public void setCellImage(Image cellImage) {
        this.cellImage = cellImage;
    }
}