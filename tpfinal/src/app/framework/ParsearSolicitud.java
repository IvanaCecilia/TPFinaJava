package app.framework;

import java.util.Arrays;
import java.util.List;

public class ParsearSolicitud {
	
	// Solicitud: transaccion/buscar/id
	// Recurso: transaccion
	// Accion: buscar

	// get: Obtener todas las transacciones
	// get/id: Obtener una transaccion por ID
	// post
	// delete
	
	// Parametros: http://localhost:5000/transaccion/accion/12+filtro

	public static Solicitud parsearSolicitud(String texto) throws ExcepcionesFramework {
		if(texto.isBlank()) {
			throw new ExcepcionesFramework("La solicitud está vacía.");
		}
		
		String[] partes = texto.split("/");
		// partes = [transaccion], [buscar], ... []
		
		if(partes.length < 2) {
			throw new ExcepcionesFramework("La solicitud es incorrecta. Se esperan dos partes y se mandó " + partes.length);
		}
		
		List<String> parametros = Arrays.stream(partes).skip(2).toList();
		
		return new Solicitud(partes[0], partes[1], parametros);
	}
}
