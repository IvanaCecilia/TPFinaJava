package framework;



public class ValidadorRangoNumerico implements Validador<String> {
    private final double min;
    private final double max;
    private final String nombreCampo;

    public ValidadorRangoNumerico(double min, double max, String nombreCampo) {
        this.min = min;
        this.max = max;
        this.nombreCampo = nombreCampo;
    }

    @Override
    public void validar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' es obligatorio");
        }
        try {
            // Reemplazamos coma por punto por si el usuario escribe "8,5"
            double numero = Double.parseDouble(valor.trim().replace(",", "."));
            if (numero < min || numero > max) {
                throw new SolicitudInvalidaException(
                    "El campo '" + nombreCampo + "' debe estar entre " + min + " y " + max
                );
            }
        } catch (NumberFormatException e) {
            throw new SolicitudInvalidaException("El campo '" + nombreCampo + "' debe ser un número válido");
        }
    }
}