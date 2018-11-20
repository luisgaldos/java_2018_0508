<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:if test="${not empty alert}">
	<div class="alert ${alert.tipo} alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<p>${alert.texto}</p>
	</div>
</c:if>
${sessionScope.alert=null}