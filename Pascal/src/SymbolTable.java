import java.util.Hashtable;

public class SymbolTable {
    private Hashtable<String, Word> table; //tabela de símbolos do ambiente
    protected SymbolTable prev; //ambiente imediatamente superior

    public SymbolTable(SymbolTable n){
        table = new Hashtable<String, Word>(); //cria a TS para o ambiente
        prev = n; //associa o ambiente atual ao anterior
    }

    public void put(String w, Word i){
        table.put(w,i);
    }

    public Word get(String w){
        for (SymbolTable e = this; e!=null; e = e.prev){
            Word found = (Word) e.table.get(w);
        if (found != null) //se Token existir em uma das TS
            return found;
        }
            return null; //caso Token não exista em uma das TS
    }
       
    public Hashtable<String, Word> getTable() {
        return this.table;
    }

}
