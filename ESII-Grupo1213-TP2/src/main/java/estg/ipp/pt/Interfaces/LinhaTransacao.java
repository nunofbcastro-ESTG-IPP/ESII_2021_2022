package estg.ipp.pt.Interfaces;

import com.orgcom.TransactionLine;

/**
 * Interface responsável por gerir as linhas de transação
 */
public interface LinhaTransacao extends TransactionLine {
    /**
     * Retorna o produto associado à linha de transação
     * 
     * @return Produto associado à linha de transação
     */
    Produto getProduto();
}
