package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import comunicador.Comunicador;

public class main {

	public static void main(String[] args) throws Exception{

		int puerto = 3000;

		try (ServerSocket servidor = new ServerSocket(puerto)) {
			System.out.println("Servidor escuchando en puerto: " + puerto);

			while (true) {
				Socket socket = servidor.accept();
				Thread hilo = new Thread(() -> atender(socket));
				hilo.start();
			}

		} catch (IOException ex) {
			System.out.println("Error al abrir la conexión: " + ex.getMessage());
		}

		System.out.println("Conexión cerrada.");

	}

	public static void atender(Socket socket) {
		String hilo = Thread.currentThread().getName();
		try (Socket s = socket;
				Comunicador com = new Comunicador(s.getInputStream(), s.getOutputStream())) {

			System.out.println("Servidor (acá): " + socket.getLocalAddress() + ":" + socket.getLocalPort());
			System.out.println("Conexión aceptada. Cliente: " + socket.getInetAddress() + ":" + socket.getPort());
			System.out.println("Hilo ejecutandose: " + Thread.currentThread().getName());

			com.enviar("Bienvenido, por favor ingrese un texto ('salir' para cortar la conexión).");

			String linea;
			while ((linea = com.recibir()) != null && !linea.equalsIgnoreCase("salir")) {
				com.enviar(linea);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
