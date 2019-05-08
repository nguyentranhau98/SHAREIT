package model.bean;

import java.sql.Timestamp;

public class Comments {
	private int id_com;
	private int id_new;
	private String user;
	private String text;
	private int status;
	private Timestamp date_create;
	public int getId_com() {
		return id_com;
	}
	public void setId_com(int id_com) {
		this.id_com = id_com;
	}
	public int getId_new() {
		return id_new;
	}
	public void setId_new(int id_new) {
		this.id_new = id_new;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getDate_create() {
		return date_create;
	}
	public void setDate_create(Timestamp date_create) {
		this.date_create = date_create;
	}
	public Comments(int id_com, int id_new, String user, String text, int status, Timestamp date_create) {
		super();
		this.id_com = id_com;
		this.id_new = id_new;
		this.user = user;
		this.text = text;
		this.status = status;
		this.date_create = date_create;
	}
	public Comments() {
		super();
	}
	
	
}
