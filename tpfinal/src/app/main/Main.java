package app.main;

import java.io.IOException;

import framework.Servicios;
import framework.Servidor;
public class Main {

	public static void main(String[] args) {
		Servidor servidor = new Servidor(3000);
		
		Servicios servicios = servidor.getServicios();
		servicios.registrar(ServicioUsuarios.class, new ServicioUsuarios());
		
		// Registro de controladores
		//servidor.registrar(new ControladorTareas());
		servidor.registrar(new ControladorUsuarios(servicios));
		
		try {
			servidor.iniciar();
		} catch (IOException ex) {
			System.out.println("No se pudo iniciar el servidor: " + ex.getMessage());
		}
	}
}