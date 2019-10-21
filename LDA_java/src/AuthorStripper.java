package nju.edu.cn.pdfbox;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class AuthorStripper extends PDFTextStripper {
    public AuthorStripper() throws IOException {
    }
    public void writeString(String text, List<TextPosition> textPositions) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        for (TextPosition position : textPositions)
        {
            float fontSize=position.getFontSize();//获取字体大小
            if(fontSize==13.5){
//                System.out.println(position.getUnicode());
                builder.append(position.toString().trim());
            }
        }
        writeString(builder.toString());
    }
}
