package task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

class User {
    private String username;
    private String password;
    Scanner sc = new Scanner(System.in);

    public String getUsername() {
        System.out.println("Enter user name");
        username = sc.nextLine();
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        System.out.println("Enter password");
        password = sc.nextLine();
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class DatabaseConnector {
    public Connection connect(String username, String password) {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);
        } catch (ClassNotFoundException | SQLException e) {
           // e.printStackTrace();
        }
        return con;
    }
}
class CrudOperation{
	Scanner sc=new Scanner(System.in);
	 private static final int min = 1000;
	    private static final int max = 9999;
	
	int reservation(Connection con)
	{
		int r=0;
		Random random = new Random();
      int   pnrNumber = random.nextInt(max) + min;
		System.out.println("Enter the passengername : ");
		String passengerName=sc.nextLine();
		System.out.println("Enter the mobilenumber : ");
		String passengerMobileNum=sc.nextLine();
		 System.out.println("Enter the train number : ");
         String trainNumber = sc.nextLine();
         System.out.println("Enter the class type : ");
        String  classType = sc.nextLine();
        System.out.println("Enter the Journey date as 'YYYY-MM-DD' format");
      String   journeyDate = sc.nextLine();
      System.out.println("Enter the starting place : ");
     String  from = sc.nextLine();
      System.out.println("Enter the destination place :  ");
      String to = sc.nextLine();
      try {
		PreparedStatement psmt=con.prepareStatement("insert into transaction values('"+passengerName+"','"+passengerMobileNum+"','"+trainNumber+"','"+classType+"','"+pnrNumber+"','"+journeyDate+"','"+from+"','"+to+"')");
		r=psmt.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return r;
        
		
	}
	int delete(Connection con,int pnr)
	{
		int r=0;
		try {
			PreparedStatement psmt=con.prepareStatement("delete from transaction where pnrNumber='"+pnr+"'  ");
			r=psmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
		
	}
	int details(Connection con,int pnr)
	{
		int res=0;
		try {
			PreparedStatement psmt=con.prepareStatement("select * from transaction where pnrNumber='"+pnr+"'");
			ResultSet re=psmt.executeQuery();
			
			while (re.next()) {
				res++;
			 String passengerName = re.getString("passengerName");
	            String passengerMobileNum = re.getString("passengerMobileNum");
	            String trainNumber = re.getString("trainNumber");
	            String classType = re.getString("classType");
	            int pnrNumber = re.getInt("pnrNumber");
	            String journeyDate = re.getString("journeyDate");
	            String start = re.getString("starting");
	            String destination = re.getString("destination");

	            // Print the values
	            System.out.println("Passenger Name: " + passengerName);
	            System.out.println("Mobile Number: " + passengerMobileNum);
	            System.out.println("Train Number: " + trainNumber);
	            System.out.println("Class Type: " + classType);
	            System.out.println("PNR Number: " + pnrNumber);
	            System.out.println("Journey Date: " + journeyDate);
	            System.out.println("Start: " + start);
	            System.out.println("Destination: " + destination);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
		
	}
}

public class Start {
    public static void main(String[] args) {
        User user = new User();
        Scanner sc=new Scanner(System.in);
        String username = user.getUsername();
        String password = user.getPassword();

        DatabaseConnector connector = new DatabaseConnector();
        Connection connection = connector.connect(username, password);
        CrudOperation crud=new CrudOperation();

        if (connection != null) {
		    while(true)
		    {
		    	System.out.println("Select the option");
		    	System.out.println("1.reservaton");
		    	System.out.println("2.cancellation");
		    	System.out.println("3.Exit");
		    	int i=sc.nextInt();
		    	switch(i)
		    	{
		    	case 1:
		    		int r=crud.reservation(connection);
		    		if(r==1)
		    		{
		    			System.out.println("reservation conformed");
		    		}
		    		break;
		    	case 2:
		    		System.out.println("please enter pnr number");
		    		int pnr=sc.nextInt();
		    		int result=crud.details(connection, pnr);
		    		if(result==1)
		    		{
		    			
		    		
		    		System.out.println("you want cancle the ticket?");
		    		System.out.println("enter 1 to conform");
		    		int res=sc.nextInt();
		    		if(res==1)
		    		{
		    			int p=crud.delete(connection,pnr );
		    			if(p==1)
		    			{
		    				System.out.println("ticket cancelled sucessfully");
		    			}
		    			
		    		} 
		    		}
		    		else
		    		{
		    			System.out.println("please enter valid pnr number");
		    		}
		    		 break;
		    	case 3:
		    		System.out.println("Thanks for using");
		    		System.exit(0);
		    		 default:
		    			 System.out.println("please enter valid option");
		    			 break;
		    		
		    		
		    	
		    	}
		    }
		    
		} else {
		    System.out.println("Please enter a valid user id and password.");
		}
        
        
      
       
      
    }
}
