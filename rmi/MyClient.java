import java.rmi.*;

import java.util.*;


public class MyClient{
	public static void main(String[] args){
		try{
		
			Concat stub = (Concat)Naming.lookup("localhost");
			Scanner sc = new Scanner(System.in);
			
			String x,y;
			
			System.out.println("Enter String x:");
			x= sc.nextLine();
			
			System.out.println("Enter String x:");
			y= sc.nextLine();
			
			System.out.println("Concatenated Strings are:"+stub.concat(x,y));
			
		}
		catch(Exception e){
			System.out.println("Error"+ e);
		
		}
	
	
	
	
	}




}
