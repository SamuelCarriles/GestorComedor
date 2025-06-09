package logiclayer;

public class UsuarioRoot extends Usuario {

    public UsuarioRoot(String solapin, String carnetID, String nombre, String correo) {
        super(solapin, carnetID, nombre, correo);
    }

    public UsuarioRoot(String nombre, String rol, String password) {
        super(null,null,null,null);
        this.nombre = nombre;
        this.rol = rol;
        this.password = password;
    }


    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }
}
