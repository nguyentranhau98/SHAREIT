package model.bean;

import java.sql.Timestamp;

public class News {
	private int id;
	private Categories cat;
	private String user;
	private String avatar;
	private String name;
	private String desc;
	private String content;
	private Timestamp date_create;
	private String picture;
	private int counter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Categories getCat() {
		return cat;
	}
	public void setCat(Categories cat) {
		this.cat = cat;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getDate_create() {
		return date_create;
	}
	public void setDate_create(Timestamp date_create) {
		this.date_create = date_create;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public News(int id, Categories cat, String user, String avatar, String name, String desc, String content, Timestamp date_create,
			String picture, int counter) {
		super();
		this.id = id;
		this.cat = cat;
		this.user = user;
		this.avatar = avatar;
		this.name = name;
		this.desc = desc;
		this.content = content;
		this.date_create = date_create;
		this.picture = picture;
		this.counter = counter;
	}
	public News() {
		super();
	}
	
	
}
