package estg.ipp.pt.Enums;

public enum EstadoContentor {
    LIVRE("Contentor livre!"),
    COMPLETO("Contentor completo!");

    private final String name;

    EstadoContentor(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}

