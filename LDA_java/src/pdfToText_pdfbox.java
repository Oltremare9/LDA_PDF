import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class pdfToText_pdfbox {
    String path="D:\\LDA\\LDAdata\\pdfdata\\";
    String outpath="D:\\LDA\\LDAdata\\txtdata-pdfbox\\";

    public void toText() throws IOException {
        File file=new File(path);
        File[] fileList=file.listFiles();
        for (int i=0;i<fileList.length;i++) {

            if (fileList[i].isFile()) {
//                String fileName = fileList[i].getName();
//                System.out.println("文件：" + fileName);
//                String filePath=path+fileName;
//                System.out.println("文件路径：" + filePath);
                PDDocument document =PDDocument.load(fileList[i]);
                //         Instantiate PDFTextStripper class
                PDFTextStripper pdfStripper = new PDFTextStripper();

                //Retrieving text from PDF document
                String text = pdfStripper.getText(document);
                System.out.println(text);
                File filetxt = new File(outpath+fileList[i].getName()+".txt");
                OutputStream out=null;

                out =new FileOutputStream(filetxt);
                out.write(text.getBytes());
                out.close();
                document.close();
            }

        }
        //Closing the document

    }
    public static void main(String args[]){
        try {
            new pdfToText_pdfbox().toText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
