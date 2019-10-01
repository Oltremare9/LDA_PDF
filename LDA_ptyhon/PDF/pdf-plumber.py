import pdfplumber as pdfplumber
import os;

inpath='D:\\LDA\\LDAdata\\pdfdata'
outpath='D:\\LDA\\LDAdata\\txtdata-plumber'

def pdf_to_txt_plumber(folder):
    files=os.listdir(folder)
    pdfFiles=[f for f in files if f.endswith('.pdf')]

    for pdfFile in pdfFiles:
        print(pdfFile)
        pdfPath = os.path.join(folder, pdfFile)
        wdPath=os.path.join(outpath,pdfFile)
        if wdPath[-4:] != '.txt':
            wdPath = wdPath + '.txt'
        pdf=pdfplumber.open(pdfPath)
        f=open(wdPath,'a',encoding='utf-8')
        i=0;
        for page in pdf.pages:
            text=pdf.pages[i].extract_text()
            print(text)
            i+=1
            f.write(text)
            f.write('\n')
        # print("------------------------------")

if __name__=='__main__':
    # with pdfplumber.open("D:\\LDA\\pdfdata\\zhm.pdf") as pdf:
    #     first_page = pdf.pages[2]
    #     text=first_page.extract_text()
    #     print(text)
    pdf_to_txt_plumber(inpath)

