package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.properties.TextAlignment;

public class CellCreator {
    public static Cell create(IBlockElement paragraph, TextAlignment alignment) {
        Cell cell = new Cell().add(paragraph);
        //cell.setPadding(0);
        cell.setTextAlignment(alignment);
        cell.setBorder(Border.NO_BORDER);
        return cell;
    }
}
