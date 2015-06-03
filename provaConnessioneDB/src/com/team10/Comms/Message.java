package com.team10.Comms;
import java.lang.String;
import java.util.Date;

public class Message {
	
	//ATTRIBUTES:
	
	int ID;
	String sender = new String();
	String receiver = new String();
	Date sendingDate = new Date();
	boolean isRead;
	String object = new String();
	String text = new String();
	

	
	//SET-GET METHODS:
	
	public int get_ID()
	{
		return this.ID;
	}
	
	
	public void set_ID(int value)
	{
		this.ID = value;
	}
	
	
	public String get_sender()
	{
		return this.sender;
	}
	
	
	public void set_sender(String value)
	{
		this.sender = value;
	}
	
	
	public String get_receiver()
	{
		return this.receiver;
	}
	
	
	public void set_receiver(String value)
	{
		this.receiver = value;
	}
	
	
	public Date get_sendingDate()
	{
		return this.sendingDate;
	}
	
	
	public void set_sendingDate(Date value)
	{
		this.sendingDate = value;
	}
	
	
	public boolean get_isRead()
	{
		return this.isRead;
	}
	
	
	public void set_isRead(boolean value)
	{
		this.isRead = value;
	}
	
	
	public String get_object()
	{
		return this.object;
	}
	
	
	public void set_object(String value)
	{
		this.object = value;
	}
	
	
	public String get_text()
	{
		return this.text;
	}
	
	
	public void set_text(String value)
	{
		this.text = value;
	}

}
