package framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Router {

	private Map<String, Controlador> controladores = new HashMap<>();
	
	public void registrar(Controlador controlador) {
		controladores.put(controlador.recurso(), controlador);
	}

	public String despachar(Solicitud solicitud) {
		Controlador c = Optional.ofNullable(controladores.get(solicitud.getRecurso()))
				.orElseThrow(() -> new RuntimeException("No existe el recurso: '" + solicitud.getRecurso() + "'"));

		Function<Solicitud, String> accion = Optional.ofNullable(c.acciones().get(solicitud.getAccion()))
				.orElseThrow(() -> new RuntimeException("No existe la accion: '" + solicitud.getAccion() + "'"));
		
		return accion.apply(solicitud);
	}
}