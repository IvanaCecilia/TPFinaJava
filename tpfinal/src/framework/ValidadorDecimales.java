package framework;


public class ValidadorDecimales implements Validador<String> {
    private final int maxDecimales;
    private final String nombreCampo;

    public ValidadorDecimales(int maxDecimales, String nombreCampo) {
        this.maxDecimales = maxDecimales;
        this.nombreCampo = nombreCampo;
    }

    @Override
    public void validar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' es obligatorio");
        }
        String normalizado = valor.trim().replace(",", ".");
        int posicionPunto = normalizado.indexOf('.');
        
        if (posicionPunto != -1) {
            int cantidadDecimales = normalizado.length() - posicionPunto - 1;
            if (cantidadDecimales > maxDecimales) {
                throw new SolicitudInvalidaException(
                    "El campo '" + nombreCampo + "' puede tener como máximo " + maxDecimales + " decimales"
                );
            }
        }
    }
}

