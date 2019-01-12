
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Barcia
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
			bos = new BufferedOutputStream(new FileOutputStream("src/Descargas/" + nombreFichero));
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
			int numeroFicheros = Integer.parseInt(datos.readUTF());
			System.out.println("Ficheros disponibles: " + numeroFicheros);
			for(int i = 0; i < numeroFicheros; i++) {
				todoFichero.add(datos.readUTF());
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return todoFichero;
	}
}
