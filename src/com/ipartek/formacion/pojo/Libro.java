package com.ipartek.formacion.pojo;

public class Libro {
	
	public static final int ISBN_MIN_LENGTH = 5;
	public static final String ISBN_MIN_EXCEPTION = "La longitud minima del ISBN debe ser " + ISBN_MIN_LENGTH;	

	private long id;
	private String titulo;
	private String isbn;
	private boolean prestado;
	
	public Libro() {
		super();
		this.id = -1;
		this.titulo = "";
		this.isbn = "";
		this.prestado = false;
	}
	
	

	public Libro(String titulo, String isbn, boolean prestado) throws Exception {
		this();
		this.titulo = titulo;		
		this.prestado = prestado;
		setIsbn(isbn);
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

	/**
	 * Guardamos el valor del ISBN
	 * @param isbn String identificador del Libro
	 * @throws Exception si ISBN == null || ISBN.length < ISBN_MIN_LENGTH
	 */
	public void setIsbn(String isbn) throws Exception {
		
		if ( isbn != null &&  isbn.trim().length() >= ISBN_MIN_LENGTH ) {
			this.isbn = isbn;	
		}else {
			throw new Exception(ISBN_MIN_EXCEPTION);
		}	
		
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", isbn=" + isbn + ", prestado=" + prestado + "]";
	}
	
	
	
	
}
