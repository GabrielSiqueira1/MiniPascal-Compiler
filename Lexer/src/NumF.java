public class NumF extends Token{
    public final double value;
    public NumF(double value){
        super(Tag.FLOATCONST);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
}
   
