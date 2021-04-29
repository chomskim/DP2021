/**
 * Created on Dec 3, 2015
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */
package hufs.ces.rmi;

import java.rmi.RemoteException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RMIMessangerServer {

	private static final String HOST = "localhost";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.setSecurityManager(new RMISecurityManager());
		try {
			String rmiObjectName = "rmi://" + HOST + "/RMIMessanger";
			RMIMessangerImpl messanger = new RMIMessangerImpl();
			Context namingContext = new InitialContext();
			namingContext.rebind(rmiObjectName, messanger);
			//Naming.rebind("RMIMessanger", messanger);
			System.out.println("RMIMessanger Server is Ready.");
		}
		catch (RemoteException ex) {
			System.out.println("RMIMessanger Remote Exception" + ex);
		//} catch (MalformedURLException ex) {
		//	System.out.println("MalformedURL Exception" + ex);
		} catch (NamingException ex) {
			System.out.println("Naming Exception" + ex);
		}

	}

}
