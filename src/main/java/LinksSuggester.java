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
    ArrayList<String> keyWords = new ArrayList<>();
    ArrayList<String> titles =new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();

    public LinksSuggester(File file, Scanner reader) throws IOException, WrongLinksFormatException {
        this.file = file;
        this.reader =reader;


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
    public  void createSuggest() {
        int g;

        while (reader.hasNextLine()){



    builder.append(reader.nextLine());
    try {


    g = builder.indexOf("\t");
    if ((g == 0) || (g == -1)) {
        keyWords.add(null);
        titles.add(null);
        url.add(null);
        throw new WrongLinksFormatException("Неверный формат конфига");

    }
    keyWords.add(builder.substring(0, g));
    builder.delete(0, g + 1);
    g = builder.indexOf("\t");
        if ((g == 0) || (g == -1)) {

            titles.add(null);
            url.add(null);
            throw new WrongLinksFormatException("Неверный формат конфига");

        }
    titles.add(builder.substring(0, g));
    builder.delete(0, g + 1);
    if (builder.length() == 0) {
        url.add(null);
        throw new WrongLinksFormatException("Неверный формат конфига");

    }
    url.add(builder.toString());
    builder.delete(0, builder.length());

          }catch (WrongLinksFormatException e){
        System.out.println(e.getMessage());
        builder.delete(0,builder.length());
    }}
        for (int i=0;i<keyWords.size();i++){
            if (keyWords.get(i) != null && titles.get(i) != null && url.get(i) != null)
            suggests.add(new Suggest(keyWords.get(i),titles.get(i), url.get(i) ));
        }

    }
}
