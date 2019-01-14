window.addEventListener('load', inicio, false);

function inicio() {
}

function myLinkedin(numero) {
    if (numero == 0) {
        window.open('https://www.linkedin.com/in/rafael-valls-11a924130/');
    }
    else if (numero == 1) {
        window.open('https://es.linkedin.com/in/guillermo-barcia-molina-311b3a167');
    }
    else if (numero == 2) {
        window.open('https://www.deviantart.com/mystic-ervo');
    }
}

function carga() {
    var divBarra = document.getElementById("segundario");
    divBarra.innerHTML = document.getElementById("barraDescarga").innerHTML;
    localStorage.setItem("barraCarga", "ok");
}