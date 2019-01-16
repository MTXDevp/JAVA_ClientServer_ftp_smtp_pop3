package Peval4.ServidorFtpStmpPop3Maven;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase que envia el nombre de fichero que desea descargar el usuario, lo
 * recibe y lo almacena.
 * 
 * @author Guillermo Barcia.
 * @version 1.0.
 */
public class ClienteBajarArchivo {

	/**
	 * <Socket> socket: Variable que permite realizar la conexion entre el cliente y
	 * el servidor.
	 */
	private Socket socket;

	/**
	 * <DataInputStream> dis: Variable que permite recibir mensajes enviados por el
	 * servidor.
	 */
	private DataInputStream dis;

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
	 * <byte[]> datosFichero: Variable que almacena los datos que contiene el
	 * fichero mandado por el servidor.
	 */
	private byte[] datosFichero;

	/**
	 * <int> lineas: Variable que indica el número de lineas que contiene el fichero
	 * que manda el servidor.
	 */
	private int lineas;

	/**
	 * Constructor vacío.
	 */
	public ClienteBajarArchivo() {
	}

	/**
	 * Constructor parametrizado.
	 * 
	 * @param <String> nombreFichero: Nombre del fichero que el usuario desea
	 *        descargar del servidor.
	 */
	public ClienteBajarArchivo(String nombreFichero) {
		bajarFicheros(nombreFichero);
	}

	/**
	 * Método que permite la transmisión de información para que el usuario pueda
	 * descargar el fichero.
	 * 
	 * @param <String> nombreFichero: Nombre del fichero que el usuario desea
	 *        descargar del servidor.
	 */
	private void bajarFicheros(String nombreFichero) {
		try {
			// Inicializamos las variables.
			socket = new Socket("127.0.0.1", 5000);
			bis = new BufferedInputStream(socket.getInputStream());
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());

			// Enviamos la opcion subir archivo.
			dos.writeUTF("bajar");

			// Enviamos el nombre del fichero.
			dos.writeUTF(nombreFichero);
			System.out.println("Nombre del fihcero que ha descargadar " + nombreFichero);

			// Directorio donde guardar fichero descargado.
			bos = new BufferedOutputStream(new FileOutputStream(
					"src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Usuario/" + nombreFichero));

			// Recibimos el contenido del fichero al servidor.
			datosFichero = new byte[1024];
			while ((lineas = bis.read(datosFichero)) != -1) {
				bos.write(datosFichero, 0, lineas);
			}
			System.out.println("Fin de la descarga");

			// Cerramos las conexiones.
			bos.close();
			dis.close();
			dos.close();
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Método que lista los ficheros que contiene el servidor.
	 * 
	 * @return ArrayList<String> todoFichero: Contiene toda la información (número y
	 *         nombres), de los fichero que contiene el servidor.
	 */
	public ArrayList<String> listarFicheros() {
		// Declaramos la variable.
		ArrayList<String> todoFichero = new ArrayList<>();

		try {
			// Inicializamos las variables.
			socket = new Socket("127.0.0.1", 5000);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());

			// Enviamos la opción listar archivo
			dos.writeUTF("listar");

			// Recibimos el número de ficheros.
			String numeroFicheros = dis.readUTF();
			System.out.println("Ficheros disponibles: " + numeroFicheros);

			// Añadimos el número de fihero en la primera posición del 'ArrayList'.
			todoFichero.add(numeroFicheros);

			// Añadimos los nombres de los fichero al 'ArrayList'.
			for (int i = 1; i <= Integer.parseInt(numeroFicheros); i++) {
				todoFichero.add(dis.readUTF());
			}

			// Cerramos las conexiones.
			dis.close();
			dos.close();
			socket.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return todoFichero;
	}
}
