package telem;

import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FactoriaPoliticasPrecioTest {

    private final LocalDate inicio = LocalDate.of(2026, 11, 10);
    private final Alquiler alquiler = new Alquiler(
            new Turismo("1234ABC", Categoria.NORMAL, 2020, 5, 450.0),
            new Cliente("Ana", "12345678A"),
            inicio, inicio.plusDays(7));

    @Test
    public void creaPoliticaNormal() {
        comprobarPolitica(PoliticasDePrecios.Normal, PoliticaNormal.class, 400.0);
    }

    @Test
    public void creaPoliticaTemporadaAlta() {
        comprobarPolitica(PoliticasDePrecios.TemporadaAlta, PoliticaTemporadaAlta.class, 800.0);
    }

    @Test
    public void creaPoliticaClienteFrecuente() {
        comprobarPolitica(PoliticasDePrecios.ClienteFrecuente, PoliticaClienteFrecuente.class, 270.0);
    }

    @Test
    public void creaPoliticaOferton() {
        comprobarPolitica(PoliticasDePrecios.Oferton, PoliticaOferton.class, 252.0);
    }

    @Test
    public void elAlquilerUsaLaPoliticaNormalPorDefecto() {
        assertEquals(400.0, alquiler.calcularPrecioAlquiler(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rechazaUnaPoliticaNula() {
        FactoriaPoliticasPrecio.getPolitica(null);
    }

    private void comprobarPolitica(PoliticasDePrecios tipo,
            Class<? extends PoliticaPrecio> claseEsperada, double precioEsperado) {
        PoliticaPrecio politica = FactoriaPoliticasPrecio.getPolitica(tipo);
        assertEquals(claseEsperada, politica.getClass());
        alquiler.setEstrategia(politica);
        assertEquals(precioEsperado, alquiler.calcularPrecioAlquiler(), 0.001);
    }
}
