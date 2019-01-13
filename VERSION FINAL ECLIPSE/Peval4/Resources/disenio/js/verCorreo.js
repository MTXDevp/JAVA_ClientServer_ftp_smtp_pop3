window.addEventListener('load', inicio, false);

function inicio() {

        alert('estoy en ver Correo');

       var cuerpo = localStorage.cuerpoSeleccionado;
       var findCorchetes = "\\[";
       var re = new RegExp(findCorchetes, 'g');
       var cuerpoClear = cuerpo.replace(re, '');
       document.getElementById("contenidoCorreo").value = cuerpoClear;





}
