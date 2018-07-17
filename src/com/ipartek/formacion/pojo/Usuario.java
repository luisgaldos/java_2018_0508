package com.ipartek.formacion.pojo;

/**
 * Un usuario va a tener un:
 * private long id;
 * private String nombre;
 * private String email;
 * private String password;
 * 
 * GET/SET/TOSTRING
 * 
 * usuarioDao--> implementa crudable
 * 
 * @author Ainara
 *
 */
public class Usuario {

	private long id;
	private String nombre;
	private String email;
	private String password;

	public Usuario() {
		super();
		this.id = -1;
		this.nombre = "";
		this.email = "";
		this.password = "";
	}

	public Usuario(long id, String nombre, String email, String password) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", password=" + password + "]";
	}

}
