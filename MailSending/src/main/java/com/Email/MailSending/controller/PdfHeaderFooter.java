package com.Email.MailSending.controller;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;

public class PdfHeaderFooter extends PdfPageEventHelper {

    private Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.darkGray);
    private Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 10,Color.gray);

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();

        // Header
        ColumnText.showTextAligned(
                canvas,
                Element.ALIGN_LEFT,
                new Phrase(" User Report", headerFont),
                document.leftMargin(),
                document.top() + 10,
                0
        );

        // Footer
        ColumnText.showTextAligned(
                canvas,
                Element.ALIGN_CENTER,
                new Phrase("Page " + writer.getPageNumber(), footerFont),
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10,
                0
        );
    }
}
