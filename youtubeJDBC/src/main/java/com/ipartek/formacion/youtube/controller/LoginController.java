package com.ipartek.formacion.youtube.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.youtube.controller.pojo.Alert;
import com.ipartek.formacion.youtube.model.UsuariosDaoJDBC;
import com.ipartek.formacion.youtube.pojo.Rol;
import com.ipartek.formacion.youtube.pojo.Usuario;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UsuariosDaoJDBC usuariosJDBC;
	private ArrayList<Usuario> usuarios;
	private boolean accessSignal = false;
	private Alert alerta;
	private String msg;
	
	private static final String VIEW_INICIO_ADMIN="/backoffice/inicio";
	private static final String VIEW_INICIO_USER="/perfil/inicio";
	private static final String VIEW_FAIL_USER="/inicio";
	
    
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
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Alert alert = new Alert();
		HttpSession session = request.getSession();
		Usuario usuario = new Usuario();
		String view =VIEW_INICIO_USER;
		
		
		
		try {
			
			//idiomas @see com.ipartek.formacion.youtube.filter.IdiomaFilter
			Locale localeTest = request.getLocale();			
			String localeTestString = localeTest.toString();
			String idioma = (String)session.getAttribute("idioma");	
			String[] parts = localeTestString.split("_");
			Locale locale = new Locale(parts[0],parts[1]);
			ResourceBundle idiomas = ResourceBundle.getBundle("idiomas", locale );
			
			Cookie coockies[]  = request.getCookies();
			
			String recuerdame = request.getParameter("recuerdame");
						
			
			//recoger parametros
			String usuarioNombre = request.getParameter("usuario");
			String pass = request.getParameter("pass");
			usuario = usuariosJDBC.checkByNamePass(usuarioNombre, pass);
			//boolean test = usuariosJDBC.checkByName(usuarioNombre);
			if(usuario != null) {
				//creamos cookies
				if(recuerdame != null) {
					Cookie cUsuario= new Cookie("cUsuario",usuarioNombre) ;
					
					Cookie uRecuerdame= new Cookie("uRecuerdame","on") ;
					response.addCookie(uRecuerdame);
					response.addCookie(cUsuario);
					//ur es el nombre a recordar
					if( recuerdame != "") {
						request.setAttribute("checked", "checked");
						request.setAttribute("ur", usuario.getNombre());
					}else {
						request.setAttribute("checked", "");
						request.setAttribute("ur", "");
					}

				}else {
					Cookie cUsuario= new Cookie("cUsuario","") ;
					Cookie uRecuerdame= new Cookie("uRecuerdame","off") ;
					response.addCookie(uRecuerdame);
					response.addCookie(cUsuario);
					session.setAttribute("recuerdame", recuerdame);
				}
				//guardamos session
				session.setAttribute("usuario", usuario);
				session.setMaxInactiveInterval(60 * 60 * 60); // 1 hora
				alert.setTexto("Bienvenido " + usuario.getNombre());
				alert.setTipo(alert.SUCCESS);
				if (usuario.getRol().getId() == Rol.ROL_ADMIN) {
					view = VIEW_INICIO_ADMIN;
				}else {
					//view = VIEW_INICIO_USER;
					view = VIEW_INICIO_USER;
				}
			}else {
				alert.setTexto("Intentelo de nuevo, el usuario o contraseña son incorrectas ");
				alert.setTipo(alert.DANGER);
				//request.getRequestDispatcher(VIEW_FAIL_USER).forward(request, response);
				
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			session.setAttribute("alert", alert);
			if("alert-danger".contentEquals(alert.getTipo())) {
				response.sendRedirect(request.getContextPath() + VIEW_FAIL_USER ); 
			}else {
				response.sendRedirect(request.getContextPath() + view ); 

			}
		}
		
		
	}


	private void gestionarCookies(HttpServletRequest request, HttpServletResponse response, Usuario u) {
		
		String recordar = (String)request.getParameter("recuerdame");
		Cookie cNombre = new Cookie("cNombre", u.getNombre());
				
		if ( recordar != null) {
			
			cNombre.setMaxAge(60*60*24*30*3); // 3meses
			
		}else {
			cNombre.setMaxAge(0); // No guardar
		}
		
		response.addCookie(cNombre);
		
		
	}

}
