package com.example.knowledgehound;

import java.sql.*;
import com.example.provaconnessionedb.DatabaseConnect;
import java.util.Calendar;


public class User {
	
	
//  2.3.40:	UC-Read Message
/* PRECONDITONS:
* Il programma deve conoscere la locazione del DataBase; Deve essere gia
* inizializzata la struttura PROCESS:User.
* 
* POSTCONDITIONS:
* Il sistema deve consentire ad un utente la lettura di un messaggio.
*/
	
	public void read_message(int id){
	
		Connection conn=null;
		final String user = "b76a43fe274f2a";
		final String psw = "b1a0a642";
		
		try {
		conn = DatabaseConnect.estabilishConnection(conn,user,psw);
			//Se non esiste in DATABASE:MESSAGES una tupla che abbia la coppia (DATABASE:MESSAGES:ID, DATABASE:MESSAGES:Receiver),
			//corrispondente a (INPUT:ID, PROCESS:User:Mail), restituisce InvalidOperation_Error (READ MESSAGE, 3);
			Message mg;//istanzia un oggetto della classe message
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from messages where Id = '"+id+"' and Receiver = '"+this.mail+"' ");
			//if (st.getResultSet() == null) throw new InvalidOperation_Error ("READ SKILL", 3);
		
		 		while (rs.next()) {
		 			mg.sender = rs.getString("Sender");
		 			mg.receiver = rs.getString("Receiver");
		 			mg.sendingDate = rs.getDate("SendingDate");
		 			mg.isRead = rs.getBoolean("IsRead");
		 			mg.object = rs.getString("Object");
		 			mg.text = rs.getString("Text");
		 			//Stampa a video il messaggio 
		 			System.out.println(mg.text);
		 		}
		// Esegue la disconnessione dal DataBase
		DatabaseConnect.closeConnection(conn);
		// Elimino dalla memoria gli oggetti usati
		rs=null;
		st=null;
		} catch (SQLException ex) {
            ex.printStackTrace();
		  }
   }
	
	
	
//  2.3.40:	UC-Send Message
/* PRECONDITONS:
* Il programma deve conoscere la locazione del DataBase; Deve essere gia
* inizializzata la struttura PROCESS:User.
* 
* POSTCONDITIONS:
* Il sistema deve consentire ad un utente di inviare messaggi di testo ad un qualsiasi altro utente.
*/
   public void Send_Message(String mail, String object, String text) {

    //Connessione al db e lancio di eccezione in caso di fallimento.	
	Connection conn=null;
	final String user = "b76a43fe274f2a";
	final String psw = "b1a0a642";
	
	try {
		conn = DatabaseConnect.estabilishConnection(conn,user,psw);
	
		//Crea una SQL date object in modo da usarla nell'INSERT statement
		Calendar calendar = Calendar.getInstance();
		java.sql.Date sendDate = new java.sql.Date(calendar.getTime().getTime());
	
		//cerca l'utente nel db; se non e' presente, lancia l'errore.
		//TODO Risolvere la +mail+
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM users WHERE Mail = '"+mail+"' ");
	
		//Se l'utente e' presente, immette una nuova tupla in DATABASE:MESSAGES; quindi invia un messaggio all'utente desiderato.
	
		String query = " insert into messages (Sender, Receiver, SendingDate, isRead, Object, Text)"
		+ " values (?, ?, ?, ?, ?, ?)";

		// Creo il mySQL insert PreparedStatement
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString (2, "email@email.com");
		preparedStmt.setString (3, mail);
		preparedStmt.setDate   (4, sendDate);
		preparedStmt.setBoolean (5, false);
		preparedStmt.setString (6, object);
		preparedStmt.setString (7, text);

		// Esecuzione del PreparedStatement
		preparedStmt.execute();

		// Esegue la disconnessione dal DataBase
		DatabaseConnect.closeConnection(conn);
		// Elimino dalla memoria gli oggetti usati 
    	rs=null;
    	st=null;
    }   catch (SQLException ex) {
          ex.printStackTrace();
	    }
   }
	
	
}
