package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Utils.Utils;

import com.orgcom.District;
import com.orgcom.TransactionLine;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public final class Estatisticas {

    /**
     * Construtor utilizado para privar a instanciação desta classe
     */
    private Estatisticas() {
        throw new UnsupportedOperationException();
    }

    /**
     * Pesquisa as encomendas existentes no iterador e calcula o número
     * médio de produtos por transação
     * 
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @return Número médio de produtos por transação
     */
    public static double getNumMedioProdutosPorTransacao(final Iterator<Encomenda> encomendas) {
        if (encomendas == null) {
            throw new IllegalArgumentException("Iterator<Encomenda> encomendas cannot be null.");
        }

        int numEncomendas = 0;
        int soma = 0;
        Encomenda encomenda;
        while (encomendas.hasNext()) {
            encomenda = encomendas.next();
            for (final TransactionLine transactionLine : encomenda.getTransactionEnvio()) {
                soma += transactionLine.getQuantity();
            }
            numEncomendas += 1;
        }

        if (numEncomendas == 0) {
            return 0;
        }
        return Utils.TwoCasesDecimals((double) soma / numEncomendas);
    }

    /**
     * Pesquisa as encomendas existentes no iterador e calcula o número
     * médio de encomendas por dia
     * 
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @return Número médio de encomendas por dia
     */
    public static double getNumMedioEncomendasPorDia(final Iterator<Encomenda> encomendas) {
        if (encomendas == null) {
            throw new IllegalArgumentException("Iterator<Encomenda> encomendas cannot be null.");
        }

        final List<Encomenda> encomendasArray = Utils.iteratorToArray(encomendas);

        final LocalDate oldestDate = findOldestDate(encomendasArray.iterator());

        if (oldestDate == null) {
            return 0;
        }

        final long dias = DAYS.between(oldestDate, LocalDate.now()) + 1;

        return Utils.TwoCasesDecimals((double) encomendasArray.size() / dias);
    }

    /**
     * Método responsavel por encontrar a data da encomenda mais antiga
     *
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @return A data mais antiga
     */
    private static LocalDate findOldestDate(final Iterator<Encomenda> encomendas) {
        LocalDate oldestDate = null;
        Encomenda encomenda;
        if (encomendas.hasNext()) {
            oldestDate = encomendas.next().getData();
        }
        while (encomendas.hasNext()) {
            encomenda = encomendas.next();
            if (encomenda.getData().isBefore(oldestDate)) {
                oldestDate = encomenda.getData();
            }
        }

        return oldestDate;
    }

    /**
     * Pesquisa as encomendas existentes no iterador e calcula o valor
     * médio de transações
     * 
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @return Valor médio de transações
     */
    public static double getValorMedioTransacoes(final Iterator<Encomenda> encomendas) {
        if (encomendas == null) {
            throw new IllegalArgumentException("Iterator<Encomenda> encomendas cannot be null.");
        }

        int valorTrasacoes = 0;
        int numEncomendas = 0;

        while (encomendas.hasNext()) {
            final Encomenda encomenda = encomendas.next();
            for (final TransactionLine transactionLine : encomenda.getTransactionEnvio()) {
                valorTrasacoes += transactionLine.getTotalPrice();
            }
            numEncomendas += 1;
        }

        return Utils.TwoCasesDecimals((double) valorTrasacoes / numEncomendas);
    }

    /**
     * Pesquisa as encomendas existentes no iterador que se encontrem
     * no período de tempo indicado e calcula o total de custos de envio
     * desse período de tempo
     * 
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @param dataInicio Data de início do período de tempo utilizado na pesquisa
     * @param dataFim    Data de fim do período de tempo utilizado na pesquisa
     * @return Total de custos de envio no período de tempo indicado
     */
    public static double getTotalCustosEnvio(final Iterator<Encomenda> encomendas, final LocalDate dataInicio,
            final LocalDate dataFim) {
        if (encomendas == null || dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("The attributes cannot be null.");
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("The start date cannot be higher than the end date.");
        }

        double totalCustoEnvio = 0;

        while (encomendas.hasNext()) {
            final Encomenda encomenda = encomendas.next();

            if (!encomenda.getData().isBefore(dataInicio) && !encomenda.getData().isAfter(dataFim)) {
                totalCustoEnvio += encomenda.getCustoEnvio();
            }
        }

        return Utils.TwoCasesDecimals((double) totalCustoEnvio);
    }

    /**
     * Pesquisa as encomendas existentes no iterador e calcula o valor
     * médio de vendas e de compras por distrito
     * 
     * @param encomendas Iterador de encomendas onde será efetuada a pesquisa
     * @return Matriz com 18 linhas e 2 colunas. Cada linha representa número de
     *         cada distrito, a coluna 1 guarda o valor médio de vendas e a coluna 2
     *         guarda o valor médio de compras
     */
    public static double[][] getValorMedioVendasComprasPorDistrito(final Iterator<Encomenda> encomendas) {
        double[][] valoresVendasCompras = new double[District.values().length][2];
        double valorVendas = 0;
        int nrVendas = 0;
        double valorCompras = 0;
        int nrCompras = 0;

        if (encomendas == null){
            throw new IllegalArgumentException("The attributes cannot be null.");
        }

        List<Encomenda> encomendasList = Utils.iteratorToArray(encomendas);


        for (int i = 0; i < District.values().length; i++) {
            final District distrito = District.values()[i];
            for (int j = 0; j < encomendasList.size(); j++) {
                final Encomenda encomenda = encomendasList.get(j);
                if (encomenda.getTransactionEnvio().getSender().getDistrict().equals(distrito.toString())) {
                    valorVendas += encomenda.getPrecoTotal();
                    nrVendas += 1;
                }
                if (encomenda.getTransactionEnvio().getReceiver().getDistrict().equals(distrito.toString())) {
                    valorCompras += encomenda.getPrecoTotal();
                    nrCompras += 1;
                }
            }

            if (valorVendas != 0) {
                valoresVendasCompras[i][0] = Utils.TwoCasesDecimals((Double) (valorVendas / nrVendas));
            } else {
                valoresVendasCompras[i][0] = 0;
            }

            if (valorCompras != 0) {
                valoresVendasCompras[i][1] = Utils.TwoCasesDecimals((Double) (valorCompras / nrCompras));
            } else {
                valoresVendasCompras[i][1] = 0;
            }

            valorVendas = 0;
            valorCompras = 0;
            nrVendas = 0;
            nrCompras = 0;
        }

        return valoresVendasCompras;
    }
}