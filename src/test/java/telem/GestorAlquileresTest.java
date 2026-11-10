package telem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GestorAlquileresTest {

    private static final LocalDate INICIO = LocalDate.of(2026, 11, 10);

    @Rule
    public TemporaryFolder carpeta = new TemporaryFolder();

    @Test
    public void guardaYRecuperaClientesVehiculosYAlquileres() throws IOException {
        Path fichero = carpeta.getRoot().toPath().resolve("alquileres.json");
        GestorAlquileres origen = new GestorAlquileres();
        Vehiculo turismo = origen.obtenerVehiculos("Turismo")[0];
        turismo.setDisponible(true);
        Cliente cliente = new Cliente("Lucía Núñez", "12345678A");
        origen.agregarAlquiler(new Alquiler(turismo, cliente, INICIO, INICIO.plusDays(2)));
        origen.agregarAlquiler(new Alquiler(turismo, cliente, INICIO.plusDays(5), INICIO.plusDays(6)));
        origen.guardarDatos(fichero);

        JSONObject json = leerJson(fichero);
        assertEquals(7, json.getJSONArray("clientes").length());
        assertEquals(9, json.getJSONArray("vehiculos").length());
        assertEquals(2, json.getJSONArray("alquileres").length());

        GestorAlquileres destino = new GestorAlquileres();
        destino.recuperarDatos(fichero);
        Alquiler[] alquileres = destino.obtenerAlquileres();
        assertEquals("Lucía Núñez", alquileres[0].getCliente().getNombre());
        assertEquals("12345678A", alquileres[0].getCliente().getDni());
        assertEquals(INICIO, alquileres[0].getFechaInicio());
        assertEquals(INICIO.plusDays(2), alquileres[0].getFechaFin());
        assertSame(alquileres[0].getCliente(), alquileres[1].getCliente());
        assertSame(destino.obtenerVehiculos("Turismo")[0], alquileres[0].getVehiculo());
        assertTrue(alquileres[0].getVehiculo().isDisponible());
        assertEquals(450.0, ((Turismo) alquileres[0].getVehiculo()).getCapacidadMaletero(), 0.001);
        assertEquals(200, ((Motocicleta) destino.obtenerVehiculos("Motocicleta")[0]).getCilindrada());
        assertEquals(1400, ((VehiculoIndustrial) destino.obtenerVehiculos("VehiculoIndustrial")[0])
                .getCapacidadCarga());
        assertNull(alquileres[2]);
    }

    @Test
    public void recuperarDosVecesReemplazaElEstadoYActualizaElContador() throws IOException {
        Path fichero = carpeta.newFile("alquileres.json").toPath();
        GestorAlquileres gestor = new GestorAlquileres();
        gestor.agregarAlquiler(alquilerDeEjemplo(gestor));
        gestor.guardarDatos(fichero);
        gestor.agregarAlquiler(alquilerDeEjemplo(gestor));

        gestor.recuperarDatos(fichero);
        gestor.recuperarDatos(fichero);
        assertNull(gestor.obtenerAlquileres()[1]);

        Alquiler nuevo = alquilerDeEjemplo(gestor);
        gestor.agregarAlquiler(nuevo);
        assertSame(nuevo, gestor.obtenerAlquileres()[1]);
        assertNull(gestor.obtenerAlquileres()[2]);
    }

    @Test
    public void recuperaUnFicheroVacioDeRegistros() throws IOException {
        Path fichero = carpeta.newFile("vacio.json").toPath();
        escribir(fichero, datosVacios().toString());
        GestorAlquileres gestor = new GestorAlquileres();
        gestor.agregarAlquiler(alquilerDeEjemplo(gestor));

        gestor.recuperarDatos(fichero);

        assertEquals(0, gestor.obtenerVehiculos("Turismo").length);
        assertNull(gestor.obtenerAlquileres()[0]);
    }

    @Test
    public void conservaElEstadoSiElJsonOUnAlquilerSonInvalidos() throws IOException {
        Path fichero = carpeta.newFile("invalido.json").toPath();
        GestorAlquileres gestor = new GestorAlquileres();
        Alquiler original = alquilerDeEjemplo(gestor);
        gestor.agregarAlquiler(original);

        escribir(fichero, "{");
        comprobarRecuperacionFallida(gestor, fichero);
        escribir(fichero, datosVacios().toString() + " texto sobrante");
        comprobarRecuperacionFallida(gestor, fichero);
        JSONObject datos = datosVacios();
        datos.getJSONArray("clientes").put(new JSONObject(original.getCliente().toString()));
        datos.getJSONArray("vehiculos").put(new JSONObject(original.getVehiculo().toString()));
        datos.getJSONArray("alquileres").put(new JSONObject(original.toString()));
        datos.getJSONArray("alquileres").put(new JSONObject(original.toString())
                .put("matricula", "NO-EXISTE"));
        escribir(fichero, datos.toString());
        comprobarRecuperacionFallida(gestor, fichero);
        assertSame(original, gestor.obtenerAlquileres()[0]);
        assertEquals(3, gestor.obtenerVehiculos("Turismo").length);
    }

    @Test
    public void rechazaCatalogosConIdentificadoresDuplicados() throws IOException {
        Path fichero = carpeta.newFile("duplicado.json").toPath();
        JSONObject datos = datosVacios();
        datos.getJSONArray("clientes")
                .put(new JSONObject(new Cliente("Ana", "001").toString()))
                .put(new JSONObject(new Cliente("Luis", "001").toString()));
        escribir(fichero, datos.toString());

        comprobarRecuperacionFallida(new GestorAlquileres(), fichero);
    }

    @Test
    public void unGuardadoQueSuperaLaCapacidadConservaElFicheroAnterior() throws IOException {
        Path fichero = carpeta.newFile("anterior.json").toPath();
        GestorAlquileres gestor = new GestorAlquileres();
        gestor.guardarDatos(fichero);
        JSONObject anterior = leerJson(fichero);
        Vehiculo vehiculo = gestor.obtenerVehiculos("Turismo")[0];
        for (int i = 0; i < 5; i++) {
            gestor.agregarAlquiler(new Alquiler(vehiculo, new Cliente("Cliente", "nuevo-" + i),
                    INICIO, INICIO));
        }
        try {
            gestor.guardarDatos(fichero);
            fail("El catálogo supera la capacidad del gestor");
        } catch (IOException esperada) {
            assertTrue(anterior.similar(leerJson(fichero)));
        }
    }

    @Test
    public void elMenuGuardaRecuperaYPermiteCancelar() throws IOException {
        Path origen = carpeta.newFile("origen.json").toPath();
        Path destino = carpeta.getRoot().toPath().resolve("copia con espacios.json");
        escribir(origen, datosVacios().toString());
        GestorAlquileres gestor = new GestorAlquileres(new Scanner(
                "G\n\nR\n" + origen + "\nG\n" + destino + "\nS\n"));
        assertFalse(Files.exists(destino));

        gestor.menuPrincipal();

        assertTrue(datosVacios().similar(leerJson(destino)));
        assertEquals(0, gestor.obtenerVehiculos("Turismo").length);
    }

    private static Alquiler alquilerDeEjemplo(GestorAlquileres gestor) {
        return new Alquiler(gestor.obtenerVehiculos("Turismo")[0],
                new Cliente("Clara Videncia", "001"), INICIO, INICIO.plusDays(1));
    }

    private static JSONObject datosVacios() {
        return new JSONObject().put("clientes", new JSONArray())
                .put("vehiculos", new JSONArray()).put("alquileres", new JSONArray());
    }

    private static void comprobarRecuperacionFallida(GestorAlquileres gestor, Path fichero)
            throws IOException {
        try {
            gestor.recuperarDatos(fichero);
            fail("Se esperaba un error al recuperar el fichero");
        } catch (IOException esperada) {
            assertTrue(esperada.getMessage().contains("no es válido"));
        }
    }

    private static JSONObject leerJson(Path fichero) throws IOException {
        return new JSONObject(new String(Files.readAllBytes(fichero), StandardCharsets.UTF_8));
    }

    private static void escribir(Path fichero, String contenido) throws IOException {
        Files.write(fichero, contenido.getBytes(StandardCharsets.UTF_8));
    }
}
