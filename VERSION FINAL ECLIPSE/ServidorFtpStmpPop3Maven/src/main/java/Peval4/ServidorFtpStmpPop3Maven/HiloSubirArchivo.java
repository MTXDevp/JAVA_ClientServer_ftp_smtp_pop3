package Peval4.ServidorFtpStmpPop3Maven;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
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
public class HiloSubirArchivo extends Thread {

	private final Socket socket;
	private BufferedInputStream entrada;
	private BufferedOutputStream salida;
	private byte[] receivedData;
	private int in;
	private String file;

	public HiloSubirArchivo(Socket s) {
		this.socket = s;
	}

	public void run() {
		try {
			System.out.println("Entro en el hilo subir archivo");
			
			//Buffer de 1024 bytes
			receivedData = new byte[1024];
			entrada = new BufferedInputStream(this.socket.getInputStream());
			DataInputStream dis = new DataInputStream(this.socket.getInputStream());
			//Recibimos el nombre del fichero
			file = dis.readUTF();
			//Para guardar fichero recibido
			salida = new BufferedOutputStream(new FileOutputStream("src/ArchivosServidor/" + file));
			while((in = entrada.read(receivedData)) != -1) {
				salida.write(receivedData, 0, in);
			}
			System.out.println("Archivo subido correctamente");
			entrada.close();
			salida.close();
			dis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
