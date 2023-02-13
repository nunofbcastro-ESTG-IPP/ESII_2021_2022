package estg.ipp.pt.Enums;

public enum TipoTransaction {
    ENVIO("Trasação envio"),
    PAGAMENTO("Trasação pagamento");

    private final String name;

    TipoTransaction(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
