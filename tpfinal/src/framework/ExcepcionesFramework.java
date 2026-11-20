package framework;

public class ExcepcionesFramework extends RuntimeException {
	public ExcepcionesFramework(String mensaje) {
		super(mensaje);
	}
}

class SolicitudInvalidaException extends ExcepcionesFramework {

	public SolicitudInvalidaException(String mensaje) {
		super(mensaje);
	}

}

class AccesoDenegadoException extends ExcepcionesFramework {
	public AccesoDenegadoException(String mensaje) {
		super(mensaje);
	}
}
