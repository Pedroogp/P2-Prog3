package telem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GestorAlquileres {

    private int contadorAlquileres = 0;
    private Alquiler[] alquileres = new Alquiler[1000];

    private Cliente[] clientes = new Cliente[10];
    private Vehiculo[] vehiculos = new Vehiculo[100];
    private final Scanner entrada;

    public GestorAlquileres() {
        this(new Scanner(System.in));
    }

    GestorAlquileres(Scanner entrada) {
        this.entrada = entrada;
        inicializarClientes();
        inicializarVehiculos();
    }

    private void inicializarClientes() {
        clientes[0] = new Cliente("Clara Videncia", "001");
        clientes[1] = new Cliente("Aitor Menta", "002");
        clientes[2] = new Cliente("Lola Mento", "003");
        clientes[3] = new Cliente("Armando Guerra", "004");
        clientes[4] = new Cliente("Ana Lítica", "005");
        clientes[5] = new Cliente("Carmelo Cotón", "006");
    }

    private void inicializarVehiculos() {
        vehiculos[0] = new Turismo("TNA2020", Categoria.NORMAL, 2020, 5, 450.0);
        vehiculos[1] = new Motocicleta("MNA2021", Categoria.NORMAL, 2021, 200);
        vehiculos[2] = new VehiculoIndustrial("CNA2019", Categoria.NORMAL, 2019, 1400, 2);
        vehiculos[3] = new Turismo("TSA2024", Categoria.SUPERIOR, 2024, 5, 550.0);
        vehiculos[4] = new Motocicleta("MSA2021", Categoria.SUPERIOR, 2021, 500);
        vehiculos[5] = new VehiculoIndustrial("CSA2025", Categoria.SUPERIOR, 2025, 2400, 3);
        vehiculos[6] = new Turismo("TEA2026", Categoria.ESPECIAL, 2026, 7, 650.0);
        vehiculos[7] = new Motocicleta("MEA2026", Categoria.ESPECIAL, 2026, 1000);
        vehiculos[8] = new VehiculoIndustrial("CEA2025", Categoria.ESPECIAL, 2025, 4400, 5);

    }

     public Vehiculo[] obtenerVehiculos(String tipo) {

        Vehiculo[] vehiculosFiltrados = new Vehiculo[vehiculos.length];
        int contador = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                if (vehiculo.getTipo().equals(tipo)) {
                    vehiculosFiltrados[contador++] = vehiculo;
                }
            }
        }

        Vehiculo[] resultado = new Vehiculo[contador];
        System.arraycopy(vehiculosFiltrados, 0, resultado, 0, contador);
        return resultado;
    }

    public Vehiculo[] obtenerVehiculos(String tipo, Categoria categoria) {

        System.out.println("GestorAlquileres: obtenerVehiculos(tipo, categoria): método no implementado");

        return null;
    }

    public void agregarAlquiler(Alquiler alquiler) {
        if (contadorAlquileres < alquileres.length) {
            alquileres[contadorAlquileres] = alquiler;
            contadorAlquileres++;
        } else {
            System.out.println("No se pueden agregar más alquileres. Capacidad máxima alcanzada.");
        }
    }

    public Alquiler[] obtenerAlquileres() {
        return alquileres;
    }

    public Alquiler[] obtenerAlquileres(Cliente cliente) {

        System.out.println("GestorAlquileres: obtenerAlquileres(cliente): método no implementado");

        return null;
    }

    private boolean esClienteFrecuente(Cliente cliente, int umbral) {

        System.out.println("GestorAlquileres: esClienteFrecuente(cliente, umbral): método no implementado");

        return false;
    }

    public void guardarDatos(Path fichero) throws IOException {
        Map<String, Cliente> clientesGuardados = new LinkedHashMap<>();
        Map<String, Vehiculo> vehiculosGuardados = new LinkedHashMap<>();
        for (Cliente cliente : clientes) {
            if (cliente != null) {
                clientesGuardados.put(cliente.getDni(), cliente);
            }
        }
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo != null) {
                vehiculosGuardados.put(vehiculo.getMatricula(), vehiculo);
            }
        }

        List<Alquiler> alquileresGuardados = new ArrayList<>();
        for (Alquiler alquiler : alquileres) {
            if (alquiler != null) {
                alquileresGuardados.add(alquiler);
                Cliente cliente = alquiler.getCliente();
                Vehiculo vehiculo = alquiler.getVehiculo();
                clientesGuardados.putIfAbsent(cliente.getDni(), cliente);
                vehiculosGuardados.putIfAbsent(vehiculo.getMatricula(), vehiculo);
            }
        }
        comprobarCapacidad("clientes", clientesGuardados.size(), clientes.length);
        comprobarCapacidad("vehículos", vehiculosGuardados.size(), vehiculos.length);

        JSONObject datos = new JSONObject()
                .put("clientes", serializar(clientesGuardados.values()))
                .put("vehiculos", serializar(vehiculosGuardados.values()))
                .put("alquileres", serializar(alquileresGuardados));

        Path destino = fichero.toAbsolutePath();
        if (destino.getFileName() == null) {
            throw new IOException("La ruta debe indicar un fichero");
        }
        Path temporal = Files.createTempFile(destino.getParent(), ".alquileres-", ".tmp");
        try {
            Files.write(temporal, datos.toString(2).getBytes(StandardCharsets.UTF_8));
            Files.move(temporal, destino, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(temporal);
        }
    }

    public void recuperarDatos(Path fichero) throws IOException {
        byte[] contenido = Files.readAllBytes(fichero);
        try {
            JSONTokener texto = new JSONTokener(new String(contenido, StandardCharsets.UTF_8));
            JSONObject datos = new JSONObject(texto);
            if (texto.nextClean() != 0) {
                throw new JSONException("Contenido inesperado después del objeto JSON");
            }

            JSONArray datosClientes = datos.getJSONArray("clientes");
            JSONArray datosVehiculos = datos.getJSONArray("vehiculos");
            JSONArray datosAlquileres = datos.getJSONArray("alquileres");
            comprobarCapacidad("clientes", datosClientes.length(), clientes.length);
            comprobarCapacidad("vehículos", datosVehiculos.length(), vehiculos.length);
            comprobarCapacidad("alquileres", datosAlquileres.length(), alquileres.length);

            List<Cliente> nuevosClientes = deserializar(datosClientes, FactoriaClientes::getCliente);
            List<Vehiculo> nuevosVehiculos = deserializar(datosVehiculos, FactoriaVehiculos::getVehiculo);
            comprobarClavesUnicas(nuevosClientes, Cliente::getDni, "DNI");
            comprobarClavesUnicas(nuevosVehiculos, Vehiculo::getMatricula, "matrícula");

            List<Alquiler> nuevosAlquileres = deserializar(datosAlquileres,
                    json -> FactoriaAlquileres.getAlquiler(json, nuevosClientes, nuevosVehiculos));

            clientes = nuevosClientes.toArray(new Cliente[clientes.length]);
            vehiculos = nuevosVehiculos.toArray(new Vehiculo[vehiculos.length]);
            alquileres = nuevosAlquileres.toArray(new Alquiler[alquileres.length]);
            contadorAlquileres = nuevosAlquileres.size();
        } catch (JSONException | IllegalArgumentException | DateTimeException e) {
            throw new IOException("El fichero de alquileres no es válido: " + e.getMessage(), e);
        }
    }

    private static JSONArray serializar(Iterable<?> objetos) {
        JSONArray resultado = new JSONArray();
        for (Object objeto : objetos) {
            resultado.put(new JSONObject(objeto.toString()));
        }
        return resultado;
    }

    private static <T> List<T> deserializar(JSONArray datos, Function<String, T> factoria) {
        List<T> resultado = new ArrayList<>(datos.length());
        for (int i = 0; i < datos.length(); i++) {
            resultado.add(factoria.apply(datos.getJSONObject(i).toString()));
        }
        return resultado;
    }

    private static void comprobarCapacidad(String coleccion, int cantidad, int capacidad)
            throws IOException {
        if (cantidad > capacidad) {
            throw new IOException("Se supera la capacidad de " + coleccion + " (" + capacidad + ")");
        }
    }

    private static <T> void comprobarClavesUnicas(List<T> objetos, Function<T, String> clave,
            String nombreClave) {
        Set<String> claves = new HashSet<>();
        for (T objeto : objetos) {
            String valor = clave.apply(objeto);
            if (!claves.add(valor)) {
                throw new IllegalArgumentException(nombreClave + " duplicado: " + valor);
            }
        }
    }

    public void menuPrincipal(){

        while (true) {
            System.out.println("Gestión de Alquileres");
            System.out.println("(N) identificarse, (G) guardar, (R) recuperar, (S) salir");

            if (!entrada.hasNextLine()) {
                return;
            }
            String opcionPrincipal = entrada.nextLine().trim();
            if (opcionPrincipal.equalsIgnoreCase("S")) {
                System.out.println("Bye ...");
                return;
            }
            if (opcionPrincipal.equalsIgnoreCase("G") || opcionPrincipal.equalsIgnoreCase("R")) {
                operarConFichero(opcionPrincipal.equalsIgnoreCase("G"));
                continue;
            }
            if (!opcionPrincipal.equalsIgnoreCase("N")) {
                System.out.println("Opción inválida.");
                continue;
            }

            Cliente cliente = identificarCliente();

            if (cliente != null) {
                System.out.println("Sr " + cliente.getNombre() + " Introduzca una opción");
                System.out.println("(1) seleccionar coche para alquilar");
                System.out.println("(2) entregar un coche alquilado");
                System.out.println("(3) listar su histórico de alquileres");

                int opcion = leerEntero();
                System.out.println("opción escogida: " + opcion);

                switch (opcion) {
                    case 1:
                        Vehiculo v = seleccionarVehiculo(cliente);
                        if (v != null && v.isDisponible()){
                            System.out.println("¿cuántos días desea alquilar el vehículo?");
                            int dias = leerEntero();
                            if (dias <= 0) {
                                System.out.println("La duración debe ser un número positivo.");
                                break;
                            }
                            LocalDate inicio = LocalDate.now();
                            LocalDate fin = inicio.plusDays(dias);

                            Alquiler q = new Alquiler(v, cliente, inicio, fin);
                            this.agregarAlquiler(q);

                            boolean usuarioFrecuente = this.esClienteFrecuente(cliente, 3);

                            if (usuarioFrecuente){

                                System.out.println("Alquiler realizado. Tarifa Usuario frecuente.");
                            }
                            else{
                                System.out.println("Alquiler realizado. Tarifa Normal.");

                            }

                        }
                        else{
                            System.out.println("Debe seleccionar un vehículo disponible");
                        }
                        break;
                    case 2:
                        System.out.println("opción no implementada");

                        break;
                    case 3:
                        Alquiler alqs [] = obtenerAlquileres(cliente);
                        if (alqs != null) {
                            for (Alquiler q: alqs){
                                System.out.print(q);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            else{
                System.out.println("Debe identificarse correctamente o salir de la app");
            }
        }
    }

    private void operarConFichero(boolean guardar) {
        System.out.println("Introduzca la ruta del fichero JSON (vacía para cancelar):");
        if (!entrada.hasNextLine()) {
            return;
        }
        String ruta = entrada.nextLine().trim();
        if (ruta.isEmpty()) {
            return;
        }

        try {
            Path fichero = Paths.get(ruta);
            if (guardar) {
                guardarDatos(fichero);
                System.out.println("Datos guardados en " + fichero);
            } else {
                recuperarDatos(fichero);
                System.out.println("Datos recuperados de " + fichero);
            }
        } catch (IOException | InvalidPathException e) {
            System.out.println("No se pudo " + (guardar ? "guardar" : "recuperar")
                    + " el fichero: " + e.getMessage());
        }
    }

    private int leerEntero() {
        if (!entrada.hasNextLine()) {
            return -1;
        }
        try {
            return Integer.parseInt(entrada.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Introduzca un número entero.");
            return -1;
        }
    }

    public Cliente identificarCliente(){

        boolean seleccionado = false;
        Cliente cliente = null;

        while (!seleccionado) {

            System.out.println("Bienvenido al Gestor de Alquileres");
            for (int i = 0; i < clientes.length; i++) {
                if (clientes[i] != null) {
                    System.out.println(i + " ---> " + clientes[i].getNombre() + " - " + clientes[i].getDni());
                }
            }
            System.out.println("Identífiquese como cliente con su número de cliente (-1 para salir):");
            int numeroCliente = leerEntero();
            if (numeroCliente == -1) {
                return null;
            }
            else if (numeroCliente < 0 || numeroCliente >= clientes.length || clientes[numeroCliente] == null) {
                System.out.println("identificarCliente:Número de cliente inválido.");
                return null;
            }
            else{
                seleccionado = true;
                cliente = clientes[numeroCliente];
            }
        }
        return cliente;
    }

    public Vehiculo seleccionarVehiculo(Cliente cliente){
        boolean seleccionado = false;
        Vehiculo vehiculo = null;

        while (!seleccionado) {

            System.out.println("Estimado Sr./Sra. " + cliente.getNombre());
            System.out.println("Introduzca el número correspondiente al tipo de vehículo que desea alquilar:");
            System.out.println("1. Turismo, 2. Motocicleta, 3. Vehículo Industrial: ");

            int tipoVehiculo = leerEntero();
            String tipo = "";
            Vehiculo[] elegibles;

            switch (tipoVehiculo) {
                case 1:
                    System.out.println("Ha seleccionado Turismo.");
                    tipo = "Turismo";
                    break;
                case 2:
                    System.out.println("Ha seleccionado Motocicleta.");
                    tipo = "Motocicleta";
                    break;
                case 3:
                    System.out.println("Ha seleccionado Vehículo Industrial.");
                    tipo = "VehiculoIndustrial";
                    break;
                case -1:
                    return null;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione un tipo de vehículo válido.");
                    continue;
            }

            System.out.println("Vehiculos disponibles");

            System.out.println("Estimado Sr./Sra. " + cliente.getNombre() + "\n seleccione categoría.");
            System.out.println("Introduzca el número correspondiente a la categoría que desea alquilar:");
            System.out.println("1. Normal, 2. Superior, 3. Especial, 4.Cualquiera: ");

            int categoriaVehiculo = leerEntero();

            switch (categoriaVehiculo) {
                case 1:
                    System.out.println("Ha seleccionado Categoría Normal.");
                    elegibles = obtenerVehiculos(tipo, Categoria.NORMAL);
                    break;
                case 2:
                    System.out.println("Ha seleccionado Categoría Superior.");
                    elegibles = obtenerVehiculos(tipo, Categoria.SUPERIOR);
                    break;
                case 3:
                    System.out.println("Ha seleccionado Categoría Especial.");
                    elegibles = obtenerVehiculos(tipo, Categoria.ESPECIAL);
                    break;
                case 4:
                    elegibles = obtenerVehiculos(tipo);
                    break;
                default:
                    return null;
            }

            if (elegibles == null || elegibles.length == 0) {
                System.out.println("No hay vehículos para esa selección.");
                return null;
            }

            System.out.println("Coches elegibles");

            for(int i = 0; i < elegibles.length; i++){
                System.out.println(i + " ---> " + elegibles[i]);
            }

            System.out.println("Introduzca número coche (sólo si está disponible)");
            int seleccion = leerEntero();
            if (seleccion < 0 || seleccion >= elegibles.length) {
                System.out.println("Número de vehículo inválido.");
                return null;
            }
            vehiculo = elegibles[seleccion];
            seleccionado = true;
        }
        return vehiculo;
    }

    public void listarAlquileres (){
        System.out.println("Listado de alquileres");
        for (Alquiler alq: alquileres){
            System.out.println(alq);
        }
        System.out.println();
    }

    public void listarVehiculos(){
        System.out.println("Listado de vehículos");
        for (Vehiculo veh: vehiculos){
            System.out.println(veh);
        }
        System.out.println();
    }

    public static void main(String [] args){
        GestorAlquileres app = new GestorAlquileres();
        app.menuPrincipal();
    }
}
