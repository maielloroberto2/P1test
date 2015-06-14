//package com.team10.Tracking;
package com.example.knowledgehound;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public final class GPSTracker implements LocationListener{
	
	private final Context mContext;
	public boolean isGPSEnabled = false; //Flag per stato GPS
	public boolean isNetworkEnabled = false; //Flag per stato Network
	public boolean canGetLocation = false; //Flag per stato GPS
	
	Location location;
	double latitude;
	double longitude;
	
	//La distanza minima per aggiornare coordinate (in metri)
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 Metri
	// Il tempo minimo tra due aggiornamenti (in millisecondi)
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 *1; // 1 Minuto
	
	protected LocationManager locationManager;
	
	public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }
	
	/**
     * Funzione getLocation che prende la posizione corrente dell'utente
     * Questo metodo tenta prima di prendere le coordinate dal network provider
     * Se il network provider non è abilitato, allora le prenderà dal GPS provider
     */
	
	public Location getLocation(){
		try{
			locationManager = (LocationManager) mContext
                    .getSystemService(Context.LOCATION_SERVICE);
			
			 //Controlla stato GPS
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.v("isGPSEnabled", "=" + isGPSEnabled);

            //Controlla stato Network
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

            if (isGPSEnabled == false && isNetworkEnabled == false) {
                //Non sono abilitati nè GPS nè Network Provider
            } else {
            	this.canGetLocation = true;
            	 if (isNetworkEnabled){
                     location=null;
                     locationManager.requestLocationUpdates(
                             LocationManager.NETWORK_PROVIDER,
                             MIN_TIME_BW_UPDATES,
                             MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                     Log.d("Network", "Network");
                     if (locationManager != null) {
                         location = locationManager
                                 .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                         if (location != null) {
                             latitude = location.getLatitude();
                             longitude = location.getLongitude();
                         }
                     }
                 }
            // Se il GPS è abilitato prende lat/long usando GPS Services
            	 if (isGPSEnabled) {
                     location=null;
                     if (location == null) {
                         locationManager.requestLocationUpdates(
                                 LocationManager.GPS_PROVIDER,
                                 MIN_TIME_BW_UPDATES,
                                 MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                         Log.d("GPS Enabled", "GPS Enabled");
                         if (locationManager != null) {
                             location = locationManager
                                     .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                             if (location != null) {
                                 latitude = location.getLatitude();
                                 longitude = location.getLongitude();
                             }
                         }
                     }
            	 }
            } 
		} catch (Exception e) {
                e.printStackTrace();
            }
		return location;
	}

	 //Funzione getLatitude per avere le coordinate della latitudine
     public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

     //Funzione getLongitude per avere le coordinate della longitudine
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }
    
    // Funzione per controllare se WiFi/GPS sono attivi
     public boolean canGetLocation() {
        return this.canGetLocation;
    }
     
     //Funzione che mostra la finestra di dialogo 'GPS non attivo'
     //E chiede di attivarlo nelle impostazioni
     public void showSettingsAlert(){
         AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
       
         //Dialog Title
         alertDialog.setTitle("GPS is settings");
         //Dialog Message
         alertDialog.setMessage(" Il GPS non e' abilitato. Vuoi attivarlo nelle impostazioni?");
         
         //Se viene premuto il bottone Impostazioni
         alertDialog.setPositiveButton("Impostazioni", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog,int which) {
                 Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                 mContext.startActivity(intent);
             }
         });
   
         //Se viene premuto il bottone Annulla
         alertDialog.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
             dialog.cancel();
             }
         });
         
       //Mostra Alert Message
         alertDialog.show();
     }
 	@Override
    public void onLocationChanged(Location location) {
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
