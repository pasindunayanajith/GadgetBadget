package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FundingBodie {
//****************************************Create Connection*****************************************
	//A common method to connect to the DB
	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.cj.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/gbsystem?useTimezone=true&serverTimezone=UTC", "root", "");
	 //For testing
	 System.out.print("Successfully connected");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}

//************************************Insert Data**************************************************	
	public String insertFundingBodie(String name, String address, String phone, String email,String password,String desc)
	 {
	 
		String output = "";
	
	try
	 {
	 Connection con = connect();
	 
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 
	 // create a prepared statement
	 String query = " insert into fundingbodies(`fbID`,`fbName`,`fbAddress`,`fbPhone`,`fbEmail`,`fbPassword`,`fbDescription`)" + " values (?, ?, ?, ?, ? , ? ,?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, name);
	 preparedStmt.setString(3, address);
	 preparedStmt.setString(4, phone);
	 preparedStmt.setString(5, email);
	 preparedStmt.setString(6, password);
	 preparedStmt.setString(7, desc);
	
	 // execute the statement
	 preparedStmt.execute();
	 //connection close
	 con.close();
	 output = "Inserted successfully";
	 
	 }
	 
	catch (Exception e)
	{
	 output = "Error while inserting the item.";
	 System.err.println(e.getMessage());
	}
	 return output;
	}

	
	
	//************************************ Read Data********************************************************************************
	public String readFundingBodies()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	
	 // Prepare the html table to be displayed
	 output = "<table border='1'>"
	 		+ "<tr>"
			+"<th>FB_ID</th>"
	 		+ "<th>Name</th>"
	 		+ "<th>Address</th>"
	 		+ "<th>Phone</th>"
	 		+ "<th>Email</th>"
	 		+"<th>Password</th>"
	 		+"<th>Descripition</th>"
	 	   	+"<th>Update</th>"
	 	    	+ "<th>Remove</th>"
	 	  	+ "</tr>";
	 //sql query
	 String query = "select * from fundingbodies";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String fbID = Integer.toString(rs.getInt("fbID"));
	 String fbName = rs.getString("fbName");
	 String fbAddress = rs.getString("fbAddress");
	 String fbPhone = rs.getString("fbPhone");
	 String fbEmail = rs.getString("fbEmail");
	 String fbPassword=rs.getString("fbPassword");
	 String fbDescription = rs.getString("fbDescription");

	 
	 // Add into the html table
	 output += "<tr><td>" + fbID + "</td>";
	 output += "<td>" + fbName + "</td>";
	 output += "<td>" + fbAddress + "</td>";
	 output += "<td>" + fbPhone + "</td>";
	 output += "<td>" + fbEmail + "</td>";
	 output += "<td>"+fbPassword+"</td>";
	 output += "<td>" +fbDescription  + "</td>";

	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>" + "<td><form method='post' action='FundingBodies.jsp'>" + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + fbID + "'>" + "</form></td></tr>";
	 }
	//connection close 
	con.close();
	 
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	
	//************************************************Update Data***************************************************************
	public String updateFundingBodie(String ID,String name, String address, String phone,String password,String desc)
	{
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for updating."; }
		 // create a prepared statement
		 String query = "UPDATE fundingbodies SET fbName=?,fbAddress=?,fbPhone=?,fbPassword=?,fbDescription=? WHERE fbID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, name);
		 preparedStmt.setString(2, address);
		 preparedStmt.setString(3, phone);
		 preparedStmt.setString(4, password);
		 preparedStmt.setString(5,desc );
		 preparedStmt.setInt(6, Integer.parseInt(ID));
		 // execute the statement
		 preparedStmt.execute();
		 //connection close
		 con.close();
		 output = "Updated successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while updating the item.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
	
	
	
	//***************************************************Delete data****************************************************8
	public String deleteFundingBodie(String fbID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from fundingbodies where fbID=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(fbID));
	 // execute the statement
	 preparedStmt.execute();
	 //connection close
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }

	// *****************************Read FundingBodie Profile*************************************
	public String ViewProileFundingBodie(int fbID) {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		
		 // Prepare the html table to be displayed
		 output = "<table border='1'>"
		 		+ "<tr>"
				+"<th>FB_ID</th>"
		 		+ "<th>Name</th>"
		 		+ "<th>Address</th>"
		 		+ "<th>Phone</th>"
		 		+ "<th>Email</th>"
		 		+"<th>Password</th>"
		 		+"<th>Descripition</th>"
		 	   	+"<th>Update</th>"
		 	    	+ "<th>Remove</th>"
		 	    	+ "</tr>";

		 String query ="select * from fundingbodies WHERE fbID=?";
		 		
		PreparedStatement stmt = con.prepareStatement(query);

		 stmt.setInt(1,fbID);
		 ResultSet rs = stmt.executeQuery();
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String fbID1 = Integer.toString(rs.getInt("fbID"));
		 String fbName = rs.getString("fbName");
		 String fbAddress = rs.getString("fbAddress");
		 String fbPhone = rs.getString("fbPhone");
		 String fbEmail = rs.getString("fbEmail");
		 String fbPassword=rs.getString("fbPassword");
		 String fbDescription = rs.getString("fbDescription");

		 
		 // Add into the html table
		 output += "<tr><td>" + fbID1 + "</td>";
		 output += "<td>" + fbName + "</td>";
		 output += "<td>" + fbAddress + "</td>";
		 output += "<td>" + fbPhone + "</td>";
		 output += "<td>" + fbEmail + "</td>";
		 output += "<td>"+fbPassword+"</td>";
		 output += "<td>" +fbDescription  + "</td>";

		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>" + "<td><form method='post' action='FundingBodies.jsp'>" + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		 + "<input name='itemID' type='hidden' value='" + fbID1 + "'>" + "</form></td></tr>";
		 }
		 //connection close
		 con.close();
		 
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the items.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }

	//Read FundingBodys Details For Users(Without Buyers)
	public String readFundingBodiesForUsers() {
		{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for reading."; }
			
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"
			 		+ "<tr>"
					+"<th>FB_ID</th>"
			 		+ "<th>Name</th>"
			 		+ "<th>Address</th>"
			 		+ "<th>Phone</th>"
			 		+ "<th>Email</th>"
			 		+"<th>Descripition</th>"
			 	    	+ "</tr>";
			//sql query
			 String query = "select * from fundingbodies";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
			 String fbID = Integer.toString(rs.getInt("fbID"));
			 String fbName = rs.getString("fbName");
			 String fbAddress = rs.getString("fbAddress");
			 String fbPhone = rs.getString("fbPhone");
			 String fbEmail = rs.getString("fbEmail");
			 String fbDescription = rs.getString("fbDescription");

			 
			 // Add into the html table
			 output += "<tr><td>" + fbID + "</td>";
			 output += "<td>" + fbName + "</td>";
			 output += "<td>" + fbAddress + "</td>";
			 output += "<td>" + fbPhone + "</td>";
			 output += "<td>" + fbEmail + "</td>";
			 output += "<td>" +fbDescription  + "</td>";

		
			 }
			 //Connection close
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
			 }
			 catch (Exception e)
			 {
			 output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			 }
			 return output;
	}

	
	}
	
}
	
	
	
