public class Word {
    private String inword;
    public static String all ="";
    public Word(String str)
    {
        inword = str;
        all= all+ " " + str;
    }

    public static void main(String[] args)
    {
        Word name = new Word("Farzana");
        Word fname = new Word("Bob");
        Word aname = new Word("Cat");
        System.out.print(Word.all);

}
}
