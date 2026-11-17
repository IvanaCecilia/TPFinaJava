package app;

import java.io.IOException;

import framework.Servidor;

public class Main {

	public static void main(String[] args) {
		Servidor servidor = new Servidor(3000);
		servidor.registrar(new ControladorTareas());
		try {
			servidor.iniciar();
		} catch (IOException ex) {
			System.out.println("No se pudo iniciar el servidor: " + ex.getMessage());
		}
	}
}
