
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;

public class ServidorFTP {

	FtpServerFactory serverFactory = new FtpServerFactory();
	ListenerFactory factory;
	FtpServer server = serverFactory.createServer();

	public ServidorFTP() {

		serverFactory = new FtpServerFactory();
		factory = new ListenerFactory();
		factory.setPort(21);
		serverFactory.addListener("default", factory.createListener());
		server = serverFactory.createServer();
		try {
			server.start();
			System.out.println("SERVIDOR FTP INICIALIZADO CON ÉXITO");
		} catch(FtpException e) {
			System.out.println("SE HA PRODUCIDO UN ERROR INICIALIZANDO EL SERVIDOR");
			throw new RuntimeException(e);
		}

	}
}
