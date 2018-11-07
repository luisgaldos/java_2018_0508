package com.ipartek.formacion.libros.pojo;

public class Libro {

	private long id;
	private String titulo;
	private String isbn;
	private Editorial editorial;
	
	
	public Libro() {
		super();
		this.id = -1;
		this.titulo = "";
		this.isbn = "";
		this.editorial = new Editorial();
	}
	
	public Libro(String titulo, String isbn, int n_ejemplares, Editorial editorial) {
		this();
		this.titulo = titulo;
		this.isbn = isbn;
		this.editorial = editorial;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", isbn=" + isbn + ", editorial=" + editorial + "]";
	}



	
	

}
