package framework;

public enum Rol {
    USUARIO,
    ADMIN;

    public boolean puedeHacer(Rol requerido) {
        return this == ADMIN || this == requerido;
    }
}
