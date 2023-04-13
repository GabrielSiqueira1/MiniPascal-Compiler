public class NumF extends Token{
    public final double value;
    public NumF(double value){
        super(Tag.FLOAT);
        this.value = value;
    }
    
    public String toString(){
        return "" + value;
    }
    public String tag() {
        return super.getName;
   }
}
   
