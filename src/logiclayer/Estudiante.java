
package logiclayer;


public class Estudiante extends Usuario{
    private String grupo;

    public Estudiante(String solapin, String carnetID, String nombre, String correo, String grupo) {
        super(solapin, carnetID, nombre, correo);
        this.grupo = grupo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    
}
