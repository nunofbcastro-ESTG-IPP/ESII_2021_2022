package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.Entidade;

import com.orgcom.BasicEntity;
import com.orgcom.District;
import estg.ipp.pt.Utils.Utils;

import java.util.StringJoiner;

public class BasicEntidade extends BasicEntity implements Entidade {
    private String morada;
    private String NIF;

    /**
     * Responsável por inicializar os atributos de uma Entidade.
     *
     * @param name     Nome da entidade
     * @param district Distrito da entidade
     * @param morada   Morada da entidade
     * @param NIF      NIF da entidade
     */
    public BasicEntidade(final String name, final District district, final String morada, final String NIF) {
        super(name, district);
        if(!Utils.validaNif(NIF)){
            throw new IllegalArgumentException("Invalid vat");
        }
        this.morada = morada;
        this.NIF = NIF;
    }

    @Override
    public void setMorada(final String morada) {
        this.morada = morada;
    }

    @Override
    public String getMorada() {
        return this.morada;
    }

    @Override
    public String getNIF() {
        return this.NIF;
    }

    @Override
    public void setNIF(final String NIF) {
        this.NIF = NIF;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        stringJoiner.add("Nome:" + this.getName());
        stringJoiner.add("District:" + this.getDistrict());
        stringJoiner.add("Morada:" + this.getMorada());
        stringJoiner.add("NIF:" + this.getNIF());

        return stringJoiner.toString();
    }
}
