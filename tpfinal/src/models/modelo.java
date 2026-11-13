package models;

import java.util.List;

public class modelo {
    // Solicitudes: transaccion/buscar/id

    private String recurso;
    private String accion;
    private List<String> parametros;

    public modelo(String recurso, String accion, List<String> parametros) {
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

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    @Override
    public String toString() {
        return "Solicitud [recurso=" + recurso + ", accion=" + accion + ", parametros=" + parametros + "]";
    }
}
