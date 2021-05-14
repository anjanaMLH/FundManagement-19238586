package com.paf10;
import java.sql.*;

public class Fund {

	//Connection
	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PAF2021",	"root", "");
			//For testing
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}

	//Insert
	public String insertFund(String type, String source, String amount, String date)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " insert into fund(`fundID`,`fundType`,`fundSource`,`fundAmount`,`fundDate`) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, type);
			preparedStmt.setString(3, source);
			preparedStmt.setDouble(4, Double.parseDouble(amount));
			preparedStmt.setString(5, date); 

			//execute the statement
			preparedStmt.execute();
			con.close();

			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the fund.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//private String readFunds() {
		// TODO Auto-generated method stub
		//return null;
	//}

	//Read
	public String readFunds()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the HTML table to be displayed
			output = "<table  class='table table-dark table-striped'><tr><th>Fund Type</th>"
					+"<th>Fund Source</th><th>Fund Amount</th>"
					+ "<th>Fund Date</th>"
					+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from fund";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next())
			{
				String fundID = Integer.toString(rs.getInt("fundID"));
				String fundType = rs.getString("fundType");
				String fundSource = rs.getString("fundSource");
				String fundAmount = Double.toString(rs.getDouble("fundAmount"));
				String fundDate = rs.getString("fundDate");

				// Add a row into the HTML table
				output += "<tr><td>" + fundType + "</td>";
				output += "<td>" + fundSource + "</td>";
				output += "<td>" + fundAmount + "</td>"; 
				output += "<td>" + fundDate + "</td>";
				

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-fundid='" + fundID + "'></td>"
						+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-fundid='" + fundID + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the funds.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update
	public String updateFund(int id, String type, String source, String amount, String date)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = "update item set `fundType`=?,`fundSource`=?,`fundAmount`=?,`fundDate`=? where `fundID`=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, type);
			preparedStmt.setString(2, source);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, date);
			preparedStmt.setInt(5, id);

			//execute the statement
			preparedStmt.executeUpdate();
			con.close();

			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
			
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the fund.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Delete
	public String removeFund(int id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = "delete from item where `fundID`=?;";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, id);

			//execute the statement
			preparedStmt.executeUpdate();
			con.close();

			String newFunds = readFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the fund.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	
}

