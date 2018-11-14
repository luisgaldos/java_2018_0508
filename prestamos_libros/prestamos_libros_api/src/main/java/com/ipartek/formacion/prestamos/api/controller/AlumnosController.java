package com.ipartek.formacion.prestamos.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.prestamos_libros.pojo.Alumno;
import com.ipartek.formacion.prestamos_libros.service.ServiceAlumno;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alumnos")
public class AlumnosController {
	
	ServiceAlumno serviceAlumno = null;
	ValidatorFactory factory = null;
	Validator validator = null;
	
	//Logger
	private final static Logger LOG = Logger.getLogger(PrestamosController.class);
	
	public AlumnosController() {
		super();
		LOG.trace("Constructor");
		serviceAlumno = ServiceAlumno.getInstance();
		LOG.trace("Servicio alumno instanciado");
		
		//Crear Factoria y Validador
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Alumno>> listado() {
		ResponseEntity<ArrayList<Alumno>> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
		
		try {
			alumnos = (ArrayList<Alumno>) serviceAlumno.listar();
			response = new ResponseEntity<>(alumnos, HttpStatus.OK);
			LOG.debug("Listado de alumnos devuelto "+alumnos.size());
		}catch(Exception e) {
//			e.printStackTrace();
			LOG.error(e);
		}
		
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Alumno> detalle(@PathVariable long id) {
		
		ResponseEntity<Alumno> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		try {
			Alumno alumno = serviceAlumno.buscarPorId(id);
			if(alumno != null && alumno.getId() > 0) {
				response = new ResponseEntity<>(alumno, HttpStatus.OK);
				LOG.debug("Detalle de alumno devuelto correctamente");
			}else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				LOG.warn("No se ha encontrado el alumno a mostrar");
			}
		}catch(Exception e) {
//			e.printStackTrace();
			LOG.error(e);
		}

		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> crear(@RequestBody Alumno alumno) {
		
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		try {
			
			Set<ConstraintViolation<Alumno>> violations = validator.validate(alumno);
			if (violations.isEmpty()) {
			    /* Ha pasado la validacion*/
				if(serviceAlumno.crear(alumno)) {
					response = new ResponseEntity<>(alumno, HttpStatus.CREATED);
					LOG.debug("Alumno creado correctamente");
				}else {
					response = new ResponseEntity<>(HttpStatus.CONFLICT);
					LOG.warn("No se ha podido crear el alumno");
				}
			}else{
			    /* Hay fallos, la Validacion no es correcta */
				ResponseMensaje msj = new ResponseMensaje();
				msj.setMensaje("Los datos no son válidos.");
				 for (ConstraintViolation<Alumno> violation : violations) {
					 msj.addError(violation.getPropertyPath()+": "+violation.getMessage());
					 LOG.warn("Errores de validacion al crear alumno: "+violation.getPropertyPath()+": "+violation.getMessage());
				 }
				response = new ResponseEntity<>(msj, HttpStatus.CONFLICT);
			}
			
			
		}catch(MySQLIntegrityConstraintViolationException e) {
//			e.printStackTrace();
			LOG.error(e);
			ResponseMensaje msj = new ResponseMensaje("Ya existe el alumno, por favor prueba con otro nombre.");
			response = new ResponseEntity<>(msj, HttpStatus.CONFLICT);
		}
		catch(Exception e) {
//			e.printStackTrace();
			LOG.error(e);
			ResponseMensaje msj = new ResponseMensaje("No existe el alumno.");
			response = new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
		}
		 
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> modificar(@PathVariable long id, @RequestBody Alumno alumno) {
		
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		try {
			Set<ConstraintViolation<Alumno>> violations = validator.validate(alumno);
			
			alumno.setId(id);
			
			if (violations.isEmpty()) {
			    /* Ha pasado la validacion*/
				if(serviceAlumno.modificar(alumno)) {
					response = new ResponseEntity<>(alumno, HttpStatus.OK);
					LOG.debug("Alumno modificado correctamente");
				}else {
					response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
					LOG.warn("No se ha encontrado el alumno a modificar");
				}
			}else{
			    /* Hay fallos, la Validacion no es correcta */
				ResponseMensaje msj = new ResponseMensaje();
				msj.setMensaje("Los datos no son válidos.");
				 for (ConstraintViolation<Alumno> violation : violations) {
					 msj.getErrores().add(violation.getPropertyPath()+": "+violation.getMessage());
					 LOG.warn("Errores de validacion al modificar alumno: "+violation.getPropertyPath()+": "+violation.getMessage());
				 }
				response = new ResponseEntity<>(msj, HttpStatus.CONFLICT);
			}
		}catch(MySQLIntegrityConstraintViolationException e) {
//			e.printStackTrace();
			LOG.error(e);
			ResponseMensaje msj = new ResponseMensaje("Ya existe el alumno, por favor prueba con otro nombre.");
			response = new ResponseEntity<>(msj, HttpStatus.CONFLICT);
		}catch(Exception e) {
//			e.printStackTrace();
			LOG.error(e);
			ResponseMensaje msj = new ResponseMensaje("No existe el alumno.");
			response = new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
		}
		 
		return response;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> eliminar(@PathVariable long id) {
		
		ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
		try {
			if(serviceAlumno.eliminar(id)) {
				response = new ResponseEntity<>(HttpStatus.OK);
				LOG.debug("Alumno eliminado correctamente");
			}else {
//				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				ResponseMensaje msj = new ResponseMensaje("No se puede borrar un registro que no existe");
				response = new ResponseEntity<>(msj, HttpStatus.NOT_FOUND);
				LOG.warn("No se ha encontrado el alumno a borrar");
			}
		}catch(MySQLIntegrityConstraintViolationException e) {
//			e.printStackTrace();
			LOG.error(e);
			ResponseMensaje msj = new ResponseMensaje("No se puede borrar el alumno porque tiene libros asociados.");
			response = new ResponseEntity<>(msj, HttpStatus.CONFLICT);
		}catch(Exception e) {
//			e.printStackTrace();
			LOG.error(e);
		}
		
		return response;
	}
	
}
