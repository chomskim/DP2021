package hufs.ces.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	int count = 0;
	static int staticCount = 0;
	protected HelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String getGreeting() throws RemoteException {
		staticCount++;
		count++;
		return ("Hello there!"+" staticCount="+staticCount+" count="+count);
	}

}
