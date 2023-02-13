package estg.ipp.pt.Modulo_Transacoes;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.StringJoiner;
import estg.ipp.pt.Enums.EstadoEncomenda;
import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Custos;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.TransactionEncomenda;
import estg.ipp.pt.Modulo_Calculo_Custos.BasicCustos;
import estg.ipp.pt.Utils.Utils;

import com.orgcom.*;

public class BasicEncomenda implements Encomenda {
    private String idEncomenda;
    private transient LocalDate date;
    private EstadoEncomenda estado;
    private final Transaction transactionEnvio;
    private Transaction transactionPagamento = null;
    private transient double volume;
    private transient double peso;
    private static int actualIdEncomenda;
    private double custoEnvio;
    private double precoTotal;
    private static Custos custos;

    static {
        actualIdEncomenda = 0;
        custos = new BasicCustos();
    }

    /**
     * Responsável por inicializar os atributos de uma encomenda. Quando chamado,
     * cria uma encomenda com os produtos
     * existentes na transactionEnvio.
     *
     * @param transactionEnvio transação de envio que contém os produtos, bem como,
     *                         as suas respetivas quantidades
     *                         e preços
     */
    public BasicEncomenda(final Transaction transactionEnvio) {
        if (!(transactionEnvio instanceof TransactionEncomenda)) {
            throw new IllegalArgumentException("Transaction is not an instance of TransactionEncomenda");
        }

        this.volume = 0;
        this.peso = 0;
        this.estado = EstadoEncomenda.EM_PROCESSAMENTO;
        this.date = LocalDate.now();
        this.idEncomenda = date.toString().replace("-", "") + "_" + String.format("%02d", ++actualIdEncomenda);
        this.transactionEnvio = transactionEnvio;

        /**
         * Atribui o id da encomenda à transaction de envio para poder ser facilmente
         * identificada a encomenda
         * correspondente
         */
        ((BasicTransactionEncomenda) transactionEnvio).setIdEncomenda(idEncomenda);
        setTransactionPagamento();

        this.calculaVolume();
        this.calculaPeso();

        custos.calculaCustoEnvio(this);
        this.precoTotal = Utils.TwoCasesDecimals(this.transactionEnvio.getTotalValue() + this.custoEnvio);
    }

    public BasicEncomenda(final Transaction transactionEnvio, final LocalDate date, final String idEncomenda) {
        this(transactionEnvio);

        if (date == null) {
            throw new IllegalArgumentException("The date cannot be null");
        }

        if (idEncomenda == null) {
            throw new IllegalArgumentException("The idEncomenda cannot be null");
        }

        if (!idEncomenda.contains(date.toString().replace("-", "") + "_")) {
            throw new IllegalArgumentException("The idEncomenda sent is not valid");
        }

        this.date = date;
        this.idEncomenda = idEncomenda;

        custos.calculaCustoEnvio(this);
        this.precoTotal = Utils.TwoCasesDecimals(this.transactionEnvio.getTotalValue() + this.custoEnvio);
    }

    /**
     * Calcula o volume total consoante o volume individual de cada produto da
     * encomenda
     */
    private void calculaVolume() {
        for (final TransactionLine transactionLine : this.transactionEnvio) {
            final BasicLinhaTransacao linhaTransacao = (BasicLinhaTransacao) transactionLine;
            volume += linhaTransacao.getProduto().getVolume() * linhaTransacao.getQuantity();
        }
    }

    /**
     * Calcula o volume total consoante o volume individual de cada produto da
     * encomenda
     */
    private void calculaPeso() {
        for (final TransactionLine transactionLine : this.transactionEnvio) {
            final BasicLinhaTransacao linhaTransacao = (BasicLinhaTransacao) transactionLine;
            this.peso += linhaTransacao.getProduto().getPeso() * linhaTransacao.getQuantity();
        }
    }

    @Override
    public String getIdEncomenda() {
        return this.idEncomenda;
    }

    @Override
    public LocalDate getData() {
        return this.date;
    }

    @Override
    public Transaction getTransactionEnvio() {
        return this.transactionEnvio;
    }

    /**
     * Verifica se a transactionPagamento contém as mesmas informações que a
     * transactionEnvio. Isto é revela-se importante,
     * uma vez que é necessário as duas transações possuirem o mesmo conteúdo, sendo
     * que, a única diferença é o tipo de
     * transação.
     * Caso isto se confirme, esta é adicionada à encomenda.
     *
     * @return true se a transactionPagemento foi atribuída com sucesso e false caso
     *         contrário.
     */
    private boolean setTransactionPagamento() {
        this.transactionPagamento = new BasicTransactionEncomenda(transactionEnvio.getReceiver(),
                transactionEnvio.getSender(), TipoTransaction.PAGAMENTO);
        ((BasicTransactionEncomenda) transactionPagamento).setIdEncomenda(idEncomenda);

        for (final TransactionLine transactionLine : this.transactionEnvio) {
            this.transactionPagamento.addTransactionLine(transactionLine);
        }

        return true;
    }

    @Override
    public Transaction getTransactionPagamento() {
        return this.transactionPagamento;
    }

    @Override
    public double getVolume() {
        return Utils.TwoCasesDecimals(this.volume);
    }

    @Override
    public double getPeso() {
        return this.peso;
    }

    @Override
    public void setEstado(final EstadoEncomenda estado) {
        this.estado = estado;
    }

    @Override
    public EstadoEncomenda getEstado() {
        return this.estado;
    }

    @Override
    public void setCustoEnvio(double custoEnvio) {
        this.custoEnvio = custoEnvio;
    }

    @Override
    public double getCustoEnvio() {
        return this.custoEnvio;
    }

    @Override
    public double getPrecoTotal() {
        return this.precoTotal;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        stringJoiner.add("IdEncomenda:" + this.getIdEncomenda());
        stringJoiner.add("Date:" + this.getData().toString());
        stringJoiner.add("Estado:" + this.getEstado().toString());
        stringJoiner.add("TransactionEnvio:" + this.getTransactionEnvio().toString());
        if (this.getTransactionPagamento() != null) {
            stringJoiner.add("TransactionPagamento:" + this.getTransactionPagamento().toString());
        }
        stringJoiner.add("Volume:" + this.getVolume());
        stringJoiner.add("Peso:" + this.getPeso());
        stringJoiner.add("CustoEnvio:" + this.getCustoEnvio());
        stringJoiner.add("precoTotal:" + this.getPrecoTotal());

        return stringJoiner.toString();
    }
}
