package framework;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import models.modelo;

public class Controlador {
    private Map<String, Controlador> controladores;
    private Map<String, Function<modelo, String>> acciones;

    public String despachar(modelo solicitud) {
        Controlador c = Optional
                .ofNullable(controladores.get(solicitud.getRecurso()))
                .orElseThrow(() -> new RuntimeException(
                        "Controlador no encontrado: " + solicitud.getRecurso()));

        Function<modelo, String> accion = Optional
                .ofNullable(acciones.get(solicitud.getAccion()))
                .orElseThrow(() -> new RuntimeException(
                        "Acción no encontrada: " + solicitud.getAccion()));

        return accion.apply(solicitud);
    }
}

