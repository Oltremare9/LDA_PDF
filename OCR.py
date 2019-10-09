from PIL import Image
import pytesseract


def ocr2txt():
    image = Image.open('D:\\LDA\\LDAdata\\pdfdata_pic\\社会心理学视角下国内吸毒成因研究_李景春_1.png')
    code = pytesseract.image_to_string(image, lang='chi_sim')
    print(code)

if __name__=='__main__':
    ocr2txt()