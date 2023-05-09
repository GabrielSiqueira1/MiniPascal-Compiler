public class NumI extends Token{
    public final int value;
    public NumI(int value){
        super(Tag.INTCONST);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
   
