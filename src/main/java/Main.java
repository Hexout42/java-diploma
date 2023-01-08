
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Canvas;

import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;

import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Main {
    File file = new File("data/config");
  Scanner reader  =new Scanner(file);
  List<File> pdfs = new ArrayList<>();
  List<Suggest> suggestList = new ArrayList<>();
  ArrayList<PdfDocument> allobject = new ArrayList<>();
  List<Suggest> allSuggest = new ArrayList<>();


  File outPdf;

    public Main() throws IOException {
    }
    LinksSuggester linksSuggester = new LinksSuggester(file,reader);


    public static void main(String[] args) throws Exception {
        Main main = new Main();



        main.pdfInput();
        main.pdfReader();


       }



    public void pdfInput() throws IOException {
        var dir = new File("data/pdfs");

        for (var fileIn : dir.listFiles()) {

            pdfs.add(fileIn);
            outPdf = new File("data/converted/" + fileIn.getName());
            outPdf.createNewFile();
            var doc = new PdfDocument(new PdfReader(fileIn), new PdfWriter(outPdf));
            allobject.add(doc);
        }


    }


    public void pdfReader() throws IOException {
       int allPages;
        for (int i=0;i<allobject.size();i++){
           var doc= allobject.get(i);
           allPages =doc.getNumberOfPages();

           allSuggest = rest(linksSuggester.getAllsuggest());

           for (int j=1;j<=allPages;j++){
               var text = PdfTextExtractor.getTextFromPage(doc.getPage(j));
               text.toLowerCase();
                  suggestList = linksSuggester.suggest(text);
                                  if ((suggestList.size() > 0)&&(allSuggest.size()>0) && (checkSuggest(suggestList))){
                                        createNewPage(suggestList,j+1,doc);
                                        check(allSuggest,suggestList);

                                        allPages+=1;
                                        j++;

                                  }

           }
            allobject.get(i).close();

        }


    }
    public void createNewPage( List<Suggest> suggestList,int i,PdfDocument doc){

         var newPage = doc.addNewPage(i);
         var rect = new Rectangle(newPage.getPageSize()).moveRight(10).moveDown(10);
        Canvas canvas = new Canvas(newPage, rect);
      Paragraph paragraph = new Paragraph("Suggestions:\n");
         paragraph.setFontSize(25);
         putIntoConfig(suggestList,rect,paragraph);
         canvas.add(paragraph);  }
    public void putIntoConfig( List<Suggest> suggestList,Rectangle rect,Paragraph paragraph){
        StringBuilder builder = new StringBuilder();
        Link link;
            for (int i=0;i<suggestList.size();i++){
                if (allSuggest.indexOf(suggestList.get(i))>-1) {
                    PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
                    PdfAction action = PdfAction.createURI(suggestList.get(i).getUrl());
                    annotation.setAction(action);
                    link = new Link(suggestList.get(i).getTitle(), annotation);
                    paragraph.add(link.setUnderline());
                    paragraph.add("\n");
                }}

    }
    public  void check (List<Suggest> all, List<Suggest> suggestList){
        for (int i=0;i<all.size();i++){
            for (int j=0;j<suggestList.size();j++){
                if (all.get(i).equals(suggestList.get(j)))
                    all.remove(i);
            }
        }
    }
    public boolean checkSuggest(List<Suggest> list){
        for (int i=0;i<suggestList.size();i++){
            if (allSuggest.indexOf(suggestList.get(i))>-1)
                return true;
    }
        return false;
    }
    public List<Suggest> rest( List<Suggest> link){
        List<Suggest> all = new ArrayList<>();
        for (int i=0; i<link.size();i++){

            all.add(link.get(i));
        }
        return all;
    }

}





