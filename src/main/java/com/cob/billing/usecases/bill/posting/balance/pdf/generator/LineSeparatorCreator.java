package com.cob.billing.usecases.bill.posting.balance.pdf.generator;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.properties.UnitValue;

public class LineSeparatorCreator {
    public static void  create(Document document){
        SolidLine line = new SolidLine();
        line.setColor(ColorConstants.LIGHT_GRAY);
        LineSeparator lineSeparator = new LineSeparator(line);
        lineSeparator.setWidth(UnitValue.createPercentValue(50));
        lineSeparator.setMarginTop(5);
        document.add(lineSeparator);
    }
}
