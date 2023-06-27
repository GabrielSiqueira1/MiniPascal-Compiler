public class Syntactic {
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
    Lexer v;

    public Syntactic(Lexer teste) throws Exception {
        v = teste;
        tok = v.scan().tag;
        program();
        System.out.println("Código sintaticamente correto!");
    }

    void advance() throws Exception {
        tok = v.scan().tag;
        if (tok == 10) {
            tok = v.scan().tag;
        }
    }

    void eat(int t) throws Exception {
        System.out.println("tok: " + tok);
        System.out.println("t: " + t);
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
        switch (tok) {
            case ID:
                identList();
                eat(IS);
                type();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void identList() throws Exception {
        switch (tok) {
            case ID:
                boolean shouldBreak = false;
                eat(ID);
                while (true) {
                    switch (tok) {
                        case COLON:
                            eat(COLON);
                            eat(ID);
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

    void type() throws Exception {
        switch (tok) {
            case INT:
                eat(INT);
                break;
            case FLOAT:
                eat(FLOAT);
                break;
            case CHAR:
                eat(CHAR);
                break;
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
                eat(ATRIB);
                simpleExpr();
                break;
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

    void simpleExpr() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
                term();
                simpleExprPrime();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void simpleExprPrime() throws Exception {
        switch (tok) {
            case SUM:
            case SUB:
            case OR:
                addOp();
                term();
                simpleExprPrime();
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

    void term() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
            case EXCL:
            case SUB:
                fatorA();
                termPrime();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void fatorA() throws Exception {
        switch (tok) {
            case ID:
            case INT:
            case FLOAT:
            case CHAR:
            case OPENP:
                factor();
                break;
            case EXCL:
                eat(EXCL);
                factor();
                break;
            case SUB:
                eat(SUB);
                factor();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void factor() throws Exception {
        switch (tok) {
            case ID:
                eat(ID);
                break;
            case INT:
            case FLOAT:
            case CHAR:
                constant();
                break;
            case OPENP:
                expression();
                eat(CLOSEP);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void termPrime() throws Exception {
        switch (tok) {
            case MULT:
            case DIV:
            case AND:
                mulOp();
                fatorA();
                termPrime();
                break;
            default:
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

    void mulOp() throws Exception {
        switch (tok) {
            case MULT:
                eat(MULT);
                break;
            case DIV:
                eat(DIV);
                break;
            case AND:
                eat(AND);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void constant() throws Exception {
        switch (tok) {
            case INT:
                eat(INT);
                break;
            case FLOAT:
                eat(FLOAT);
                break;
            case CHAR:
                eat(CHAR);
                break;
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
                eat(CLOSEP);
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void expression() throws Exception {
        switch (tok) {
            case OPENP:
                eat(OPENP);
                simpleExpr();
                expressionAuxiliar();
                break;
            default:
                throw new Exception("Token inesperado na linha " + v.getLines());
        }
    }

    void expressionAuxiliar() throws Exception {
        switch (tok) {
            case EQ:
            case G:
            case GE:
            case NE:
            case L:
            case LE:
                relOp();
                simpleExpr();
                break;
            default:
        }
    }
}
