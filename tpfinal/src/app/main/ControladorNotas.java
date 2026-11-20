package app.main;

import java.util.Map;
import java.util.function.Function;

import app.models.Notas;
import framework.Controlador;
import framework.Rol;
import framework.Servicios;
import framework.Solicitud;
import framework.Validador;
import framework.ValidadorDecimales;
import framework.ValidadorLargoTexto;
import framework.ValidadorRangoNota;

public class ControladorNotas implements Controlador {

	private static final Validador<String> VALIDADOR_NOMBRE_ALUMNO = new ValidadorLargoTexto(3, 40, "nombre del alumno");
	private static final Validador<String> VALIDADOR_NOTA = new ValidadorRangoNota(0, 10, "nota")
			.y(new ValidadorDecimales(2, "nota"));

	private ServicioNotas notas;

	public ControladorNotas(Servicios servicios) {
		this.notas = servicios.obtener(ServicioNotas.class);
	}

	@Override
	public Map<String, Function<Solicitud, String>> acciones() {
		return Map.of(
				"verNota", this::verNota,
				"cargarNota", this::cargarNota,
				"modificarNota", this::modificarNota
		);
	}

	@Override
	public String recurso() {
		return "notas";
	}

	@Override
	public Map<String, Rol> permisos() {
		return Map.of(
				"cargarNota", Rol.ADMIN,
				"modificarNota", Rol.ADMIN,
				"verNota", Rol.USUARIO
		);
	}

	public String verNota(Solicitud s) {
		String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
		Notas registro = notas.verNota(nombreAlumno);
		return registro.toString();
	}

	public String cargarNota(Solicitud s) {
		String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
		String notaTexto = s.getParametro(1, "nota", VALIDADOR_NOTA);
		double nota = Double.parseDouble(notaTexto.replace(",", "."));
		int posicion = notas.cargarNota(nombreAlumno, nota);
		return "Nota " + posicion + " cargada para " + nombreAlumno + ": " + nota;
	}

	public String modificarNota(Solicitud s) {
		String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
		int posicion = parsearPosicion(s.getParametro(1, "posicion"));
		String notaTexto = s.getParametro(2, "nota", VALIDADOR_NOTA);
		double nota = Double.parseDouble(notaTexto.replace(",", "."));
		notas.modificarNota(nombreAlumno, posicion, nota);
		return "Nota " + posicion + " modificada para " + nombreAlumno + ": " + nota;
	}

	private int parsearPosicion(String valor) {
		try {
			int posicion = Integer.parseInt(valor.trim());
			if (posicion < 1 || posicion > 3) {
				throw new framework.ExcepcionesFramework("La posicion debe ser 1, 2 o 3");
			}
			return posicion;
		} catch (NumberFormatException ex) {
			throw new framework.ExcepcionesFramework("La posicion debe ser 1, 2 o 3");
		}
	}
}