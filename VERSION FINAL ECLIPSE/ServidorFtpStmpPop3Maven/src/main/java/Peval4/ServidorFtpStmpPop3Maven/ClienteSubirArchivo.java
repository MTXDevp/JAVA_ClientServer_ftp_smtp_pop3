package Peval4.ServidorFtpStmpPop3Maven;

import java.io.*;
import java.net.Socket;

/**
 * Calse que manda el fihcero que se envia al servidor.
 * 
 * @author Guillermo Barcia.
 * @version 1.0.
 * 
 */
public class ClienteSubirArchivo {

	/**
	 * <DataOutputStream> dos: Variable que permite mandar mensajes al servidor.
	 */
	private DataOutputStream dos;

	/**
	 * <BufferedInputStream> bis: Variable que permite la lectura del fichero que se
	 * va a enviar al servidor.
	 */
	private BufferedInputStream bis;

	/**
	 * <BufferedOutputStream> bos: Variable que permite mandar la información del
	 * fichero al servidor.
	 */
	private BufferedOutputStream bos;

	/**
	 * <File> fichero: Variable que referencia al fichero que se envia al servidor.
	 */
	private File fichero;

	/**
	 * <Socket> socket: Variable que permite realizar la conexion entre el cliente y
	 * el servidor.
	 */
	private Socket socket;

	/**
	 * <int> lineas: variable que indica el numero de lineas que contiene el fichero
	 * que se va a enviar al servidor.
	 */
	private int lineas;

	/**
	 * <byte[]> datosFichero: Variable que almacena la información que contiene el
	 * fichero para mandarla al servidor en forma de bytes.
	 */
	private byte[] datosFichero;

	/**
	 * <String> nomFichero: Nombre del fichero que se va a mandar al servidor.
	 */
	private final String nomFichero;

	/**
	 * Constructor parametrizado de la clase.
	 * 
	 * @param <String> rutaFichero: Ruta del fichero que se desea subir al servidor.
	 */
	public ClienteSubirArchivo(String rutaFichero) {
		this.nomFichero = rutaFichero;
		SubirArchivo();
	}

	/**
	 * Método que establece la conexión con el servidor y le transmite el fichero
	 * que se va a subir.
	 */
	private void SubirArchivo() {
		try {
			System.out.println("Nombre fichero : " + this.nomFichero);

			// Inicializamos variables.
			fichero = new File(nomFichero);
			socket = new Socket("127.0.0.1", 5000);
			bis = new BufferedInputStream(new FileInputStream(fichero));
			bos = new BufferedOutputStream(socket.getOutputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// Enviamos al servidor la opcion subir archivo.
			dos.writeUTF("subir");

			// Enviamos al servidor el nombre del fichero que se va a subir.
			dos.writeUTF(fichero.getName());

			// Enviamos el contenido del fichero al servidor.
			datosFichero = new byte[8192];
			while ((lineas = bis.read(datosFichero)) != -1) {
				bos.write(datosFichero, 0, lineas);
			}

			// Cerramos las conexiones.
			bis.close();
			bos.close();
			dos.close();
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
