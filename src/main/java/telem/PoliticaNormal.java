package telem;

import java.time.temporal.ChronoUnit;

public class PoliticaNormal implements PoliticaPrecio {

    @Override
    public double calcular(Alquiler alquiler) {

        long dias = ChronoUnit.DAYS.between(
            alquiler.getFechaInicio(),
            alquiler.getFechaFin()
        ) + 1;

        return dias * Alquiler.PRECIO_BASE;
    }
}