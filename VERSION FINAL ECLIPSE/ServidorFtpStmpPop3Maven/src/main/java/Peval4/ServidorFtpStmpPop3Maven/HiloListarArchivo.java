package Peval4.ServidorFtpStmpPop3Maven;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
public class HiloListarArchivo extends Thread {

	Socket connection;

	public HiloListarArchivo(Socket s) {
		this.connection = s;
	}

	public void run() {
		try {
			System.out.println("Entro en el hilo listar archivos");
			OutputStream flujoSalida = connection.getOutputStream();
			DataOutputStream dos = new DataOutputStream(flujoSalida);
			//String sDirectorio = "C:\\Users\\Barcia\\Desktop\\Prueba";
			File f = new File("src/main/java/Peval4/ServidorFtpStmpPop3Maven/Archivos/Servidor");
			File[] ficheros = f.listFiles();
			dos.writeUTF(String.valueOf(ficheros.length));//Numero de elementos
			for(int x = 0; x < ficheros.length; x++) {
				dos.writeUTF(ficheros[x].getName());
			}
			System.out.println("Fin listar archivos");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
