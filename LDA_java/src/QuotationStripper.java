package nju.edu.cn.pdfbox;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.List;

public class QuotationStripper extends PDFTextStripperByArea {
    public QuotationStripper() throws IOException {
        super();
    }
    public void writeString(String text, List<TextPosition> textPositions) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        for (TextPosition position : textPositions)
        {
            float fontSize=position.getFontSize();//获取字体大小
            String baseFont = position.getFont().getName();

            System.out.println(position.toString()+"!");
            if(fontSize>=8 &&fontSize<=8.5 && position.getUnicode()!="主"
                    && position.getUnicode()!="要"
                    && position.getUnicode()!="参"
                    && position.getUnicode()!="考"
                    && position.getUnicode()!="文"
                    && position.getUnicode()!="献"
                    && position.getUnicode()!="："){
                builder.append(position.toString());
            }
        }
        writeString(builder.toString());
    }
}
