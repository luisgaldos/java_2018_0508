<%@page
	import="com.ipartek.formacion.prestamolibros.controller.CrudControllable"%>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav.jsp"%>

<div id="page-wrapper">

	<%@include file="../includes/alert.jsp"%>

	<div class="row">
		<form action="editoriales" method="post">

			<div class="form-group">
				<label for="id">ID:</label> <input type="text" class="form-control"
					id="id" name="id" value="${editorial.id }" readonly />
			</div>

			<div class="form-group">
				<label for="nombre">Nombre:</label> <input type="text"
					class="form-control" id="nombre" name="nombre"
					value="${editorial.editorial }" required autofocus />
			</div>

			<input type="hidden" name="op"
				value="<%=CrudControllable.OP_GUARDAR%>" /> <input type="submit"
				value="${(editorial.id == -1)? 'Crear' : 'Modificar' }"
				class="btn ${(editorial.id == -1)? 'btnCrear' : 'btn-modificar' } btn-block" />

			<c:if test="${editorial.id >= 0}">
				<a
					href="editoriales?id=${editorial.id}&op=<%=CrudControllable.OP_ELIMINAR %>" onclick="confirmar(event)"
					class="btn btn-finalizar btn-block">Eliminar</a>
			</c:if>

		</form>

	</div>

</div>
<%@include file="../includes/footer.jsp"%>