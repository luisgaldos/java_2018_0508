<%@page
	import="com.ipartek.formacion.prestamolibros.controller.CrudControllable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav.jsp"%>

<div id="page-wrapper">
	<%@include file="../includes/alert.jsp"%>
	
	<div class="row divTitulo">
        <div class="col-md-8">
            <h1 class="page-header titulo">Alumnos <span class="badge">${fn:length(alumnos)}</span></h1>
        </div>
            
    
		<div class="col-md-4 btn-crear">
  			<a href="alumnos?id=-1&op=<%=CrudControllable.OP_IR_FORMULARIO%>" class="btn btn-primary">Crear nuevo alumno</a>  			
  		</div>
	</div>

	<table id="tablaOrdenable" class="display">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellidos</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach items="${alumnos}" var="a">

				<tr>
					<td>${a.id }</td>
					<td><a href="alumnos?id=${a.id }&op=<%=CrudControllable.OP_IR_FORMULARIO %>">${a.nombre }</a></td>
					<td>${a.apellidos }</td>
				</tr>

			</c:forEach>

		</tbody>
		<tfoot>
			<tr>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellidos</th>
			</tr>
		</tfoot>
	</table>


</div>
<%@include file="../includes/footer.jsp"%>