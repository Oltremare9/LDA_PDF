package nju.edu.cn.binaryCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class binaryCode {
    public static void main(String args[]) throws IOException {
        FileInputStream in = new FileInputStream(new File("D:\\LDA\\LDAdata\\pdfdata\\体检报告.pdf"));
        int xx = in.read();
        String str="";
        while(xx!=-1) {
            System.out.print((char) xx);
            xx = in.read();
            str+=xx;
        }
    }

}
