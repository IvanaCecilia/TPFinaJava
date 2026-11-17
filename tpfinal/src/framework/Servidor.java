package framework;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import comunicador.Comunicador;
import exeptions.excepcion;
import models.modelo;
import services.ParsearSolicitud;

public class Servidor {
    private int puerto;
    private Router router = new Router();

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    public void registrar(Controlador controlador) {
        router.registrar(controlador);
    }

    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread hilo = new Thread(() -> atender(socket, router));
                hilo.start();
            }
        }
    }

    public static void atender(Socket socket, Router router) {
        String hilo = Thread.currentThread().getName();
        try (Socket s = socket;
             Comunicador com = new Comunicador(s.getInputStream(), s.getOutputStream())) {
            com.enviar("Bienvenido al servidor");

            String linea;
            while ((linea = com.recibir()) != null && !linea.equalsIgnoreCase("salir")) {
                com.enviar(procesar(linea, router));
            }
        } catch (IOException | excepcion e) {
            System.out.println("Error en el hilo " + hilo + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error en el hilo " + hilo + ": " + e.getMessage());
        }
        System.out.println("Hilo " + hilo + " se ha desconectado");
    }

    private static String procesar(String linea, Router router) {
        try {
            modelo solicitud = ParsearSolicitud.parsearSolicitud(linea);
            return "Ok " + router.despachar(solicitud);
        } catch (excepcion e) {
            return "Error: " + e.getMessage();
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}