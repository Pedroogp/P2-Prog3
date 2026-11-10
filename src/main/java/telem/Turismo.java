package telem;
import org.json.JSONObject;
public class Turismo extends Vehiculo {

    int plazas;
    double capacidadMaletero;

    public Turismo(String matricula, Categoria categoria, int annio,
        int plazas, double capacidadMaletero
    ) {
        super(matricula, categoria, annio);
        this.plazas = plazas;
        this.capacidadMaletero = capacidadMaletero;
    }

    public int getPlazas() {
        return plazas;
    }

    public double getCapacidadMaletero() {
        return capacidadMaletero;
    }

    @Override
        public String getTipo() {
    return "Turismo";
    }


    @Override
    public String toString() {
        JSONObject datos = new JSONObject(super.toString());

         datos.put("plazas", plazas);
        datos.put("capacidadMaletero", capacidadMaletero);

        return datos.toString();
}
}
