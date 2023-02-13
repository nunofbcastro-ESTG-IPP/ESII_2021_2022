package estg.ipp.pt.Modulo_Transacoes;

import estg.ipp.pt.Interfaces.LinhaTransacao;
import estg.ipp.pt.Interfaces.Produto;

import com.orgcom.BasicTransactionLine;

import java.util.StringJoiner;

public class BasicLinhaTransacao extends BasicTransactionLine implements LinhaTransacao {
    private final Produto produto;

    /**
     * Responsável por inicializar os atributos de uma linha de transação. Herda
     * todos os métodos
     * da classe BasicTransactionLine fornecida na API, no entanto acrescenta mais
     * um atributo, o produto.
     *
     * @param produto    Produto a adicionar à linha de transação
     * @param quantidade Quantidade do produto adicionado
     */
    public BasicLinhaTransacao(final Produto produto, final int quantidade) {
        super(produto.getDescricao(), quantidade, produto.getPrecoUnitario());
        this.produto = produto;
    }

    @Override
    public Produto getProduto() {
        return this.produto;
    }

    @Override
    public String toString() {
        final StringJoiner stringJoiner = new StringJoiner("\n");

        stringJoiner.add("Produto:" + this.getProduto().toString());
        stringJoiner.add("Quantidade:" + this.getQuantity());

        return stringJoiner.toString();
    }
}
