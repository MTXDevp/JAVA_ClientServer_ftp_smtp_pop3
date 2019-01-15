package Peval4.ServidorFtpStmpPop3Maven;

import java.sql.*;

/**
 * Clase encargada de realizar las consultas con la base de datos.
 * 
 * @author Rafael Valls.
 * @version 1.0.
 *
 */
public class Conexion {

	/**
	 * <Statement> stm: Variable que permite realizar consultas sobre la base de
	 * datos.
	 */
	private Statement stm;

	/**
	 * <Connection> con: Variable que permite realizar la conexión con la base de
	 * datos.
	 */
	private Connection con;

	/**
	 * <ResultSet> rs: Variable que recibe los valores de la consulta realizada
	 * sobre la base de datos.
	 */
	private ResultSet rs = null;

	/**
	 * Constructor el cual se encarga de establecer la conexión con la base de datos
	 * y crear las tabla.
	 */
	public Conexion() {

		try {
			// Driver para conectar con la base de datos.
			Class.forName("org.h2.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("Error en el driver");
			e.printStackTrace();
		}
		try {
			// Ruta donde se guarda la base de datos.
			con = DriverManager.getConnection("jdbc:h2:.\\database\\USUARIOS", "", "");
			System.out.println("¡Estado de la base de datos: OK!");
			stm = con.createStatement();
			String sentencia = "CREATE TABLE IF NOT EXISTS USUARIOS " + "(correo VARCHAR(30), "
					+ " contraseña VARCHAR(30))";
			stm.executeUpdate(sentencia);

		} catch (SQLException e) {
			System.out.println("Error en la creación de la base de datos");
			System.out.println(e.getMessage());

		}
	}

	/**
	 * Método que comprueba si los credenciales del usuario son correctos.
	 * 
	 * @param <String> correo: Variable que contiene los datos del correo del
	 *        cliente.
	 * @param <String> contraseña: Variable que contiene los datos sobre la
	 *        contraseña del cliente.
	 */
	public void CheckLogin(String correo, String contraseña) {
		String sentencia = "select " + "'" + correo + "'" + " from USUARIOS where contraseña=" + "'" + contraseña
				+ "';";
		try {
			rs = stm.executeQuery(sentencia);
		} catch (SQLException e) {
			System.out.println("Se ha producido un error realizando la consulta ala base de datos usuarios");
			System.out.println("Detalles : ");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Método que inserta un nuevo usuario en la base de datos cuando este se
	 * registra por primera vez.
	 * 
	 * @param <String> correo: Variable que contiene los datos del correo del
	 *        cliente.
	 * @param <String> contraseña: Variable que contiene los datos sobre la
	 *        contraseña del cliente.
	 */
	public void InsertNewUsuario(String correo, String contraseña) {
		String sentencia = "insert into usuarios values(" + "'" + correo + "'" + "," + "'" + contraseña + "'" + ");";
		try {
			stm.executeUpdate(sentencia);
		} catch (SQLException e) {
			System.out.println("Se ha producido un error insertando los datos a la base de datos");
			e.printStackTrace();
		}
	}

	/**
	 * Método que permite obtener la conexion a la base de datos.
	 * 
	 * @return <Connection> con.
	 */
	public Connection getCon() {
		return con;
	}

	/**
	 * Método que permite obtener la variable ResultSet.
	 * 
	 * @return <ResultSet> rs.
	 */
	public ResultSet getRs() {
		return rs;
	}
}
