package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class VehiculoIndustrialTest {

    @Test
    public void testCrearVehiculoIndustrial() {

        VehiculoIndustrial vehiculo = new VehiculoIndustrial(
            "9876GHI", null, 2021, 3500, 2
        );

        assertEquals("9876GHI", vehiculo.getMatricula());
        assertEquals(2021, vehiculo.getAnnio());
        assertEquals(3500, vehiculo.getCapacidadCarga());
        assertEquals(2, vehiculo.getNumeroEjes());
    }

    @Test
    public void testDisponibilidad() {

        VehiculoIndustrial vehiculo = new VehiculoIndustrial(
            "9876GHI", Categoria.SUPERIOR, 2021, 3500, 2
        );

        vehiculo.setDisponible(true);
        assertTrue(vehiculo.isDisponible());

        vehiculo.setDisponible(false);
        assertFalse(vehiculo.isDisponible());
        assertEquals(Categoria.SUPERIOR, vehiculo.getCategoria());
    }
}