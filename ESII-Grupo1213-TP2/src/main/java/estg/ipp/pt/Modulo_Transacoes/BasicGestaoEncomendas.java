package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.GestaoEncomendas;
import estg.ipp.pt.Modulo_Expedicoes.Expedicoes;
import estg.ipp.pt.Utils.JSON;

import com.orgcom.BasicOrganization;

import estg.ipp.pt.Enums.EstadoEncomenda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicGestaoEncomendas extends BasicOrganization implements GestaoEncomendas {

    private final List<Encomenda> encomendas;

    /**
     * Responsável por inicializar a gestão de encomendas
     */
    public BasicGestaoEncomendas() {
        super();
        this.encomendas = new ArrayList<Encomenda>();
    }

    @Override
    public boolean addEncomenda(final Encomenda encomenda) {
        if (encomenda == null) {
            throw new IllegalArgumentException("Encomenda cannot be null.");
        }

        if (findEncomenda(encomenda.getIdEncomenda()) == null) {
            this.encomendas.add(encomenda);

            return true;
        }

        return false;
    }

    @Override
    public boolean cancelarEncomenda(final Encomenda encomenda) {
        if (encomenda == null) {
            throw new IllegalArgumentException("Encomenda cannot be null.");
        }

        if (findEncomenda(encomenda.getIdEncomenda()) != null) {
            if (encomenda.getEstado().equals(EstadoEncomenda.EM_PROCESSAMENTO)) {
                encomenda.setEstado(EstadoEncomenda.CANCELADA);
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Encomenda> getEncomendas() {
        return encomendas.iterator();
    }

    @Override
    public Encomenda findEncomenda(final String idEncomenda) {
        if (idEncomenda == null) {
            throw new IllegalArgumentException("idEncomenda cannot be null.");
        }

        for (final Encomenda encomenda : this.encomendas) {
            if (encomenda.getIdEncomenda().equals(idEncomenda)) {
                return encomenda;
            }
        }

        return null;
    }

    @Override
    public boolean atribuirEncomendas(final String path) {
        if (Expedicoes.assignEncomendas(this.getEncomendas(), path)) {
            for (Encomenda encomenda : encomendas) {
                if (encomenda.getEstado() == EstadoEncomenda.COMPLETA) {
                    encomenda.getTransactionEnvio().getSender().addTokens(1);
                    encomenda.getTransactionPagamento().getSender().addTokens(1);
                    super.addTransaction(encomenda.getTransactionEnvio());
                    super.addTransaction(encomenda.getTransactionPagamento());
                }
            }

            registerTransactionsInLedger();
            return true;
        }
        return false;
    }

    @Override
    public boolean importEncomendas(final String path) {
        final List<Encomenda> encomendas;

        try {
            encomendas = JSON.importEncomendas(path);
        } catch (IOException e) {
            return false;
        }

        for (final Encomenda encomenda : encomendas) {
            this.addEncomenda(encomenda);
        }

        return true;
    }
}
