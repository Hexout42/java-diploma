import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LinksSuggester {
 private final  List<Suggest> suggests = new ArrayList<>();
List<Suggest> inText ;

File file;
Scanner reader;
StringBuilder builder =new StringBuilder();


    public LinksSuggester(File file) throws IOException, WrongLinksFormatException {
        this.file = file;
        reader = new Scanner(file);


            createSuggest();

    }
    public List<Suggest> getAllsuggest(){
        return suggests;
    }

    public List<Suggest> suggest(String text)  {

        inText = new ArrayList<>();
           for (int i =0; i<suggests.size();i++){
               if( text.indexOf(suggests.get(i).getKeyWord())>0){
                   inText.add(suggests.get(i));
               }

           }

           return inText;
    }
    private   void createSuggest() {
        int g;

        while (reader.hasNextLine()){



    builder.append(reader.nextLine());
    try {


    g = builder.indexOf("\t");
    if ((g == 0) || (g == -1)) {

        throw new WrongLinksFormatException("Неверный формат конфига");

    }
    SuggestBuilder suggestBuilder =new SuggestBuilder(builder.substring(0,g));
    builder.delete(0, g + 1);
    g = builder.indexOf("\t");
        if ((g == 0) || (g == -1)) {


            throw new WrongLinksFormatException("Неверный формат конфига");

        }
   suggestBuilder.withTitle(builder.substring(0, g));
    builder.delete(0, g + 1);
    if (builder.length() == 0) {

        throw new WrongLinksFormatException("Неверный формат конфига");

    }
    suggestBuilder.withUrl(builder.toString());
    builder.delete(0, builder.length());
    suggests.add(suggestBuilder.build());
          }catch (WrongLinksFormatException e){
        System.out.println(e.getMessage());
        builder.delete(0,builder.length());
    }}
        reader.close();


    }
}
