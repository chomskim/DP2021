package hufs.ces.udp;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class UDPEchoServer extends Thread {

	public final static int DEFAULT_PORT = 7770;
	private int bufferSize; // in bytes
	protected DatagramSocket socket;

	public UDPEchoServer(int port, int bufferSize) 
			throws SocketException {
		this.bufferSize = bufferSize;
		this.socket = new DatagramSocket(port);
	}
	public UDPEchoServer(int port) throws SocketException {
		this(port, 8192);
	}
	public UDPEchoServer() throws SocketException {
		this(DEFAULT_PORT); 
	}

	public void run() {	  
		byte[] buffer = new byte[bufferSize];
		SocketAddress theSender;
		while (true) {
			DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
			try {			
				socket.receive(incoming);
				theSender = incoming.getSocketAddress();
				echo(incoming, theSender);

			}
			catch (IOException e) {
				System.err.println(e);
			}
		} // end while

	}  // end run
	public void echo(DatagramPacket packet, SocketAddress socketAddress) {

		try {
			DatagramPacket outgoing = new DatagramPacket(packet.getData(), packet.getLength(), socketAddress);
			// For logging 
			byte[] data = new byte[packet.getLength()];
			System.arraycopy(packet.getData(), 0, data, 0, packet.getLength());
			String s = new String(data, "8859_1");
			System.out.println(socketAddress +  " says:" + s+ ":");
			// now send
			if (!s.equals("**connect**")) {
				socket.send(outgoing);
			}
		}
		catch (java.io.UnsupportedEncodingException ex) {
			// This shouldn't happen
		}
		catch (IOException ex) {
			System.err.println(ex);
		}

	}

	public static void main(String[] args) {

		try {
			UDPEchoServer server = new UDPEchoServer();
			server.start();
		}
		catch (SocketException ex) {
			System.err.println(ex);
		}

	}

}
