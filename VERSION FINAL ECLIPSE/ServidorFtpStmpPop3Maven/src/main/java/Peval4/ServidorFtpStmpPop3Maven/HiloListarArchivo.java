package Peval4.ServidorFtpStmpPop3Maven;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Clase que lista todos los fichero que contenga el servidor.
 * 
 * @author Guillermo Barcia
 * @version 1.0.
 */
public class HiloListarArchivo extends Thread {

	/**
	 * <Socket> socket: Variable que permite realizar la conexion entre el cliente y
	 * el servidor.
	 */
	private Socket socket;

	/**
	 * <DataOutputStream> dos: Variable que permite mandar mensajes al usuario.
	 */
	private DataOutputStream dos;

	/**
	 * <File> fichero: Variable que referencia al fichero que se envia al usuario.
	 */
	private File fichero;

	/**
	 * <File[]> todoFicheros: Variable que alamacena todos los ficheros que haya en
	 * el servidor.
	 */
	private File[] todoFicheros;

	/**
	 * Constructor parametrizado.
	 * 
	 * @param <Socket> s: Variable que indica la conexión entre servidor y usuario.
	 */
	public HiloListarArchivo(Socket s) {
		this.socket = s;
	}

	/**
	 * Método que permite el transpaso y envio de datos entre el servidor y el
	 * usuario.
	 */
	public void run() {
		try {
			System.out.println("Entro en el hilo listar archivos");

			// Inicializamos las variables.
			dos = new DataOutputStream(socket.getOutputStream());
			fichero = new File("src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Servidor");

			// Almacenamos el número de ficheros que contiene el servidor y lo enviamos al
			// usuario.
			todoFicheros = fichero.listFiles();
			dos.writeUTF(String.valueOf(todoFicheros.length));

			// Enviamos al usuario los nombres de los ficheros.
			for (int x = 0; x < todoFicheros.length; x++) {
				dos.writeUTF(todoFicheros[x].getName());
			}

			// Cerramos todas las conexiones.
			System.out.println("Fin listar archivos");
			dos.close();
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
