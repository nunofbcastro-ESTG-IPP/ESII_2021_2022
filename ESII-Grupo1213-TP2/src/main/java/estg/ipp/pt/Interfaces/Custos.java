package estg.ipp.pt.Interfaces;

/**
 * Interface responsável por gerir o cálculo de custos de encomendas
 */
public interface Custos {
    /**
     * Retorna o custo de envio de determinada encomenda, este é calculado
     * através do volume e do peso dos produtos contidos na mesma.
     * 
     * @param encomenda Encomenda a determinar o custo
     * @return Custo de envio da encomenda
     */
    double calculaCustoEnvio(Encomenda encomenda);
}
