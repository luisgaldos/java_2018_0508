package com.ipartek.formacion.youtube.pojo;

public class Usuario {
	
	private long id;
	private String nombre;
	private String pass;
	private Rol rol; //todo crear pojo y tabla
	

	
	public Usuario() {
		super();
		this.id=-1;
		this.nombre = "";
		this.pass = "";
		this.rol = new Rol();
	}

		
	public Usuario(String nombre, String pass) {
		this();
		
		this.nombre = nombre;
		this.pass = pass;
	}
	
	public Usuario(int id,String nombre, String pass) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.pass = pass;
		
	}
	
	public Usuario(long id, String nombre, String pass,Rol rol) {
		this();
		this.id=id;
		this.nombre = nombre;
		this.pass = pass;
		this.rol=rol;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public Rol getRol() {
		return this.rol;
	}
	
	public void setRol(Rol rol) {
		this.rol= rol;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", pass=" + pass + "]";
	}
	
}