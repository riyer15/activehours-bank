import java.util.*;

public class DataReader
{
  private int userID;
  private String dateAndTime;
  private String location;
  private double amount;
  private double balance;
  private double preceding;
  private String category;
  private List<String> thisInput;
  
  public DataReader(String input)
  {
    int quote1 = input.indexOf('"');
    if(quote1 > 0)
    {
      StringBuffer inputBuffer = new StringBuffer(input);
      StringBuffer newInput = new StringBuffer(inputBuffer.substring(quote1));
      int quote2 = newInput.lastIndexOf("\"");
      if(quote2 > 0)
      {
        newInput = new StringBuffer(newInput.substring(0, quote2));
        int comma = newInput.indexOf(",");
        while(newInput.indexOf(",") > -1)
        {
          comma = newInput.indexOf(",");
          newInput = newInput.deleteCharAt(comma);
          //System.out.println(newInput);
        } 
        inputBuffer =  inputBuffer.delete(quote1+1, quote1+quote2+1).insert(quote1,newInput.toString());
        input = inputBuffer.toString();
        //System.out.println(inputBuffer);
        //System.out.println("quote1 = "+ quote1 + ", quote2 = "+ quote2 + ", comma = " + comma);
      }
    }
   thisInput = new LinkedList<String>(Arrays.asList(input.split(",")));
   userID = Integer.parseInt(thisInput.get(0));
   dateAndTime = thisInput.get(1);
   location = thisInput.get(2);
   amount = Double.parseDouble(thisInput.get(3));
   category = thisInput.get(4);
   balance = Double.parseDouble(thisInput.get(5));
   preceding = balance - amount; // inital value calculated w maximum sig figs
   preceding = Math.round(preceding * 100);
   preceding = preceding/100;
  
  }
  
  public String toString()
  {
    return "" + userID + "," + dateAndTime + "," + location +
        "," + amount + "," + category + "," + balance + "," + preceding +"\n";
  }
  
  public int getUserID()
  {
    return userID;
    
  }
  
  public String getDateAndTime()
  {
    return dateAndTime;
  }
  
  public String getLocation()
  {
    return location;
  }
  
  public double getAmount()
  {
    return amount;
  }
  
  public double getBalance()
  {
    return balance;
  }
  
  public double getPreceding()
  {
    return preceding;
  } 
  
  public static void main(String[] args)
  {
    DataReader myData = new DataReader("89166,2015,\"TACO BELL ,PH 3768 3818 STATE HWY \",-7.39,<null>,1687.18");
    System.out.println(myData);
    
  }
}
