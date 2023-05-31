public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lex = new Lexer("../../Testes/Lexer/segundo-teste-extra-erro.txt");
        Sintatic sint = new Sintatic(lex);
    }
}
