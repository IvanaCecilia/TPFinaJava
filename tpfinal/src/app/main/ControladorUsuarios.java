package app.main;

import java.util.Map;
import java.util.function.Function;

import app.framework.Controlador;
import app.framework.Servicios;
import app.framework.Solicitud;

public class ControladorUsuarios implements Controlador{
	
	private ServicioUsuarios usuarios;
	
	public ControladorUsuarios(Servicios servicios) {
		this.usuarios = servicios.obtener(ServicioUsuarios.class);
	}

	@Override
	public String recurso() {
		return "usuarios";
	}

	@Override
	public Map<String, Function<Solicitud, String>> acciones() {
		return Map.of(
				"login", this::login,
				"logout", this::logout,
				"usuarioLogueado", this::usuarioLogueado
				);
	}
	
	// -> /usuarios/login/nombre/clave
	private String login(Solicitud s) {
		String nombre= s.getParametro(0, "nombre");
		String clave= s.getParametro(1, "clave");
		
		Usuario usuario = usuarios.autenticar(nombre, clave);
		s.getSesion().iniciar(usuario.nombre(), usuario.rol());
		return "Hola, " + usuario.nombre() + " (" + usuario.rol() + ")";
	}
	
	// -> /usuarios/logout
	private String logout(Solicitud s) {
		String nombre = s.getSesion().getUsuario();
		s.getSesion().cerrar();
		return "Chau + " + nombre;
	}
	
	// -> /usuarios/usuarioLogueado
	private String usuarioLogueado(Solicitud s) {
		if(!s.getSesion().estaLogueado()) {
			return "(nadie)";
		}
		return "Usuario logueado: " + s.getSesion().getUsuario() + " (" + s.getSesion().getRol() + ")"; 
	}

}
