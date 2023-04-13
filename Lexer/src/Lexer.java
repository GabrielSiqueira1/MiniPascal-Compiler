import java.io.*;
import java.util.*;


public class Lexer {

   public static int line = 1; //contador de linhas
   private int length = 0; // limitador do tamanho das palavras
   private char ch = ' '; //caractere lido do arquivo
   private FileReader file;
   private SymbolTable words = new SymbolTable(null);
 
   /* Método para inserir palavras reservadas na HashTable */
   private void reserve(Word w){
      words.put(w.getLexeme(), w); // lexema é a chave para entrada na HashTable
   }

   public Lexer(String filename) throws FileNotFoundException{
      
      try{
         file = new FileReader (filename);
      }
      catch(FileNotFoundException e){
         System.out.println("Arquivo não encontrado");
         throw e;
      }
    
      //Insere palavras reservadas na HashTable
      reserve(new Word ("if", Tag.IF));
      reserve(new Word ("program", Tag.PRG));
      reserve(new Word ("begin", Tag.BEG));
      reserve(new Word ("end", Tag.END));
      reserve(new Word ("type", Tag.TYPE));
      reserve(new Word ("int", Tag.INT));
      reserve(new Word ("char", Tag.CHAR));
      reserve(new Word ("bool", Tag.BOOL));
      reserve(new Word ("then", Tag.THEN));
      reserve(new Word ("else", Tag.ELSE));
      reserve(new Word ("rep", Tag.REP));
      reserve(new Word ("until", Tag.UNTIL));
      reserve(new Word ("while", Tag.WHILE));
      reserve(new Word ("do", Tag.DO));
      reserve(new Word ("read", Tag.READ));
      reserve(new Word ("write", Tag.WRITE));
      reserve(new Word ("float", Tag.FLOAT));
   }

   /*Lê o próximo caractere do arquivo*/
   private void readch() throws IOException{
      ch = (char) file.read();
   }
   
   /* Lê o próximo caractere do arquivo e verifica se é igual a c*/
   private boolean readch(char c) throws IOException{
      readch();
      if (ch != c) return false;
         ch = ' ';
         return true;
   }
   
    public Token scan() throws Exception{
      //Desconsidera delimitadores na entrada
      for (;; readch()) {
         if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
         else if (ch == '\n') line++; //conta linhas
         else break;
      }
        
      switch(ch){
      //Operadores, pontuação, chaves e parênteses
         case '&':
            if (readch('&')) return Word.and;
            else return new Token('&');
         case '|':
            if (readch('|')) return Word.or;
            else return new Token('|');
         case '=':
            if (readch('=')) return Word.eq;
            else return Word.atrib;
         case '<':
            if (readch('=')) return Word.le;
            else return Word.l;
         case '>':
            if (readch('=')) return Word.ge;
            else return Word.g;
         case '+':
            readch();
            return Word.sum;
         case '-':
            readch();
            return Word.sub;
         case '*':
            readch();
            return Word.mult;
         case '/':
               if (readch('*')){
                  do{
                     readch();
                  }while(ch == '*' && readch('/'));
               }
               else return Word.div;
         case '.':
            readch();
            return Word.dot;
         case ';':
            readch();
            return Word.semicolon;
         case ',':
            readch();
            return Word.colon;
         case '{':
            if (!readch('\n')){
               StringBuffer sb = new StringBuffer();
               sb.append('{');
               do{
                  length += 1;
                  sb.append(ch);
                  readch();
               }while(Integer.valueOf(ch) != 10 && Integer.valueOf(ch) != 125 && length <= 255);
               
               if(Integer.valueOf(ch) == 10){
                  System.out.println("Token mal formado.");
               } else if (Integer.valueOf(ch) == 125){
                  sb.append(ch);
                  String s = sb.toString();

                  return new Literal(s);
               } else if (length == 256){
                  System.out.println("Literal com tamanho limite.");
               }
            }
            else return Word.openb;
         case '}':
            readch();
            return Word.closeb;
         case ')':
            readch();
            return Word.closep;
         case '(':
            readch();
            return Word.openp;
      }

      // Caractere Constante ''
      if(Integer.valueOf(ch) == 39){
         StringBuffer sb = new StringBuffer();
         sb.append(ch);
         readch();
         sb.append(ch);
         readch();
         if(Integer.valueOf(ch) == 39){
            sb.append(ch);
            String s = sb.toString();

            return new CharConst(s);
         } else throw new Exception("Token mal formado na linha "+line);
      }

      //Números
      if (Character.isDigit(ch)){
         int value=0;
         int dot = 0;
         int f = 0;
         do{
            value = 10*value + Character.digit(ch,10);
            if(readch('.')){
               f = 1;
               dot = -1;
               readch();
               if(!Character.isDigit(ch)) throw new Exception("Token mal formado na linha "+line);  
            }
            dot += 1;
         }while(Character.isDigit(ch));
         if(f == 0) return new NumI(value);
         else return new NumF(value/(Math.pow(10, dot)));
      }

      //Identificadores
      if (Character.isLetter(ch)){
         StringBuffer sb = new StringBuffer();
         do{
            sb.append(ch);
            readch();
         }while(Character.isLetterOrDigit(ch) || Integer.valueOf(ch) == 95);
         
         String s = sb.toString();
         Word w = (Word)words.get(s);
         
         if (w != null) return w; //palavra já existe na HashTable
            w = new Word (s, Tag.ID);
            words.put(s, w);
         return w;
      }
    
      //Caracteres não especificados
      Token t = new Token(ch);
      ch = ' ';
      return t;

   }

   public Hashtable<String, Word> getWords(){
      return words.getTable();
   }
      
}
