public class Sintatic {
    final int
    //Palavras reservadas
    IS = 255,
    PRG = 256,
    BEG = 257,
    END = 258,
    TYPE = 259,
    INT = 260,
    CHAR = 261,
    BOOL = 262,
    IF = 263,
    THEN = 264, 
    ELSE = 265,
    REPEAT = 266,
    UNTIL = 267,
    WHILE = 268,
    DO = 269,
    READ = 270,
    WRITE = 271,
    FLOAT = 272,

    //Operadores e pontuação
    EQ = 288,
    G = 289,
    GE = 290,
    L = 291,
    LE = 292,
    NE = 293,
    SUM = 294,
    SUB = 295,
    OR = 296,
    MULT = 297,
    DIV = 298,
    AND = 299,
    DOT = 300,
    COLON = 301,
    SEMICOLON = 302,
    EXCL = 303,

    //Outros tokens
    ID = 273,
    OPENB = 274,
    CLOSEB = 275,
    OPENQ = 276,
    CLOSEQ = 277,
    OPENP = 278,
    CLOSEP = 279,
    ATRIB = 280,
    CHARCONST = 281,
    LITERAL = 282;

    int tok = 0;
    Lexer v;

    public Sintatic(Lexer teste) throws Exception{
        v = teste;
        tok = v.scan().tag; 
    }

    void advance() throws Exception{
        tok = v.scan().tag;
    }

    void eat(int t) throws Exception{
        if(tok == t) advance();
        else throw new Exception("Token desigual ao caractere esperado!");
    }

    void simpleExpr() throws Exception{
        switch(tok){
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP: term(); simpleExprPrime(); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void simpleExprPrime() throws Exception{
        switch(tok){
            case SUM:
            case SUB:
            case OR: addOp(); term(); simpleExprPrime(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void addOp() throws Exception{
        switch(tok){
            case SUM: eat(SUM); break;
            case SUB: eat(SUB); break;
            case OR: eat(OR); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void term(){
        switch(tok){
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
            case EXCL: 
            case SUB: fatorA(); termPrime(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void fatorA(){
        switch(tok){
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP: factor(); break;
            case EXCL: eat(EXCL); factor(); break;
            case SUB: eat(SUB); factor(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void factor(){
        switch(tok){
            case ID: eat(ID); break;
            case INT: eat(INT); break;
            case FLOAT: eat(FLOAT); break;
            case CHAR: eat(CHAR); break;
            case OPENP: eat(OPENP); expr(); eat(CLOSEP); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void termPrime(){
        switch(tok){
            case MULT:
            case DIV:
            case AND: mulop(); fatorA(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void mulop(){
        switch(tok){
            case MULT: eat(MULT); break;
            case DIV: eat(DIV); break;
            case AND: eat(AND); break;
            default: throw new Exception("Token inesperado!");
        }
    }
}
