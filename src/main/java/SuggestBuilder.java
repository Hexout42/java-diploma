public class SuggestBuilder {
    private  String keyWord;
    private  String title;
    private  String url;
    public  SuggestBuilder(String keyWord){
        this.keyWord=keyWord;
    }

    public SuggestBuilder withTitle(String title){
        this.title=title;
        return this;
    }
    public SuggestBuilder withUrl(String url){
        this.url=url;
        return this;
    }
    public Suggest build(){
        return new Suggest(keyWord,title,url);
    }
}
