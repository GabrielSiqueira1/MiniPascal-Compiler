public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Semantic/teste1-segundaCorrecao.txt");
        new Syntactic(lex);
    }
}
