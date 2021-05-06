package hufs.ces.udp;
import java.net.*;

import java.io.*;

public class UDPEchoClient {

	final static int DEFAULT_PORT = 7770;
	final static String DEFAULT_HOST = "127.0.0.1";

	private static String hostname;
	private static int port;

	public volatile boolean stopClient = false;

	public UDPEchoClient() {

		hostname = DEFAULT_HOST;
		port = DEFAULT_PORT;

		BufferedReader userIn = null;

		InetAddress server = null;
		Sender sender = null;

		try {
			server = InetAddress.getByName(hostname);
			sender = new Sender(server, port);
			userIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Connected to Echo server");

			ReceiverThread receiver = new ReceiverThread(sender.getSocket());
			receiver.start();

			while (true) {
				if (stopClient) {
					receiver.halt();
					break;
				}

				String theLine = userIn.readLine();
				if (theLine.startsWith(".")) {
					sender.send("<<END Echo Client>>");
					receiver.halt();
					break;
				}
				sender.send(theLine);
			}

		}  // end try
		catch (IOException e) {
			System.err.println(e);
		}
		finally {
			try {
				if (userIn != null) userIn.close(); 
			}
			catch (Exception e) {}
		}

	}

	public static void main(String[] args) {

		UDPEchoClient uec = new UDPEchoClient();

	}  // end main
	class ReceiverThread extends Thread {

		DatagramSocket socket;
		private boolean stopped = false;

		public ReceiverThread(DatagramSocket ds) throws SocketException {
			this.socket = ds;
			socket.setSoTimeout(10000); // 10 sec.
		}

		public void halt() {
			stopClient = true;
			this.stopped = true; 
		}

		public void run() {

			byte[] buffer = new byte[65507];
			while (true) {
				if (stopped) return;
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				try {
					socket.receive(dp);
					String outMsg = new String(dp.getData(), 0, dp.getLength());
					System.out.println(">>"+outMsg);

					Thread.yield();
				}
				catch (SocketTimeoutException ex) {
					//System.err.println(ex);
				} 
				catch (IOException ex) {
					System.err.println(ex);
				} 

			}  

		}

	}

}  


