
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Barcia
 */
public class HiloBajarArchivo extends Thread {

	Socket connection;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	byte[] receivedData;
	int in;
	String nombreFichero;

	public HiloBajarArchivo(Socket s) {
		this.connection = s;
	}

	public void run() {
		try {
			System.out.println("Entro en el hilo bajar archivo");
			//Recibimos el nombre del fichero
			DataInputStream dis = new DataInputStream(connection.getInputStream());
			nombreFichero = dis.readUTF();
			System.out.println(nombreFichero);
			final File localFile = new File("src/ArchivosServidor/" + nombreFichero);
			bis = new BufferedInputStream(new FileInputStream(localFile));
			System.out.println("He pasado por el punto critico 1");
			bos = new BufferedOutputStream(connection.getOutputStream());
			System.out.println("He pasado el punto critico 2");
			System.out.println("Acabo de mandar el nombre del fichero");
			//Enviamos el fichero
			receivedData = new byte[8192];
			while((in = bis.read(receivedData)) != -1) {
				bos.write(receivedData, 0, in);
			}
			System.out.println("Fichero mandado, aqui se acaba mi trabajo");
			bis.close();
			bos.close();

		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
