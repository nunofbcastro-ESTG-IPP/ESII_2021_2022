package estg.ipp.pt.Modulo_Expedicoes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.orgcom.District;
import estg.ipp.pt.Enums.EstadoContentor;
import estg.ipp.pt.Interfaces.Contentor;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Utils.Utils;


public class BasicContentor implements Contentor {
    private final static double CAPACIDADE_MAX = 63.0;
    private String idContentor;
    private District distrito;
    private double capacidade; 
    private List<Encomenda> encomendas;
    private EstadoContentor estado;
    private static int actualId;

    static{
        actualId = 0;
    }
    
    BasicContentor(District distrito) {
        if(distrito == null){
            throw new IllegalArgumentException("District cannot be null");
        }

        this.distrito = distrito;
        this.idContentor = distrito.toString().replaceAll(" ", "_") + "_" + String.format("%02d", ++actualId);
        this.estado = EstadoContentor.LIVRE;
        this.encomendas = new ArrayList<>();
    }

    @Override
    public boolean addEncomenda(Encomenda encomenda) {
        if(encomenda == null){
            throw new IllegalArgumentException("Encomenda cannot be null.");
        }

        if ((capacidade + encomenda.getVolume()) > CAPACIDADE_MAX) {
            return false;
        }

        if (Utils.TwoCasesDecimals(capacidade + encomenda.getVolume()) == CAPACIDADE_MAX) {
            this.estado = EstadoContentor.COMPLETO;
        }

        this.encomendas.add(encomenda);
        this.capacidade += encomenda.getVolume();
        return true;
    }

    @Override
    public Iterator<Encomenda> getEncomendas() {
        return encomendas.iterator();
    }

    @Override
    public int getSizeEncomendas(){
        return encomendas.size();
    }

    @Override
    public String getIdContentor() {
        return this.idContentor;
    }

    @Override
    public District getDistrito() {
        return this.distrito;
    }

    @Override
    public double getCapacidade() {
        return this.capacidade;
    }

    @Override
    public EstadoContentor getEstado() {
        return this.estado;
    }
}
