package telem;

import java.time.temporal.ChronoUnit;

public class PoliticaOferton implements PoliticaPrecio {

    @Override
    public double calcular(Alquiler alquiler) {

        long dias = ChronoUnit.DAYS.between(
            alquiler.getFechaInicio(),
            alquiler.getFechaFin()
        ) + 1;

        double precio = dias * Alquiler.PRECIO_BASE;

        precio = precio * 0.70;

        if (dias >= 8) {
            precio = precio * 0.90;
        }

        return precio;
    }
}