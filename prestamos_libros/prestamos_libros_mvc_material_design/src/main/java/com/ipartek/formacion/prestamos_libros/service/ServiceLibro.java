package com.ipartek.formacion.prestamos_libros.service;

import java.util.List;

import com.ipartek.formacion.prestamos_libros.model.LibroDAO;
import com.ipartek.formacion.prestamos_libros.pojo.Libro;

public class ServiceLibro implements IServiceLibro{
	
	private LibroDAO daoLibro = LibroDAO.getInstance();
	
	public ServiceLibro ()  {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean crear(Libro l) throws Exception {
		boolean resul = false;
		
		if(daoLibro.insert(l)) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public boolean modificar(Libro l) throws Exception {
		boolean resul = false;
		
		if(daoLibro.update(l)) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public boolean eliminar(long id) throws Exception {
		boolean resul = false;
		
		if(daoLibro.delete(Long.toString(id))) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public List<Libro> listar() throws Exception {
		List <Libro> libros = daoLibro.getAll();
		return libros;
	}

	@Override
	public Libro buscarId(long id) throws Exception {
		Libro l = daoLibro.getById(Long.toString(id));
		return l;
	}

}
