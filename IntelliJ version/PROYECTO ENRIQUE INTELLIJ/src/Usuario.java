import java.io.Serializable;

public class Usuario implements Serializable {

    String usuario;
    String contraseña;

    public Usuario(String nombre, String contraseña){

        this.usuario = nombre;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return usuario;
    }

    public void setNombre(String nombre) {
        this.usuario = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
