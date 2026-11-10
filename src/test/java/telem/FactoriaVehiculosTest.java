
package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactoriaVehiculosTest {

    @Test
    public void testRecuperarTurismo() {

        Turismo original = new Turismo(
            "1234ABC", Categoria.NORMAL, 2020, 5, 450.0
        );
        original.setDisponible(true);

        String json = original.toString();

        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(json);

        assertTrue(recuperado instanceof Turismo);
        assertEquals("1234ABC", recuperado.getMatricula());
        assertEquals(Categoria.NORMAL, recuperado.getCategoria());
        assertEquals(2020, recuperado.getAnnio());
        assertTrue(recuperado.isDisponible());

        Turismo turismo = (Turismo) recuperado;

        assertEquals(5, turismo.getPlazas());
        assertEquals(450.0, turismo.getCapacidadMaletero(), 0.001);
    }

    @Test
    public void testRecuperarMotocicleta() {

        Motocicleta original = new Motocicleta(
            "5678DEF", Categoria.ESPECIAL, 2022, 600
        );

        String json = original.toString();

        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(json);

        assertTrue(recuperado instanceof Motocicleta);
        assertEquals("5678DEF", recuperado.getMatricula());
        assertEquals(Categoria.ESPECIAL, recuperado.getCategoria());

        Motocicleta moto = (Motocicleta) recuperado;

        assertEquals(600, moto.getCilindrada());
    }

    @Test
    public void testRecuperarVehiculoIndustrial() {

        VehiculoIndustrial original = new VehiculoIndustrial(
            "9876GHI", Categoria.SUPERIOR, 2021, 3500, 2
        );

        String json = original.toString();

        Vehiculo recuperado = FactoriaVehiculos.getVehiculo(json);

        assertTrue(recuperado instanceof VehiculoIndustrial);
        assertEquals("9876GHI", recuperado.getMatricula());
        assertEquals(Categoria.SUPERIOR, recuperado.getCategoria());

        VehiculoIndustrial industrial = (VehiculoIndustrial) recuperado;

        assertEquals(3500, industrial.getCapacidadCarga());
        assertEquals(2, industrial.getNumeroEjes());
    }
}

