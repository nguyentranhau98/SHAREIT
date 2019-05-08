package model.bean;

public class Categories {
	private int id;
	private String cat_name;
	private int id_parent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public int getId_parent() {
		return id_parent;
	}
	public void setId_parent(int id_parent) {
		this.id_parent = id_parent;
	}
	public Categories(int id, String cat_name, int id_parent) {
		super();
		this.id = id;
		this.cat_name = cat_name;
		this.id_parent = id_parent;
	}
	public Categories() {
		super();
	}
	
}
