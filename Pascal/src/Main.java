public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Syntactic/segundo-teste-extra-quartaCorrecao.txt");
        new Syntactic(lex);
    }
}
