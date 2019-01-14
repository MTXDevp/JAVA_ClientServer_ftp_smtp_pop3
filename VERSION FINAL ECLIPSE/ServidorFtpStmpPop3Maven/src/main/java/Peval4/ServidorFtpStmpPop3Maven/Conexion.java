package Peval4.ServidorFtpStmpPop3Maven;


import java.sql.*;

public class Conexion {

    Statement stm;
    Connection con;
    ResultSet rs=null;
    

    public Conexion() {

        try {
            Class.forName("org.h2.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR REGISTRANDO EL DRIVER");
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:h2:~/plz", "", "");
            System.out.println("BASE DE DATOS CREADA!");
            stm = con.createStatement();
            String sentencia = "CREATE TABLE IF NOT EXISTS USUARIOS "
                    + "(correo VARCHAR(30), "
                    + " contraseña VARCHAR(30))";
            stm.executeUpdate(sentencia);
            System.out.println("Tabla creada con exito");

        } catch (SQLException e) {
            System.out.println("ERROR CREANDO LA BASE DE DATOS");
            System.out.println(e.getMessage());
            
        }
    }

    public void CheckLogin(String correo, String contraseña){
                String sentencia = "select " + "'" + correo + "'"+ " from USUARIOS where contraseña=" + "'"+contraseña+"'"+";";
        try {
            rs = stm.executeQuery(sentencia);
        } catch (SQLException e) {
            System.out.println("Se ha producido un error realizando la consulta ala base de datos usuarios");
            System.out.println("DETALLES : ");
            System.out.println(e.getMessage());
        }
    }
    public void InsertNewUsuario(String correo, String contraseña){
        String sentencia = "insert into usuarios values("+"'"+correo+"'"+","+"'"+contraseña+"'"+");";
        try {
            stm.executeUpdate(sentencia);
            System.out.println("Inserción realizada con éxito");
        } catch (SQLException e) {
            System.out.println("Se ha producido un error insertando los datos a la base de datos");
            e.printStackTrace();
        }
    }

    public Statement getStm() {
        return stm;
    }

    public void setStm(Statement stm) {
        this.stm = stm;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }


}
