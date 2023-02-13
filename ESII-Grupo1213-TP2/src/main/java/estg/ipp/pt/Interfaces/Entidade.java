package estg.ipp.pt.Interfaces;

import com.orgcom.Entity;

/**
 * Interface responsável por gerir as entidades
 */
public interface Entidade extends Entity {
    /**
     * Define a morada para a entidade
     * 
     * @param morada Morada a definir para a entidade
     */
    void setMorada(String morada);

    /**
     * Retorna a morada associada à entidade
     * 
     * @return Morada da entidade
     */
    String getMorada();

    /**
     * Define o nif para a entidade
     * 
     * @param NIF Nif a definir para a entidade
     */
    void setNIF(final String NIF);

    /**
     * Retorna o nif associado à entiidade
     * 
     * @return Nif da entidade
     */
    String getNIF();
}
