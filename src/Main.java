
public class Main {
	public static void main (String[] args)
	{
		if(args[0].equals("test"))
		{
			System.out.println("test:");
			Test.test();
		}
		else
		{
			System.out.println("Building Test:");
			Test.test();
		}
	}

}
