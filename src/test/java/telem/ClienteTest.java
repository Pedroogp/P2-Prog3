package telem;

import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteTest {

     @Test
    public void testCrearCliente() {

        Cliente cliente = new Cliente("Juan Pérez", "12345678A");

        assertEquals("Juan Pérez", cliente.getNombre());
        assertEquals("12345678A", cliente.getDni());

    }


    @Test
    public void testToString() {
        Cliente cliente = new Cliente("Juan Pérez", "12345678A");

        String json = cliente.toString();

        assertTrue(json.contains("Juan Pérez"));
        assertTrue(json.contains("12345678A"));
}
}
    

