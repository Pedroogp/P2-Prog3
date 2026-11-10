package telem;



import org.junit.Test;
import static org.junit.Assert.*;

public class TurismoTest {

    @Test
    public void testCrearTurismo() {

        Turismo turismo = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );

        assertEquals("1234ABC", turismo.getMatricula());
        assertEquals(2020, turismo.getAnnio());
        assertEquals(5, turismo.getPlazas());
        assertEquals(450.0, turismo.getCapacidadMaletero(), 0.001);
        assertEquals(Categoria.NORMAL, turismo.getCategoria());
    }

    @Test
    public void testDisponibilidad() {

        Turismo turismo = new Turismo(
            "1234ABC", null, 2020, 5, 450.0
        );

        turismo.setDisponible(true);
        assertTrue(turismo.isDisponible());

        turismo.setDisponible(false);
        assertFalse(turismo.isDisponible());
    }
}


