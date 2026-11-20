package framework;

import java.util.List;

public class Solicitud {
	// Solicitudes: tareas/alta/Estudiar para el parcial
	// Recurso = "tareas"
	// Accion = "alta"
	// Parametros = ["Estudiar para el parcial"]
	
	private String recurso;
	private String accion;
	private List<String> parametros;
	private Sesion sesion;

	public Solicitud(String recurso, String accion, List<String> parametros) {
		this.recurso = recurso;
		this.accion = accion;
		this.parametros = parametros;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}
	
	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	public List<String> getParametros() {
		return parametros;
	}
	
	public String getParametro(int indice, String nombre, Validador<String> validador) {
		String valor = getParametro(indice, nombre);
		validador.validar(valor);
		return valor;
	}


	public String getParametro(int indice, String nombre) {
		if(indice >= parametros.size()) {
			throw new SolicitudInvalidaException("Falta el parametro '" + nombre + "'");
		}
		return parametros.get(indice);
	}

	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}

	@Override
	public String toString() {
		return "Solicitud [recurso=" + recurso + ", accion=" + accion + ", parametros=" + parametros + "]";
	}
	
}