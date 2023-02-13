package estg.ipp.pt.Interfaces;

import java.time.LocalDate;

import com.orgcom.Transaction;

import estg.ipp.pt.Enums.EstadoEncomenda;

/**
 * Interface responsável por gerir a criação de encomenda
 */
public interface Encomenda {

    /**
     * Retorna o id associado à encomenda
     * 
     * @return Id da encomenda
     */
    String getIdEncomenda();

    /**
     * Retorna a data associada à encomenda
     * 
     * @return Data da encomenda
     */
    LocalDate getData();

    /**
     * Retorna a transação de envio associada à encomenda
     * 
     * @return Transação de envio da encomenda
     */
    Transaction getTransactionEnvio();

    /**
     * Retorna a transação de pagamento associada à encomenda
     * 
     * @return Transação de pagamento da encomenda
     */
    Transaction getTransactionPagamento();

    /**
     * Retorna o volume total da encomenda
     * 
     * @return Volume da encomenda
     */
    double getVolume();

    /**
     * Retorna o peso total da encomenda
     * 
     * @return Peso da encomenda
     */
    double getPeso();

    /**
     * Define o estado para a encomenda (EM_PROCESSAMENTO | COMPLETA | CANCELADA)
     * 
     * @param estado Estado a definir para a encomenda
     */
    void setEstado(final EstadoEncomenda estado);

    /**
     * Define o custo de envio para a encomenda
     * 
     * @param custoEnvio Custo a definir para a encomenda
     */
    void setCustoEnvio(double custoEnvio);

    /**
     * Retorna o custo de envio associado à encomenda
     * 
     * @return Custo de envio da encomenda
     */
    double getCustoEnvio();

    /**
     * Retorna o preço total da encomenda (Custo dos produtos + Custo de envio)
     * 
     * @return Preço total da encomenda
     */
    double getPrecoTotal();

    /**
     * Retorna o estado da encomenda (EM_PROCESSAMENTO | COMPLETA | CANCELADA)
     * 
     * @return Estado da encomenda
     */
    EstadoEncomenda getEstado();
}
