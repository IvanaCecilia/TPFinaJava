package app.framework;

import java.util.Map;
import java.util.function.Function;

public interface Controlador {
	
	// Ej: tareas -> TareasController
	String recurso();
	
	// Ej: alta
	Map<String, Function<Solicitud, String>> acciones();
	
	default Map<String, Rol> permisos(){
		return Map.of();
	}
}