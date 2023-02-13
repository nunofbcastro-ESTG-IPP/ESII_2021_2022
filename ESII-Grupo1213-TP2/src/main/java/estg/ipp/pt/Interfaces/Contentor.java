package estg.ipp.pt.Interfaces;

import estg.ipp.pt.Enums.EstadoContentor;

import java.util.Iterator;

import com.orgcom.District;

/**
 * Interface responsável por gerir os contentores
 */
public interface Contentor {

    /**
     * Adiciona uma nova encomenda ao contentor, esta é apenas adicionada se a sua
     * capacidade não ultrapassar a capacidade máxima do contentor, caso ultrapasse
     * é criado um novo contentor
     * 
     * @param encomenda Encomenda a adicionar
     * @return True se a encomenda for adicionada ao contentor, False caso contrário
     */
    boolean addEncomenda(Encomenda encomenda);

    /**
     * Retorna o iterador para as encomendas armazenadas no contentor
     * 
     * @return Iterador das encomendas
     */
    Iterator<Encomenda> getEncomendas();

    /**
     * Retorna o id associado ao contentor
     * 
     * @return Id do contentor
     */
    String getIdContentor();

    /**
     * Retorna o distrito associado ao contentor
     * 
     * @return Distrito do contentor
     */
    District getDistrito();

    /**
     * Retorna o numero de encomendas armazenadas no contentor
     * 
     * @return Numero de encomendas no contentor
     */
    int getSizeEncomendas();

    /**
     * Retorna a capacidade ocupada pelas encomendas armazenadas no contentor
     * 
     * @return Capacidade ocupada pelas encomendas
     */
    double getCapacidade();

    /**
     * Retorna o estado do contetor (Livre | Completo)
     * 
     * @return Estado do contentor
     */
    EstadoContentor getEstado();
}
