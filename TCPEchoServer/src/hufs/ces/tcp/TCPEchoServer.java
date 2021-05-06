package hufs.ces.tcp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer{

	final static int DEFAULT_PORT = 7070;

	public static void main(String[] args) {

		int port = DEFAULT_PORT;     

		try {
			ServerSocket server = new ServerSocket(port);
			while (true) {
				System.out.println("Wait for a Connection");
				Socket  con1 = server.accept();
				System.out.println("Connection: Inet " + con1.getInetAddress() 
				+ ":" + con1.getPort()
				+ "		Local"	+ con1.getLocalAddress() 
				+ "	:" + con1.getLocalPort());

				Thread echoThread = new EchoThread(con1);
				echoThread.start();

			}  // end while
		}  // end try
		catch (IOException ex) {
			System.err.println(ex);
		} // end catch
	} // end main
	
} // end Messanger Server
