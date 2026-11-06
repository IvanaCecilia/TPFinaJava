package comunicador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Comunicador implements AutoCloseable {
    private BufferedReader entrada;
	private PrintWriter salida;

	public Comunicador(InputStream is, OutputStream os) {
		this.entrada = new BufferedReader(new InputStreamReader(is));
		this.salida = new PrintWriter(new OutputStreamWriter(os), true);
	}

	public void enviar(String mensaje) {
		salida.println(mensaje);
	}

	public String recibir() throws IOException  {
		return entrada.readLine();
	}

	@Override
	public void close() throws Exception {
		entrada.close();
		salida.close();
	}
}
