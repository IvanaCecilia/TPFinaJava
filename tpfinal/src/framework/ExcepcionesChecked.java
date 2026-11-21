package framework;

public class ExcepcionesChecked extends Exception {
	public ExcepcionesChecked(String mensaje) {
		super(mensaje);
	}

	public ExcepcionesChecked(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
}