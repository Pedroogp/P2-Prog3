package telem;

import org.json.JSONObject;

public class VehiculoIndustrial extends Vehiculo {

    private int capacidadCarga;
    private int numeroEjes;

    public VehiculoIndustrial(String matricula, Categoria categoria, int annio,
        int capacidadCarga, int numeroEjes
    ) {
        super(matricula, categoria, annio);
        this.capacidadCarga = capacidadCarga;
        this.numeroEjes = numeroEjes;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public int getNumeroEjes() {
        return numeroEjes;
    }


        @Override
    public String getTipo() {
        return "VehiculoIndustrial";
    }


    @Override
    public String toString() {
        JSONObject datos = new JSONObject(super.toString());

        datos.put("capacidadCarga", capacidadCarga);
        datos.put("numeroEjes", numeroEjes);

        return datos.toString();
    }

}
