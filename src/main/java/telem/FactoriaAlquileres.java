
package telem;

import java.time.LocalDate;
import java.util.List;
import org.json.JSONObject;

public class FactoriaAlquileres {

    public static Alquiler getAlquiler(
            String cadena,
            List<Cliente> clientes,
            List<Vehiculo> vehiculos) {

        JSONObject datos = new JSONObject(cadena);

        String dni = datos.getString("dni");
        String matricula = datos.getString("matricula");

        Cliente cliente = null;
        Vehiculo vehiculo = null;

        // Buscar el cliente por DNI
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                cliente = c;
                break;
            }
        }

        // Buscar el vehículo por matrícula
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula().equals(matricula)) {
                vehiculo = v;
                break;
            }
        }

        // Comprobar que existen
        if (cliente == null || vehiculo == null) {
            throw new IllegalArgumentException(
                "Cliente o vehículo no encontrado"
            );
        }

        LocalDate inicio = LocalDate.parse(
            datos.getString("fechaInicio")
        );

        LocalDate fin = LocalDate.parse(
            datos.getString("fechaFin")
        );

        return new Alquiler(vehiculo, cliente, inicio, fin);
    }
}
