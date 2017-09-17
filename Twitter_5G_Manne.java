 //name:       date:
import twitter4j.*;  //set the classpath to:  lib\twitter4j-core-4.0.2.jar
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.*;
public class Twitter_5G_Manne
{
   private int totalcount;
   public static void main(String []args) throws TwitterException, IOException
   {
   	// Remembers the standard output location (the screen)
      PrintStream stnd_out = System.out;                	
   	
   	// Make an instance of Twitter - this is re-useable and thread safe.
      
      Twitter twitter = TwitterFactory.getSingleton(); //connects to Twitter and performs authorizations          
      //twitter.updateStatus("hi");
      
   	//Ask for Twitter handle
      Scanner scan = new Scanner(System.in);
      System.out.print("Please enter a Twitter handle (without the @ symbol, type done to quit) --> ");
      String handle = scan.next();
      while (!handle.equals("done"))
      {
         System.setOut(new PrintStream(new FileOutputStream("garbageOutput.txt"))); // Creates file for useless console output
      
         Paging page = new Paging(1,200);  // Accesses last 200 tweets, I think this is the limit
         int p = 1;
         List<Status> statuses = new ArrayList<Status>();
         while (p < 10) // Can only access 20 per page I think
         {
            p++;
            page.setPage(p);
            statuses.addAll(twitter.getUserTimeline(handle,page));  //get the tweets
         }
            	
         List<String> terms = new ArrayList<String>();//Make a list of all words in the tweets
         for (Status status : statuses)
         {			
            String[]array = status.getText().split(" ");
            for (String word : array)
               terms.add(removePunctuation(word)); // Remove all punctuation from word before adding to list
         }	
        
       // DirectMessage message = twitter.sendDirectMessage(username, message);
        
         
           
            	
      	// Make a list of all words from user timeline
         // Remove all punctuation from word before adding to list
         File f = new File("commonWords.txt");
         Scanner s = new Scanner(f);
         int wordcount = 0;
         String str = "";
         ArrayList<String> commonWords = new ArrayList<String>();
         ArrayList<String> list = new ArrayList<String>();
         //List<String> text = new ArrayList<String>();
         
            /*while(s.hasNext())
            {
               s.next();
               wordcount++;
            }*/
          
            /*(while((str = br.readLine()) != null){   
               commonWords.add(str);
            }*/
         while(s.hasNext())    //add common words to array list
         {
            commonWords.add(s.next());
         }
         
         for(String c : commonWords) //make everything lowercase
         {
            c.toLowerCase();
         }
         
         for(String t : terms)
         {
            t.toLowerCase();
         }
         
         for(String t : terms)  //remove common words
         {
            if(!(commonWords.contains(t.toLowerCase())))
               list.add(t);
         }
         
         //catch(FileNotFoundException e)
         //{
            //System.out.print(e.getMessage());
         //}
      								
      			
         // Remove common English words, which are stored in commonWords.txt
         
         sort(list);
      	
         
      	// Sort words in alpha order
      	
      	
         ArrayList<String> newList = new ArrayList<String>();
         for(String l : list)
         {
            if(!(l.equals(" ")))
               newList.add(l);
         }
         
      	
      	// Remove all empty strings ""
      	
         for(String l : newList)
         {
            System.out.print(l);
         }
         System.setOut(stnd_out);   //reset output to the screen
         System.out.println("The most common word from @" + handle + " is: " + findMostCommonWord(newList));  //Finds most common word
         System.out.println();
         
         //TWITTER PART 2
         //**************************
         //Enter a handle and birthday, and we will wish that person a happy birthday on that date
         System.out.print("Enter a Twitter handle with @ sign: ");
         String user = scan.next();
         System.out.print("Enter the user's birthday with appropriate format (i.e. 4/07/2015): ");
         String birthday = scan.next();
         wish(user, birthday, twitter);
         s.close();
      }	
      scan.close();
      
   }	//end main
	
// static methods go here
   public static String removePunctuation(String s)
   {
      String punct = ".\"/,';?!:";
      String finalString = "";
      for(int i=0; i<s.length(); i++)
      {
         if(punct.indexOf(s.charAt(i)) == -1)
         {
            finalString = finalString + s.charAt(i);
         }
      }
      return finalString;
   }
   
   public static String findMostCommonWord(List<String> str)
   {
      Map<String, Integer> count = new HashMap<>();
      for(String s : str)
      {
         Integer i = count.get(s);
         if(i == null) i = new Integer(0);
         i++;
         stringsCount.put(s,i);
      }
      Map.Entry<String,Integer> common = null;
      for(Map.Entry<String, Integer> m: count.entrySet())
      {
         if(common == null || most.getValue()<m.getValue())
            common = m;
      }
      return common.getKey();
   }
            
            

   public static void sort(ArrayList<String> array)
   {
      int maxPos;
      for(int k=0; k<array.size(); k++)
      {
         maxPos = findMax(array, array.size() - k - 1);
         swap(array, maxPos, array.size()-k-1);
      }
   }
   public static int findMax(ArrayList<String> array, int upper)
   {
      int maxPos = 0;
      for(int j=1; j<=upper; j++)
      {
         if((array.get(j)).compareTo(array.get(maxPos)) > 0)
            maxPos = j;
      }
      return maxPos;
   }
   public static void swap(ArrayList<String> array, int a, int b)
   {
      String temp = array.get(a);
      array.set(a, array.get(b));
      array.set(b, temp);
   }
   public static void wish(String user, String birthday, Twitter t) throws TwitterException
   {
      Calendar cal = Calendar.getInstance();
      String[] array = birthday.split("/");
      String month = array[0];
      String date = array[1];
      String year = array[2];
      if(cal.get(Calendar.YEAR) == Integer.parseInt(year) && cal.get(Calendar.MONTH)+1 == Integer.parseInt(month) && cal.get(Calendar.DATE) == Integer.parseInt(date))
      {
         t.updateStatus("Happy Birthday! Hope you have a great day! " + user);
      }
         
   }
}