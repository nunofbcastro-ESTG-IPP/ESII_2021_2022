package estg.ipp.pt.Modulo_Expedicoes;

import estg.ipp.pt.Enums.EstadoEncomenda;
import estg.ipp.pt.Interfaces.Contentor;
import estg.ipp.pt.Interfaces.Encomenda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import estg.ipp.pt.Utils.JSON;
import estg.ipp.pt.Utils.Utils;
import com.orgcom.District;
import estg.ipp.pt.Enums.EstadoContentor;

/**
 * Classe responsável por gerir as expedições das encomendas
 */
public final class Expedicoes {
    private static final List<Contentor> contentores = new ArrayList<>();

    private Expedicoes() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adiciona um contentor à lista de contentores
     * 
     * @param distrito Distrito para associar ao contentor a ser adicionado
     */
    private static void addContentor(final District distrito) {
        if (distrito == null) {
            throw new IllegalArgumentException("District cannot be null");
        }

        contentores.add(new BasicContentor(distrito));
    }

    /**
     * Retorna os contentores de determinado distrito que não estão cheios, ou seja,
     * estão livres
     * 
     * @param distrito Distrito a verificar os contentores livres
     * @return Lista de contentores livres para determinado distrito
     */
    private static List<Contentor> checkContentoresLivres(final District distrito) {
        final List<Contentor> tempContentores = new ArrayList<>();

        for (final Contentor contentore : contentores) {
            if (contentore.getDistrito().equals(distrito)
                    && contentore.getEstado().equals(EstadoContentor.LIVRE)) {
                tempContentores.add(contentore);
            }
        }

        return tempContentores;
    }

    /**
     * Associa uma encomenda a um contentor válido e é alterado o estado da
     * mesma para COMPLETA. Caso não exista um contentor
     * válido para o distrito da encomenda é então criado um novo contentor
     * 
     * @param encomenda Encomenda a associar a um contentor válido
     */
    private static void setEncomendaToContentor(final Encomenda encomenda) {
        final District distritoEncomenda = Utils
                .stringLabelToDistrict(encomenda.getTransactionEnvio().getReceiver().getDistrict());
        List<Contentor> tempContentores = checkContentoresLivres(distritoEncomenda);

        for (final Contentor tempContentor : tempContentores) {
            if (tempContentor.addEncomenda(encomenda)) {
                encomenda.setEstado(EstadoEncomenda.COMPLETA);
                return;
            }
        }

        addContentor(distritoEncomenda);
        contentores.get(contentores.size() - 1).addEncomenda(encomenda);
    }

    /**
     * Atribui encomendas aos respetivos contentores e exporta para JSON as
     * informações dos contentores e das suas encomendas
     * 
     * @param encomendas Encomendas a atribuir aos contentores
     * @param path       Caminho para onde será exportado o documento JSON
     * @return True caso o JSON seja exportado com sucesso e tenham sido atribuidas
     *         encomendas aos contentores, False caso contrário
     */
    public static boolean assignEncomendas(final Iterator<Encomenda> encomendas, final String path) {
        boolean isValid = true;

        if (encomendas == null) {
            throw new IllegalArgumentException("encomendas cannot be null.");
        }

        while (encomendas.hasNext()) {
            setEncomendaToContentor(encomendas.next());
        }

        if (contentores.isEmpty()) {
            isValid = false;
        }

        if (isValid) {
            try {
                isValid = JSON.exportContentores(contentores, path);
            } catch (IOException e) {
                isValid = false;
            }

            if(!isValid){
                revertOrderState();
            }
        }

        contentores.removeAll(contentores);
        return isValid;
    }

    /**
     * Muda o estado das encomendas para EM_PROCESSAMENTO, caso exista algum erro a
     * exportar o JSON
     */
    private static void revertOrderState() {
        Iterator<Encomenda> iterator = null;

        for (Contentor contentor : contentores) {
            iterator = contentor.getEncomendas();
            while (iterator.hasNext()) {
                iterator.next().setEstado(EstadoEncomenda.EM_PROCESSAMENTO);
            }
        }
    }
}