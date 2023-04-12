import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Lexer teste = new Lexer("/home/gabriel/Projetos/MiniPascal-Compiler/Testes/Lexer/teste1.txt");
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
