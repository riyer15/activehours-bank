import java.util.Scanner;
import java.io.*;

public class TestQuotes
{
  public static void main(String[] args) throws IOException
  {
   /* Scanner keyboard = new Scanner(System.in);
    System.out.println("What is your input?");
    String input = keyboard.nextLine();
    StringBuffer inputBuffer = new StringBuffer(input);
    int quote1 = input.indexOf('"');
    System.out.println("quote1 = "+ quote1);
    StringBuffer newInput = new StringBuffer(inputBuffer.substring(quote1+1));
    int quote2 = newInput.lastIndexOf("\"");
    System.out.println("quote2 = " + quote2);
    if(quote2 > 0)
      {
        newInput = new StringBuffer(newInput.substring(0, quote2));
        int comma = newInput.indexOf(",");
        while(newInput.indexOf(",") > -1)
        {
          comma = newInput.indexOf(",");
          newInput = newInput.deleteCharAt(comma);
          System.out.println("newInput = "+ newInput);
        }
        
        inputBuffer =  inputBuffer.delete(quote1+1, quote1+quote2+1).insert(quote1+1,newInput.toString());
      }
    System.out.println(inputBuffer);
    */
    
    PrintWriter outputFile =
        new PrintWriter(new FileWriter("raniYes.txt"));
    outputFile.print("rANIisCool");
    outputFile.close();
    System.out.println("Done!!!!");
    
  }
}
