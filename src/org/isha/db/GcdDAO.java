package org.isha.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.isha.beans.Numbers;
import org.isha.exceptions.DBException;
import org.isha.exceptions.JMSQueueException;
import org.isha.util.Constants;

public class GcdDAO {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	private String errorStr = "Error occurred while accessing DataBase. ";

	public void getConnection() throws DBException {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(Constants.JDBC_DRIVER_NAME);		

			// Setup the connection with the DB
			connect = DriverManager.getConnection(Constants.DB_CONNECTION + "user=" + Constants.USERNAME + "&password=" + Constants.PASSWORD);
		} 
		catch (ClassNotFoundException e) {
			throw new DBException(errorStr + e.getMessage());			
		}catch (SQLException e) {
			throw new DBException(errorStr + e.getMessage());
		}catch(Exception e){
			throw new DBException(errorStr + e.getMessage());
		}		
	}

	/*
	 * Inserts inputs into the table.
	 */
	public void insertIntoTbl(int i1, int i2, int gcd) throws DBException  
	{
		try {

			getConnection();             

			preparedStatement = connect.prepareStatement("insert into  gcddb.tbl_gcd (number1, number2, gcd) values (?,?,?)");

			preparedStatement.setInt(1, i1);
			preparedStatement.setInt(2, i2);
			preparedStatement.setInt(3, gcd);
			preparedStatement.executeUpdate();

		} catch(Exception e){
			throw new DBException(errorStr + e.getMessage());
		}finally {
			try {
				connect.close();
			} catch (SQLException e) {
				throw new DBException(errorStr + e.getMessage());
			}
		}
	}

	/*
	 * selects and returns all rows from table tbl_gcd
	 */
	public List<Numbers> selectFromTbl() throws DBException
	{
		Numbers numbers; 
		List<Numbers> list = new ArrayList<Numbers>();

		try{
			getConnection();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from gcddb.tbl_gcd");

			while(resultSet.next())
			{
				numbers = new Numbers();

				numbers.setInput1(resultSet.getInt("number1"));
				numbers.setInput2(resultSet.getInt("number2"));
				numbers.setGcd(resultSet.getInt("gcd"));

				list.add(numbers);
			}
			return list;

		}catch (Exception e) {
			throw new DBException(errorStr + e.getMessage());
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				throw new DBException(errorStr + e.getMessage());
			}			
		}
	}

	/*
	 * returns list of all GCDs from table.
	 */
	public List<Integer> selectGcdFromTbl() throws DBException
	{
		List<Integer> list = new ArrayList<Integer>();

		try{
			getConnection();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select gcd from gcddb.tbl_gcd");		     

			while(resultSet.next())
			{					
				list.add(resultSet.getInt("gcd"));
			}

			return list;

		}catch (Exception e) {
			throw new DBException(errorStr + e.getMessage());
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				throw new DBException(errorStr + e.getMessage());
			}			
		}

	}

	/*
	 * returns sum of all GCDs
	 */
	public int getGcdSum() throws DBException
	{
		int sum = 0;

		try{
			getConnection();
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select SUM(gcd) as sum_gcd from gcddb.tbl_gcd");		     

			if(resultSet.next())
			{					
				sum = resultSet.getInt("sum_gcd");
			}

			return sum;

		}catch (Exception e) {
			throw new DBException(errorStr + e.getMessage());
		} finally {
			try {
				resultSet.close();
				statement.close();
				connect.close();
			} catch (SQLException e) {
				throw new DBException(errorStr + e.getMessage());
			}			
		}
	}
}
