package logiclayer;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable{
    private String solapin;
    private String carnetID;
    protected boolean marcado;
    protected String correo;
    protected String rol;
    protected String password;
    protected String nombre;

    public Usuario(String solapin, String carnetID, String nombre,String correo) {
        this.solapin = solapin;
        this.carnetID = carnetID;
        this.marcado = false;
        this.correo = correo;
        this.nombre=nombre;
        this.rol="cliente";
        this.password = carnetID;
    }

    public Usuario() {
       this.solapin = null;
        this.carnetID = null;
        this.marcado = false;
        this.correo = null;
        this.nombre= null;
        this.rol="cliente";
        this.password = null; 
    }

    public String getSolapin() {
        return solapin;
    }

    public String getCarnetID() {
        return carnetID;
    }

    public void setSolapin(String solapin) {
        this.solapin = solapin;
    }

    public void setCarnetID(String carnetID) {
        this.carnetID = carnetID;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return isMarcado() == usuario.isMarcado() && Objects.equals(getSolapin(), usuario.getSolapin()) && Objects.equals(getCarnetID(), usuario.getCarnetID()) && Objects.equals(getCorreo(), usuario.getCorreo()) && Objects.equals(getRol(), usuario.getRol()) && Objects.equals(getPassword(), usuario.getPassword()) && Objects.equals(getNombre(), usuario.getNombre());
    }
    public boolean someEqual(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getSolapin(), usuario.getSolapin()) || Objects.equals(getCarnetID(), usuario.getCarnetID()) || Objects.equals(getCorreo(), usuario.getCorreo()) || Objects.equals(getNombre(), usuario.getNombre());
    }
    public String whichEqual(Object o) {
        if (o == null || getClass() != o.getClass()) return null;
        Usuario usuario = (Usuario) o;
            if(Objects.equals(getSolapin(), usuario.getSolapin())) return "número solapín";
            else if(Objects.equals(getCarnetID(), usuario.getCarnetID())) return "carné de identidad";
            else if(Objects.equals(getCorreo(), usuario.getCorreo())) return "correo";
            else if(Objects.equals(getNombre(), usuario.getNombre()))return "nombre";
            return null;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getSolapin(), getCarnetID(), isMarcado(), getCorreo(), getRol(), getPassword(), getNombre());
    }
}
