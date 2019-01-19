window.addEventListener('load', inicio, false);

function inicio() {}

function Subir() {
	var body = document.getElementsByTagName("body")[0];
	var opcion = confirm("¿Subir Archivo?");
	if (opcion == true) {
		SentDatosFTP();
		var divBarra = document.getElementById("segundario");
		divBarra.innerHTML = document.getElementById("barraDescarga").innerHTML;
		body.appendChild(divBarra);
	} else {
		var divBarra = document.getElementById("segundario");
		divBarra.innerHTML = " ";
		body.appendChild(divBarra);
	}
}

function SentDatosFTP() {
	var direccionFTP = formFTP.elements['direccionFTP'].value;
	Account.save(direccionFTP);
}
