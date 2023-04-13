public class Main {
    public static void main(String[] args) throws Exception {
        Lexer teste = new Lexer("C:/Users/Gabriel/Projetos/MiniPascal-Compiler/Testes/Lexer/teste2.txt");
        Token scan = teste.scan();
        while(!(scan.toString().equals("65535"))){
            
            if(scan.getClass().toString().equals("class Word")){
                Word word = (Word) scan;
                System.out.println("<"+word.getLexeme()+"\t,\t"+word.tag+">");
            } else System.out.println("<"+scan+"\t,\t"+scan.tag+">");

            scan = teste.scan();
        }
    }
}
