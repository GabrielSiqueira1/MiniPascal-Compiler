import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lexer teste = new Lexer("C:/Users/Gabriel/Projetos/MiniPascal-Compiler/Testes/Lexer/teste-sem-erros.txt");
        Token scan = teste.scan();
        if(scan.getClass().toString().equals("class Word")){
            Word word = (Word) scan;
            System.out.println("<"+word.getLexeme()+"\t,\t"+word.tag+">");
        } else System.out.println(scan);
        while(!(scan.toString().equals("65535"))){
            scan = teste.scan();
            if(scan.getClass().toString().equals("class Word")){
                Word word = (Word) scan;
                System.out.println("<"+word.getLexeme()+"\t,\t"+word.tag+">");
            } else System.out.println(scan);
        }
    }
}
