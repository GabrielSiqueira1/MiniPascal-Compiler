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
    LITERAL = 282,
    INTCONST = 283,
    FLOATCONST = 284;

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
    
    void program(){
        switch(tok){
            case PROGRAM: eat(ID);
                switch(tok){
                    case ID: decl_list(); eat(BEGIN); break;
                    case BEGIN: break;
                    default: throw new Exception("Token inesperado!");
                }

                stmt_list(); eat(END); eat(DOT); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void decl_list(){ 
        switch(tok){
            case ID: decl(); eat(SEMICOLON); 
                boolean shouldBreak = false;
                while(true){
                    switch(tok){
                        case ID: decl(); eat(SEMICOLON); break;
                        default: shouldBreak = true; break;
                    }
                    if(shouldBreak){
                        break;
                    }
                }
                break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void decl(){ 
        switch(tok){
            case ID: ident_list(); eat(IS); type(); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void ident_list(){ 
        switch(tok){
            case ID: 
                boolean shouldBreak = false;
                while(true){
                    switch(tok){
                        case SEMICOLON: eat(ID); break;
                        default: shouldBreak = true; break;
                    }
                    if(shouldBreak){
                        break;
                    }
                }
                break;
                default: throw new Exception("Token inesperado!");
        }
    }

    void type(){
        switch(tok){
            case INT: break;
            case FLOAT: break;
            case CHAR: break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void stmt_list(){ 
        switch(tok){
            case ID:
            case IF:
            case WHILE:
            case REPEAT:
            case READ:
            case WRITE: stmt();
                boolean shouldBreak = false;
                    while(true){
                        switch(tok){
                            case SEMICOLON: stmt(); break;
                            default: shouldBreak = true; break;
                        }
                        if(shouldBreak){
                            break;
                        }
                    }
                    break;
                default: throw new Exception("Token inesperado!");
        }
    }

    void stmt(){
        switch(tok){
            case ID: assign_stmt(); break;
            case IF: if_stmt(); break;
            case WHILE: while_stmt(); break;
            case REPEAT: repeat_stmt(); break;
            case READ: read_stmt(); break;
            case WRITE: write_stmt(); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void assign_stmt(){
        switch(tok){
            case ID: eat(EQ); simple_expr(); break;
            default: throw new Exception("Token inesperado!");
        }
    }

    void if_stmt(){
        switch(tok){
            case IF: condition(); eat(THEN); stmt_list(); if_stmt_prime(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void if_stmt_prime(){
        switch(tok){
            case END: break;
            case ELSE: stmt_list(); eat(END); break;
            default: throw new Exception("Token inesperado!");
        }
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

    void term() throw Exception{
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
    
    void fatorA() throw Exception{
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
    
    void factor() throw Exception{
        switch(tok){
            case ID: eat(ID); break;
            case INT:
            case FLOAT:
            case CHAR: constant(); break;
            case OPENP: eat(OPENP); expr(); eat(CLOSEP); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void termPrime() throw Exception{
        switch(tok){
            case MULT:
            case DIV:
            case AND: mulOp(); fatorA(); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void relOp() throw Exception{
        switch(tok){
            case EQ: eat(EQ); break;
            case G: eat(G); break;
            case GE: eat(GE); break;
            case NE: eat(NE); break;
            case L: eat(L); break;
            case LE: eat(LE); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void mulOp() throw Exception{
        switch(tok){
            case MULT: eat(MULT); break;
            case DIV: eat(DIV); break;
            case AND: eat(AND); break;
            default: throw new Exception("Token inesperado!");
        }
    }
    
    void constant() throw Exception{
        switch(tok){
            case INTCONST: eat(INTCONST); break;
            case FLOATCONST: eat(FLOATCONST); break;
            case CHARCONST: eat(CHARCONST); break;
            default: throw new Exception("Token inesperado!");
        }
    }
}
