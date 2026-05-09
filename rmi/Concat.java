import java.rmi.*;


public interface Concat extends Remote{

	public String concat(String x , String y ) throws RemoteException;

}
