package telem;

import org.json.JSONObject;

public class Cliente {
    String nombre;
    String dni;

    public Cliente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Cliente(JSONObject datos) {
        this(datos.getString("nombre"), datos.getString("dni"));
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    @Override
    public String toString() {
        JSONObject datos = new JSONObject();
        datos.put("nombre", nombre);
        datos.put("dni", dni);
        return datos.toString();
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Pérez", "12345678A");
        System.out.println(cliente);
    }
}