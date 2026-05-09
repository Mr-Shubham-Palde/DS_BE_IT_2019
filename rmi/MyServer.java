import java.rmi.*;
import java.rmi.registry.*;


public class MyServer{
	public static void main(String[] args)
	{
		try{
		
			LocateRegistry.createRegistry(1099);
			Concat stub= new ConcatRemote();
			Naming.rebind("localhost",stub);
			System.out.println("Server is ready and listening");
		
		
		}
		catch(Exception e){
		
		
		}
	
	
	}
}
