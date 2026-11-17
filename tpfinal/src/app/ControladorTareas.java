package app;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import framework.Controlador;
import framework.Solicitud;

public class ControladorTareas implements Controlador{
	
	private List<Tarea> tareas = new ArrayList<>();

	@Override
	public String recurso() {
		return "tareas";
	}

	@Override
	public Map<String, Function<Solicitud, String>> acciones() {
		return Map.of(
				"alta", this::alta,
				"listar", this::listar
				);
	}
	
	private synchronized String alta(Solicitud s) {
		String descripcionTarea = s.getParametro(0, "tarea");
		tareas.add(new Tarea(descripcionTarea));
		return "Tarea agregada: " + descripcionTarea;
	}
	
	private String listar(Solicitud s) {
		return tareas.stream()
				.map(Tarea::descripcion)
				.collect(Collectors.joining(", ", "[", "]"));
	}

}
