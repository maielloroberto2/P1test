package com.example.provaconnessionedb;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import android.widget.Toast;

public class ConnectAsync extends AsyncTask<String,Void,String> {
	private String queryresult = null;
	
	private ProgressDialog myProgress;
	private boolean connected = false;
	public Context context;
	private Connection connection;
	
	public ConnectAsync(Context context)
	{super();
	this.context = context;
	myProgress = new ProgressDialog(context);}
	
	
	@Override
    protected void onPreExecute()
    {   super.onPreExecute();
	myProgress.setTitle("Carico i dati..");    
	myProgress.setMessage("Please Wait..");
    myProgress.show();

    }

	
	 @Override
     protected String doInBackground(String... urls) {

             connection = DatabaseConnect.estabilishConnection(connection, MainActivity.username, MainActivity.password);
             if(!(connection == null))
             {connected = true;}
            	 
             ResultSet rs = null;
     	try {
				Statement stmt = connection.createStatement();
				String sql = "SELECT NAME FROM COMMUNITIES WHERE ID = 1";
				rs = stmt.executeQuery(sql);
				if(!rs.next())
				{System.out.println("Vuota");}
				else
				{
					do{
						
						try {
							queryresult= new String( rs.getBytes("Name"), "UTF-8");
							
						} catch (UnsupportedEncodingException e) {
							//serve per la codifica dei caratteri,non ci badate
							e.printStackTrace();
						}
						

						
						
						
						
					}while(rs.next());}
				
				
				
				
				
			} catch (SQLException e) {
				System.out.println("Query error");
			}


         if(connected)
         	return "Connessione riuscita";
         else return "Connessione non riuscita";

     }




	 @Override
	 //notate che il parametro result coincide con quello che viene ritornato dalla funzione doInBackGround,ovvero
	 // potranno essere una tra le due stringhe: 'Connessione riuscita' oppure 'Connessione non riuscita'
	 protected void onPostExecute(String result) //istruzioni da eseguire dopo l'asynctask
	 {   myProgress.dismiss();
	     
	 	if(result.equals("Connessione riuscita"))
	 		{Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	         
	 		MainActivity.myText.setText(queryresult);
	         }
	         
	         else 
	         	Toast.makeText(context, result, Toast.LENGTH_SHORT).show();


	 }













}
