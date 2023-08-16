public class InterfaceRules 
{
	public static void main(String[] arg)
	{
		System.out.println(System.getProperty("java.runtime.version"));
		test t = new test();
		System.out.println(t.MyNumber);
	}
}
class test implements TestInterface
{
	public void method1(){} //note that public must be added when actually defining the methods
}
interface test1
{
	//any fields or methods will be public (so typically the word is omitted) - a private interface makes no sense
	void method1();
}

//Interfaces can extend other interfaces but not other classes
interface TestInterface extends test1 //note that they use extends here instead of implements
{
	//this will override the previous MyNumber
	int MyNumber=5; //Fields can be defined but by default they will be static and final
	
	//note that this is an instance method (it is not static - therefore you cannot use the interface name to call this method)
	void method1(); //methods cannot be given a definition within them (this is similar to abstract methods)
	
	//Note that in 1.8 you can do this other stuff:
	/*static boolean resolveNumber(String s)
	{
		return true;
	}
	
	default int testDefault()
	{
	  return 5;
	}*/
	
}

