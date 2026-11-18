package app;

import java.util.Map;

import framework.ExcepcionesFramework;

import framework.Rol;

public class ServicioUsuarios {
	
	private Map<String, Usuario> usuarios = Map.of(
				"ana", new Usuario("ana", "1234", Rol.USUARIO),
				"beto", new Usuario("beto", "4321", Rol.USUARIO),
				"admin", new Usuario("admin", "admin", Rol.ADMIN)
			);

	public Usuario autenticar(String nombre, String clave) {
		Usuario usuario = usuarios.get(nombre);
		if(usuario == null || !usuario.clave().equals(clave)) {
			throw new ExcepcionesFramework("Usuario o clave incorrecta.");
		}
		return usuario;
	}
}
