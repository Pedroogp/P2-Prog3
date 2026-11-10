package telem;

import org.json.JSONObject;

/**
 * Modela un vehículo con matrícula, categoría, año y disponibilidad.
 * La matrícula debe ser única para cada vehículo.
 * Vehiculo
 */
public abstract class Vehiculo {

    private String matricula;
    private Categoria categoria;
    private int annio;
    private boolean disponible;

    public Vehiculo(String matricula, Categoria categoria, int annio) {
        this.matricula = matricula;
        this.categoria = categoria;
        this.annio = annio;
    }

    public String getMatricula() {
        return matricula;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getAnnio() {
        return annio;
    }

    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public abstract String getTipo();
    @Override
    public String toString() {
        JSONObject datos = new JSONObject();

        datos.put("tipo", getTipo());
        datos.put("matricula", matricula);
        datos.put("categoria", categoria.name());
        datos.put("annio", annio);
        datos.put("disponible", disponible);

        return datos.toString();
}

}
