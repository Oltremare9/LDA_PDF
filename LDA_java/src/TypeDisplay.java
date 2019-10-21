package nju.edu.cn.pdfbox;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class TypeDisplay extends PDFTextStripper {
    public TypeDisplay() throws IOException {
    }
    public void writeString(String text, List<TextPosition> textPositions) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        String prevBaseFont = "";

        for (TextPosition position : textPositions)
        {
            String baseFont = position.getFont().getName();//getBasefont
            float fontSize=position.getFontSize();//获取字体大小
            if (baseFont != null && !baseFont.equals(prevBaseFont))
            {

                builder.append('[').append(fontSize).append(']');
                prevBaseFont = baseFont;
            }
                builder.append(position.toString());


        }

        writeString(builder.toString());
    }
}
