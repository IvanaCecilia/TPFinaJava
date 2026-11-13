package services;

import java.util.Arrays;
import java.util.List;

import exeptions.excepcion;
import models.modelo;

public class ParsearSolicitud {
	
	// Solicitud: transaccion/buscar/id
	// Recurso: transaccion
	// Accion: buscar

	// get: Obtener todas las transacciones
	// get/id: Obtener una transaccion por ID
	// post
	// delete
	
	// Parametros: http://localhost:5000/transaccion/accion/12+filtro

	public static modelo parsearSolicitud(String texto) throws excepcion {
		if(texto.isBlank()) {
			throw new excepcion("La solicitud está vacía.");
		}
		
		String[] partes = texto.split("/");
		// partes = [transaccion], [buscar], ... []
		
		if(partes.length < 2) {
			throw new excepcion("La solicitud es incorrecta. Se esperan dos partes y se mandó " + partes.length);
		}
		
		List<String> parametros = Arrays.stream(partes).skip(2).toList();
		
		return new modelo(partes[0], partes[1], parametros);
	}
}
