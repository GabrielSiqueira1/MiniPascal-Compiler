public class NumI extends Token{
    public final int value;
    public NumI(int value){
        super(Tag.INT);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
   