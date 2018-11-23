package pdf;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.util.*;

import java.io.FileOutputStream;
import java.util.List;

/**
 * @author xuyue_2017@csii.com.cn
 * @ClassName: PdfTest
 * @Description:
 * @date 2018/11/14 21:17
 * Copyright (c) CSII.
 * All Rights Reserved.
 */


public class PdfTest {
    static {
        List list = new ArrayList();

    }

    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F://testPdfFour.pdf"));
        document.setPageSize(PageSize.A4);
        document.setMargins(30, 30, 50, 30);
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //设置字体
        Font fontChinese = new Font(baseFont, 20, Font.NORMAL);
        document.open();
        String content = "凭证表单";
        setHead(document, content, fontChinese);
        ParagraphContent paragraphContent = new ParagraphContent();
        paragraphContent.setColumNum(3);
        paragraphContent.setRowNum(3);
        List<ParagraphContent> contents = new ArrayList<ParagraphContent>();
        contents.add(new ParagraphContent("xuy","hello"));
        paragraphContent.setContent(contents);
        PdfPTable table = createTable(paragraphContent.getContent(),fontChinese);
        document.add(table);
        document.close();
        writer.close();




    }

    public static void setHead(Document document, String content, Font baseFont) {
        try {
            Paragraph paragraph = new Paragraph(content, baseFont);
            paragraph.setAlignment(1);//设置段落居中
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static void setImage(Document document, String content, Font baseFont) {
        try {
            Image image = Image.getInstance(Base64.decodeBase64(content));
            image.setAlignment(Image.ALIGN_CENTER);
            document.add(image);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static PdfPTable createTable(List<ParagraphContent> content, Font baseFont) {
        PdfPTable pdfPTable = new PdfPTable(2);//列数
        List rows = pdfPTable.getRows();
        for (int i = 0; i < content.size(); i++) {
            rows.add(createRow(content.get(i), baseFont));
        }
        return pdfPTable;

    }


    public static PdfPCell createCell(String content, Font baseFont) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, baseFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        return cell;

    }


    public static PdfPRow createRow(ParagraphContent paragraphContent, Font baseFont) {
        PdfPCell[] pdfPCell = new PdfPCell[2];
        PdfPRow row1 = new PdfPRow(pdfPCell);
        baseFont.setSize(10);
        pdfPCell[0] = createCell(paragraphContent.getName(), baseFont);
        pdfPCell[1] = createCell(paragraphContent.getDes(), baseFont);
        return row1;

    }

    public static void morePdfTopdf(List<String> fileList, String savepath) {
        Document document = null;
        try {
            document = new Document(new PdfReader(fileList.get(0)).getPageSize(1));
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
            document.open();
            for (int i = 0; i < fileList.size(); i++) {
                PdfReader reader = new PdfReader(fileList.get(i));
                int n = reader.getNumberOfPages();// 获得总页码
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);// 从当前Pdf,获取第j页
                    copy.addPage(page);
                }
            }
        } catch (Exception e) {

        } finally {
            if (document != null) {
                document.close();
            }
            System.out.println("finish " + new Date());
        }
    }


}
