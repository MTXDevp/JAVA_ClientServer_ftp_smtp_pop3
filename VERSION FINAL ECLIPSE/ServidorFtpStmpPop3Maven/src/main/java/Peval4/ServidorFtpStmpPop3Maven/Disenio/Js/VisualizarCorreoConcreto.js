window.addEventListener('load', inicio, false);

function inicio() {
	GetDatos();
}

function GetDatos(){

	var remitente = localStorage.getItem("remitenteSeleccionado");
	var asunto = localStorage.getItem("asuntoSeleccionado");
	var cuerpo = localStorage.getItem("cuerpoSeleccionado");

	document.getElementById("remitente").value = remitente;
	document.getElementById("asunto").value = asunto;
	document.getElementById("contenidoCorreo").value = cuerpo;


}