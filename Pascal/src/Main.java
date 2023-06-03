public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Syntactic/teste1-quintaCorrecao.txt");
        new Syntactic(lex);
    }
}
