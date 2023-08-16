import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




//JAVA regex are used primarily in split and matches functions with strings

public class regularExpressionDemo 
{
	public static void main(String[] args) throws IOException 
	{	
		//regex testers are nice:
		//https://www.regextester.com/97778
		
		//Regular Expressions - similar procedures - standard 
		//usually abreviated as "regex"
		//language of strings
		//bridge the gap between all languages
		
		
		//as of java 1.5 (2002/3)
		//1st is matching or finding things in strings
			//string.matches
		//2nd is splitting apart strings
			//string.split
		//The Pattern is a dedicated regex class used for precompiling regex expressions
		
		//implications of regular expressions 
		//+highly efficient though typically slower than string functions
		//+They (minor differences) the same between languages
		//+small amount of code (easier to update and/or rewrite)
		//-not checked by the compiler (you have to test them)
		//-not the most intuitive thing	
		//EVERYWHERE
		
		//examples showing that regex can be much more concise
		
		//string method way of doing things
		String card = "4891169129812901";
		if (card.length() == 16)
		{
			boolean b = true;
			for (int i=0;i<16;i++)
				if (!Character.isDigit(card.charAt(i)))
				{
					b = false;
					break;
				}
		}
		//regex way of doing things
		if (card.matches("\\d{16}"))
		{
			
		}
		
		
		//Examples using regular expressions
		//match a given set of letters		
		String s = "if"; //of if ef
		System.out.println("if: "+s+": "+s.matches("if"));
		//Every character in the string must match in the pattern 
		//and everything in the pattern must match in the string
		
		//[] all the characters in the brackets are or'ed together
		System.out.println("[ioe]f: "+s+": "+s.matches("[ioe]f"));
				
		//do not match a given set of letters
		//^ is a not symbol
		s = "a"; 
		System.out.println("[^a]: "+s+": "+s.matches("[^a]"));
		
		//the dot . matches any 1 character
		s = "cot"; //true
		System.out.println(".[^a].: "+s+": "+s.matches(".[^a]."));
		
		//match more than once
		s = "oof"; //
		System.out.println("[oi]{2}f: "+s+": "+s.matches("[oi]{2}f"));
				
		s = "bob";
		s.matches("bob//..*");
		
		//The star * is a Quantifier Which matches 0-any
			//period (.) just matches 1 character
			//(greedy vs reluctant)
		//+ The plus matches 1-any
			//"a+"
		//? The Question mark match 0-1
			//"/(?//d{3}/)?"
		//* The star match any and all
			//"file//d*"
		//match a pattern
		s = "the is fun";
		System.out.println(".*is.*: "+s+": "+s.matches(".*is.*"));
		
		//use grouping to repeat the pattern
		s = "this is fun";		
		System.out.println(".*(is ){2}.*: "+s+": "+s.matches(".*(is ){2}.*"));
		
		
		//(Mr)|(Mrs)|(Dr)
		
		s = "at";
		System.out.println("(at)|(it): "+s+": "+s.matches("(at)|(it)"));
		s = "atat";
		System.out.println("(at)|(it): "+s+": "+s.matches("(at)|(it)"));
		s = "atatatatat";
		System.out.println("(at)+|(it)+ "+s+": "+s.matches("(at)+|(it)+"));//would work for itit or atat
		s = "atitatitititat";
		
		//nested grouping
		System.out.println("((at)|(it))+ "+s+": "+s.matches("((at)|(it))+"));
		
		s = " = 87";
		s.matches("\\D+ = \\d+");
		
		//    \\d digit \\D nondigit   
		//    \\s white space  \\S non whitespace  
		//    \\b word boundary  
		//    \\w word characters \\W nonWord characters  
		
		//checking for duplicates		                                  s = "test test"; 
		System.out.println("Check for duplicate words: "+s+": "+s.matches("\\b(\\w+)\\s+\\1\\b"));

		//Searching a string:
		s = "ab6c";
		//this (?=) //is to look for something
		//in the example below it is looking for a digit
		//somewhere in the string
		System.out.println("^(?=.*\\d).*$ "+s+": "+s.matches("^(?=.*\\d).*$"));
		
		
		
	
		//So far The Regex's have been demos 
		//but typically if we are going to run the expression 
		//on a server we are going to be checking the regex against
		//thousands, millions of entries. Each time we use a regex
		//it must be compiled (in the case of java this is at runtime)
		//to avoid recompiling the regex over and over again it is 
		//usually set into a regex pattern like the following:
		
		Pattern pattern = Pattern.compile("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
		int rSum=0, lSum=0;
		long rTime = 0, lTime=0;
		//lets test the regex engine vs conventional string operations
		for (int i=0;i<500000;i++)
		{				
			String val = ""+(int)(Math.random()*1000);
			
			//using the regular expression
			long start = System.currentTimeMillis();
		    if (pattern.matcher(val).matches()) 
		        rSum++;		    
		    rTime += System.currentTimeMillis() - start;
		    
		    //using string/conventional means
		    start = System.currentTimeMillis();
		    int number = Integer.parseInt(val);
		    if (number < 300)
		    	lSum++;
		    lTime += System.currentTimeMillis() - start;
		}
		System.out.println(rSum + " " + lSum);
		System.out.println(rTime + " " + lTime);
		
				
		
	}
	
	//some additional problems and examples:
	
	public static boolean problem1()
	{
		String s = "working123";
		return s.matches("\\D+\\d{2,3}");
	}
	public static boolean problem2()
	{
		String s = "student: Bob Barker";
		return s.matches("\\w*:\\s\\w+\\W\\w+");
	}
	public static boolean problem3()
	{
		String s = "alks8;lskj";
		return s.matches("\\D*\\d?[,./:;'][a-z]*");
	}
	
	public static String[] problem4()
	{
		String s = "this is a sentence for testing";
		return s.split("[ts]");
	}

	public static void problem5()
	{
		/* What is java regex for below string S?
				S must be of length: 6
				First character: 1, 2 or 3
				Second character: 1, 2 or 0
				Third character: x, s or 0
				Fourth character: 3, 0, A or a
				Fifth character: x, s or u
				Sixth character: . or,

				A) ^([1-3][0-2][xs0][30Aa][xsu][.,])$
				-) ([1-3][0-2][xs0][3|0|A|a][x|s|u][.|,])
				-) ([1-3][0-2][xs0][30Aa][xsu][.,]){6}
				-) ^([1-3][0-2][x,s,0][3,0,A,a][x,s,u][\.\,])$
		*/
	}
	
	
	public boolean isLessThenThreeHundred(String s)
	{
	    return s.matches("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
	}
	
	
	public static boolean prime(String s) 
	{
		  return !s.matches(".?|(..+?)\\1+");
		  //for explanations see: http://stackoverflow.com/questions/2795065/how-to-determine-if-a-number-is-a-prime-with-regex
	}
	
	public static boolean prime(int n) 
	{
		  return !new String(new char[n]).matches(".?|(..+?)\\1+");
		  //for explanations see: http://stackoverflow.com/questions/2795065/how-to-determine-if-a-number-is-a-prime-with-regex
	}

	
	//Adam Paynter
	public static String[] separateCamelCase(String s)
	{
		return s.split("(?<=[a-z])(?=[A-Z])");
	}


}


/*
To explain split, I will first show you the different split operations:

"Ram-sita-laxman".split("");
This splits your string on every zero-length string. There is a zero-length string between every character. Therefore, the result is:

["", "R", "a", "m", "-", "s", "i", "t", "a", "-", "l", "a", "x", "m", "a", "n"]
Now, I modify my regular expression ("") to only match zero-length strings if they are followed by a dash.

"Ram-sita-laxman".split("(?=-)");
["Ram", "-sita", "-laxman"]
In that example, the ?= means "lookahead". More specifically, it mean "positive lookahead". Why the "positive"? Because you can also have negative lookahead (?!) which will split on every zero-length string that is not followed by a dash:

"Ram-sita-laxman".split("(?!-)");
["", "R", "a", "m-", "s", "i", "t", "a-", "l", "a", "x", "m", "a", "n"]
You can also have positive lookbehind (?<=) which will split on every zero-length string that is preceded by a dash:

"Ram-sita-laxman".split("(?<=-)");
["Ram-", "sita-", "laxman"]
Finally, you can also have negative lookbehind (?<!) which will split on every zero-length string that is not preceded by a dash:

"Ram-sita-laxman".split("(?<!-)");
["", "R", "a", "m", "-s", "i", "t", "a", "-l", "a", "x", "m", "a", "n"]

These four expressions are collectively known as the lookaround expressions.
*/









