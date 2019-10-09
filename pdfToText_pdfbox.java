package nju.edu.cn.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import static jdk.nashorn.internal.objects.Global.print;

public class pdfToText_pdfbox {
    String path="D:\\LDA\\LDAdata\\pdfdata\\";
    String outpath="D:\\LDA\\LDAdata\\txtdata-pdfbox\\";

    public void toTextFilter(Rectangle rect) throws IOException {
        File file=new File(path);
        File[] fileList=file.listFiles();
        for (int i=0;i<fileList.length;i++) {

            if (fileList[i].isFile()) {

                PDDocument document =PDDocument.load(fileList[i]);
                //Instantiate PDFTextStripper class
                PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
                //Retrieving text from PDF document
                pdfStripper.setSortByPosition(false);
//                Rectangle rect=new Rectangle(10,10,1000,1000);
                pdfStripper.addRegion("class1",rect);
                int page=document.getDocumentCatalog().getPages().getCount();
                String text="";
                for(int j=0;j<page;j++) {
                	pdfStripper.extractRegions(document.getPage(j));
                    text = text+pdfStripper.getTextForRegion("class1");
                }
                
//                System.out.println(text);
                File filetxt = new File(outpath+fileList[i].getName()+".txt");
                PrintWriter out=null;

                out =new PrintWriter(filetxt);
                out.write(text);
                out.close();
                document.close();
            }

        }
        //Closing the document

    }
    public void toText() throws IOException {
    	File file=new File(path);
        File[] fileList=file.listFiles();
        for (int i=0;i<fileList.length;i++) {

            if (fileList[i].isFile()) {

                PDDocument document =PDDocument.load(fileList[i]);

                //尝试提取单个页面内容

                //尝试获取pdf自有信息 效果很差
//                PDDocumentInformation info=document.getDocumentInformation();
//                System.out.println("标题"+info.getTitle());
//                System.out.println("主题"+info.getSubject());
//                System.out.println("作者"+info.getAuthor());

                //实例化pdf文本分离器
                PDFTextStripper pdfStripper = new PDFTextStripper();
                //转换文本
                pdfStripper.setShouldSeparateByBeads(false);
                pdfStripper.setSortByPosition(false);
                String text=pdfStripper.getText(document);
                File filetxt = new File(outpath+fileList[i].getName()+".txt");
                PrintWriter out=null;
                out =new PrintWriter(filetxt);
                out.write(text);
                out.close();
                document.close();
            }
        }
    }

    public static void main(String args[]) throws IOException{
        Date start=new Date();
        System.out.println(start);
        //设置矩形区域 过滤pdf
//        Rectangle rect=new Rectangle(70,200,1000,1000);
//        new pdfToText_pdfbox().toTextFilter(rect);
        new pdfToText_pdfbox().toText();

        Date end=new Date();
        System.out.println(end);
    }
}
