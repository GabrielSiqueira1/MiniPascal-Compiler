import java.util.HashMap;
import java.util.ArrayList;

public class Syntactic {

    public class SemObj {
        String type;

        public SemObj(String s) {
            this.type = s;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    HashMap<String, String> variables = new HashMap<>();
    final int
    // Palavras reservadas
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

            // Operadores e pontuação
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

            // Outros tokens
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
    Token token = null;
    Word w;
    Lexer v;

    public Syntactic(Lexer teste) throws Exception {
        v = teste;
        tok = v.scan().tag;
        program();
        System.out.println("Código semanticamente correto!");
    }

    void advance() throws Exception {
        if (tok == 273 && token instanceof Word) {
            w = (Word) token;
            token = v.scan();
            tok = token.tag;
        } else {
            token = v.scan();
            tok = token.tag;
        }

        if (tok == 10) {
            token = v.scan();
            tok = token.tag;
        }
    }

    void eat(int t) throws Exception {
        // System.out.println("tok: " + tok);
        // System.out.println("t: " + t);
        // System.out.println(variables);
        if (tok == t)
            advance();
        else
            throw new Exception("Token desigual ao caractere esperado na linha " + v.getLines());
    }

    void program() throws Exception {
        switch (tok) {
            case PRG:
                eat(PRG);
                eat(ID);
                switch (tok) {
                    case ID:
                        declList();
                        eat(BEG);
                        break;
                    case BEG:
                        break;
                    default:
                        throw new Exception("Token inesperado na linha " + v.getLines());
                }

                stmtList();
                eat(END);
                eat(DOT);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void declList() throws Exception {
        switch (tok) {
            case ID:
                decl();
                eat(SEMICOLON);
                boolean shouldBreak = false;
                while (true) {
                    switch (tok) {
                        case ID:
                            decl();
                            eat(SEMICOLON);
                            break;
                        default:
                            shouldBreak = true;
                            break;
                    }
                    if (shouldBreak) {
                        break;
                    }
                }
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void decl() throws Exception {
        ArrayList<String> newVar;
        switch (tok) {
            case ID:
                newVar = identList();
                eat(IS);
                SemObj o = type();
                for (String s : newVar) {
                    variables.put(s, o.type);
                }
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    ArrayList<String> identList() throws Exception {
        ArrayList<String> newVar = new ArrayList<>();
        switch (tok) {
            case ID:
                boolean shouldBreak = false;
                eat(ID);
                if (!variables.containsKey(w.getLexeme())){
	    		variables.put(w.getLexeme(), "none");
	    		newVar.add(w.getLexeme());
		}
		else throw new Exception("Variável já declarada na linha "+ v.getLines());
                while (true) {
                    switch (tok) {
                        case COLON:
                            eat(COLON);
                            eat(ID);
                            if (!variables.containsKey(w.getLexeme())){
                            	variables.put(w.getLexeme(), "none");
                            	newVar.add(w.getLexeme());
                            }
                            else throw new Exception("Variável já declarada na linha "+ v.getLines());
                            break;
                        default:
                            shouldBreak = true;
                            break;
                    }
                    if (shouldBreak) {
                        break;
                    }
                }
                return newVar;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj type() throws Exception {
        switch (tok) {
            case INT:
                eat(INT);
                SemObj o1 = new SemObj("int");
                return o1;
            case FLOAT:
                eat(FLOAT);
                SemObj o2 = new SemObj("float");
                return o2;
            case CHAR:
                eat(CHAR);
                SemObj o3 = new SemObj("char");
                return o3;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void stmtList() throws Exception {
        switch (tok) {
            case ID:
            case IF:
            case WHILE:
            case REPEAT:
            case READ:
            case WRITE:
                stmt();
                boolean shouldBreak = false;
                while (true) {
                    switch (tok) {
                        case SEMICOLON:
                            eat(SEMICOLON);
                            stmt();
                            break;
                        default:
                            shouldBreak = true;
                            break;
                    }
                    if (shouldBreak) {
                        break;
                    }
                }
                break;

            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void stmt() throws Exception {
        switch (tok) {
            case ID:
                assignStmt();
                break;
            case IF:
                ifStmt();
                break;
            case WHILE:
                whileStmt();
                break;
            case REPEAT:
                repeatStmt();
                break;
            case READ:
                readStmt();
                break;
            case WRITE:
                writeStmt();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void assignStmt() throws Exception {
        switch (tok) {
            case ID:
                eat(ID);
                if (!variables.containsKey(w.getLexeme()))
                    throw new Exception("Variável não declarada na linha " + v.getLines());
                SemObj o1 = new SemObj(variables.get(w.getLexeme()));
                eat(ATRIB);
                SemObj o2 = simpleExpr();
                if ((o1.type == o2.type) || (o1.type == "float" && o2.type == "int")) {
                    break;
                } else {
                    throw new Exception("Tipos incompatíveis na linha " + v.getLines());
                }

            default:
                throw new Exception("Token inesperado " + v.getLines());
        }
    }

    void ifStmt() throws Exception {
        switch (tok) {
            case IF:
                eat(IF);
                condition();
                eat(CLOSEP);
                eat(THEN);
                stmtList();
                ifStmtPrime();
                break;
            default:
                throw new Exception("Token inesperado!");
        }
    }

    void ifStmtPrime() throws Exception {
        switch (tok) {
            case END:
                eat(END);
                break;
            case ELSE:
                eat(ELSE);
                stmtList();
                eat(END);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj simpleExpr() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
                SemObj o1 = term();
                simpleExprPrime(o1);
                return o1;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void simpleExprPrime(SemObj o1) throws Exception {
        switch (tok) {
            case SUM:
            case SUB:
            case OR:
                addOp();
                SemObj o2 = term();
                if (o1.type == o2.type) {
                    simpleExprPrime(o2);
                } else {
                    throw new Exception("Tipos incompatíveis na linha " + v.getLines());
                }
                break;
            default:
        }
    }

    void addOp() throws Exception {
        switch (tok) {
            case SUM:
                eat(SUM);
                break;
            case SUB:
                eat(SUB);
                break;
            case OR:
                eat(OR);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj term() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
            case EXCL:
            case SUB:
                SemObj o1 = fatorA();
                o1 = termPrime(o1);
                return o1;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj fatorA() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
                SemObj o1 = factor();
                return o1;
            case EXCL:
                eat(EXCL);
                SemObj o2 = factor();
                return o2;
            case SUB:
                eat(SUB);
                SemObj o3 = factor();
                if (o3.type == "int" || o3.type == "float") {
                    return o3;
                } else {
                    throw new Exception("Tipos incompatíveis na linha " + v.getLines());
                }
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj factor() throws Exception {
        switch (tok) {
            case ID:
                eat(ID);
                if (!variables.containsKey(w.getLexeme()))
                    throw new Exception("Variável não declarada na linha " + v.getLines());
                SemObj o1 = new SemObj(variables.get(w.getLexeme()));
                return o1;
            case INT:
            case FLOAT:
            case CHAR:
                SemObj o2 = constant();
                return o2;
            case OPENP:
                SemObj o3 = expression();
                eat(CLOSEP);
                return o3;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj termPrime(SemObj o1) throws Exception {
        switch (tok) {
            case MULT:
            case DIV:
            case AND:
                SemObj o4 = new SemObj("float");
                SemObj o3 = mulOp();
                SemObj o2 = fatorA();
                if (o1.type == o2.type && o3.type == "/") {
                    o4.type = "float";
                    termPrime(o2);
                } else if (o1.type == o2.type) {
                    o4.type = o1.type;
                    termPrime(o2);
                } else {
                    throw new Exception("Tipos incompatíveis na linha " + v.getLines());
                }
                return o4;
            default:
                return o1;
        }
    }

    void relOp() throws Exception {
        switch (tok) {
            case EQ:
                eat(EQ);
                break;
            case G:
                eat(G);
                break;
            case GE:
                eat(GE);
                break;
            case NE:
                eat(NE);
                break;
            case L:
                eat(L);
                break;
            case LE:
                eat(LE);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj mulOp() throws Exception {
        switch (tok) {
            case MULT:
                eat(MULT);
                SemObj o1 = new SemObj("*");
                return o1;
            case DIV:
                eat(DIV);
                SemObj o2 = new SemObj("/");
                return o2;
            case AND:
                eat(AND);
                SemObj o3 = new SemObj("&&");
                return o3;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj constant() throws Exception {
        switch (tok) {
            case INT:
                eat(INT);
                SemObj o1 = new SemObj("int");
                // System.out.println(o1.type);
                return o1;
            case FLOAT:
                eat(FLOAT);
                SemObj o2 = new SemObj("float");
                return o2;
            case CHAR:
                eat(CHAR);
                SemObj o3 = new SemObj("char");
                return o3;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void writeStmt() throws Exception {
        switch (tok) {
            case WRITE:
                eat(WRITE);
                eat(OPENP);
                writable();
                eat(CLOSEP);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void writable() throws Exception {
        switch (tok) {
            case LITERAL:
                eat(LITERAL);
                break;
            default:
                simpleExpr();
        }
    }

    void condition() throws Exception {
        expression();
    }

    void stmtSuffix() throws Exception {
        switch (tok) {
            case UNTIL:
                eat(UNTIL);
                condition();
                eat(CLOSEP);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void repeatStmt() throws Exception {
        switch (tok) {
            case REPEAT:
                eat(REPEAT);
                stmtList();
                stmtSuffix();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void whileStmt() throws Exception {
        stmtPrefix();
        stmtList();
        try {
            eat(END);
        } catch (Exception e) {
            throw new Exception("Token inesperado na linha " + v.getLines());
        }

    }

    void stmtPrefix() throws Exception {
        switch (tok) {
            case WHILE:
                eat(WHILE);
                condition();
                eat(CLOSEP);
                eat(DO);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void readStmt() throws Exception {
        switch (tok) {
            case READ:
                eat(READ);
                eat(OPENP);
                eat(ID);
                if (!variables.containsKey(w.getLexeme()))
                    throw new Exception("Variável não declarada na linha " + v.getLines());
                eat(CLOSEP);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    SemObj expression() throws Exception {
        switch (tok) {
            case OPENP:
                eat(OPENP);
                SemObj o1 = simpleExpr();
                expressionAuxiliar(o1);
                return o1;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void expressionAuxiliar(SemObj o1) throws Exception {
        switch (tok) {
            case EQ:
            case G:
            case GE:
            case NE:
            case L:
            case LE:
                relOp();
                SemObj o2 = simpleExpr();
                if (o1.type == o2.type) {
                    break;
                } else {
                    throw new Exception("Tipos incompatíveis na linha " + v.getLines());
                }
            default:
        }
    }
}
