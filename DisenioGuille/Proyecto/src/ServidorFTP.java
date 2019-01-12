
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorFTP {

	ServerSocket server;
	Socket connection;
	DataOutputStream output;
	DataInputStream entrada;
	BufferedInputStream bis;
	BufferedOutputStream bos;
	byte[] receivedData;
	int in;
	String file;

	public static void main(String[] args) {
		ServidorFTP servidor = new ServidorFTP();
		servidor.iniServidor();
	}

	public void iniServidor() {
		try {
			//Servidor Socket en el puerto 5000
			server = new ServerSocket(5000);
			System.out.println("Arranco el servidor");
			
			while(true) {
				//Aceptar conexiones
				connection = server.accept();
				System.out.println("Un usuario ha accedido al servidor");
				entrada = new DataInputStream(connection.getInputStream());
				String opcion = entrada.readUTF();
				System.out.println("Opcion " + opcion);
				switch(opcion) {
					case "subir":
						HiloSubirArchivo subir = new HiloSubirArchivo(connection);
						subir.start();
						break;
					case "bajar":
						HiloBajarArchivo bajar = new HiloBajarArchivo(connection);
						bajar.start();
						break;
					case "listar":
						HiloListarArchivo listar = new HiloListarArchivo(connection);
						listar.start();
						break;
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
