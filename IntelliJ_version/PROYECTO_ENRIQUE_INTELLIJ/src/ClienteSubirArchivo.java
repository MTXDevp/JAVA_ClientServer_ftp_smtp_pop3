import java.io.*;
import java.net.Socket;

public class ClienteSubirArchivo {

    public ClienteSubirArchivo(String nombreServer, String pathFichero){

        System.out.println("Nombre Servidor : " + nombreServer);
        System.out.println("Nombre fichero : " + pathFichero);

        DataInputStream input;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        int in;
        byte[] byteArray;
        //Fichero a transferir
        final String filename = pathFichero;

        try{
            final File localFile = new File( filename );
            Socket client = new Socket(nombreServer, 5000);
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());
            //Enviamos el nombre del fichero
            DataOutputStream dos=new DataOutputStream(client.getOutputStream());
            dos.writeUTF(localFile.getName());
            //Enviamos el fichero
            byteArray = new byte[8192];
            while ((in = bis.read(byteArray)) != -1){
                bos.write(byteArray,0,in);
            }
            bis.close();
            bos.close();

        }catch ( Exception e ) {
            e.printStackTrace();
            }
        }
    }

