window.addEventListener('load', inicio, false);

function inicio() {
    genera_tabla();
    genera_btnSalir();
}

function genera_btnSalir() {
    // Obtener la referencia del elemento body
    var body = document.getElementsByTagName("body")[0];
    var div = document.getElementById("principal");
    // Crea un elemento <table>, un elemento <thead> y un elemento <tbody>
    var boton = document.createElement("button");
    //Crear texto del elemento y añadiro al elemento
    var texto = document.createTextNode("Salir");
    boton.appendChild(texto);
    //Añadir la <table> al <div> correspondiente
    div.appendChild(boton);
    // appends <div> into <body>
    body.appendChild(div);
    //Añadir propiedades al boton
    boton.onclick = function () { location.href = 'pruebaMenu.html'; };
    boton.setAttribute("id", "salir");
    document.getElementById("salir").className = "btn btn-primary";
}
function genera_tabla() {
    // Obtener la referencia del elemento body
    var body = document.getElementsByTagName("body")[0];
    var div = document.getElementById("principal");

    // Crea un elemento <table>, un elemento <thead> y un elemento <tbody>
    var tabla = document.createElement("table");
    var thTitulo = document.createElement("thead");
    var tblBody = document.createElement("tbody");

    //Crear la hilera del titulo
    var cabecera = document.createElement("tr");
    //Crear elemenro <th> y un nodo texto
    var titulo = document.createElement("th");
    var nomTitulo = document.createTextNode("Archivos");
    titulo.appendChild(nomTitulo);

    //Añadir la cabecera a la tabla
    thTitulo.appendChild(titulo);
    //Añadir la cabecera a la tabla
    tabla.appendChild(thTitulo);

    // Crea las celdas (Tantas celdas como archivos contenga el servidor) 
    for (var i = 0; i < 4; i++) {
        // Crea las hileras de la tabla
        var hilera = document.createElement("tr");
        hilera.onclick = function () {
            var opcion = confirm("¿ Descargar Archivo?");
            if (opcion == true) {
                console.log("You pressed OK!");
                var divBarra = document.getElementById("segundario");
                divBarra.innerHTML = document.getElementById("barraDescarga").innerHTML;
                body.appendChild(divBarra);
            } else {
                console.log("You pressed Cancel!");
                var divBarra = document.getElementById("segundario");
                divBarra.innerHTML = " ";
                body.appendChild(divBarra);
            }
        };
        //Crear elemento <td>
        var celda = document.createElement("td");
        //Crear contenido de la tabla
        var textoCelda = document.createTextNode("Aquí va los nombres de los archivos.");
        //Insertar el contenido en el elemento <td>
        celda.appendChild(textoCelda);
        //Insertar el elemento <td> en el elemento <tr>
        hilera.appendChild(celda);
        // Agrega la fila a la tabla
        tblBody.appendChild(hilera);
    }

    // posiciona el <tbody> debajo del elemento <table>
    tabla.appendChild(tblBody);
    //Añadir la <table> al <div> correspondiente
    div.appendChild(tabla);
    // appends <div> into <body>
    body.appendChild(div);
    // modifica el atributo "border" de la tabla y lo fija a "2";
    tabla.setAttribute("id", "tabla");
    document.getElementById("tabla").className = "table table-hover";
}