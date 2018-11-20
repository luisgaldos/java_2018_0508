package com.ipartek.formacion.prestamos_libros.service;

import java.util.List;

import com.ipartek.formacion.prestamos_libros.model.EditorialDAO;
import com.ipartek.formacion.prestamos_libros.pojo.Editorial;

public class ServiceEditorial implements IServiceEditorial{
	

	private static ServiceEditorial INSTANCE = null;
	private static EditorialDAO daoEditorial;

	
	private ServiceEditorial ()  {
		super();

		daoEditorial = EditorialDAO.getInstance();

	}
	
	public static synchronized ServiceEditorial getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServiceEditorial();
		}
		return INSTANCE;
	}

	@Override
	public boolean crear(Editorial e) throws Exception {
		boolean resul = false;
		
		if(daoEditorial.insert(e)) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public boolean modificar(Editorial e) throws Exception {
		boolean resul = false;
		
		if(daoEditorial.update(e)) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public boolean eliminar(long id) throws Exception {
		boolean resul = false;
		
		if(daoEditorial.delete(Long.toString(id))) {
			resul = true;
		}
		
		return resul;
	}

	@Override
	public List<Editorial> listar() throws Exception {
		List <Editorial> editoriales = daoEditorial.getAll();
		return editoriales;
	}

	@Override
	public Editorial buscarId(long id) throws Exception {
		Editorial e = daoEditorial.getById(Long.toString(id));
		return e;
	}

}
