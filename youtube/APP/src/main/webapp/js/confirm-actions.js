/*	FORMULARIOS MODALES DE CONFIRMACION*/
function showModalForm(idVideo, codOp, codigoVideo) {
	
	var modalForm;
	var btnGo;

	if (codOp == 1) {
		
		modalForm = $('#modalEliminar');
		btnGo = $('#btnEliminarVideo');

	} else {
		
		modalForm = $('#modalModificar');
		btnGo = $('#btnModificarVideo');
	}
	
	modalForm.modal('show');
	btnGo.attr("href", "inicio?id=" + idVideo + "&op=" + codOp);
	$('#idModificar').val(idVideo);
	$('#nuevoCod').val(codigoVideo);
	console.log(idVideo);
}