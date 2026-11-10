package telem;

public final class FactoriaPoliticasPrecio {

    private FactoriaPoliticasPrecio() {
    }

    public static PoliticaPrecio getPolitica(PoliticasDePrecios tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Debe indicar una política de precios");
        }

        switch (tipo) {
            case Normal:
                return new PoliticaNormal();
            case TemporadaAlta:
                return new PoliticaTemporadaAlta();
            case ClienteFrecuente:
                return new PoliticaClienteFrecuente();
            case Oferton:
                return new PoliticaOferton();
            default:
                throw new IllegalArgumentException("Política de precios desconocida: " + tipo);
        }
    }
}
