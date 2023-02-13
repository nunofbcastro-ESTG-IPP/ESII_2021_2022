package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.Produto;

import java.util.StringJoiner;

public class BasicProduto implements Produto {
    private final int idProduto;
    private final String nome;
    private final String descricao;
    private double precoUnitario;
    private final double volume;
    private final double peso;
    private static int actualIdProduto;

    static {
        actualIdProduto = 1;
    }

    /**
     * Responsável por inicializar os atributos de um produto.
     *
     * @param nome          Nome do produto
     * @param id            Id do produto
     * @param precoUnitario Preço unitario do produto
     * @param volume        Volume do produto
     * @param peso          Peso do produto
     * @param descricao     Descrição do produto
     */
    public BasicProduto(final String nome, final int id, final double precoUnitario, final double volume,
            final double peso, final String descricao) {
        this.idProduto = id;
        this.nome = nome;
        this.volume = volume;
        this.peso = peso;
        this.descricao = descricao;
        this.setPrecoUnitario(precoUnitario);
    }

    /**
     * Responsável por inicializar os atributos de um produto.
     *
     * @param nome          Nome do produto
     * @param precoUnitario Preço unitario do produto
     * @param volume        Volume do produto
     * @param peso          Peso do produto
     * @param descricao     Descrição do produto
     */
    public BasicProduto(final String nome, final double precoUnitario, final double volume, final double peso,
            final String descricao) {
        this(nome, actualIdProduto, precoUnitario, volume, peso, descricao);
        actualIdProduto += 1;
    }

    @Override
    public int getIdProduto() {
        return this.idProduto;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public void setPrecoUnitario(final double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public double getPrecoUnitario() {
        return this.precoUnitario;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public double getVolume() {
        return this.volume;
    }

    @Override
    public double getPeso() {
        return this.peso;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        stringJoiner.add("IdProduto:" + this.getIdProduto());
        stringJoiner.add("Nome:" + this.getNome());
        stringJoiner.add("Descricao:" + this.getDescricao());
        stringJoiner.add("PrecoUnitario:" + this.getPrecoUnitario());
        stringJoiner.add("Volume:" + this.getVolume());
        stringJoiner.add("Peso:" + this.getPeso());

        return stringJoiner.toString();
    }
}
