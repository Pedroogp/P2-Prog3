
package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class PoliticasPrecioTest {

    @Test
    public void testPoliticaNormal() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        Cliente cliente = new Cliente("Pedro", "12345678A");

        Alquiler alquiler = new Alquiler(
            turismo,
            cliente,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 12)
        );

        alquiler.setEstrategia(new PoliticaNormal());

        assertEquals(150.0, alquiler.calcularPrecioAlquiler(), 0.001);
    }

    @Test
    public void testPoliticaTemporadaAlta() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        Cliente cliente = new Cliente("Pedro", "12345678A");

        Alquiler alquiler = new Alquiler(
            turismo,
            cliente,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 12)
        );

        alquiler.setEstrategia(new PoliticaTemporadaAlta());

        assertEquals(300.0, alquiler.calcularPrecioAlquiler(), 0.001);
    }



@Test
    public void testPoliticaOferton() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
     );

        Cliente cliente = new Cliente("Pedro", "12345678A");

        Alquiler alquiler = new Alquiler(
            turismo,
            cliente,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 17)
        );

        alquiler.setEstrategia(new PoliticaOferton());

        assertEquals(252.0, alquiler.calcularPrecioAlquiler(), 0.001);
    }



@Test
public void testPoliticaClienteFrecuente() {

        Turismo turismo = new Turismo(
         "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
     );

        Cliente cliente = new Cliente("Pedro", "12345678A");

        Alquiler alquiler = new Alquiler(
            turismo,
            cliente,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 14)
        );

        alquiler.setEstrategia(new PoliticaClienteFrecuente());

        assertEquals(180.0, alquiler.calcularPrecioAlquiler(), 0.001);
}


@Test
public void testCambiarEstrategia() {

    Turismo turismo = new Turismo(
        "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
    );

    Cliente cliente = new Cliente("Pedro", "12345678A");

    Alquiler alquiler = new Alquiler(
        turismo,
        cliente,
        LocalDate.of(2026, 11, 10),
        LocalDate.of(2026, 11, 12)
    );

    // Primera estrategia: precio normal
    alquiler.setEstrategia(new PoliticaNormal());

    assertEquals(150.0, alquiler.calcularPrecioAlquiler(), 0.001);

    // Cambiamos a temporada alta
    alquiler.setEstrategia(new PoliticaTemporadaAlta());

    assertEquals(300.0, alquiler.calcularPrecioAlquiler(), 0.001);
}
}