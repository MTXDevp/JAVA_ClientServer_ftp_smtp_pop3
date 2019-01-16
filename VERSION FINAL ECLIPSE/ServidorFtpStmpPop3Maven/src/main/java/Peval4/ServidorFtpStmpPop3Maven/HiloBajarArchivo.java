package Peval4.ServidorFtpStmpPop3Maven;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase encargada de recibir el fichero que el usuario quiere descargar,
 * buscarlo en el servidor y mandarlo al usuario. (Extiende de Thread)
 * 
 * @author Guillermo Barcia.
 * @version 1.0.
 */
public class HiloBajarArchivo extends Thread {

	/**
	 * <Socket> socket: Variable que permite realizar la conexion entre el cliente y
	 * el servidor.
	 */
	private Socket socket;

	/**
	 * <DataInputStream> dis: Variable que permite recibir mensajes enviados por el
	 * usuario.
	 */
	private DataInputStream dis;

	/**
	 * <BufferedInputStream> bis: Variable que permite la lectura del fichero que se
	 * va a enviar al usuario.
	 */
	private BufferedInputStream bis;

	/**
	 * <BufferedOutputStream> bos: Variable que permite mandar la información del
	 * fichero al usuario.
	 */
	private BufferedOutputStream bos;

	/**
	 * <File> fichero: Variable que referencia al fichero que se envia al usuario.
	 */
	private File fichero;
	/**
	 * <byte[]> datosFichero: Variable que almacena los datos que contiene el
	 * fichero mandado por el usuario.
	 */
	private byte[] datosFichero;

	/**
	 * <int> lineas: Número de lineas que contiene el fichero.
	 */
	private int lineas;

	/**
	 * <String> nomFichero: Nombre del fichero mandado por el usuario.
	 */
	private String nombreFichero;

	/**
	 * Constructor parametrizado de la clase.
	 * 
	 * @param <Socket>s: Variable que indica la conexión entre servidor y usuario.
	 */
	public HiloBajarArchivo(Socket s) {
		this.socket = s;
	}

	/**
	 * Método que permite el transpaso y envio de datos entre el servidor y el
	 * usuario.
	 */
	public void run() {
		try {
			System.out.println("Entro en el hilo bajar archivo");

			// Inicializamos variables.
			fichero = new File("src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Servidor/" + nombreFichero);
			dis = new DataInputStream(socket.getInputStream());
			bis = new BufferedInputStream(new FileInputStream(fichero));
			bos = new BufferedOutputStream(socket.getOutputStream());

			// Recibimos el nombre del fichero.
			nombreFichero = dis.readUTF();

			// Enviamos el contenido del fichero al usuario.
			datosFichero = new byte[8192];
			while ((lineas = bis.read(datosFichero)) != -1) {
				bos.write(datosFichero, 0, lineas);
			}

			// Cerramos las conexiónes
			System.out.println("Fichero mandado con exito");
			bis.close();
			bos.close();
			dis.close();
			socket.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
