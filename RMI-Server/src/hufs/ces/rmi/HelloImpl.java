package hufs.ces.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {

	static int getCount = 0;
	protected HelloImpl() throws RemoteException {
		super();
	}

	@Override
	public String getGreeting() throws RemoteException {
		getCount++;
		return ("Hello there!"+" count="+getCount);
	}

}
