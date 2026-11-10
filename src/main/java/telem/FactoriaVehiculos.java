
package telem;

import org.json.JSONObject;

public class FactoriaVehiculos {

    public static Vehiculo getVehiculo(String cadena) {

        JSONObject datos = new JSONObject(cadena);

        String tipo = datos.getString("tipo");
        String matricula = datos.getString("matricula");
        Categoria categoria = Categoria.valueOf(
            datos.getString("categoria")
        );
        int annio = datos.getInt("annio");

        Vehiculo vehiculo;

        switch (tipo) {
            case "Turismo":
                vehiculo = new Turismo(
                    matricula, categoria, annio,
                    datos.getInt("plazas"),
                    datos.getDouble("capacidadMaletero")
                );
                break;

            case "Motocicleta":
                vehiculo = new Motocicleta(
                    matricula, categoria, annio,
                    datos.getInt("cilindrada")
                );
                break;

            case "VehiculoIndustrial":
                vehiculo = new VehiculoIndustrial(
                    matricula, categoria, annio,
                    datos.getInt("capacidadCarga"),
                    datos.getInt("numeroEjes")
                );
                break;

            default:
                throw new IllegalArgumentException(
                    "Tipo de vehículo desconocido: " + tipo
                );
        }

        vehiculo.setDisponible(datos.getBoolean("disponible"));

        return vehiculo;
    }
}

