package com.example.provaconnessionedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnect {

	
public static Connection estabilishConnection(Connection conn,String user,String password)
{  
	
	String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_a23e937b6cf1c6b";
 	
	
	
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,user,password);
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
		System.out.println("Driver non caricato");
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("Errore con url e credenziali!");
		
	}


    return conn;


}



public static void closeConnection(Connection conn)
{
	try {
		conn.close();
	} catch (SQLException e) {
		System.out.println("Impossibile chiudere la connessione!");
		e.printStackTrace();
	}
	
	
	
	
	
	
}














}
