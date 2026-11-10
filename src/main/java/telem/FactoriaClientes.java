package telem;

import org.json.JSONObject;

public class FactoriaClientes {
    public static Cliente getCliente(String cadena) {
        return new Cliente(new JSONObject(cadena));
    }

    public static void main(String[] args) {
        Cliente original = new Cliente("Clara Videncia", "001");
        String cadena = original.toString();
        Cliente recuperado = getCliente(cadena);

        System.out.println(cadena);
        System.out.println(recuperado.getNombre() + " - " + recuperado.getDni());
    }
}