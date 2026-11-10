package telem;
import org.json.JSONObject;
public class Motocicleta extends Vehiculo {

    int cilindrada;

    public Motocicleta(String matricula, Categoria categoria, int annio,
        int cilindrada
    ) {
        super(matricula, categoria, annio);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    @Override
        public String getTipo() {
    return "Motocicleta";
    }


    @Override
        public String toString() {
     JSONObject datos = new JSONObject(super.toString());

        datos.put("cilindrada", cilindrada);

        return datos.toString();
    }


}
