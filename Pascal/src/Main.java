public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Semantic/primeiro-teste-extra-primeiraCorrecao.txt");
        new Syntactic(lex);
    }
}
