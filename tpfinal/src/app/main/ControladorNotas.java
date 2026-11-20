package app.main;
import framework.Controlador;
import framework.Rol;
import framework.Solicitud;
import java.util.Map;
import java.util.function.Function;
public class ControladorNotas implements Controlador {

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
        // TODO Auto-generated method stub
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
    public String cargarNota(Solicitud solicitud) {
        return "notas";
    }
    public String modificarNota(Solicitud solicitud) {
        return "notas";
    }
}

