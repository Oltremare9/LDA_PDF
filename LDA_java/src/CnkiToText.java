package nju.edu.cn.pdfbox;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CnkiToText {
    public static void toText(String path, String out) throws IOException {
        File file = new File(path);
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isFile()) {
                PDDocument document = PDDocument.load(fileList[i]);
                int pageAmount = document.getPages().getCount();
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                Rectangle rect = new Rectangle(0, 100, 2000, 2000);
                stripper.addRegion("head", rect);
                String cut="";
                for (int page = 0; page < 3; page++) {
                    stripper.extractRegions(document.getPage(page));
                    String temp=stripper.getTextForRegion("head");
                    byte[] b=null;
                    for(int c=0;c<temp.length();c++){
                        String s=temp.substring(c,c+1);
                        if(s.equals("　")) {
                            cut += " ";
                            continue;
                        }
                        b=s.getBytes("unicode");
                        if(b[2]==-1){
                            b[3]=(byte)(b[3]+32);
                            b[2]=0;
                            cut+=(new String(b,"unicode"));
                        }else{
                            cut+=s;
                        }

                    }
//                    cut += temp;

                }
                for (int page = pageAmount-5; page < pageAmount; page++) {
                    stripper.extractRegions(document.getPage(page));
                    String temp=stripper.getTextForRegion("head");
                    byte[] b=null;
                    for(int c=0;c<temp.length();c++){
                        String s=temp.substring(c,c+1);
                        if(s.equals("　")) {
                            cut += " ";
                            continue;
                        }
                        b=s.getBytes("unicode");
                        if(b[2]==-1){
                            b[3]=(byte)(b[3]+32);
                            b[2]=0;
                            cut+=(new String(b,"unicode"));
                        }else{
                            cut+=s;
                        }

                    }
//                    cut += temp;

                }
                File filetxt = new File(out + fileList[i].getName() + ".txt");
                FileOutputStream f = new FileOutputStream(filetxt, false);
                f.write(cut.getBytes());
                f.close();
                document.close();
            }
        }
    }
    public static void main(String args[]){
        try {
            CnkiToText.toText("D:\\LDA\\LDAdata\\cnki_pdf\\","D:\\LDA\\LDAdata\\cnki_txt\\");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
