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
 *
 * @author Guillermo Barcia.
 * @version 1.0.
 */
public class ClienteBajarArchivo {

	Socket connection;
	DataOutputStream output;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	byte[] receivedData;
	int in;
	String file;

	public ClienteBajarArchivo() {
	}

	public ClienteBajarArchivo(String nombreFichero) {
		bajarFicheros(nombreFichero);
	}

	private void bajarFicheros(String nombreFichero) {
		try {
			System.out.println("Me voy a conectar al servidor");
			connection = new Socket("127.0.0.1", 5000);
			//Buffer de 1024 bytes
			receivedData = new byte[1024];
			bis = new BufferedInputStream(connection.getInputStream());
			DataInputStream dis = new DataInputStream(connection.getInputStream());
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			// Enviamos la opcion subir archivo
			dos.writeUTF("bajar");
			//Enviamos el nombre del fichero
			System.out.println("Voy a mandar el nombre del fihcero");
			dos.writeUTF(nombreFichero);
			System.out.println("Nombre del fihcero que ha descargadar " + nombreFichero);
			//Para guardar fichero recibido
			bos = new BufferedOutputStream(new FileOutputStream("src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Usuario/" + nombreFichero));
			while((in = bis.read(receivedData)) != -1) {
				bos.write(receivedData, 0, in);
			}
			System.out.println("Fin de la descarga");
			bos.close();
			dis.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<String> listarFicheros() {
		ArrayList<String> todoFichero = new ArrayList<>();
		try {
			connection = new Socket("127.0.0.1", 5000);
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			// Enviamos la opcion subir archivo
			dos.writeUTF("listar");
			DataInputStream datos = new DataInputStream(connection.getInputStream());
			String numeroFicheros = datos.readUTF();
			System.out.println("Ficheros disponibles: " + numeroFicheros);
			todoFichero.add(numeroFicheros);
			for(int i = 1; i <= Integer.parseInt(numeroFicheros); i++) {
				todoFichero.add(datos.readUTF());
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return todoFichero;
	}
}
