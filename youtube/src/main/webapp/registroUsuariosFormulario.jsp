<%@page import="com.ipartek.formacion.pojo.Usuario"%>
<%@page import="com.ipartek.formacion.pojo.Alerts"%>
<%@page import="com.ipartek.formacion.pojo.Video"%>
<%@page import="com.ipartek.formacion.youtube.controller.RegistroUsuarioController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<html lang="en">

  <head>

	<!-- Comenza todas las URLs desde el href indicado -->
	<base href="<%=request.getContextPath()%>/">
	
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Youtube Video Play List</title>

	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css" integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ" crossorigin="anonymous">
	
    <!-- Bootstrap core CSS -->
    <link href="https://blackrockdigital.github.io/startbootstrap-shop-item/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="https://blackrockdigital.github.io/startbootstrap-shop-item/css/shop-item.css" rel="stylesheet">
	<!-- Fontawasome -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<!-- Estilos propios pero no termina de cogerlos -->
	<link rel="stylesheet" href="css/formularioREgistroUsuarios.css" >
	

  </head>

  <body>
  <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">Youtube PlayList</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
            
            <% 
            	//Gestion Usuario Logeado   
            	Usuario usuario = (Usuario)session.getAttribute("usuario");
            	if ( usuario == null ){            
            %>	            
              <!-- formulario Login -->
              <form action="login" method="post" class="form-inline mt-2 mt-md-0">
	            <input id="usuario" name="usuario" class="form-control mr-sm-2" type="text" placeholder="Nombre Usuario" required pattern=".{3,30}">
	            <input name="pass" class="form-control mr-sm-2" type="password" placeholder="Contraseña" required pattern=".{2,50}">
	            <button class="btn btn-outline-info my-2 my-sm-0" type="submit">Entrar</button>
	          </form>            
            <%
            	} else {
            %>              
              <!-- formulario Crear Video -->
              
              <form action="" method="post" class="form-inline mt-2 mt-md-0">
	            <input name="id" class="form-control mr-sm-2" type="text" placeholder="ID 11 caracerteres" title="11 caracteres" required pattern=".{11,11}">
	            <input name="nombre" class="form-control mr-sm-2" type="text" placeholder="Nombre minimo 2 letras" required pattern=".{2,125}">
	            <button class="btn btn-outline-info my-2 my-sm-0" type="submit">Añadir</button>
	           
	          </form>	  
				     
	          
	          
            </li>      
            <li>
            	 <i class="fas fa-user" style="color:red; margin-left:5px;"> <%=usuario.getNombre() %> </i>
	            <a href="logout">Cerrar Sesion</a>
	             <a href="backoffice/index.jsp">Backoffice</a>
            </li> 
            <%
            	} 
              %>       
          </ul>
          
          
          
        </div>
      </div>
    </nav>
  

    <!-- Page Content -->
    <div class="container"><!-- align-self-center -->
	    <div class="formularioAlta row" >
	    <div class="col " >
			<form action="RegistroUsuarioControler" method="post">
				  <div class="form-row">
				  <div class="form-group col-md-6">
				      <label for="nombreUsuario">Usuario</label>
				      <input type="text" class="form-control" name="nombreUsuario" autofocus required="required" placeholder="% caracteres minimo" pattern="[A-Za-z]{5,45}">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="emailUsuario">Email</label>
				      <input type="email" class="form-control" name="emailUsuario" required="required" placeholder="ejemplo@correo.com" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{1,45}">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="passUsuario">Password</label>
				      <input type="password" class="form-control" required="required" name="passUsuario" placeholder="*******">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="replyPassUsuario">Repita el password</label>
				      <input type="password" class="form-control" required="required" name="replyPassUsuario" placeholder="*******">
				    </div>
				  </div>
				  <div class="center-button">
				  	<button  type="submit" class="btn btn-primary">Date de alta</button>
				  </div>
			</form>
			</div>
	    </div>
	    
    </div>
    <!-- /.container -->

    <!-- Footer -->
    <footer class="py-5 bg-dark">
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2017</p>
      </div>
      <!-- /.container -->
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="https://blackrockdigital.github.io/startbootstrap-shop-item/vendor/jquery/jquery.min.js"></script>
    <script src="https://blackrockdigital.github.io/startbootstrap-shop-item/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  </body>

</html>