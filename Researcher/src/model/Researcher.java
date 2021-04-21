package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Researcher{
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
			public String insertResearcher(String fname,String lname ,String gender,String nic, String phone,Date birthday ,String email,String password,String rdetails,String accountnum,String bankname)
			 {
			 
				String output = "";
			
			try
			 {
			 Connection con = connect();
			 
			 if (con == null)
			 {return "Error while connecting to the database for inserting."; }
			 
			 // create a prepared statement
			 String query = " insert into researcher(`researcherID`,`researcherFname`,`researcherLname`,`researcherGender`,`researcherNic`,`researcherPhone`,`researcherBirthday`,`researcherEmail`,`researcherPassword`,`researchDetails`,`accountnumber`,`bankname`)" + " values (?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // binding values
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, fname);
			 preparedStmt.setString(3, lname);
			 preparedStmt.setString(4, gender);
			 preparedStmt.setString(5, nic);
			 preparedStmt.setString(6, phone);
			 preparedStmt.setDate(7, birthday);
			 preparedStmt.setString(8, email);
			 preparedStmt.setString(9, password);
			 preparedStmt.setString(10, rdetails);
			 preparedStmt.setString(11, accountnum);
			 preparedStmt.setString(12, bankname);

			 // execute the statement
			 preparedStmt.execute();
			 //Connection close
			 con.close();
			 output = "Inserted successfully";
			 
			 }
			 
			catch (Exception e)
			{
			 output = "Error while inserting the researcher.";
			 System.err.println(e.getMessage());
			}
			 return output;
			}

			
			
			//************************************ Read Data********************************************************************************
			public String readReseacher()
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
					+"<th>Researcher_ID</th>"
			 		+ "<th>FirstName</th>"
			 		+ "<th>LastName</th>"
			 		+ "<th>Gender</th>"
			 		+ "<th>Nic</th>"
			 		+"<th>Phone</th>"
			 		+"<th>Birth Day</th>"
			 		+"<th>Email</th>"
			 		+"<th>Password</th>"
			 		+"<th>Research Details</th>"
			 		+"<th>BankAccount Number</th>"
			 		+"<th>Bank Name</th>"
			 	    +"<th>Update</th>"
			 	    + "<th>Remove</th>"
			 	    + "</tr>";

			 String query = "select * from researcher";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 // iterate through the rows in the result set
			 while (rs.next())
			 {
			 String researcherID  = Integer.toString(rs.getInt("researcherID"));
			 String researcherFname = rs.getString("researcherFname");
			 String researcherLname = rs.getString("researcherLname");
			 String researcherGender = rs.getString("researcherGender");
			 String researcherNic = rs.getString("researcherNic");
			 String researcherPhone=rs.getString("researcherPhone");
			 Date researcherBirthday = rs.getDate("researcherBirthday");
			 String researcherEmail = rs.getString("researcherEmail");
			 String researcherPassword = rs.getString("researcherPassword");
			 String researchDetails= rs.getString("researchDetails");
			 String accountnumber= rs.getString("accountnumber");
			 String bankname= rs.getString("bankname");

			 
			 // Add into the html table
			 output += "<tr><td>" + researcherID + "</td>";
			 output += "<td>" + researcherFname + "</td>";
			 output += "<td>" + researcherLname + "</td>";
			 output += "<td>" + researcherGender + "</td>";
			 output += "<td>" + researcherNic + "</td>";
			 output += "<td>"+researcherPhone+"</td>";
			 output += "<td>" +	researcherBirthday + "</td>";
			 output += "<td>" +researcherEmail  + "</td>";
			 output += "<td>" +researcherPassword  + "</td>";
			 output += "<td>" +researchDetails  + "</td>";
			 output += "<td>" +accountnumber  + "</td>";
			 output += "<td>" +bankname  + "</td>";

			 // buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>" + "<td><form method='post' action='Reasearchers.jsp'>" + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
			 + "<input name='researcherID' type='hidden' value='" + researcherID + "'>" + "</form></td></tr>";
			 }
			 //Connection Close
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
			 }
			 catch (Exception e)
			 {
			 output = "Error while reading the researcher.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
			
			
			//************************************************Update Data***************************************************************
			public String updateResearcher(String ID,String fname,String lname ,String gender, String phone,String birthday ,String password,String rdetails,String accountnum,String bankname)
			{
				 String output = "";
				 try
				 {
				 Connection con = connect();
				 if (con == null)
				 {return "Error while connecting to the database for updating."; }
				 // create a prepared statement
				 String query = "UPDATE researcher SET researcherFname=?,researcherLname=?,researcherGender=?,researcherPhone=?,researcherBirthday=?,researcherPassword=?,researchDetails=?,accountnumber=?,bankname=?  WHERE researcherID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
				 // binding values
				 preparedStmt.setString(1, fname);
				 preparedStmt.setString(2, lname);
				 preparedStmt.setString(3, gender);
				 preparedStmt.setString(4, phone);
				 preparedStmt.setString(5, birthday) ;
				 preparedStmt.setString(6,password );
				 preparedStmt.setString(7,rdetails);
				 preparedStmt.setString(8,accountnum);
				 preparedStmt.setString(9,bankname);
				 preparedStmt.setInt(10, Integer.parseInt(ID));

			

				 // execute the statement
				 preparedStmt.execute();
				 //Connection Close
				 con.close();
				 output = "Updated successfully";
				 }
				 catch (Exception e)
				 {
				 output = "Error while updating the researcher.";
				 System.err.println(e.getMessage());
				 }
				 return output;
				 }
			
			
			
			//***************************************************Delete data****************************************************8
			public String deleteResearcher(String researcherID )
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for deleting."; }
			 // create a prepared statement
			 String query = "delete from researcher where researcherID =?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(researcherID));
			 // execute the statement
			 preparedStmt.execute();
			 //Connection Close
			 con.close();
			 output = "Deleted successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while deleting the researcher.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
//**********************************View Profile Researcher Id Only****************************************

			public String ViewProileReasearcher(int researcherId) {
				// TODO Auto-generated method stub
				 String output = "";
				 try
				 {
				 Connection con = connect();
				 if (con == null)
				 {return "Error while connecting to the database for reading."; }
				
				 // Prepare the html table to be displayed
				 output = "<table border='1'>"
				 		+ "<tr>"
						+"<th>Researcher_ID</th>"
				 		+ "<th>FirstName</th>"
				 		+ "<th>LastName</th>"
				 		+ "<th>Gender</th>"
				 		+ "<th>Nic</th>"
				 		+"<th>Phone</th>"
				 		+"<th>Birth Day</th>"
				 		+"<th>Email</th>"
				 		+"<th>Password</th>"
				 		+"<th>Research Details</th>"
				 		+"<th>Account Number</th>"
				 		+"<th>Bank Name</th>"
				 	    +"<th>Update</th>"
				 	    + "<th>Remove</th>"
				 	    + "</tr>";

				 String query = "select * from researcher WHERE researcherID = ?";

					PreparedStatement stmt = con.prepareStatement(query);

					stmt.setInt(1,researcherId);
				 ResultSet rs = stmt.executeQuery();
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
				 String researcherID1  = Integer.toString(rs.getInt("researcherID"));
				 String researcherFname = rs.getString("researcherFname");
				 String researcherLname = rs.getString("researcherLname");
				 String researcherGender = rs.getString("researcherGender");
				 String researcherNic = rs.getString("researcherNic");
				 String researcherPhone=rs.getString("researcherPhone");
				 Date researcherBirthday = rs.getDate("researcherBirthday");
				 String researcherEmail = rs.getString("researcherEmail");
				 String researcherPassword = rs.getString("researcherPassword");
				 String researchDetails= rs.getString("researchDetails");
				 String accountnumber= rs.getString("accountnumber");
				 String bankname= rs.getString("bankname");

				 
				 // Add into the html table
				 output += "<tr><td>" + researcherID1 + "</td>";
				 output += "<td>" + researcherFname + "</td>";
				 output += "<td>" + researcherLname + "</td>";
				 output += "<td>" + researcherGender + "</td>";
				 output += "<td>" + researcherNic + "</td>";
				 output += "<td>"+researcherPhone+"</td>";
				 output += "<td>" +researcherBirthday + "</td>";
				 output += "<td>" +researcherEmail  + "</td>";
				 output += "<td>" +researcherPassword  + "</td>";
				 output += "<td>" +researchDetails  + "</td>";
				 output += "<td>" +accountnumber  + "</td>";
				 output += "<td>" +bankname  + "</td>";

				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>" + "<td><form method='post' action='Reasearchers.jsp'>" + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
				 + "<input name='researcherID' type='hidden' value='" + researcherID1 + "'>" + "</form></td></tr>";
				 }
				 //Connection Close
				 con.close();
				 
				 // Complete the html table
				 output += "</table>";
				 }
				 catch (Exception e)
				 {
				 output = "Error while reading the researcher.";
				 System.err.println(e.getMessage());
				 }
				 return output;			
				 }

//******************************************View All Researchers for Users (Without Buyers)****************************************************			
			public String readReasearchersForUsers() {
				// TODO Auto-generated method stub
				 String output = "";
				 try
				 {
				 Connection con = connect();
				 if (con == null)
				 {return "Error while connecting to the database for reading."; }
				
				 // Prepare the html table to be displayed
				 output = "<table border='1'>"
				 		+ "<tr>"
						+"<th>Researcher_ID</th>"
				 		+ "<th>FirstName</th>"
				 		+ "<th>LastName</th>"
				 		+ "<th>Gender</th>"
				 		+ "<th>Nic</th>"
				 		+"<th>Phone</th>"
				 		+"<th>Birth Day</th>"
				 		+"<th>Email</th>"
				 		+"<th>Research Details</th>"	
				 	    + "</tr>";

				 String query = "select * from researcher";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 // iterate through the rows in the result set
				 while (rs.next())
				 {
				 String researcherID  = Integer.toString(rs.getInt("researcherID"));
				 String researcherFname = rs.getString("researcherFname");
				 String researcherLname = rs.getString("researcherLname");
				 String researcherGender = rs.getString("researcherGender");
				 String researcherNic = rs.getString("researcherNic");
				 String researcherPhone=rs.getString("researcherPhone");
				 Date researcherBirthday = rs.getDate("researcherBirthday");
				 String researcherEmail = rs.getString("researcherEmail");
				 String researchDetails= rs.getString("researchDetails");

				 
				 // Add into the html table
				 output += "<tr><td>" + researcherID + "</td>";
				 output += "<td>" + researcherFname + "</td>";
				 output += "<td>" + researcherLname + "</td>";
				 output += "<td>" + researcherGender + "</td>";
				 output += "<td>" + researcherNic + "</td>";
				 output += "<td>"+researcherPhone+"</td>";
				 output += "<td>" +	researcherBirthday  + "</td>";
				 output += "<td>" +researcherEmail  + "</td>";
				 output += "<td>" +researchDetails  + "</td>";

				
				 }
				 //Connection Close
				 con.close();
				 
				 // Complete the html table
				 output += "</table>";
				 }
				 catch (Exception e)
				 {
				 output = "Error while reading the researcher.";
				 System.err.println(e.getMessage());
				 }
				 return output;		
				 
			}

			
		
			
}
