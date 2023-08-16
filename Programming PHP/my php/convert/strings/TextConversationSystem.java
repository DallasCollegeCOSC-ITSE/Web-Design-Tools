import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextConversationSystem {
	public static void main(String[] args) throws FileNotFoundException {
		
		Topic.parseFile("Sample.txt");
		
		Topic.startConversation();
		
	}
}

//passage/topic
class Topic
{
	public static void parseFile(String fileName) throws FileNotFoundException
	{
		Scanner file = new Scanner(new File(fileName));
		Topic current = null;
				
		while(file.hasNextLine())
		{
			String line = file.nextLine();
			
			if(line.length() < 2)//if line is is less then two skip it
				continue;
			
			if(line.startsWith("::"))
			{
				current = new Topic(line.substring(2));//start after the "::" and take everything else
			}
			else if (line.startsWith("[["))
			{
				current.addOption(line.substring(2,line.length()-2));
			}
			else
				current.addOutput(line);
		}
	}
	
	public static void startConversation() 	
	{
		//System.out.println(allTopics.size());
		Topic current = findTopicByTitle("Start");
		while(true)
		{
			System.out.println(current.output);
			
			if(current.options.size() == 0)
				break; //Conversation over 
			current.outputOptions();
			
			
			int choice = getUserNumber(current.options.size()) - 1;
			current = findTopicByTitle(current.options.get(choice));
			
		}
	}

	private void outputOptions() {
		for(int i = 0;i<options.size();i++)
			System.out.println(/*Display option num*/(i+1)+ ": "+options.get(i));
		
	}

	private static int getUserNumber(int limit) 
	{
		Scanner input = new Scanner(System.in);
		String userInput;
		while(true)
		{
			userInput = input.nextLine();
		boolean goodInput = true;
		
		for(char c :/*step through every char in string*/ userInput.toCharArray())
			if(!Character.isDigit(c))
			goodInput = false;
		if(goodInput)/* if number can be parsed into an int*/
		{
			int number = Integer.parseInt(userInput);
			if(number <= limit || number > 0)
			return number;
		}
		System.out.println("Please give me a number between 1 and "+limit);
		}
		
	}

	private static Topic findTopicByTitle(String title)
	{
		for (Topic p : allTopics) 
			if(p.title.equals(title))
				return p;
		
		System.err.println("Passage " + title + "\" not found" );
			return null;
	}
	private static ArrayList<Topic> allTopics = new ArrayList<>();
	
	String title;
	String output = ""; //TODO change this into a string builder
	
	ArrayList<String> options = new ArrayList<>();
	
	public Topic(String title)
	{
		this.title = title;
		allTopics.add(this);
		//System.out.println(title);
	}
	public void addOutput(String str)
	{
		output += str + "\n";//each output will be on it's own line
	}
	
	public void addOption(String opt)
	{
		options.add(opt);
		
	}
}