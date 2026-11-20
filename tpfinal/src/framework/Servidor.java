package framework;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
	private int puerto;
	private Router router = new Router();
	private Servicios servicios = new Servicios();

	public Servidor(int puerto) {
		this.puerto = puerto;
	}

	public void registrar(Controlador controlador) {
		router.registrar(controlador);
	}

	public Servicios getServicios() {
		return servicios;
	}

	public void iniciar() throws IOException {
		try (ServerSocket servidor = new ServerSocket(puerto)) {
			System.out.println("Servidor abierto en puerto: " + puerto);
			while (true) {
				Socket socket = servidor.accept();
				Thread hilo = new Thread(() -> atender(socket));
				hilo.start();
			}
		}
	}

	public void atender(Socket socket) {
		String hilo = Thread.currentThread().getName();
		Sesion sesion = new Sesion();
		try (Socket s = socket; Comunicador com = new Comunicador(s.getInputStream(), s.getOutputStream())) {

			com.enviar("Bienvenido");

			String linea;
			while ((linea = com.recibir()) != null && !linea.equalsIgnoreCase("salir")) {
				String respuesta = procesar(linea, sesion);
				System.out.println("[" + hilo + "]" + linea + " -> " + respuesta);
				com.enviar(respuesta);
			}

		} catch (Exception ex) {
			System.out.println("[" + hilo + "] " + "error de conexion: " + ex.getMessage());
		}
		System.out.println("[" + hilo + "] " + "se ha desconectado.");
	}

	private String procesar(String linea, Sesion sesion) {
		try {
			Solicitud solicitud = ParsearSolicitud.parsearSolicitud(linea);
			solicitud.setSesion(sesion);
			return "OK " + router.despachar(solicitud);
		} catch (ExcepcionesFramework ex) {
			return "ERROR " + ex.getMessage();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return "ERROR Error interno del servidor";
		}
	}

}