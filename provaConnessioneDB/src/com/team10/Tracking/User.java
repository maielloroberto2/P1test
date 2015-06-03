package com.team10.Tracking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		final String user = "b76a43fe274f2a";
		final String psw = "b1a0a642";
		
		//Crea una SQL date object in modo da usarla nell'INSERT statement
		Calendar calendar = Calendar.getInstance();
		java.sql.Date trackDate = new java.sql.Date(calendar.getTime().getTime());
		
		try {
		conn = DatabaseConnect.estabilishConnection(conn,user,psw);

		Double latitude = null;
		Double longitude = null;
		
		GPSTracker gps;
		gps = new GPSTracker(User.this);
		
		//Se il GPS è abilitato (ritorna true/false)
		if(gps.canGetLocation()){ //Se canGetLocation == true
			//Ottiene latitudine e longitudine
			latitude = gps.getLatitude();
			longitude = gps.getLongitude(); 
		}else{
            //Non puoi ottenere le coordinate: GPS o Network non abilitati
            gps.showSettingsAlert();
         }
		//Se è stato possibile tracciare l'utente con GPS o Network provider 
		//inserisce in DATABASE:TRACKINGS una nuova tupla contenente latitudine e longitudine
		if(latitude != null && longitude != null){
			String query = " insert into trackings (Latitude, Longitude, RegistrationDate, User)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.setDouble (1, latitude);
			pStmt.setDouble (2, longitude);
			pStmt.setDate   (3, trackDate);
			pStmt.setString (4, mail);
		} else{
			Toast.makeText(getApplicationContext(), "Impossibile effettuare il Tracking.",
                    Toast.LENGTH_SHORT).show();	
		}
		} catch (SQLException ex) {
        ex.printStackTrace();
	  }
	}
}
