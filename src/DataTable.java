import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataTable extends GetData
{
  private int userID;
  private double totalBalance;
  private int listSize;// totalBalance/listSize = averageVal
  private double averageVal;
  private int numNegInflow;
  private int numPosInflow;
  private int numPosChanges;//numPosChanges/numPosInflow is the percent o/ times a positive inflow gives a bal < the 
  private double percentChanges;
  private int numNegBal;
  private double percNegBal;
  private int numPosBal;
  private double percPosBal;
  private int firstMonth;
  private int lastMonth;
  private int totalMonths;
  private static ArrayList<DataTable> myData = new ArrayList<DataTable>(60000);
  
  public DataTable(int id, double total, int size, int negIn, int posIn, int posChange,
      int negBal,int posBal,int month1, int finMonth)
  {
    userID = id;
    totalBalance = total;
    listSize = size;
    averageVal = ((double)totalBalance)/listSize;
    numNegInflow = negIn;
    numPosInflow = posIn;
    numPosChanges = posChange;
    percentChanges = ((double)numPosInflow)/numPosChanges;
    numNegBal = negBal;
    percNegBal = ((double)numNegBal)/listSize;
    numPosBal = posBal;
    percPosBal = ((double)numPosBal)/listSize;
    firstMonth = month1;
    lastMonth = finMonth;
    totalMonths = lastMonth - firstMonth+1;
  }
  
  public static void add(DataReader data, int index )
  {
   if(index < 0) // if an entry for this userID doesn't exist in our list yet -- index is checked 
    {
      int id = data.getUserID();
      double total = data.getBalance();
      int size = 1;
      int negIn = 0;
      int posIn = 0;
      int posChange = 0;
      int negBal = 0;
      int posBal = 0;
      int month1 = Integer.parseInt(data.getDateAndTime().substring(11,13));
      int finMonth = month1;
      if(data.getAmount() < 0 )
      {
        negIn = 1;
      }
      if(data.getAmount() > 0)
      {
        posIn = 1;
        if(data.getAmount() > data.getBalance())
          {posChange = 1;}
      }
      if(data.getBalance() < 0)
      {
        negBal = 1;
      }
      if(data.getBalance() > 0)
      {
        posBal = 1;
      }
      DataTable newUser = new DataTable(id, total, size, negIn, posIn, posChange, negBal, posBal, month1, finMonth);
      myData.add(newUser);
    }
   
   if(index >= 0) // an entry for this UserID already exists, we set new values based on this DataReader's characteristics
   {
    DataTable me = myData.get(index);
    me.setTotalBalance(data.getBalance());
    me.addSize();
    if(data.getAmount() < 0)
    {
      me.addNegInflow();
    }
    if(data.getAmount() > 0)
    {
      me.addPosInflow();
      if(data.getAmount() > data.getBalance())
      {
        me.addPosChanges();
      }
    }
    if(data.getBalance() < 0)
    {
      me.addNegBal();
    }
    if(data.getBalance() > 0)
    {
      me.addPosBal();
    }
    if(me.getLastMonth() < Integer.parseInt(data.getDateAndTime().substring(11,13)))
    {
      me.setMonth(Integer.parseInt(data.getDateAndTime().substring(11,13)) - me.getLastMonth());
    }
   }
  }
  
  public void setTotalBalance(double total)
  {
    totalBalance += total;
    averageVal = totalBalance/listSize;
  }
  
  public void addSize()
  {
    listSize++;
    averageVal = totalBalance/listSize;
  }
  
  public void addNegInflow()
  {
    numNegInflow++;
  }
  
  public void addPosInflow()
  {
    numPosInflow++;
    percentChanges = ((double)numPosInflow)/numPosChanges;
  }
 
  public void addPosChanges()
  {
    numPosChanges++;
    percentChanges = ((double)numPosChanges)/numPosInflow;
  }
  
  public void addNegBal()
  {
    numNegBal++;
    percNegBal = ((double)numNegBal)/listSize;
  }
  
  public void addPosBal()
  {
    numPosBal++;
    percPosBal = ((double)numPosBal)/listSize;
  }
  
  public void setMonth(int diff)
  {
    lastMonth+=diff;
    totalMonths = lastMonth - firstMonth +1; 
  }
  
 //                                   **IDENTIFY THE ID'S AND WHERE THEY ARE **
  public static int identifyID(DataReader data)
  {
    for(int a = 0; a < myData.size(); a++)
    {
      if(myData.get(a).getUserID() == data.getUserID())
      {
        return a;
      }
    }
    
    return -1;
  }
  
  public int getUserID()
  {
    return userID;
  }
  
  public int getLastMonth()
  {
    return lastMonth;
  }
  
  public String toString()
  {
    averageVal = Math.round(averageVal*100000);
    averageVal = averageVal/100000;
    percentChanges = Math.round(percentChanges*100000);
    percentChanges = percentChanges /100000;
    percNegBal = Math.round(percNegBal*100000);
    percNegBal = percNegBal/100000;
    percPosBal = Math.round(percPosBal*100000);
    percPosBal = percPosBal/100000;
    return ""+ userID + ","+ averageVal + "," + percNegBal + ","+ percPosBal + "," + percentChanges+ "," + totalMonths;
   }
  
  public static void main(String[] args) throws FileNotFoundException, IOException
  { Scanner keyboard = new Scanner(System.in);
    System.out.println("Get Ready to process all this data!!");
    System.out.println("What is the name of your output file?");
    String filename = keyboard.nextLine();
    PrintWriter outputFile = new PrintWriter(new FileWriter(filename));
    outputFile.println("UserID, AverageValue, PercentNegBal, PercentPosBal, PercentPosInWNegBal, TotalMonths");
    try(BufferedReader br = new BufferedReader(new FileReader("Purdue-data-v1.3.txt"))) 
    {
      String line = br.readLine();
      int lineNum = 0;
      while(line != null && line != "")
      {
       DataReader meData = new DataReader(line);
       int index = identifyID(meData);
       lineNum++;
       System.out.println(lineNum);
       add(meData, index);
       line = br.readLine();
      }
      // line is not visible here.
    }
    for(DataTable me: myData)
    {
      outputFile.println(me);
    }
    outputFile.close();
    System.out.println("Done!!");

  }

}
