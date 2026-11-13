package framework;

import java.util.Optional;

public class Controlador {
    public String despachar(Solicitud solicitud) {
        Controlador c = Optional.ofNullable(controladores.get(solicitud.getRecurso()))
                .orElseThrow(() -> new FrameworkException("Controlador no encontrado: " + solicitud.getControlador()));
        Function<Solicitud, String> accion = Optional.ofNullable(c.acciones())
                .get(solicitud.getAccion())
                .orElseThrow(() -> new FrameworkException("Acción no encontrada: " + solicitud.getAccion()));
        return accion.apply(solicitud);
    }

}
