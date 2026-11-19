package app.main;
import framework.Controlador;
import framework.Solicitud;
import java.util.Map;
import java.util.function.Function;
public class ControladorNotas implements Controlador {

    @Override
    public Map<String, Function<Solicitud, String>> acciones() {
        // TODO Auto-generated method stub
        return Map.of("mostrarNotas", this::mostrarNotas,
                        "subirNotas", this::subirNotas
        );
    }

    @Override
    public String recurso() {
        // TODO Auto-generated method stub
        return "notas";
    }
    public String mostrarNotas(Solicitud solicitud) {
        return "notas";
    }
    public String subirNotas(Solicitud solicitud) {
        return "notas";
    }
}

