public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Syntactic/primeiro-teste-extra-sextaCorrecao.txt");
        new Syntactic(lex);
    }
}
