package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.Entidade;
import estg.ipp.pt.Interfaces.LinhaTransacao;
import estg.ipp.pt.Interfaces.TransactionEncomenda;

import com.orgcom.Entity;
import com.orgcom.TransactionLine;

import estg.ipp.pt.Enums.TipoTransaction;

import com.orgcom.BasicTransaction;

import java.util.StringJoiner;

public class BasicTransactionEncomenda extends BasicTransaction implements TransactionEncomenda {
    final private TipoTransaction tipoTrasaction;
    private String idEncomenda;

    /**
     * Responsável por inicializar os atributos de uma TransactionEncomenda. Quando
     * chamado, cria uma transactionEncomenda
     * que, por sua vez é uma Transaction, possuindo assim todos os atributos da
     * mesma.
     * Identifica a entidade sender, a entidade receiver e a tipo de transação que
     * identifica se a transaction é do
     * tipo envio ou pagamento.
     *
     * @param sender         entidade que envia a encomenda
     * @param receiver       entidade que recebe a encomenda
     * @param tipoTrasaction tipo da transação
     */
    public BasicTransactionEncomenda(final Entity sender, final Entity receiver, final TipoTransaction tipoTrasaction) {
        super(sender, receiver);

        if (!(sender instanceof Entidade) || !(receiver instanceof Entidade)) {
            throw new IllegalArgumentException("Sender or receiver has to be instance of Entidade.");
        }

        if (tipoTrasaction == null) {
            throw new IllegalArgumentException("TipoTrasaction cannot be null.");
        }

        this.tipoTrasaction = tipoTrasaction;
    }

    @Override
    public boolean addTransactionLine(final TransactionLine transactionLine) {
        if (!(transactionLine instanceof LinhaTransacao)) {
            throw new IllegalArgumentException("TransactionLine has to be instance of LinhaTransacao.");
        }
        return super.addTransactionLine(transactionLine);
    }

    @Override
    public void setIdEncomenda(final String idEncomenda) {
        if (idEncomenda == null) {
            throw new IllegalArgumentException("idEncomenda cannot be null.");
        }

        this.idEncomenda = idEncomenda;
    }

    @Override
    public String getIdEncomenda() {
        return this.idEncomenda;
    }

    @Override
    public TipoTransaction getTipoTrasaction() {
        return this.tipoTrasaction;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        stringJoiner.add("IdEncomenda:" + this.getIdEncomenda());
        stringJoiner.add("Sender:" + this.getSender().toString());
        stringJoiner.add("Receiver:" + this.getReceiver().toString());
        for (TransactionLine transactionLine : this) {
            stringJoiner.add(transactionLine.toString());
        }
        stringJoiner.add("TipoTrasaction:" + this.getTipoTrasaction().toString());

        return stringJoiner.toString();
    }
}
