package framework;


@FunctionalInterface
public interface Validador<T> {
    void validar(T valor);

    default Validador<T> y(Validador<T> otro) {
        return valor -> {
            this.validar(valor);
            otro.validar(valor);
        };
    }
}
