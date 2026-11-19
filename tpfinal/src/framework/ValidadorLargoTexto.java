package framework;


public class ValidadorLargoTexto implements Validador<String> {
    private final int min;
    private final int max;
    private final String nombreCampo;

    public ValidadorLargoTexto(int min, int max, String nombreCampo) {
        this.min = min;
        this.max = max;
        this.nombreCampo = nombreCampo;
    }

    @Override
    public void validar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' no puede estar vacío");
        }
        String limpio = valor.trim();
        if (limpio.length() < min || limpio.length() > max) {
            throw new SolicitudInvalidaException(
                "El campo '" + nombreCampo + "' debe tener entre " + min + " y " + max + " caracteres"
            );
        }
    }
}