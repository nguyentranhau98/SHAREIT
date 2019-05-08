package model.bean;

public class Contacts {
	private int id;
	private String group_name;
	private String email;
	private String website;
	private String message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Contacts(int id, String group_name, String email, String website, String message) {
		super();
		this.id = id;
		this.group_name = group_name;
		this.email = email;
		this.website = website;
		this.message = message;
	}
	public Contacts() {
		super();
	}
	
}
