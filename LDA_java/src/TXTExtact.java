package nju.edu.cn.pdfbox;

import com.google.common.base.CharMatcher;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class TXTExtact {
    public static String extractAuthor(File file, String out) throws IOException {
        String temp = "";
        if (file.isFile()) {
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                temp = s;
                while ((s = re.readLine()) != null) {
                    s = CharMatcher.WHITESPACE.trimFrom(s);
                    s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                    if (s.startsWith("(")) {
                        temp = Pattern.compile("[\\d]").matcher(temp).replaceAll("");
                        temp = temp.replace("，", " ");
                        temp = temp.replaceAll("1|2|3|4", "");
                        System.out.println(temp);
                        break;
                    } else {
                        temp = s;
                    }
                }
                break;
            }

        }
        return temp;
    }

    public static String extractTitle(File file, String out) throws IOException {
        //从文件名直接读取论文题目 效果并不好 问题出在下载论文时题目就有所改动
//        String str=file.getName();
//        if(str.startsWith("_"))
//            str.substring(1);
//        str=str.substring(0,str.length()-8);
//        System.out.println(str);

        //从文本内容中提取
        String res = "";
        if (file.isFile()) {
            String s = "";
            String temp = "";

            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
                res += s;
                temp = CharMatcher.WHITESPACE.trimFrom(temp);
                temp = CharMatcher.WHITESPACE.replaceFrom(temp, "");
                while ((s = re.readLine()) != null) {
                    if (s.startsWith("(")) {
                        res.replaceAll("＊", "");
                        res.replaceAll("\\*", "");
                        System.out.println(res);
                        break;
                    } else {
                        res += temp;
                        temp = s;
                    }
                }
                break;
            }

        }
        return res;
    }

    public static String extractAbstract(File file, String out) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                s.replaceAll(" ","");
//                s = s.trim().replaceAll("\\ +", "");
//                System.out.println(s);
                if (s.startsWith("摘要")) {
//                        System.out.println(s+"----------");
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        CharMatcher.WHITESPACE.replaceFrom(s," ");
//                        s.replaceAll(" ","");
//                        s=s.replaceAll("\\ +","");
                        if (!s.startsWith("关键词"))
                            res += s;
                        else
                            break;
                    }
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res.substring(3);
    }

    public static String extractKeyword(File file, String out) throws IOException {
        String res = "";
        if (file.isFile()) {

            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                s.replaceAll(" ","");
//                s = s.trim().replaceAll("\\ +", "");
//                System.out.println(s);
                if (s.startsWith("关键词")) {
//                        System.out.println(s+"----------");
                    res += s;
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        s.replaceAll(" ","");
//                        s=s.replaceAll("\\ +","");
                        if (!s.startsWith("中图"))
                            res += s;
                        else
                            break;
                    }
                    res = res.substring(4, res.length());
//                    System.out.println(res);
                    break;
                }
            }

        }
        return res;
    }

    public static ArrayList<String> extractQuotation(File file, String out) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        if (file.isFile()) {
            String res = "";
            String s = "";
            BufferedReader re = new BufferedReader(new FileReader(file));
            while ((s = re.readLine()) != null) {
                s = CharMatcher.WHITESPACE.trimFrom(s);
                s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                s.replaceAll(" ","");
//                s = s.trim().replaceAll("\\ +", "");
//                System.out.println(s);
                if (s.startsWith("主要参考文献")||s.startsWith("参考文献")) {
//                        System.out.println(s+"----------");
                    while ((s = re.readLine()) != null) {
                        s = CharMatcher.WHITESPACE.trimFrom(s);
                        s = CharMatcher.WHITESPACE.replaceFrom(s, "");
//                        s=s.replaceAll("［","[");
//                        s=s.replaceAll("］","]");

                        res += s;
//                        else
//                            break;
                    }
//                    String str[] = res.split("\\[\\d+\\]");
                    String str[] = res.split("\\[\\d+\\]");
                    for (int i = 0; i < str.length - 1; i++) {
                        if(str[i].equals(""))
                            continue;
                        if (!str[i].startsWith("•")) {
//                            System.out.println(str[i]);
                            result.add(str[i]);
                        }
                        else {
                            char c[] = str[i].toCharArray();
                            for (int num = 1; num < c.length; num++) {
                                if (c[num] == '•') {
                                    String temp = str[i].substring(num + 1);
//                                    System.out.println(temp);
                                    result.add(temp);
                                    break;
                                }
                            }
                        }
//                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    }

                    break;
                }
            }

            return result;
        }
        return result;
    }

    private static void writeCsv(File file){

    }
    public static void main(String args[]) throws IOException, WriteException {
        File file = new File("D:\\LDA\\LDAdata\\cnki_txt\\");
        File[] fileList = file.listFiles();
        File csv=new File("D:\\LDA\\LDAdata\\财经研究拆分.xls");
        if(csv.exists()){
            csv.delete();
        }
        csv.createNewFile();
        WritableWorkbook workbook= Workbook.createWorkbook(csv);
        WritableSheet sheet =workbook.createSheet("sheet1",0);
        Label label=null;
        int count=0;
        for (int i = 0; i < fileList.length; i++) {
//            System.out.println(fileList[i].getName() + "  " + i);
            label=new Label(0,i,extractTitle(fileList[i], ""));
            sheet.addCell(label);
            label=new Label(1,i,extractAuthor(fileList[i], ""));
            sheet.addCell(label);
            label=new Label(2,i,extractAbstract(fileList[i], ""));
            sheet.addCell(label);
            label=new Label(3,i,extractKeyword(fileList[i], ""));
            sheet.addCell(label);
            ArrayList<String> quotation=extractQuotation(fileList[i], "");
            //            extractTitle(fileList[i], "");
            //            extractAuthor(fileList[i], "");
//            extractAbstract(fileList[i], "");
//            extractKeyword(fileList[i], "");
//            System.out.println(quotation.toString());


            if(quotation.size()==0) {
                count++;
                System.out.println(fileList[i].getName()+"  "+i);
            }
            for(int num=0;num<quotation.size();num++){
                label=new Label(num+4,i,quotation.get(num).toString());
                sheet.addCell(label);
//                System.out.println(quotation.get(num));
//
            }
//            System.out.println(quotation.size());
            System.out.println("------------------------------------------------------------------------------");
        }
        workbook.write();
        workbook.close();
        System.out.println(count);
    }
}
