package app;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import framework.Controlador;
import models.modelo;

public class ControladorTareas extends Controlador {
	
	private List<Tarea> tareas = new ArrayList<>();

	public ControladorTareas() {
		super("tareas");
		registrarAccion("alta", this::alta);
		registrarAccion("listar", this::listar);
	}

	private synchronized String alta(modelo s) {
		List<String> parametros = s.getParametros();
		if (parametros.isEmpty()) {
			throw new IllegalArgumentException("Falta el parámetro 'tarea'");
		}
		String descripcionTarea = parametros.get(0);
		tareas.add(new Tarea(descripcionTarea));
		return "Tarea agregada: " + descripcionTarea;
	}
	
	private String listar(modelo s) {
		return tareas.stream()
				.map(Tarea::descripcion)
				.collect(Collectors.joining(", ", "[", "]"));
	}

}
