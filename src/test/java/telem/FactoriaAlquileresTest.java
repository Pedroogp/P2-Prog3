
package telem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactoriaAlquileresTest {

    @Test
    public void testRecuperarAlquiler() {

        // Creamos un cliente y un vehículo
        Cliente cliente = new Cliente("Pedro", "12345678A");

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        // Creamos las listas de objetos existentes
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);

        List<Vehiculo> vehiculos = new ArrayList<>();
        vehiculos.add(turismo);

        // Creamos el alquiler original
        Alquiler original = new Alquiler(
            turismo,
            cliente,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 12)
        );

        // Convertimos el alquiler a JSON
        String json = original.toString();

        // Recuperamos el alquiler mediante la factoría
        Alquiler recuperado = FactoriaAlquileres.getAlquiler(
            json, clientes, vehiculos
        );

        // Comprobamos las referencias
        assertSame(cliente, recuperado.getCliente());
        assertSame(turismo, recuperado.getVehiculo());

        // Comprobamos las fechas
        assertEquals(
            LocalDate.of(2026, 11, 10),
            recuperado.getFechaInicio()
        );

        assertEquals(
            LocalDate.of(2026, 11, 12),
            recuperado.getFechaFin()
        );
    }


@Test(expected = IllegalArgumentException.class)
public void testClienteNoEncontrado() {

    // Lista de clientes vacía
    List<Cliente> clientes = new ArrayList<>();

    // Creamos un vehículo existente
    Turismo turismo = new Turismo(
        "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
    );

    List<Vehiculo> vehiculos = new ArrayList<>();
    vehiculos.add(turismo);

    // JSON que hace referencia a un cliente inexistente
    String json = "{"
        + "\"dni\":\"99999999Z\","
        + "\"matricula\":\"1234ABC\","
        + "\"fechaInicio\":\"2026-11-10\","
        + "\"fechaFin\":\"2026-11-12\""
        + "}";

    FactoriaAlquileres.getAlquiler(json, clientes, vehiculos);
}


@Test(expected = IllegalArgumentException.class)
public void testVehiculoNoEncontrado() {

    // Creamos un cliente existente
    Cliente cliente = new Cliente("Pedro", "12345678A");

    List<Cliente> clientes = new ArrayList<>();
    clientes.add(cliente);

    // Lista de vehículos vacía
    List<Vehiculo> vehiculos = new ArrayList<>();

    // JSON con una matrícula inexistente
    String json = "{"
        + "\"dni\":\"12345678A\","
        + "\"matricula\":\"9999ZZZ\","
        + "\"fechaInicio\":\"2026-11-10\","
        + "\"fechaFin\":\"2026-11-12\""
        + "}";

    FactoriaAlquileres.getAlquiler(json, clientes, vehiculos);
}

}
