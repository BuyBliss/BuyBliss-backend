package com.commerce.ecommerce.service;

import com.commerce.ecommerce.model.entity.Order;
import com.commerce.ecommerce.model.entity.OrderItem;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;

@Service
public class ReceiptService {

    public byte[] generateReceipt(Order order) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Paragraph title = new Paragraph("Buy Bliss", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Font titleFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title2 = new Paragraph("Order Receipt", titleFont2);
        title2.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title2);

        document.add(new Paragraph("\n\n"));

        document.add(new Paragraph("Order Id: " + order.getOrderId()));
        document.add(new Paragraph("Date: " + order.getBillDate()));
        document.add(new Paragraph("Customer: " + order.getConsumer().getName()));
        document.add(new Paragraph("Email: " + order.getConsumer().getEmail()));
        document.add(new Paragraph("Shipping Address:\n" + order.getDeliveryAddress()));
        document.add(new Paragraph("Contact: " + order.getConsumer().getContactNo()));

        document.add(new Paragraph("\n\n"));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);


        PdfPCell cell1 = new PdfPCell(new Phrase("Product"));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell1.setBorderWidth(1);
        cell1.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Quantity"));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell2.setBorderWidth(1);
        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase("Price"));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell3.setBorderWidth(1);
        cell3.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell3);


        for (OrderItem item : order.getOrderItemList()) {
            table.addCell(new PdfPCell(new Phrase(item.getProduct().getProductName())) {{
                setHorizontalAlignment(Element.ALIGN_CENTER);
                setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            }});
            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getProductQuantity()))) {{
                setHorizontalAlignment(Element.ALIGN_CENTER);
                setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            }});
            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getProduct().getPrice()))) {{
                setHorizontalAlignment(Element.ALIGN_CENTER);
                setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            }});
        }

        document.add(table);

        Font totalAmountFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
        Paragraph totalAmount = new Paragraph("Total Amount: " + order.getTotalPrice(), totalAmountFont);
        totalAmount.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(totalAmount);

        document.add(new Paragraph("\n\n\n\n\n\n\n\n"));

        Font footerFont = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 11);
        Paragraph footer = new Paragraph("Buy Bliss :) ..here money can buy happiness!", footerFont);
        footer.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(footer);

        document.close();

        return baos.toByteArray();
    }
}
