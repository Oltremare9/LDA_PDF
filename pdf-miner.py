from pdfminer.pdfparser import PDFParser,PDFDocument
from pdfminer.pdfinterp import PDFResourceManager,PDFPageInterpreter
from pdfminer.converter import PDFPageAggregator
from pdfminer.layout import LTTextBoxHorizontal,LAParams,LTImage
import os
import time

path='D:\\LDA\\LDAdata\\pdfdata'
txtpath='D:\\LDA\\LDAdata\\txtdata-miner'
def pdf_to_txt_miner(folder,password):
    #获取指定目录下的所有文件
    files=os.listdir(folder)
    pdfFiles=[f for f in files if f.endswith('.pdf')]

    #获取pdf类型的文件，放到一个列表中
    for pdfFile in pdfFiles:
        print(pdfFile)
        #将目录和文件合并成一个路径 os.path.join('root','test','runoob.txt')  ##root/test/runoob.txt
        pdfPath=os.path.join(folder,pdfFile)
        #设置将要转换后存放word文件的路径
        wdPath=os.path.join(txtpath,pdfFile)
        #判断是否已经存在对应的文件，如果不存在就加入到存放的路径中去
        if wdPath[-4:]!='.txt':
            wdPath=wdPath+'.txt'
        fn=open(path+"/{}".format(pdfFile), 'rb')
        #创建一个PDF文本档分析器：PDFParser
        parser=PDFParser(fn)
        #创建一个PDF文档：PDFDocumeng
        doc=PDFDocument()
        #链接分析器与文档
        parser.set_document(doc)
        doc.set_parser(parser)
        #提供出事话的密码，如果没有密码，输入空字符串
        doc.initialize('')
        #检测文档是否提供txt转换，不提供就直接忽略
        if not doc.is_extractable:
            print('PDFTextExtractionNotAllowed')
        else:
        #创建PDF资源管理器：PDFResourceManager
            resource=PDFResourceManager()
            #创建一个PDF参数分析器：；AParams
            laparams=LAParams()
            #创建聚合器，用于读取文档的对象：PDFPageAggregator
            device=PDFPageAggregator(resource,laparams=laparams)
            #创建解释器，对文档编码，解释成python能够识别的格式：PDFPageInterpreter
            interpreter=PDFPageInterpreter(resource,device)
            #doc.get_pages()是获取page列表的一个方法
            num_page,num_image,num_Text=0,0,0
            num=0;
            pdf_str = ''
            for page in doc.get_pages():

                # num+=1;
                # if (num!=2):
                #     continue;
                #利用解释器的peocess_page()方法解析单独页数
                interpreter.process_page(page)
                layout=device.get_result()
                for out in layout:
                    if isinstance(out,LTTextBoxHorizontal):
                        num_Text+=1
                        # print(type(out.get_text()))
                        # if out.get_text().strip().replace(" ","")=="摘要":
                        #     print("找到摘要位置")
                        pdf_str+=out.get_text().strip()+'\n'
                        # f=open(wdPath,'a',encoding='utf-8')
                        # f.write(out.get_text()+'\n')
                    if isinstance(out,LTImage):
                        num_image+=1
                        # f = open(wdPath, 'a', encoding='utf-8')
                        # f.write(out.get_text() + '\n')
                        pdf_str += out.get_text().strip()+'\n'
                # print(pdf_str)
            f = open(wdPath, 'w', encoding='utf-8')
            f.write(pdf_str + '\n')
                    # with open(wdPath,'a',encoding='utf-8') as f:
                    #     f.write(out.get_text()+'\n')
                # break;

if __name__=='__main__':

    tickets = time.time()
    print(tickets)
    pdf_to_txt_miner(path,'')
    ticket = time.time()
    print(ticket)
