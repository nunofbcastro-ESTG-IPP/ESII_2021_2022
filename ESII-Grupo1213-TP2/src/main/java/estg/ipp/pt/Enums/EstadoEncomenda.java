package estg.ipp.pt.Enums;

public enum EstadoEncomenda {
    EM_PROCESSAMENTO("Encomenda em processamento!"),
    COMPLETA("Encomenda completa!"),
    CANCELADA("Encomenda cancelada!");

    private final String name;

    EstadoEncomenda(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
