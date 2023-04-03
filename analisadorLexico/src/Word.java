public class Word extends Token{
    private String lexeme = "";

    public static final Word eq = new Word ("==", Tag.EQ);
    public static final Word g = new Word (">", Tag.G);
    public static final Word ge = new Word (">=", Tag.GE);
    public static final Word l = new Word ("<", Tag.L);
    public static final Word le = new Word ("<=", Tag.LE);
    public static final Word ne = new Word("!=", Tag.NE);
    public static final Word sum = new Word("+", Tag.SUM);
    public static final Word sub = new Word("-", Tag.SUB);
    public static final Word or = new Word("||", Tag.OR);
    public static final Word mult = new Word("*", Tag.MULT);
    public static final Word div = new Word("/", Tag.DIV);
    public static final Word and = new Word("&&", Tag.AND);


    public Word (String s, int tag){
        super (tag);
        lexeme = s;
    }
    public String getLexeme(){
        return "" + lexeme;
    }
}
   