package com.ipartek.formacion.prestamos.api.controller;

import java.sql.Date;
import java.util.ArrayList;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.prestamos_libros.pojo.Libro;
import com.ipartek.formacion.prestamos_libros.pojo.Prestamo;
import com.ipartek.formacion.prestamos_libros.pojo.Usuario;
import com.ipartek.formacion.prestamos_libros.service.ServicePrestamo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@Api(tags= {"Prestamos"}, produces="application/json", description="Gestion Prestamos")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/prestamos")
public class PrestamosController {
	private final static Logger LOG = Logger.getLogger(PrestamosController.class);

	ServicePrestamo servicePrestamo = null;
	ValidatorFactory factory = null;
	Validator validator = null;


	public PrestamosController() {
		super();
		LOG.trace("constructor");
		servicePrestamo = ServicePrestamo.getInstance();
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		LOG.trace("Serivicios prestamos instanciados");
		
	}
	@ApiOperation(value = "Listado Prestados Activos o historico")
	@ApiResponses( value = {
			@ApiResponse (code = 200, message = "Listado Prestamos"),
			@ApiResponse (code = 404, message = "No se encontro prestamo")}
	)
	
	@ApiParam( value="activo", required = false, defaultValue = "1")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Prestamo>> listado(
			@ApiParam(value = "No obligatorio <br> 1) true: Prestamos sin retornar <br> 2) false: Prestamos Retornados")
			@RequestParam(value = "activo", required = false, defaultValue = "true") boolean estado) {

		ArrayList<Prestamo> list = new ArrayList<Prestamo>();
		ResponseEntity<ArrayList<Prestamo>> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		try {
			if(estado) {
				list = (ArrayList<Prestamo>) servicePrestamo.listar();
				LOG.debug("prestamos recuperados" + list.size());
			}else {
				list = (ArrayList<Prestamo>) servicePrestamo.listardevueltos();
			}
			
			response = new ResponseEntity<>(list, HttpStatus.OK);

		} catch (Exception e) {
			LOG.error(e);
		}

		return response;
	}
	
	@ApiOperation(value = "Prestar un Libro a un Usuario para una fecha concreta", response = Prestamo.class, notes = "Campos Obligatorios: <ol><li><b>fech_inicio</b> Fecha inicio</li><li><b>libro.id</b> Identificador del Libro </li><li><b>usuario.id</b> Identificador del Usuario </li></ol>")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Prestamos creado", response = Prestamo.class),
			@ApiResponse(code = 400, message = "Faltan campos obligatorios", response = ResponseMensaje.class),
			@ApiResponse(code = 409, message = "<ol><li>No existe le Libro o Usuario</li><li>Libro o Usuario ya tienen un prestamo activo</li></ol>", response = ResponseMensaje.class), })

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> crear(@RequestBody Prestamo prestamo) {
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		try {

			if (servicePrestamo.crear(prestamo)) {
				response = new ResponseEntity<Object>(prestamo, HttpStatus.CREATED);
			} else {
				response = new ResponseEntity<Object>(prestamo, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {

			String message = e.getMessage();
			ResponseMensaje responseMsg = null;
			
			if (message.equals(ServicePrestamo.EXCEPTION_LIBRO_PRESTADO)
					|| message.equals(ServicePrestamo.EXCEPTION_USUARIO_PRESTADO)) {

				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.CONFLICT);

			}else {
				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.BAD_REQUEST);
			}

			LOG.debug(e);
		}

		return response;
	}
	
	@ApiOperation(value = "Prestamo Devuelto", response = Prestamo.class, notes = "Campos Obligatorios: <ol><li><b>fech_inicio</b> Fecha inicio</li><li><b>libro.id</b> Identificador del Libro </li><li><b>usuario.id</b> Identificador del Usuario </li><li><b>fecha_devolucion</b> Fecha Devolucion </li><li</ol>" )
	@ApiResponses( value = {
			@ApiResponse (code = 200, message = "Prestamo Devuelto", response = Prestamo.class),
			@ApiResponse(code = 400, message = "Faltan campos obligatorios", response = ResponseMensaje.class),
			@ApiResponse (code = 409, message = "<o><li>No existe le Libro o Usuario</li><li>Usuario no tiene prestamo activo</li></o>", response = ResponseMensaje.class)}
	)
	
	@RequestMapping(value = "/{idUsuario}/{idLibro}/{finicio}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> devolver(@PathVariable long idUsuario, @PathVariable long idLibro, @PathVariable Date finicio, @RequestBody Prestamo prestamo){
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		try {
			
			prestamo.setUsuario(new Usuario(idUsuario,""));
			prestamo.setLibro(new Libro(idLibro, "", "",null));
			prestamo.setFecha_inicio(finicio);
			
			
			boolean devuelto = servicePrestamo.devolver(prestamo);
			if(devuelto) {
				response = new ResponseEntity<Object>(prestamo, HttpStatus.OK);

			}else {
				response = new ResponseEntity<Object>(prestamo, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			
			String message = e.getMessage();
			ResponseMensaje responseMsg = null;
			
			if (message.equals(ServicePrestamo. EXCEPTION_LIBRO_DEVUELTO )
					|| message.equals(ServicePrestamo.EXCEPTION_USUARIO_DEVUELTO)) {

				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.CONFLICT);

			}else {
				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.BAD_REQUEST);
			}

			LOG.debug(e);
		}

		return response;
	}
	
	@ApiOperation(value = "Prestamo Modificado", response = Prestamo.class, notes = "Campos Obligatorios - Prestamo antiguo: <ol><li><b>fech_inicio</b> Fecha inicio</li><li><b>libro.id</b> Identificador del Libro </li><li><b>usuario.id</b> Identificador del Usuario </li></ol>" )
	@ApiResponses( value = {
			@ApiResponse (code = 201, message = "Prestamo Modificado", response = Prestamo.class),
			@ApiResponse(code = 400, message = "Faltan campos obligatorios", response = ResponseMensaje.class),
			@ApiResponse (code = 409, message = "<o><li>No existe le Libro o Usuario</li><li>Datos no validos</li></o>")}
	)
	
	@RequestMapping(value = "/{idUsuario}/{idLibro}/{finicio}", method = RequestMethod.PUT)
	public ResponseEntity<Object> modificar(@PathVariable long idUsuario, @PathVariable long idLibro, @PathVariable Date finicio, @RequestBody Prestamo prestamoNuevo){
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		
		
		try {
			Prestamo p = new Prestamo();
			Libro l = new Libro();
			Usuario u = new Usuario();
			l.setId(idLibro);
			p.setLibro(l);
			u.setId(idUsuario);
			p.setUsuario(u);
			p.setFecha_inicio(finicio);

			boolean modificado = servicePrestamo.modificarHistorico(prestamoNuevo, p);
			if (modificado) {
				response = new ResponseEntity<Object>(prestamoNuevo, HttpStatus.CREATED);
		
			} else {
				response = new ResponseEntity<Object>(prestamoNuevo, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			String message = e.getMessage();
			ResponseMensaje responseMsg = null;
			
			if (message.equals(ServicePrestamo.EXCEPTION_LIBRO_DEVUELTO)
					|| message.equals(ServicePrestamo.EXCEPTION_USUARIO_DEVUELTO)) {
 				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.CONFLICT);
 			}else {
				responseMsg = new ResponseMensaje(message);
				response = new ResponseEntity<Object>(responseMsg,HttpStatus.BAD_REQUEST);
			}
			
			LOG.error(e);
		}
		
		return response;
	}
}