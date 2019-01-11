import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorFTP {

    ServerSocket server;
    Socket connection;
    DataOutputStream output;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    byte[] receivedData;
    int in;
    String file;

    public static void main(String[] args) {

        ServidorFTP sftp = new ServidorFTP();
    }
    public ServidorFTP() {

        try{
            //Servidor Socket en el puerto 5000
            server = new ServerSocket( 5000 );
            while ( true ) {
                //Aceptar conexiones
                connection = server.accept();
                //Buffer de 1024 bytes
                receivedData = new byte[1024];
                bis = new BufferedInputStream(connection.getInputStream());
                DataInputStream dis=new DataInputStream(connection.getInputStream());
                //Recibimos el nombre del fichero
                file = dis.readUTF();
                //Para guardar fichero recibido
                bos = new BufferedOutputStream(new FileOutputStream("C:\\Users\\USUARIO\\Desktop\\ArchivosServidorFTP\\" + file));
                while ((in = bis.read(receivedData)) != -1){
                    bos.write(receivedData,0,in);
                }
                bos.close();
                dis.close();
            }
        }catch (Exception e ) {
            e.printStackTrace();
        }
        }
    }


