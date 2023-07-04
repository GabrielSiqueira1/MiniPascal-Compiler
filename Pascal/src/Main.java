public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Semantic/segundo-teste-extra-segundaCorrecao.txt");
        new Syntactic(lex);
    }
}
