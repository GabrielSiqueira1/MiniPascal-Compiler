public class Word extends Token{
    private String lexeme = "";

    public static final Word is = new Word ("is", Tag.IS);
    public static final Word eq = new Word ("==", Tag.EQ);
    public static final Word g = new Word (">", Tag.G);
    public static final Word ge = new Word (">=", Tag.GE);
    public static final Word l = new Word ("<", Tag.L);
    public static final Word le = new Word ("<=", Tag.LE);
    public static final Word ne = new Word("!=", Tag.NE);
    public static final Word sum = new Word("+", Tag.SUM);
    public static final Word sub = new Word("-", Tag.SUB);
    public static final Word or = new Word("||", Tag.OR);
    public static final Word excl = new Word("!", Tag.EXCL);
    public static final Word mult = new Word("*", Tag.MULT);
    public static final Word div = new Word("/", Tag.DIV);
    public static final Word and = new Word("&&", Tag.AND);
    public static final Word atrib = new Word("=", Tag.ATRIB);
    public static final Word dot = new Word(".", Tag.DOT);
    public static final Word colon = new Word(",", Tag.COLON);
    public static final Word semicolon = new Word(";", Tag.SEMICOLON);
    public static final Word openb = new Word("{", Tag.OPENB);
    public static final Word closeb = new Word("}", Tag.CLOSEB);
    public static final Word openp = new Word("(", Tag.OPENP);
    public static final Word closep = new Word(")", Tag.CLOSEP);

    public Word (String s, int tag){
        super (tag);
        lexeme = s;
    }
    public String getLexeme(){
        return "" + lexeme;
    }
}
   