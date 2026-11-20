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

    
    // 1. Regla para el nombre del alumno: no vacío y entre 3 y 40 caracteres
    private static final Validador<String> VALIDADOR_NOMBRE_ALUMNO =
        new ValidadorLargoTexto(3, 40, "nombre del alumno");

    // 2. Regla para la nota: entre 0 y 10 Y con máximo 2 decimales
    private static final Validador<String> VALIDADOR_NOTA =
        new ValidadorRangoNota(0, 10, "nota")
            .y(new ValidadorDecimales(2, "nota"));

    private String cargarNota(Solicitud s) {
        // Si algo falla acá, lanza SolicitudInvalidaException solo y frena la ejecución
        String nombreAlumno = s.getParametro(0, "alumno", VALIDADOR_NOMBRE_ALUMNO);
        String notaTexto = s.getParametro(1, "nota", VALIDADOR_NOTA);
        
        double nota = Double.parseDouble(notaTexto.replace(",", "."));
        
        // Acá llamás a tu servicio para guardar la nota...
        // servicioNotas.cargar(nombreAlumno, nota);
        
        return "Nota " + nota + " registrada para " + nombreAlumno;
    }
    

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        // TODO Auto-generated method stub
        return Map.of("verNota", this::verNota,
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
    public String verNota(Solicitud solicitud) {
        return "notas";
    }

    public String modificarNota(Solicitud solicitud) {
        return "notas";
    }
}

