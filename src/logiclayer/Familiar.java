package logiclayer;

public class Familiar extends Usuario{
    private String apartamento;


    public Familiar(String solapin, String carnetID, String nombre, String correo, String apartamento) {
        super(solapin, carnetID, nombre, correo);
        this.apartamento=apartamento;
    }


    public String getNumApartamento() {
        return apartamento;
    }


    public void setNumApartamento(String apartamento) {
        this.apartamento = apartamento;
    }
    
}
