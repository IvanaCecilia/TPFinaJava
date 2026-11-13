package framework;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import framework.Comunicador;
import framework.ParsearSolicitud;

public class Servidor {
private int puerto;
private Router router= new Router();
public Servidor(int puerto) {
    this.puerto = puerto;
}
public void registrar (Controlador controlador) {
    router.registrar(controlador);
}
public void iniciar() throws IOException {

   try (ServerSocket serverSocket = new ServerSocket(puerto)) {
        System.out.println("Servidor iniciado en el puerto " + puerto);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread hilo= new Thread(() -> atender(socket));
            hilo.start();
        }
    }

}
public static void atender (Socket socket) {
    String hilo= Thread.currentThread().getName();
    try(Socket s= socket){
        Comunicador com= new Comunicador(s.getInputStream(), s.getOutputStream());
        com.enviar("Bienvenido al servidor");
    }
    String linea;
    while ((linea=com.recibir())!=null && !linea.equalsIgnoreCase("salir")){

    }catch (IOException e) {
        System.out.println("Error en el hilo " + hilo + ": " + e.getMessage());
    }
    System.out.println("Hilo " + hilo + " se ha desconectado");

}
private String procesar (String linea){
    try{
        Solicitud solicitud= ParsearSolicitud.parsear(linea);
        return "Ok" + router.procesar(solicitud);
    }catch (RuntimeException e){
        e.printStackTrace();
        return "Error interno del servidor";
    }
}
}