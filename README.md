# ClienteServidor-ftp-smtp-pop3
Creación de un programa Java cliente/servidor haciendo uso de los protocolos ftp, smtp, pop3

INTRODUCCIÓN

Ante el aumento significativo del uso de tecnologías webs para el desarrollo de aplicaciones de escritorio decidimos adentrarnos en este tipo de tecnologías para descubrir lo que tenían que ofrecernos, la necesidad de usar java para el backend nos limitó bastante al respecto en un inicio, puesto que teníamos en mente usar javascript para el backend usando frameworks como electrón, el cual brinda la posibilidad de desarrollar el backend con node.js, y el frontend con chromium. Ante esta situación decidimos desarrollar el backend en java y el front con chromium (Específicamente el framework JxBrowser), lo que nos permitiría hacer uso de javascript y html para nuestro frontend y la comunicación con el backend.

Requisitos

Hemos hecho uso de Maven para gestionar nuestras librerías, para ello debimos de crear un proyecto Maven y añadir los repositorios de las mismas en un archivo llamado pom.xml, a excepción de las librerías de Jquery y Bootstrap las cuales fueron añadidas manualmente.

Configuración de correos 

Nuestro programa esta configurado para funcionar con hotmail y gmail para mostrar y enviar correos

HOTMAIL
La cuenta de hotmail ha sido habilitada anteriormente para poder usar la configuración POP

GMAIL
Si queremos que gmail nos muestre los mensajes que ya han sido descargados con anterioridad deberemos de entrar en la configuración de la cuenta y clickear en la opción : "Habilitar POP para todos los mensajes (incluso si se han descargado)"

Librerias

Las librerías añadidas son las siguientes:
JxBrowser : Librería que incorpora Chromium, el cual nos permite “empotrar” páginas webs en objetos Jframe de java
Javax.main : Librería que nos facilitará el envío y lectura de correos
com.h2database: Librería que nos permitirá implementar una h2 database, una base de datos relacional ligera y rápida
org.jsoup: Librería que proporciona métodos para trabajar con documentos html, la cual nos será muy útil para acceder al contenido de nuestros mensajes, puesto que estos serán recibidos en formato html


Base de datos

Si quieres visualizar la base de datos, deberás de descargar la aplicación H2 Console
http://www.h2database.com/html/download.html

EXPLICACIÓN DE LA IMPLEMENTACIÓN

La idea general de la interacción entre nuestras clases es la siguiente:
Nuestras vistas son documentos HTML, los cuales hacen uso de CSS para que luzcan bien
Puesto que el usuario interactúa con ese HTML, la manera de enviar el resultado de esas interacciones a nuestros .java se realiza por medio de javascript, funciones que e n mayor parte sirven para usar un método muy conocido en el ámbito del desarrollo web llamada webstorage, el cual nos permitirá guardar datos de manera local usando el navegador, teniendo en cuenta que usamos documentos html “empotrados” en un Jframe podremos hacer uso de esa herramienta, el webstorage es más seguro y proporciona más capacidad útil de almacenaje que los cookies, que se solían usar antes de la aparición de html5.

Main

El main se encargará de aplicar el hack al framework Jbrowser, crear una instancia del controlador login e inicializar el servidor FTP

Controlador Login

Creamos un objeto de tipo browser el cual será “empotrado” en un Jframe
A través de un evento (onFinish Loading Frame) detectaremos que el Frame se ha cargado completamente para evitar valores nulos en el proceso de traspaso de datos, accederemos a la URL cargada en ese momento mediante String url = event.get Validated URL(); , lo cual nos permitirá realizar unas acciones u otras dependiendo del html cargado en ese preciso momento

Clase Conexión

Clase encargada de crear una base de datos en la carpeta DataBase de nuestro proyecto la cual almacenará los usuarios registrados en nuestro programa
La cual será consultada para acceder al programa a través del login

Autentificar Correo

Esta clase será utilizada tras realizar el registro a nuestra base de datos, su función es impedir que te registres en nuestra aplicación con una dirección de correo u contraseñas no válidas

Cliente Subir Archivo

Clase que se encarga de establecer la conexión con la base de datos, envía la opción bajar al servidor para mandar el fichero que el usuario desea subir, manda el nombre del fichero y posteriormente el contenido del mismo.


Controlador Mostrar Archivos

Esta clase conecta con el servidor, le envía la opción de listar los ficheros, una vez recibida la opción el servidor recorre todos los fichero que el servidor contiene y los manda al cliente para listar los ficheros por pantalla.

Cliente Bajar Archivo

Una vez listado los fichero que contiene el servidor, esta clase se encarga de establecer la conexión con el servidor, le manda la opción al servidor de bajar los ficheros y el nombre del fichero que desea descargar, el servidor cuando recibe la opción y el nombre del fichero lo busca en su directorio y manda la información de dicho fichero al usuario.

Servidor FTP

Esta clase se encarga de organizar las funciones de subir-lista-bajar dependiendo de la opción del usuario, una vez asignada la opción esta clase se encarga de lanzar el ‘Thread’ correspondiente para que realice función necesaria.

Hilo Cargar Correo

Cuando se lanza el hilo con la opción cargar fichero se ejecuta esta clase que se encarga de obtener el nombre del fichero que se desea subir al servidor y posteriormente recibe el contenido de dicho fichero en forma de bytes.

Hilo Listar Correo

Cuando se lanza el hilo con la opción listar fichero se ejecuta esta clase que se encarga de almacenar todos los fichero almacenados en el directorio del servidor, tanto número de fichero como los nombres de cada uno de ellos, una vez terminada la recopilación de información de los fichero se le envía al usuario para que puedan ser listados en la vista.

Hilo Bajar Archivo

Cuando se lanza el hilo con la opción bajar fichero se ejecuta esta clase que se encarga de
recibir el nombre del fichero que se desea descargar, posteriormente buscar en el directorio del servidor si se encuentra el fichero con dicho nombre, una vez localizado el fichero se recoge el contenido del mismo y se le envía al usuario para que sea almacenado en el directorio del usuario.

Controlador FTP

La clase bajar archivo para que el usuario pueda elegir el fichero de su ordenador abre una ventana en la que se le muestra todos los fichero almacenados en su ordenador, para poder obtener la ruta del fichero seleccionado es ejecutada esta clase que se encarga de almacenar la ruta del fichero que se desea subir al servidor y mandarla de vueltas a la clase subir archivo para que dicho fichero pueda ser localizado para realizar la subida.

Comunicación Js-Java

Esta clase actúa como nexo de unión entre nuestras vistas (html) y nuestros archivos java, contiene una serie de clases cuya funcionalidad reside en recibir datos enviados desde funciones JavaScript alojadas en los correspondientes html

ControladorMostrarCorreos

Clase encargada de realizar la conexión a través de POP3 para acceder a nuestra bandeja de correo, rescataremos los datos en arrays, los cuales serán almacenados mediante webStorage para ser mostrados posteriormente en Visualizar Correo.js

Enviar Email

Clase encargada de realizar una conexión a través del protocolo SMTP, la cual permitirá realizar el envío de un mensaje de correo

Obtener Cuerpo Mensaje

Esta clase nos permitirá obtener la información del cuerpo del correo en formato html, formateando posteriormente con la librería JSoup para obtener un String del mismo.

