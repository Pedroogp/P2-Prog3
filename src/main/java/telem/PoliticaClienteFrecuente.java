
package telem;

import java.time.temporal.ChronoUnit;

public class PoliticaClienteFrecuente implements PoliticaPrecio {

    @Override
    public double calcular(Alquiler alquiler) {

        long dias = ChronoUnit.DAYS.between(
            alquiler.getFechaInicio(),
            alquiler.getFechaFin()
        ) + 1;

        double precioBase = Alquiler.PRECIO_BASE;
        double precio;

        if (dias <= 3) {
            precio = dias * precioBase * 0.80;
        } else {
            precio = 3 * precioBase * 0.80
                   + (dias - 3) * precioBase * 0.60;
        }

        return precio;
    }
}