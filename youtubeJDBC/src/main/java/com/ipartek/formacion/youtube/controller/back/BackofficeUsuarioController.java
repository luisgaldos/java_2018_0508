package com.ipartek.formacion.youtube.controller.back;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.youtube.model.UsuariosDaoJDBC;
import com.ipartek.formacion.youtube.pojo.Usuario;

/**
 * Servlet implementation class BackofficeUsuarioController
 */
@WebServlet("/backoffice/usuario")
public class BackofficeUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UsuariosDaoJDBC usuariosJDBC;
	private ArrayList<Usuario> usuarios;
	private String view = "tree";
	
	@Override
	public void init(ServletConfig config) throws ServletException {	
		super.init(config);
		//Se ejecuta solo con la 1º petición, el resto de peticiones iran a "service"
		//inicializamos el arraydao de usuarios
		usuariosJDBC =  UsuariosDaoJDBC.getInstance();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String view = request.getParameter("view");
			usuarios = (ArrayList<Usuario>) usuariosJDBC.getAll();
			String id = request.getParameter("id");
			Usuario usuarioSeleccionado = new Usuario();
			
			
			if(usuarios != null) {
				request.setAttribute("usuarios", usuarios);

			}
			if (view == null) {
				view = "tree";
			}
			//Si viene id automaticamente cambiamos a vista formulario
			if(id != null) {
				view = "form";
				usuarioSeleccionado = usuariosJDBC.getById(id);
				/*if("".contentEquals(usuarioSeleccionado.getNombre())){
					usuarioSeleccionado = null;
				}*/
			}
			request.setAttribute("usuarioSeleccionado",usuarioSeleccionado);
			request.setAttribute("view", view);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//response.sendRedirect( "usuario/index.jsp" ); 
			request.getRequestDispatcher("usuario/index.jsp").forward(request, response);
		}
		
	}

/*	private Object getUsuariosVirtuales() {
		Usuario user = new Usuario();
		ArrayList<Usuario> usuariosVirtuales = new ArrayList<Usuario>();
		for(int i = 1; i <= 100;i++) {
			user.setId(i);
			user.setNombre("usuario"+i);
			usuariosVirtuales.add(user);
		}
		return usuariosVirtuales;
	}*/


	private String setView(String treeView, String formView, String kanbanView) {
		if(treeView != null) {
			view = "treeView"; 
		}
		if(formView != null) {
			view = "formView"; 
		}
		if(kanbanView != null) {
			view = "kanbanView"; 
		}
		return view;
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Usuario usuario = null;
			String id = request.getParameter("id");
			String nombreUsuario = request.getParameter("nombreUsuario");
			String passwordUsuario = request.getParameter("passwordUsuario");
			//String rol = request.getParameter("rol");
			
			if(nombreUsuario != null && passwordUsuario != null) {
				usuario = new Usuario(nombreUsuario,passwordUsuario);
				//usuarios.add(usuario);
				usuariosJDBC.insert(usuario);
			}
			
/*			Usuario usuario = new Usuario();
			usuario.setId(Integer.parseInt(id));
			usuario.setNombre(nombreUsuario);
			usuario.setPass(passwordUsuario);*/
			//usuario.setRol(Integer.parseInt(rol));
			
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("usuario/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
