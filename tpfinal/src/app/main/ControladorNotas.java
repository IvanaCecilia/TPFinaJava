package app.main;
import framework.Controlador;
import framework.Rol;
import framework.Solicitud;
import java.util.Map;
import java.util.function.Function;

import framework.Validador;
import framework.ValidadorDecimales;
import framework.ValidadorLargoTexto;
import framework.ValidadorRangoNota;
public class ControladorNotas implements Controlador {

    private static final Validador<String> VALIDADOR_NOMBRE_ALUMNO =
        new ValidadorLargoTexto(3, 40, "nombre del alumno");
    private static final Validador<String> VALIDADOR_NOTA =
        new ValidadorRangoNota(0, 10, "nota")
            .y(new ValidadorDecimales(2, "nota"));

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
        return "Nota para " + nombreAlumno + ": 8.5";
    }

    public String cargarNota(Solicitud s) {
        String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
        String notaTexto = s.getParametro(1, "nota", VALIDADOR_NOTA);
        double nota = Double.parseDouble(notaTexto.replace(",", "."));
        return "Nota " + nota + " registrada para " + nombreAlumno;
    }

    public String modificarNota(Solicitud s) {
        String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
        String notaTexto = s.getParametro(1, "nota", VALIDADOR_NOTA);
        double nota = Double.parseDouble(notaTexto.replace(",", "."));
        return "Nota " + nota + " modificada para " + nombreAlumno;
    }
}