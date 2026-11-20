package framework;

public class ExcepcionesChecked extends Exception{
    public ExcepcionesChecked(String mensaje){
        super(mensaje);
    }
}

class AccesoDenegadoException extends ExcepcionesChecked {
	public AccesoDenegadoException(String mensaje) {
		super(mensaje);
	}
}