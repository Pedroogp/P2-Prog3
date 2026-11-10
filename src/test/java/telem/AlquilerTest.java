package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;


public class AlquilerTest {

    @Test
    public void testCrearAlquiler() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        Cliente cliente = new Cliente("Juan Pérez", "12345678A");

        LocalDate inicio = LocalDate.of(2026, 11, 10);
        LocalDate fin = LocalDate.of(2026, 11, 12);

        Alquiler alquiler = new Alquiler(
            turismo, cliente, inicio, fin
        );

        assertSame(turismo, alquiler.getVehiculo());
        assertSame(cliente, alquiler.getCliente());
        assertEquals(inicio, alquiler.getFechaInicio());
        assertEquals(fin, alquiler.getFechaFin());
    }

    @Test
    public void testCalcularPrecioAlquiler() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        Cliente cliente = new Cliente("Juan Pérez", "12345678A");


        LocalDate inicio = LocalDate.of(2026, 11, 10);
        LocalDate fin = LocalDate.of(2026, 11, 12);

        Alquiler alquiler = new Alquiler(
            turismo, cliente, inicio, fin
        );

        int precio = alquiler.calcularPrecioAlquiler(inicio, fin);

        assertEquals(150, precio);
    }
}