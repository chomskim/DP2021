package hufs.ces.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class MessangerThread extends Thread {
	BufferedReader netIn;
	PrintWriter netOut;

	public MessangerThread(Socket conFrom, Socket conTo) {
		try {
			this.netIn = new BufferedReader(new InputStreamReader(conFrom.getInputStream()));
			this.netOut = new PrintWriter(conTo.getOutputStream());			
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	public void run()  {
		try {     
			while (true) {
				String messageLine = netIn.readLine();
				if (messageLine==null) break;
				netOut.println(messageLine);
				netOut.flush();
			}
		}
		catch (SocketException ex) {
			System.err.println(ex);
		}
		catch (IOException ex) {
			System.err.println(ex);
		}
		try {
			if (netIn != null) netIn.close(); 
			if (netOut != null) netOut.close(); 
		}
		catch (IOException ex) { } 
	}
}
