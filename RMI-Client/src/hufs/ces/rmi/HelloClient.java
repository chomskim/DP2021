package hufs.ces.rmi;

import java.rmi.ConnectException;
import java.rmi.Naming;

public class HelloClient {

	private static final String HOST = "localhost";
	public static void main(String[] args)
	{
		try
		{
			//Obtain a reference to the object from the
			//registry and typecast it into the appropriate
			//type��
			Hello greeting = (Hello)Naming.lookup("rmi://" + HOST + "/Hello");
			//Use the above reference to invoke the remote
			//object's method��
			System.out.println("Message received: "	+ greeting.getGreeting());
		}
		catch(ConnectException conEx)
		{
			System.out.println("Unable to connect to server!");
			System.exit(1);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
