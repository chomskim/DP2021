package hufs.ces.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class EchoThread extends Thread {
	BufferedReader netIn;
	PrintWriter netOut;

	public EchoThread(Socket con) {
		try {
			this.netIn = new BufferedReader(new InputStreamReader(con.getInputStream()));
			this.netOut = new PrintWriter(con.getOutputStream());			
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	public void run()  {
		try {     
			while (true) {
				String echoLine = netIn.readLine();
				if (echoLine==null || echoLine.startsWith("<<END Echo Client>>")) {
					netOut.println(echoLine);
					netOut.flush();
					break;
				}
				netOut.println(echoLine);
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
