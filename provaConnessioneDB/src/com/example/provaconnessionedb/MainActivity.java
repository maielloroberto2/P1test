package com.example.provaconnessionedb;

import android.app.Activity;




import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;




import java.sql.*;


public class MainActivity extends Activity implements OnClickListener {
	
	public static TextView myText; 
	private Button connButton;
	
	public static final String username = "b76a43fe274f2a";
	public static final String password = "b1a0a642";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    myText = (TextView) findViewById(R.id.mytext);
	    
	    connButton = (Button) findViewById(R.id.connbutton);
	    connButton.setOnClickListener(this);
}
	





/*class ConnectAsync extends AsyncTask<String, Void, String> {
private String name = null;
private String surname = null;
private ProgressDialog myProgress;





            @Override
            protected void onPreExecute()
            {   super.onPreExecute();
            	myProgress = ProgressDialog.show(MainActivity.this, "Wait", "Loading..");
            }







	        @Override
	        protected String doInBackground(String... urls) {

	        	String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_2ea8739675bae19";
	        	String user = "b204f48548d75b";
	        	String password = "3cd0f4eb";

	            ResultSet rs = null;
	        	Connection connection = null;
	        	try {
	        		Class.forName("com.mysql.jdbc.Driver");
	        		connection = DriverManager.getConnection(url,user,password);
	        	} catch (ClassNotFoundException e) {
	        		System.out.println("Driver non caricato");
	        	} catch (SQLException e) {
	        		System.out.println("Errore con url e credenziali!");
	        		
	        	}

	        	if(connection!= null)
	        	{System.out.println("Connessione riuscita!");
	        	connected = true;}

	        	try {
					Statement stmt = connection.createStatement();
					String sql = "SELECT NOME,COGNOME FROM CALCIATORI WHERE ID = 1";
					rs = stmt.executeQuery(sql);
					if(!rs.next())
					{System.out.println("Vuota");}
					else
					{
						do{
							
							try {
								name= new String( rs.getBytes("NOME"), "UTF-8");
								surname= new String( rs.getBytes("COGNOME"), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
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
protected void onPostExecute(String result) //istruzioni da eseguire dopo l'asynctask
{   myProgress.dismiss();
	if(result.equals("Connessione riuscita"))
		{Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        
		nome1.setText(name);
        cognome1.setText(surname);}
        
        else 
        	Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();


}







}
*/



public void connectToDB(View view)
{

new ConnectAsync(this).execute();







}




@Override
public void onClick(View v) {
connectToDB(v);
}





}







