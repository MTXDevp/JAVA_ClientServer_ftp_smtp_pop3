package Peval4.ServidorFtpStmpPop3Maven;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

/**
 * Clase encargada de recibir el fichero subido por el usuario y guardarlo.
 * (Extiende de Thread)
 * 
 * @author Guillermo Barcia.
 * @version 1.0.
 */
public class HiloSubirArchivo extends Thread {

	/**
	 * <DataInputStream> dis: Variable que permite recibir mensajes enviados por el
	 * usuario.
	 */
	private DataInputStream dis;

	/**
	 * <Socket> socket: Variable que permite realizar la conexion entre el cliente y
	 * el servidor.
	 */
	private Socket socket;

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
	 * fichero mandado por el usuario.
	 */
	private byte[] datosFichero;

	/**
	 * <in> lineas: Número de lineas que contiene el fichero mandado por el usuario.
	 */
	private int lineas;

	/**
	 * <String> nomFichero: Nombre del fichero mandado por el usuario.
	 */
	private String nomFichero;

	/**
	 * Constructor de la clase
	 * 
	 * @param <Socket> s: Variable que indica la relacion entre servidor y usuario.
	 */
	public HiloSubirArchivo(Socket s) {
		this.socket = s;
	}

	/**
	 * Método que gestiona el transpaso y recibo de datos del usuario.
	 */
	public void run() {
		try {
			System.out.println("Entro en el hilo Subir Archivo");

			// Inicializamos variables.
			bis = new BufferedInputStream(this.socket.getInputStream());
			dis = new DataInputStream(this.socket.getInputStream());

			// Recibimos el nombre del fichero
			nomFichero = dis.readUTF();

			// Para guardar fichero recibido
			bos = new BufferedOutputStream(new FileOutputStream(
					"src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Servidor/" + nomFichero));

			// Recibimos el contenido del fichero.
			datosFichero = new byte[1024];
			while ((lineas = bis.read(datosFichero)) != -1) {
				bos.write(datosFichero, 0, lineas);
			}
			System.out.println("Archivo subido correctamente");

			// Cerramos las conexiones.
			bis.close();
			bos.close();
			dis.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
