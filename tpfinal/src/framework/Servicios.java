package framework;

import java.util.HashMap;
import java.util.Map;

public class Servicios {
	
	// Asigamos la clase del servicio, al objeto que queremos referenciar:
	private Map<Class<?>, Object> servicios = new HashMap<>();
	
	public <T> void registrar(Class<T> tipo, T servicio) {
		servicios.put(tipo, servicio);
	}
	
	public <T> T obtener(Class<T> tipo) {
		Object servicio = servicios.get(tipo);
		if(servicio == null) {
			throw new ExcepcionesFramework("No hay un servicio registrado de tipo: " + tipo);
		}
		return tipo.cast(servicio);
	}
}
