package com.ipartek.formacion.controller.back;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.youtube.model.RolDAO;
import com.ipartek.formacion.youtube.model.UsuarioDAO;
import com.ipartek.formacion.youtube.pojo.Usuario;
/**
 * Servlet implementation class BackofficeUsuarioController
 */
@WebServlet("/backoffice/usuarios")
public class BackofficeUsuarioController extends HttpServlet implements CrudControllable{
	private static final long serialVersionUID = 1L;
	
	private static UsuarioDAO daoUsuario = null;
	private static RolDAO daoRol = null;
	
	private static final String VIEW_FORM_USUARIOS = "usuarios/form.jsp";
	private static final String VIEW_INDEX_USUARIOS = "usuarios/index.jsp";
	
	private String view = "";
	private Alert alert = null;
	
//	ArrayList<Usuario> usuarios = null;
	
	//Parametros
	private String op; //Operacion a realizar
	private String id; //Id del usuario
	private String nombre; //Nombre del usuario
	private String contrasena; //Contraseña del usuario
	private String rol; //Rol del usuario
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		daoUsuario = UsuarioDAO.getInstance();
		daoRol = RolDAO.getInstance();
	}
	
	@Override
	public void destroy() {
		super.destroy();
		daoUsuario = null;
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

	@Override
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			alert=null;
			
			//Recoge los parametros de la request y los guarda en variables
			getParameters(request);
			
			//Dependiendo del valor del parametro op hace una funcion u otra
			switch (op) {
				case OP_ELIMINAR:
					eliminar(request); //Elimina un usuario de la bbdd
					break;
					
				case OP_GUARDAR:
					guardar(request); //Crea o modifica un usuario en la bbdd
					break;
					
				case OP_IR_FORMULARIO:
					irFormulario(request); //Cambia a la vista del formulario de gestion de usuario
					break;
	
				default: //Listar
					listar(request); //Cambia a la vista de listado de usuarios
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			view = VIEW_INDEX_USUARIOS;
			alert = new Alert(Alert.ALERT_DANGER, "Ha ocurrido un error no controlado.");
		}finally {
			request.setAttribute("alert", alert);
			request.getRequestDispatcher(view).forward(request, response);
		}
	}
	
	@Override
	public void getParameters(HttpServletRequest request) {
		op = (request.getParameter("op") != null) ? request.getParameter("op"): OP_LISTAR; 
		id = request.getParameter("id"); // 0 = crear, 1 = modificar
		nombre = request.getParameter("nombre");
		contrasena = request.getParameter("contrasena");
		rol = request.getParameter("rol");
	}
	
	@Override
	public void listar(HttpServletRequest request){
		view = VIEW_INDEX_USUARIOS;
		try {
			request.setAttribute("usuarios", daoUsuario.getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void irFormulario(HttpServletRequest request) throws Exception {	
		Usuario usuario = null;
		if(Integer.parseInt(id)>0) {
			usuario = daoUsuario.getById(id);
		}else {
			usuario = new Usuario();
		}
		request.setAttribute("usuario", usuario);
		request.setAttribute("roles", daoRol.getAll());
		view = VIEW_FORM_USUARIOS;
	}
	
	@Override
	public void guardar(HttpServletRequest request){
		Usuario usuario = null;
		try {
//		if(nombre != null && contrasena != null) {
//			if(nombre.length() <= 50) {
//				if(contrasena.length() <= 20) {
					
					usuario = new Usuario();
					usuario.setNombre(nombre);
					usuario.setContrasena(contrasena);
					
					usuario.setRol(daoRol.getById(rol));
					
						if(id.equals("")) {
							//Crear Usuario nuevo
							daoUsuario.insert(usuario);
//								if(daoUsuario.insert(usuario)) {
//									alert = new Alert(Alert.ALERT_SUCCESS, "Usuario creado con éxito.");
//								}else {
//									alert = new Alert(Alert.ALERT_DANGER, "No se ha podido crear el usuario.");
//								}
						}else{
							//Modificar Usuario existente
							usuario.setId(Long.parseLong(id));
							daoUsuario.update(usuario);
//								usuario.setId(Long.parseLong(id));
//								if(daoUsuario.update(usuario)) {
//									alert = new Alert(Alert.ALERT_SUCCESS, "Usuario modificado con éxito.");
//								}else {
//									alert = new Alert(Alert.ALERT_DANGER, "No se ha podido modificar el usuario.");
//								}
						}
						alert = new Alert(Alert.ALERT_SUCCESS, "Usuario guardado con éxito.");
						request.setAttribute("roles", daoRol.getAll());					
					}
					//Nombre repetido
					catch(SQLIntegrityConstraintViolationException e) {
						e.printStackTrace();
						alert = new Alert(Alert.ALERT_WARNING, "El nombre de usuario <b>" + usuario.getNombre() + "</b> ya existe.");
					}
					//Longitud campos nombre y pswd
					catch(SQLException e) {
						e.printStackTrace();
						if(e.getMessage().contains("nombre")) {
							alert = new Alert(Alert.ALERT_WARNING, "El nombre de usuario debe tener máx. 50 caracteres.");
						}else if(e.getMessage().contains("password")) {
							alert = new Alert(Alert.ALERT_WARNING, "La contraseña debe tener máx. 20 caracteres.");
						}
						
						
					}
					catch(Exception e){
						e.printStackTrace();
//						alert = new Alert(Alert.ALERT_DANGER, "No se ha podido crear el usuario.");
					}
//				}else {
//					alert = new Alert(Alert.ALERT_WARNING, "La contraseña no puede tener más de 20 caracteres.");
//				}
//			}else {
//				alert = new Alert(Alert.ALERT_WARNING, "El nombre de usuario no puede tener más de 50 caracteres.");
//			}
//		}else {
//			alert = new Alert(Alert.ALERT_DANGER, "Debe rellenar todos los campos.");
//		}
	
		request.setAttribute("usuario", usuario);
		view = VIEW_FORM_USUARIOS;
	}
	
	@Override
	public void eliminar(HttpServletRequest request) throws Exception {
		try {
			daoUsuario.delete(id);
			alert = new Alert(Alert.ALERT_SUCCESS, "Usuario borrado con éxito.");
		}catch(Exception e) {
			e.printStackTrace();
			alert = new Alert(Alert.ALERT_WARNING, "No se puede borrar el usuario porque tiene vídeos creados.");
		}
//		if(daoUsuario.delete(id)) {
//			alert = new Alert(Alert.ALERT_SUCCESS, "Usuario borrado con éxito.");
//		}else {
//			alert = new Alert(Alert.ALERT_DANGER, "No se ha podido borrar el usuario.");
//		}
		view = VIEW_INDEX_USUARIOS;
		request.setAttribute("usuarios", daoUsuario.getAll());
	}
}