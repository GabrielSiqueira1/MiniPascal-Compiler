import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lexer teste = new Lexer();
        for (int i = 0; i < 100; i++){
            System.out.println(teste.scan());
            System.out.println("------------------------");
            System.out.println(teste.getWords());
        }
    }
}
