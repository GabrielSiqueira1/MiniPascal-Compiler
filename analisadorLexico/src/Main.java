import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lexer teste = new Lexer("C:/Users/Gabriel/Projetos/MiniPascal-Compiler/Testes/Lexer/teste-sem-erros.txt");
        System.out.println(teste.scan().toString());
        System.out.println("-----");
        while(!(teste.scan().toString().equals("65535"))){
            System.out.println(teste.scan().toString());
            System.out.println("-----");
        }
    }
}
