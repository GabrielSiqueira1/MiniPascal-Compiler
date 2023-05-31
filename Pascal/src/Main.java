public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("/home/gabriel/Projetos/MiniPascal-Compiler/Testes/Sintatic/teste1-quartaCorrecao.txt");
        Sintatic sint = new Sintatic(lex);
    }
}
