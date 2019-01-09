window.addEventListener('load', inicio, false);


function inicio() {
	genera_tabla();
}


function cambiarBrillo() {
	
	var tabla = document.getElementById("tablaCorreos");
	var barra = document.getElementById("barraNavegacion");
	if(tabla.className === 'table table-hover'){
		barra.className = 'navbar navbar-expand-sm navbar-dark bg-dark justify-content-between';
		tabla.className = 'table table-hover table-dark';
	} else {
		barra.className = 'navbar navbar-expand-sm navbar-light bg-light justify-content-between';
		tabla.className = 'table table-hover';
	}
	
}

function genera_tabla() {
	// Obtener la referencia del elemento body


	//alert('entro en la function genera_tabla JS');

	var body = document.getElementsByTagName("body")[0];
	var div = document.getElementById("principal");
	var tabla = document.getElementById("tablaCorreos");
	var tblBody = document.getElementById("contenidoTabla");

	// Crea las celdas (Tantas celdas como archivos contenga el servidor) 
	for (var i = 0; i < numCorreos.cantidad; i++) {
		// Crea las hileras de la tabla
		var correos = document.createElement("tr");
		correos.onclick = function () {


		};


		for (var x = 0; x < 3; x++) {

			//Crear elemento <td>
			var celda = document.createElement("td");
			celda.setAttribute("name", "td"+x);
			if (x == 0) {
				//Crear contenido de la tabla
				var idCorreo = document.createTextNode("" + (i + 1));
				//Insertar el contenido en el elemento <td>
				celda.appendChild(idCorreo);
			}
			if (x == 1) {
				//Crear contenido de la tabla
				var asuntoCorreo = document.createTextNode("Asunto del correo.");
				//Insertar el contenido en el elemento <td>
				celda.appendChild(asuntoCorreo);
			}
			if (x == 2) {
				//Crear contenido de la tabla
				var fechaCorreo = document.createTextNode("" + generar_fecha());
				//Insertar el contenido en el elemento <td>
				celda.appendChild(fechaCorreo);
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
}

function generar_fecha() {
	var meses = new Array("Ene.", "Feb.", "Mar.", "Abr.", "May.", "Jun.", "Jul.", "Ago.", "Sep.", "Oct.", "Nov.", "Dic.");
	var f = new Date();
	var fecha = f.getDate() + " " + meses[f.getMonth()];
	return fecha;
}
