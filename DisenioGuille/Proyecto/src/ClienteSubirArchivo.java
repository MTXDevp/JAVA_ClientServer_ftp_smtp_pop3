
import java.io.*;
import java.net.Socket;

public class ClienteSubirArchivo {

	BufferedInputStream bis;
	BufferedOutputStream bos;
	int in;
	byte[] byteArray;
	//Fichero a transferir
	final String filename;

	public ClienteSubirArchivo(String pathFichero) {
		this.filename = pathFichero;
		try {
			System.out.println("Nombre fichero : " + this.filename);
			final File localFile = new File(filename);
			Socket client = new Socket("127.0.0.1", 5000);
			bis = new BufferedInputStream(new FileInputStream(localFile));
			bos = new BufferedOutputStream(client.getOutputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			// Enviamos la opcion subir archivo
			dos.writeUTF("subir");
			//Enviamos el nombre del fichero
			dos.writeUTF(localFile.getName());
			//Enviamos el fichero
			byteArray = new byte[8192];
			while((in = bis.read(byteArray)) != -1) {
				bos.write(byteArray, 0, in);
			}
			bis.close();
			bos.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
