package estg.ipp.pt.Interfaces;

import com.orgcom.Transaction;
import com.orgcom.TransactionLine;

import estg.ipp.pt.Enums.TipoTransaction;

/**
 * Interface responsável por gerir as transações
 */
public interface TransactionEncomenda extends Transaction {
    /**
     * Adiciona uma linha de transação à transação
     *
     * @param transactionLine Linha de transação a adicionar
     * @return True caso a linha de transação seja adicionada com sucesso, False
     *         caso contrário
     */
    boolean addTransactionLine(final TransactionLine transactionLine);

    /**
     * Retorna o tipo de transação (ENVIO | PAGAMENTO)
     * 
     * @return Tipo de transação
     */
    TipoTransaction getTipoTrasaction();

    /**
     * Retorna o id da encomenda associado à transação
     * 
     * @return Id da encomenda da transação
     */
    String getIdEncomenda();

    /**
     * Define o id da encomenda associado à transação
     * 
     * @param idEncomenda Id da encomenda da transação a definir
     */
    void setIdEncomenda(final String idEncomenda);
}