public class CharConst extends Token{
    public final String value;
    public CharConst(String value){
        super(Tag.CHARCONST);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
   