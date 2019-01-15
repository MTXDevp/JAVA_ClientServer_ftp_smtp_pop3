window.addEventListener('load', inicio, false);

function inicio() {
}

function carga() {
    var divBarra = document.getElementById("segundario");
    divBarra.innerHTML = document.getElementById("barraDescarga").innerHTML;
    localStorage.setItem("barraCarga", "ok");
}