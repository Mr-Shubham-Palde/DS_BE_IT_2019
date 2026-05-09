import java.rmi.*;

import java.rmi.server.*;


public class ConcatRemote extends UnicastRemoteObject implements Concat{
	ConcatRemote() throws RemoteException{
		super();	
	}
	
	public String concat(String x, String y){
		return x+y;
		
	
	}

}
