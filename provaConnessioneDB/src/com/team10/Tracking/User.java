package com.team10.Tracking;
import java.sql.*;
import java.util.Calendar;
import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
import android.widget.Toast;

import com.example.provaconnessionedb.DatabaseConnect;

public class User extends Activity {


	public void track_user(int id, String mail){
		
		Connection conn = null;
		Double latitude = null; //NB: Double (D maiuscola)
		Double longitude = null;
		final String user = "b76a43fe274f2a";
		final String psw = "b1a0a642";
		int count = 0;
		Timestamp oldestDate = null;
		
		//Crea una SQL date object in modo da usarla nell'INSERT statement
		Calendar calendar = Calendar.getInstance();
		java.sql.Date trackDate = new java.sql.Date(calendar.getTime().getTime());
		
		try {
		conn = DatabaseConnect.estabilishConnection(conn,user,psw);
		//Dichiaro oggetto della classe GPSTracker
		GPSTracker gps;
		gps = new GPSTracker(User.this);
		
		//Se il GPS è abilitato (ritorna true/false)
		if(gps.canGetLocation()){ //Se canGetLocation == true
			//Ottiene latitudine e longitudine
			latitude = gps.getLatitude();
			longitude = gps.getLongitude(); 
		} else{
            //Non puoi ottenere le coordinate: GPS o Network non abilitati
            gps.showSettingsAlert();
          } 
		
		//Se è stato possibile tracciare l'utente con GPS o Network provider 
		//inserisce in DATABASE:TRACKINGS una nuova tupla contenente latitudine e longitudine
		//TODO Diversificare nome variabile query.
			if(latitude != null && longitude != null){
				String query = " insert into trackings (Latitude, Longitude, RegistrationDate, User)"
					+ " values (?, ?, ?, ?)";
				PreparedStatement pStmt = conn.prepareStatement(query);
				pStmt.setDouble (1, latitude);
				pStmt.setDouble (2, longitude);
				pStmt.setDate   (3, trackDate);
				pStmt.setString (4, mail);
			}   else{
					Toast.makeText(getApplicationContext(), "Impossibile effettuare il Tracking.",
                    Toast.LENGTH_SHORT).show();	
			    }
				//Controllo se nel database sono presenti più di 10 tracciamenti;
				//Se è vero, cancella il tracciamento con la data meno recente;
			
				//Stringa per operazione di select sul database
				String query1 = ("select count(*) from trackings where User = '"+mail+"' ");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query1);
					while(rs.next()){
						count = rs.getInt(1); //1 indica l'indice della colonna
					}
				if(count>10){
					String query2 = ("select min(RegistrationDate) from trackings where User = '"+mail+"'");
					Statement st1 = conn.createStatement();
					ResultSet rs1 = st1.executeQuery(query2);
					while (rs1.next()){
						oldestDate = rs.getTimestamp(1);
						
						String query3 = ("delete from trackings where User = '"+mail+"' and RegistrationDate = '"+oldestDate+"' ");
						Statement st2 = conn.createStatement();
						st2.executeUpdate(query3);
					}
				}	
		} catch (SQLException ex) {
			ex.printStackTrace();
		  }
	}
}