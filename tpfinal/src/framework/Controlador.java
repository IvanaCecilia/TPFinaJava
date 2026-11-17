package framework;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import models.modelo;

public class Controlador {
    private final String recurso;
    private final Map<String, Function<modelo, String>> acciones = new HashMap<>();

    public Controlador(String recurso) {
        this.recurso = recurso;
    }

    public String recurso() {
        return recurso;
    }

    public Map<String, Function<modelo, String>> acciones() {
        return acciones;
    }

    public void registrarAccion(String accion, Function<modelo, String> implementacion) {
        acciones.put(accion, implementacion);
    }

    public String despachar(modelo solicitud) {
        Function<modelo, String> accion = acciones.get(solicitud.getAccion());

        if (accion == null) {
            throw new RuntimeException(
                    "Acción no encontrada: " + solicitud.getAccion());
        }

        return accion.apply(solicitud);
    }
}