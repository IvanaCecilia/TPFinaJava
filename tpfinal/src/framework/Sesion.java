package framework;

public class Sesion {
    private String usuario;
    private Rol rol;

    public void iniciar(String usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
    }

    public void cerrar() {
        this.usuario = null;
        this.rol = null;
    }

    public String getUsuario() {
        return usuario;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean estaLogueado() {
        return usuario != null;
    }

    public void verificar(Rol requerido) throws ExcepcionesChecked {
        if (!estaLogueado()) {
            throw new AccesoDenegadoException("Tenes que iniciar sesion");
        }
        if (!rol.puedeHacer(requerido)) {
            throw new AccesoDenegadoException("No tenes permisos para esta accion");
        }
    }
}
