package app.main;

import java.util.LinkedHashMap;
import java.util.Map;

import app.main.models.Notas;
import framework.ExcepcionesFramework;

public class ServicioNotas {

	private Map<String, Notas> notasPorAlumno = new LinkedHashMap<>();

	public int cargarNota(String alumno, double nota) {
		Notas notas = notasPorAlumno.computeIfAbsent(clave(alumno), k -> new Notas(alumno));
		try {
			return notas.agregarNota(nota);
		} catch (IllegalStateException ex) {
			throw new ExcepcionesFramework(ex.getMessage());
		}
	}

	public void modificarNota(String alumno, int posicion, double nota) {
		Notas notas = obtener(alumno);
		try {
			notas.setNota(posicion, nota);
		} catch (IllegalArgumentException ex) {
			throw new ExcepcionesFramework("La posicion debe ser 1, 2 o 3");
		}
	}

	public Notas verNota(String alumno) {
		return obtener(alumno);
	}

	private Notas obtener(String alumno) {
		Notas notas = notasPorAlumno.get(clave(alumno));
		if (notas == null) {
			throw new ExcepcionesFramework("No hay notas cargadas para el alumno '" + alumno + "'");
		}
		return notas;
	}

	private String clave(String alumno) {
		return alumno.trim().toLowerCase();
	}
}
