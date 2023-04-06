import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Lexer teste = new Lexer("C:/Users/Gabriel/Projetos/MiniPascal-Compiler/Testes/Lexer/teste-sem-erros.txt");
        Token scan = teste.scan();
        while(!(scan.toString().equals("65535"))){
            
            if(scan.getClass().toString().equals("class Word")){
                Word word = (Word) scan;
                System.out.println("<"+word.getLexeme()+"\t,\t"+word.tag+">");
            } else System.out.println("<"+scan+"\t,\t404>");

            scan = teste.scan();
        }
    }
}
