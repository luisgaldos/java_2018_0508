package com.ipartek.formacion.prestamos_libros.pojo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class Usuario {
	
	private Long id;
	
	@NotBlank
	@Size(min=2, max=150)
	private String nombreApellidos;
	
	public Usuario() {
		super();
		this.id = (long) -1;
		this.nombreApellidos = "";
	}

	public Usuario(Long id, String nombreApellidos) {
		super();
		this.id = id;
		this.nombreApellidos = nombreApellidos;
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}
	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreApellidos=" + nombreApellidos + "]";
	}

}
