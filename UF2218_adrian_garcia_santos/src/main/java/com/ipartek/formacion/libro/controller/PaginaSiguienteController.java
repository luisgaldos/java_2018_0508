package com.ipartek.formacion.libro.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.libro.model.Pagina;
import com.ipartek.formacion.libro.model.PaginaArrayListDAO;

/**
 * Servlet implementation class PaginaSiguienteController
 */
@WebServlet("/paginaSiguiente")
public class PaginaSiguienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PaginaArrayListDAO dao;
	private ArrayList<Pagina> paginas;
     
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		dao = PaginaArrayListDAO.getInstance();
		paginas = (ArrayList<Pagina>) dao.getAll();
		int nPaginas = paginas.size();
		
		try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			if(id > nPaginas) {
				
				Pagina p = new Pagina();
				request.setAttribute("nPaginas", nPaginas);
				request.setAttribute("pagina", p);
				
				
			}else {
				
				Pagina p = paginas.get(id - 1);
				request.setAttribute("nPaginas", nPaginas);
				request.setAttribute("pagina", p);
				
			}
			request.getRequestDispatcher("home.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}