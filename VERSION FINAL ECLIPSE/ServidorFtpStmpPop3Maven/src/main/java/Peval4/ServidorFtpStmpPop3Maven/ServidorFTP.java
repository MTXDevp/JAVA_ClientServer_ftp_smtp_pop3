package Peval4.ServidorFtpStmpPop3Maven;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase que dependiendo de la solicitud del usuario llama a otras clases.
 * 
 * @author Guillermo Barcia.
 * @version 1.0.
 */
public class ServidorFTP {

	/**
	 * <ServerSocket> server: Variable que permite establecer la relación entre el
	 * usuario y el servidor.
	 */
	private ServerSocket server;

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
	 * Métdo que ejecuta la clase.
	 * 
	 * @param <String[]> args: Variable por defecto
	 */
	public static void main(String[] args) {
		ServidorFTP servidor = new ServidorFTP();
		servidor.iniServidor();
	}

	/**
	 * Método que dependiendo de la opción elegida por el usuario llamara a una
	 * clase.
	 */
	public void iniServidor() {
		try {
			// Establecemos la conexión.
			server = new ServerSocket(5000);
			System.out.println("------Servidor encendido------");

			while (true) {
				// Aceptamos las conexiones.
				socket = server.accept();
				System.out.println("Un usuario ha accedido al servidor");

				// Inicializamos las variables.
				dis = new DataInputStream(socket.getInputStream());
				String opcion = dis.readUTF();

				// Dependiendo de la opción elegida por el servidor
				switch (opcion) {
				case "subir":
					HiloSubirArchivo subir = new HiloSubirArchivo(socket);
					subir.start();
					break;
				case "bajar":
					HiloBajarArchivo bajar = new HiloBajarArchivo(socket);
					bajar.start();
					break;
				case "listar":
					HiloListarArchivo listar = new HiloListarArchivo(socket);
					listar.start();
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
