package app.main.models;

public class Notas {
	private String nombre;
	private Double nota1;
	private Double nota2;
	private Double nota3;

	public Notas(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public Double getNota1() {
		return nota1;
	}

	public Double getNota2() {
		return nota2;
	}

	public Double getNota3() {
		return nota3;
	}

	public void setNota(int posicion, double valor) {
		if (posicion == 1) {
			nota1 = valor;
		} else if (posicion == 2) {
			nota2 = valor;
		} else if (posicion == 3) {
			nota3 = valor;
		} else {
			throw new IllegalArgumentException("Posicion de nota invalida");
		}
	}

	public int agregarNota(double valor) {
		if (nota1 == null) {
			nota1 = valor;
			return 1;
		}
		if (nota2 == null) {
			nota2 = valor;
			return 2;
		}
		if (nota3 == null) {
			nota3 = valor;
			return 3;
		}
		throw new IllegalStateException("El alumno ya tiene las 3 notas cargadas");
	}

	@Override
	public String toString() {
		return nombre + " [nota1=" + formato(nota1) + ", nota2=" + formato(nota2) + ", nota3=" + formato(nota3) + "]";
	}

	private String formato(Double nota) {
		return nota == null ? "-" : String.valueOf(nota);
	}
}
