package nju.edu.cn.iText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ReadPdfByiText {
    public static void main(String[] args) throws IOException {
    	Date start=new Date();
    	
        String path="D:\\LDA\\LDAdata\\pdfdata\\";
        String outpath="D:\\LDA\\LDAdata\\txtdata_itext\\";
        File file=new File(path);
        File[] fileList=file.listFiles();
        for(int i=0;i<fileList.length;i++){
            String outputFile=outpath+fileList[i].getName()+".txt";
            String inputFile=fileList[i].getAbsolutePath();
            PrintWriter writer = new PrintWriter(new FileOutputStream(outputFile));
            readPdf(writer, inputFile);//直接读全PDF面

//           readPdf_filter(writer,inputFile);//读取PDF面的某个区域
        }
        Date end=new Date();
        System.out.println(start);
        System.out.println(end);

    }

    public static void readPdf(PrintWriter writer,String fileName){
        String pageContent = "";
        try {
            PdfReader reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            for(int i=1;i<=pageNum;i++){
                pageContent += PdfTextExtractor.getTextFromPage(reader, i);//读取第i页的文档内容
            }
            writer.write(pageContent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            writer.close();
        }
    }

    public static void readPdf_filter(PrintWriter writer,String fileName){
        String pageContent = "";
        try {
            Rectangle rect = new Rectangle(100, 100, 700, 700);
            RenderFilter filter = new RegionTextRenderFilter(rect);
            PdfReader reader = new PdfReader(fileName);
            int pageNum = reader.getNumberOfPages();
            TextExtractionStrategy strategy;
            for (int i = 1; i <= pageNum; i++) {
                strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                pageContent +=PdfTextExtractor.getTextFromPage(reader, i, strategy);
            }
			/*String[] split = pageContent.split(" ");
			for(String ss : split){
				System.out.println(ss.substring(ss.lastIndexOf("：")+1, ss.length()));
			}*/
            writer.write(pageContent);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            writer.close();
        }
    }
}