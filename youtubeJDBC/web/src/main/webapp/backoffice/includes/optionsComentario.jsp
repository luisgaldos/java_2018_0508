
<div class="options">
	<c:if test="${view != 'form'}">
		<a href="<%=request.getContextPath()%>/backoffice/comentario?vista=form-comentario"><i class="fab fa-wpforms fa-3x"></i></a>
	</c:if>
	<c:if test="${view != 'tree'}">
		<a href="<%=request.getContextPath()%>/backoffice/comentario?vista=lista-comentario"><i class="fas fa-th-list fa-3x"></i></a>
	</c:if>
	<c:if test="${view != 'kanban'}">
		<a href="<%=request.getContextPath()%>/backoffice/comentario?vista=kanban-comentario"><i class="fas fa-grip-vertical fa-3x"></i></a>
	</c:if>
</div> 