package estg.ipp.pt.Interfaces;

/**
 * Interface responsável por gerir os produtos
 */
public interface Produto {
    /**
     * Retorna o id do produto
     * 
     * @return Id do produto
     */
    int getIdProduto();

    /**
     * Retorna o nome do produto
     * 
     * @return Nome do produto
     */
    String getNome();

    /**
     * Retorna o volume do produto
     * 
     * @return Volume do produto
     */
    double getVolume();

    /**
     * Retorna o peso do produto
     * 
     * @return Peso do produto
     */
    double getPeso();

    /**
     * Retorna a descrição do produto
     * 
     * @return Descrição do produto
     */
    String getDescricao();

    /**
     * Define o preço unitário do produto
     * 
     * @param precoUnitario Preço unitário a definir
     */
    void setPrecoUnitario(final double precoUnitario);

    /**
     * Retorna o preço unitário
     * 
     * @return Preço unitário do produto
     */
    double getPrecoUnitario();
}
