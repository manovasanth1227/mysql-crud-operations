import java.sql.*;
import java.util.*;

import connectionMySQL.MySQLConnection;
public class JDBC_Connection {
	static Connection conn;
	 static int empID ;
	 static Scanner s = new Scanner(System.in);
	public static void preprocess() {
		try {
		    if(conn==null)conn = MySQLConnection.establishMySQLConnection();
		    Statement st = conn.createStatement();
		    int i = 0;
		    ResultSet rs  = st.executeQuery("select * from emp");
		    while(rs.next()) {
		    	i++;
		    }
		    empID = i+1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void fetchData() {
		int i = 1; 
		try {
		    if(conn==null)conn = MySQLConnection.establishMySQLConnection();
		    Statement st = conn.createStatement();
		    ResultSet rs  = st.executeQuery("Select e.eid, e.ename, d.deptname , d.strength from emp as e join department as d on d.deptid = e.depid ;");
		    while(rs.next()){
		    	System.out.println(i+". Employee Id : "+rs.getInt(1)+ " Employee Name : "+rs.getString(2)+" Department Name :  "+rs.getString(3)+" Strength : "+rs.getInt(4));  
		    	i++;
		    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void getEmpData() {
		int i = 1; 
		try {
		    if(conn==null)conn = MySQLConnection.establishMySQLConnection();
//		    Statement st = conn.createStatement();
		    CallableStatement st = conn.prepareCall("call display()");
		    ResultSet rs  = st.executeQuery();
		    while(rs.next()){
		    	System.out.println(i+". Employee Id : "+rs.getInt(1)+ " Employee Name : "+rs.getString(2)+" City Name :  "+rs.getString(3)+" Department ID : "+rs.getInt(4));  
		    	i++;
		    }
		    System.out.println("------------------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static  void insertData(String name , String city , int depID) {
		try {
			if(conn!=null) conn = MySQLConnection.establishMySQLConnection();
		    PreparedStatement pt = conn.prepareStatement("INSERT INTO EMP VALUES (?, ? ,? ,? ) ");
		    pt.setInt(1,empID );
		    pt.setString(2,name);
		    pt.setString(3,city);
		    pt.setInt(4,depID);
		    int i=pt.executeUpdate();  
		    System.out.println(i+" record inserted");  
		    empID++;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void delete(int id) {
		try {
			if(conn!=null) conn = MySQLConnection.establishMySQLConnection();
			PreparedStatement st  = conn.prepareStatement("DELETE from emp where eid = (?)");
			st.setInt(1, id);
			 st.execute();
			System.out.println("1 record deleted");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void update(int eID , String city) {
		try {
			if(conn!=null) conn = MySQLConnection.establishMySQLConnection();
		    PreparedStatement pt = conn.prepareStatement("UPDATE emp set CITY = (?) where eid = (?) ");
		    pt.setString(1, city);
		    pt.setInt(2, eID);
		    int i=pt.executeUpdate();  
		    System.out.println(i+" record updated....");  

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void display() {
		try {
			if(conn!=null) conn = MySQLConnection.establishMySQLConnection();
			Statement st = conn.createStatement();
		    ResultSet rs  = st.executeQuery(" select d.deptname , count(e.ename) as count from department as d join emp as e on e.depid = d.deptid group by d.deptid;");
		    while(rs.next()){
		    	System.out.println("Department name : " + rs.getString(1) + "  Count : " + rs.getInt(2));  
		    	
		    }
		    System.out.println("------------------------------------");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[])  {
		preprocess();
		do {
			System.out.println("1.Insert");
			System.out.println("2.View Employees");
			System.out.println("3.Update Employee");
			System.out.println("4.Delete Employee");
			System.out.println("5.Display");
			System.out.println("6.Exit");
			int ch = s.nextInt();
			s.nextLine();
			switch(ch) {
				case 1: System.out.println("Enter Name : ->");String name  = s.nextLine();
						System.out.println("Enter City : ->");String city  = s.nextLine();
						System.out.println("Enter Deparment ID : ->");int depID  = s.nextInt();s.nextLine();
						insertData(name , city , depID);break;
				case 2 : getEmpData();break;
				case 3 : System.out.println("Enter Employee ID : ->");int eID  = s.nextInt();s.nextLine();
						 System.out.println("Enter City : ->");String City  = s.nextLine();
						 update(eID, City);break;
				case 4 : System.out.println("Enter Employee ID : ->"); int id = s.nextInt();s.nextLine();delete(id);break;
				case 5 : display();break;
				case 6 : try { conn.close();}catch(Exception e) {e.printStackTrace();}finally {System.out.println("Connection closed....");}break;
			}
	
		}while(true);
		
		
	}
}
