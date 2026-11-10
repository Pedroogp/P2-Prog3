
package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactoriaClientesTest {

    @Test
    public void testCrearClienteDesdeJSON() {

        // Creamos un cliente original
        Cliente original = new Cliente("Pedro", "12345678A");

        // Convertimos el cliente a JSON
        String json = original.toString();

        // Recuperamos el cliente mediante la factoría
        Cliente recuperado = FactoriaClientes.getCliente(json);

        // Comprobamos que los datos coinciden
        assertEquals(original.getNombre(), recuperado.getNombre());
        assertEquals(original.getDni(), recuperado.getDni());
    }
}
