window.addEventListener('load', inicio, false);

function inicio() {
	genera_tabla();
}


function cambiarBrillo() {

	var tabla = document.getElementById("tablaCorreos");
	var barra = document.getElementById("barraNavegacion");
	if (tabla.className === 'table table-hover') {
		barra.className = 'navbar navbar-expand-sm navbar-dark bg-dark justify-content-between';
		tabla.className = 'table table-hover table-dark';
	} else {
		barra.className = 'navbar navbar-expand-sm navbar-light bg-light justify-content-between';
		tabla.className = 'table table-hover';
	}

}

function Actualizar(){
	
	localStorage.setItem("actualizar", "true");
	var myWindow = window.open("../Html/VisualizarCorreo.html", "_self");
	
}

function genera_tabla() {

	var body = document.getElementsByTagName("body")[0];
	var div = document.getElementById("principal");
	var tabla = document.getElementById("tablaCorreos");
	var tblBody = document.getElementById("contenidoTabla");

	//var numCorreos = window.numCorreos[1];
	//alert(numCorreos);
	//ACCEDEMOS AL NUMERO DE CORREOS EL CUAL ESTA ALMACENADO EN LOCAL STORAGE
	var tamano = localStorage.numCorreos;
	var aFrom = localStorage.from;
	var aSubject = localStorage.subject;
	var aFecha = localStorage.fecha;
	var aCuerpo = localStorage.cuerpo;

	var findComas = ',';
	var findCorchetes = "\\[";
	var re = new RegExp(findComas, 'g');// el parametro g indica que se deben de remplazar todas las coincidencias
	var re2 = new RegExp(findCorchetes, 'g');

	var aFromClear = aFrom.replace(re, '');
	var aFromClear2 = aFromClear.replace(re2, '');

	var aSubjectClear = aSubject.replace(re, '');
	var aSubjectClear2 = aSubjectClear.replace(re2, '');

	var aFechaClear = aFecha.replace(re, '');
	var aFechaClear2 = aFechaClear.replace(re2, '');

	var aCuerpoClear1 = aCuerpo.replace(re, '');
	var aCuerpoClear = aCuerpoClear1.replace(re2, '');

	var splitFrom = aFromClear2.split("||");
	var splitSubject = aSubjectClear2.split("||");
	var splitFecha = aFechaClear2.split("||");
	var splitCuerpos = aCuerpoClear.split("||");

	var h = 0;


	// Crea las celdas (Tantas celdas como archivos contenga el servidor) 
	for (var i = 0; i < tamano; i++) {
		// Crea las hileras de la tabla
		var correos = document.createElement("tr");
		correos.setAttribute("id", "" + i);

		correos.onclick = function(){

		var cuerpos = localStorage.cuerpo;

		localStorage.setItem("remitenteSeleccionado", splitFrom[this.id]);
		localStorage.setItem("asuntoSeleccionado", splitSubject[this.id]);
		localStorage.setItem("cuerpoSeleccionado", splitCuerpos[this.id]);

		var win = window.open("../Html/visualizarMensajeConcreto.html");

		}


		for (var x = 0; x < 3; x++) {
			//Crear elemento <td> una celda de la tabla
			var celda = document.createElement("td");
			celda.setAttribute("name", "td" + x);
			//HACER UN FOR K NOS PERMITA RECORRER NUESTRO ARRAY DE TITULOS DE MENSAJES PARA PODER MANIPULAR LOS INDICES EN LOS CREATE TEXT NODE

			if (x == 0) {
				//Crear contenido de la tabla
				var idCorreo = document.createTextNode(splitFrom[h]);
				//Insertar el contenido en el elemento <td>
				celda.appendChild(idCorreo);
			}
			else if (x == 1) {
				//Crear contenido de la tabla
				var asuntoCorreo = document.createTextNode(splitSubject[h]);
				//Insertar el contenido en el elemento <td>
				celda.appendChild(asuntoCorreo);


			} else if (x == 2) {
				//Crear contenido de la tabla
				var fechaCorreo = document.createTextNode(splitFecha[h]);
				//Insertar el contenido en el elemento <td>
				celda.appendChild(fechaCorreo);

				h++;
			}
			//Insertar el elemento <td> en el elemento <tr>
			correos.appendChild(celda);
		}

		// Agrega la fila a la tabla
		tblBody.appendChild(correos);
	}
	// posiciona el <tbody> debajo del elemento <table>
	tabla.appendChild(tblBody);
	//añadir tabla al div
	div.appendChild(tabla);
	// appends <div> into <body>
	body.appendChild(div);

	//LIMPIAMOS EL WEB STORAGE/LOCAL STORAGE

	//aplicarEventosCorreos(arrayCorreos, tamano);


	//localStorage.clear();

}//final llenar tabla


function buscar() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("cajaBuscar");
	filter = input.value.toUpperCase();
	table = document.getElementById("contenidoTabla");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
