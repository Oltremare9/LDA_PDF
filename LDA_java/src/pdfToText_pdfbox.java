package nju.edu.cn.pdfbox;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import nju.edu.cn.tool.RemoveLine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

import java.awt.Rectangle;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

public class pdfToText_pdfbox {
    static String pdf = "D:\\LDA\\LDAdata\\pdfdata\\";
    static String txt = "D:\\LDA\\LDAdata\\txtdata-pdfbox\\";
    static String fepath = "D:\\LDA\\LDAdata\\finance&economics_pdf\\";
    static String fetxt = "D:\\LDA\\LDAdata\\finance&economics_txt\\";

    static String path = fepath;
    static String outpath = fetxt;

//    String path=pdf;
//    String outpath=txt;

    //未启用的方法 启用时需要重新考虑入参
    public void toTextFilter(Rectangle rect) throws IOException {
        File file = new File(path);
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {

            if (fileList[i].isFile()) {

                PDDocument document = PDDocument.load(fileList[i]);
                //Instantiate PDFTextStripper class
                PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
                //Retrieving text from PDF document
                pdfStripper.setSortByPosition(false);
//                Rectangle rect=new Rectangle(10,10,1000,1000);
                pdfStripper.addRegion("class1", rect);
                int page = document.getDocumentCatalog().getPages().getCount();
                String text = "";
                for (int j = 0; j < page; j++) {
                    pdfStripper.extractRegions(document.getPage(j));
                    text = text + pdfStripper.getTextForRegion("class1");
                }

//                System.out.println(text);
                File filetxt = new File(outpath + fileList[i].getName() + ".txt");
                PrintWriter out = null;

                out = new PrintWriter(filetxt);
                out.write(text);
                out.close();
                document.close();
            }

        }
        //Closing the document

    }

    public void typeDisplay(File file) throws IOException {
        PDDocument document = PDDocument.load(file);

        //尝试获取pdf自有信息 效果很差
//                PDDocumentInformation info=document.getDocumentInformation();
//                System.out.println("标题"+info.getTitle());
//                System.out.println("主题"+info.getSubject());
//                System.out.println("作者"+info.getAuthor());

        //实例化pdf文本分离器
        TypeDisplay pdfStripper = new TypeDisplay();
//                PDFTextStripper pdfStripper=new PDFTextStripper();
        //转换文本
        pdfStripper.setShouldSeparateByBeads(false);
        pdfStripper.setSortByPosition(false);
        String text = pdfStripper.getText(document);
        File filetxt = new File("D:\\LDA\\LDAdata\\finance&economics_type\\" + file.getName() + ".txt");
        FileOutputStream out = new FileOutputStream(filetxt, false);

        out.write(text.getBytes());
        out.close();
        document.close();

    }

    public void toText(File file) throws IOException {

        PDDocument document = PDDocument.load(file);
        int pageAmount = document.getPages().getCount();

        //尝试获取pdf自有信息 效果很差
//                PDDocumentInformation info=document.getDocumentInformation();
//                System.out.println("标题"+info.getTitle());
//                System.out.println("主题"+info.getSubject());
//                System.out.println("作者"+info.getAuthor());

        //实例化pdf文本分离器
        PDFTextStripper pdfStripper = new TitleStripper();
        pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(1);
//                PDFTextStripper pdfStripper=new PDFTextStripper();
        //转换文本
        pdfStripper.setShouldSeparateByBeads(false);
        pdfStripper.setSortByPosition(false);
        String text = "*\n" + pdfStripper.getText(document);

        pdfStripper = new AuthorStripper();
        pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(1);
        text += "**\n" + pdfStripper.getText(document);

        pdfStripper = new AbstractStripper();
        pdfStripper.setStartPage(1);
        pdfStripper.setEndPage(1);
        text += "***\n"+pdfStripper.getText(document);

        QuotationStripper stripper = new QuotationStripper();
        Rectangle rect = new Rectangle(0, 120, 2000, 2000);
        stripper.addRegion("head", rect);
        text+="****\n";
        for(int i=pageAmount-5;i<pageAmount-1;i++){
            stripper.extractRegions(document.getPage(i));
            String zhengze="\\[\\d+\\]";
//            System.out.print(zhengze);\
            String cut=stripper.getTextForRegion("head");
            String temp[]=cut.split(zhengze);
            for(int k=0;k<temp.length;k++){
//                System.out.println(temp[k]+"///////////");
                text+=temp[k]+"\n";
            }
//            text+=stripper.getTextForRegion("head");
        }
        File filetxt = new File(outpath + file.getName() + ".txt");
        FileOutputStream out = new FileOutputStream(filetxt, false);
//        PrintWriter out=null;
//        out =new PrintWriter(filetxt);
        out.write(text.getBytes());
        out.close();
        document.close();
    }

    public static void main(String args[]) throws IOException {
        Date start = new Date();
        System.out.println(start);
        File file = new File(path);
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                new pdfToText_pdfbox().toText(fileList[i]);
                new pdfToText_pdfbox().typeDisplay(fileList[i]);
            }
        }
        RemoveLine.removeLine(outpath, "D:\\LDA\\LDAdata\\finance&economics_txt_result\\");
        Date end = new Date();
        System.out.println(end);
    }
}
