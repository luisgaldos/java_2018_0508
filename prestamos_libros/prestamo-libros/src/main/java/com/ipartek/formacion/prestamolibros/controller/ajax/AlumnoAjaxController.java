package com.ipartek.formacion.prestamolibros.controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.prestamolibros.pojo.Alumno;
import com.ipartek.formacion.prestamolibros.service.IService;
import com.ipartek.formacion.prestamolibros.service.ServicioAlumno;

/**
 * Servlet implementation class AlumnoAjaxController
 */
@WebServlet("/alumnoAjax")
public class AlumnoAjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IService<Alumno> servicioAlumno;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servicioAlumno = ServicioAlumno.getInstance();

	}

	@Override
	public void destroy() {
		super.destroy();
		servicioAlumno = null;

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Respondemos con formato JSON Y UTF-8
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// coger parametros
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		Alumno alumno = new Alumno(nombre, apellido);

		// llamar al servicio
		try {
			if (servicioAlumno.crear(alumno)) {
				response.setStatus(HttpServletResponse.SC_OK);
				
			}else {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// respuesta codigo http
		
		// respuesta del cuerpo
		PrintWriter out = response.getWriter();
		out.print("{" + "	\"id\": " + alumno.getId() + "," + "	\"nombre\": \"" + alumno.getNombre() + "\"," + "	\"apellidos\": \""
				+ alumno.getApellidos() + "\"" + "}");
	}

}