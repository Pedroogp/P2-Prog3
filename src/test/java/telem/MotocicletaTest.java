package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class MotocicletaTest {

    @Test
    public void testCrearMotocicleta() {

        Motocicleta moto = new Motocicleta(
            "5678DEF", Categoria.ESPECIAL, 2022, 600
        );

        assertEquals("5678DEF", moto.getMatricula());
        assertEquals(2022, moto.getAnnio());
        assertEquals(600, moto.getCilindrada());
        assertEquals(Categoria.ESPECIAL, moto.getCategoria());
    }

    @Test
    public void testDisponibilidad() {

        Motocicleta moto = new Motocicleta(
            "5678DEF", null, 2022, 600
        );

        moto.setDisponible(true);
        assertTrue(moto.isDisponible());

        moto.setDisponible(false);
        assertFalse(moto.isDisponible());
    }
}