 import java.util.ArrayList;

public class InterfaceExample 
{
	public static void main(String arg[])
	{
		Book b = new Book();
		b.CheckOut();
		Library Rlc = new Library();
		Rlc.shelf.add(b);
	}
}

interface CanBeCheckedOut
{
	boolean CheckOut();
	boolean CheckedIn();
}
interface CanBeRead
{
	boolean Read();
	
}
interface CanBeWatched
{
	boolean Watch();	
}
class Book extends Object implements CanBeCheckedOut, CanBeRead
{
	private boolean isAvailable = true;
	public boolean CheckOut()
	{
		if (isAvailable)
		{
			isAvailable = false;
			return true;
		}
		return false;
	}	
	public boolean CheckedIn()
	{
		return true;
	}
	public boolean Read() 
	{
		return false;
	}
}
class EBook implements CanBeCheckedOut
{
	public boolean CheckOut()
	{
		return true;
	}	
	public boolean CheckedIn()
	{
		return true;
	}
}
class Library
{
	//Things can be stored by their interface (just like the can be by their superclass)
	public ArrayList<CanBeCheckedOut> shelf = new ArrayList<CanBeCheckedOut>();
}
