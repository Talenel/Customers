import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ArrayList<String> getCustomers(String last)
	{
		ArrayList<String> ids= new ArrayList<>();;
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Customers?autoReconnect=true&useSSL=false&"
                                + "user=root&password=password");
			stmt = con.prepareStatement("Select FirstName, LastName,CustID from customer where LastName= ?");
			stmt.setString(1, last);
			 
			rs=stmt.executeQuery();	
			ResultSetMetaData col=rs.getMetaData();
			for(int i=1;i<=col.getColumnCount();i++)
			{
				
				System.out.print(col.getColumnName(i) + "\t");
			}
			System.out.println();
			System.out.println();
					
			while(rs.next())
			{
							
				ids.add(rs.getString(3));
				for(int i=1;i<=col.getColumnCount();i++)
				{
						
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
			return ids;
			
			}catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return ids;
		
		
	}
	
	public String[] getCustomer(int custID)
	{
		Connection con=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Customers?autoReconnect=true&useSSL=false&"
                                + "user=root&password=password");
			stmt = con.prepareStatement("Select customer.CustID,Title,FirstName,LastName,StreetAddress,City,State,ZipCode,EmailAddress,position,company " 
					+"from ((customer join address using(addID)) join isEmployee using(CustID)) join companies using(compID) "
					+"where CustID=?");
			stmt.setInt(1, custID);
			 
			rs=stmt.executeQuery();	
			
					
			while(rs.next())
			{
				String[] vals=new String[11];
				
				for(int i=0;i<vals.length;i++)
				{
					vals[i]=rs.getString(i+1);
					
				}
				return vals;
				
				
			}
			
			}catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return null;
		
		
	}
	
	
	
	
	
	public void updateAddress(String street, String city,String state,String zip, int custID )
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "update address a join customer c using(addID) "
				+ "set a.StreetAddress = ?,a.City = ?,a.State = ?,a.ZipCode = ? "
						+ " where c.CustID= ?";
		try{
			Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/Customers?autoReconnect=true&useSSL=false&"
                                + "user=root&password=password");
			stmt = con.prepareStatement(sql);
			stmt.setString(1, street);
			stmt.setString(2, city);
			stmt.setString(3, state);
			stmt.setString(4, zip);
			stmt.setInt(5, custID);
			
			stmt.executeUpdate();
			
				 
				
				sql= "Select * from customer join address using(addID) where CustID= ?";
				
				stmt = con.prepareStatement(sql);
				stmt.setInt(1, custID);
				
				rs=stmt.executeQuery();
				
				
				ResultSetMetaData col=rs.getMetaData();
				for(int i=1;i<=col.getColumnCount();i++)
				{
					
					System.out.print(col.getColumnName(i) + "\t");
				}
				System.out.println();
				System.out.println();
				while(rs.next()){
					
					
					
					for(int i=1;i<=col.getColumnCount();i++)
					{
						
						System.out.print(rs.getString(i) + "\t");
					}
					System.out.println();
				}
			 
		}
			catch (SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
	

}
