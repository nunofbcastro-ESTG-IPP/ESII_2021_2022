package estg.ipp.pt.Interfaces;

import com.orgcom.Organization;

import java.util.Iterator;

/**
 * Interface responsável por gerir as encomendas
 */
public interface GestaoEncomendas extends Organization {
    /**
     * Adiciona uma encomenda à lista de encomendas
     *
     * @param encomenda encomenda a adicionar
     * @return True caso a encomenda seja adicionada com sucesso, False caso
     *         contrário
     */
    boolean addEncomenda(final Encomenda encomenda);

    /**
     * Cancela uma encomenda existente na lista de encomendas (Define o seu estado
     * para CANCELADA)
     *
     * @param encomenda Encomenda a ser cancelada
     * @return True caso a encomenda seja cancelada com sucesso, False caso
     *         contrário
     */
    boolean cancelarEncomenda(final Encomenda encomenda);

    /**
     * Retorna o iterador para as encomendas armazenadas
     * 
     * @return Iterador das encomendas
     */
    Iterator<Encomenda> getEncomendas();

    /**
     * Pesquisa se uma encomenda está armazenada na lista de encomendas
     *
     * @param idEncomenda id da encomenda a ser pesquisada
     * @return Encomenda caso a encomenda seja encontrada com sucesso, null caso
     *         contrário
     */
    Encomenda findEncomenda(final String idEncomenda);

    /**
     * Atribui as encomendas armazenadas a contentores. Além disso são adicionadas
     * as transações de envio e de pagamento bem como 1 token a cada entidade
     * sender. No final as transações são registadas na ledger
     *
     * @param path Caminho para onde será exportado o documento JSON
     * @return True caso os dados sejam adicionados com sucesso, False caso
     *         contrário
     */
    boolean atribuirEncomendas(final String path);

    /**
     * Importa as encomendas existentos no ficheiro JSON que se encontra
     * no caminho enviado por parâmetro
     *
     * @param path Caminho para onde será exportado o documento JSON
     * @return True caso as encomendas sejam importadas com sucesso, False caso
     *         contrário
     */
    boolean importEncomendas(final String path);
}
