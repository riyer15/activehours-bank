import java.io.*;
import java.util.*;


public class GetData extends ArrayList<DataReader>
{
  private static ArrayList<DataReader> thisData = new ArrayList<DataReader>();
  
  public static void addAllEntries() throws FileNotFoundException, IOException
  {
    try(BufferedReader br = new BufferedReader(new FileReader("Purdue-data-v1.3.txt"))) 
    {
      String line = br.readLine();
      while(line != null && line != "")
      {
       thisData.add(new DataReader(line));
       line = br.readLine();
      }
      // line is not visible here.
    }
  }
 
  public static ArrayList<DataReader> allUserId(int myID) throws FileNotFoundException, IOException
  {
    ArrayList<DataReader> myData = new ArrayList<DataReader>();
    
    try(BufferedReader br = new BufferedReader(new FileReader("Purdue-data-v1.3.txt"))) 
    {
      String line = br.readLine();
      while(line != null && line != "")
      {
         DataReader thisData = new DataReader(line);

         if(thisData.getUserID() == myID)
         {
           myData.add(new DataReader(line));
         }
         
         line = br.readLine();
       }
      }
      // line is not visible here.
  
    return myData;
  }
  
  public String toString()
  {
    
    String myString = "";
    for(DataReader myData: this)
      {
        myString += "\n" + myData.toString();    
      }
    
    return myString;
  }
  
  public static void main(String[] args) throws FileNotFoundException, IOException
  {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Which User's data would you like to access?");
    String myID = keyboard.nextLine();
    ArrayList<DataReader> myArrayList = allUserId(Integer.parseInt(myID));
    System.out.println("Enter output file name:");
    String filename = keyboard.nextLine();
    PrintWriter outputFile = new PrintWriter(new FileWriter(filename));
    outputFile.print(myArrayList);
    outputFile.close();
    System.out.println("Done!!");
    System.out.println("number of entries = " + myArrayList.size());
  }
}
