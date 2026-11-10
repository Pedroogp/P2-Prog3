package telem;

import java.time.LocalDate;
import java.util.Locale;

public class App {

    public static void main(String[] args) {
        Vehiculo vehiculo = new Turismo("1234ABC", Categoria.NORMAL, 2020, 5, 450.0);
        Cliente cliente = new Cliente("Ana", "12345678A");
        LocalDate inicio = LocalDate.of(2026, 11, 10);
        Alquiler alquiler = new Alquiler(vehiculo, cliente, inicio, inicio.plusDays(7));

        System.out.println("Precio de un alquiler de 8 días por política:");
        for (PoliticasDePrecios tipo : PoliticasDePrecios.values()) {
            alquiler.setEstrategia(FactoriaPoliticasPrecio.getPolitica(tipo));
            System.out.printf(Locale.forLanguageTag("es-ES"), "%s: %.2f EUR%n",
                    tipo, alquiler.calcularPrecioAlquiler());
        }
    }
}
