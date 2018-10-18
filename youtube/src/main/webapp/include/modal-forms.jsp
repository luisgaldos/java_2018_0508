<!-- SET JAVA LANGUAGE AND UTF-8 CODIFICATION -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${ empty sessionScope.usuario }">
	                       	   	
	<!-- FORMULARIO DE LOGIN -->
	<p class="h3 text-center">Acceder</p>
	<form class="form" role="form" action="login" method="post">
	                                
		<div class="form-group">
	    	<input name="usuario" value="${ cookie.cNombre.value }" id="usuario" placeholder="Usuario" class="form-control form-control-sm" type="text" required >
	    </div>
	                                
	    <div class="form-group">
	  		<input name="pass" id="password" placeholder="Contraseña" class="form-control form-control-sm" type="password" required>
	    </div>
	                                
	    <div class="form-group ml-3">
			<input type="checkbox" name="recuerdame" ${ (not empty cookie.cNombre.value) ? "checked" : ""}>
			<label class="mr-2">Recordar Usuario</label>
		</div>
	                                
	     <div class="form-group">
	     	<button type="submit" class="btn btn-primary btn-block">Acceder</button>
	     </div>
	     
	     <div class="form-group text-center">
	        <small><a href="#" data-toggle="modal" data-target="#modalPassword">¿Olvidaste tu contraseña?</a></small>
	     </div>
	 </form>
	 
	 <!-- RECUPERAR CONTRASEÑA -->
	<div id="modalPassword" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h3>Forgot password</h3>
	                <button type="button" class="close font-weight-light" data-dismiss="modal" aria-hidden="true">×</button>
	            </div>
	            <div class="modal-body">
	                <p>Reset your password..</p>
	            </div>
	            <div class="modal-footer">
	                <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	                <button class="btn btn-primary">Save changes</button>
	            </div>
	        </div>
	    </div>
	</div>
</c:if>
                            
<c:if test="${ not empty sessionScope.usuario }">
                            	
	<!-- FORMULARIO DE INSERTAR VIDEO -->
    <p class="h3 text-center">Insertar video</p>
	<form class="form" role="form" action="inicio" method="post">
	                           		
		<div class="form-group">
	    	<input name="cod" class="form-control mr-sm-2" type="text" placeholder="Código (11 caracteres)" required pattern=".{11,11}">
	   </div>
	                           		
	   <div class="form-group">
	   		<input name="nombre" class="form-control mr-sm-2" type="text" placeholder="Título (2 a 150 caracteres)" required pattern=".{2,150}">
	   </div>
	                           		
	   <div class="form-group">
	   		<button type="submit" class="btn btn-primary btn-block">Insertar</button>
	   </div>
	</form>
</c:if>