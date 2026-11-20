package app.main;

import framework.Router;
import framework.Servidor;
import framework.Servicios;
import framework.Sesion;
import framework.Solicitud;
import framework.Validador;
import framework.ValidadorDecimales;
import framework.ValidadorLargoTexto;
import framework.ValidadorRangoNota;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int puerto = 3000;
        Servidor servidor = new Servidor(puerto);
        Servicios servicios = new Servicios();

        ControladorNotas controladorNotas = new ControladorNotas();
        servicios.registrar(ControladorNotas.class, controladorNotas);
        servidor.registrar(controladorNotas);

        ControladorUsuarios controladorUsuarios = new ControladorUsuarios(servicios);
        servicios.registrar(ControladorUsuarios.class, controladorUsuarios);
        servidor.registrar(controladorUsuarios);

        try {
            servidor.iniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}