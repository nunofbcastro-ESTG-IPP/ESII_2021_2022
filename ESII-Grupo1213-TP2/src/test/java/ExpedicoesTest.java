import com.orgcom.District;
import com.orgcom.Transaction;
import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.Entidade;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Expedicoes.Expedicoes;
import estg.ipp.pt.Modulo_Transacoes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ExpedicoesTest {
    BasicGestaoEncomendas gestaoEncomendas = null;
    Exception exception;
    Entidade entidade1;
    Entidade entidade2;
    Entidade entidade3;
    Produto produto1;
    Produto produto2;
    Produto produto3;
    Produto produto4;
    Transaction transacaoEnvio1;
    Transaction transacaoEnvio2;
    Transaction transacaoEnvio3;
    Transaction transacaoEnvio4;
    Transaction transacaoEnvio5;
    Encomenda encomenda1;
    Encomenda encomenda2;
    Encomenda encomenda3;
    Encomenda encomenda4;
    Encomenda encomenda5;

    @BeforeEach
    void setUp() {
        gestaoEncomendas = new BasicGestaoEncomendas();
        entidade1 = new BasicEntidade("Entidade1", District.AVEIRO, "Santa Maria da Feira", "210764180");
        entidade2 = new BasicEntidade("Entidade2", District.PORTALEGRE, "Gaia", "229473660");
        entidade3 = new BasicEntidade("Entidade3", District.PORTO, "Porto", "269585605");
        produto1 = new BasicProduto("Headsets Gaming", 30, 1.65, 0.50, "Headsets 7.1");
        produto2 = new BasicProduto("Rato Gaming", 15, 1.65, 0.10, "Rato Gaming 7000 dpis");
        produto3 = new BasicProduto("Teclado Gaming", 50, 1.65, 0.10, "Teclado Gaming Mecanico");
        produto4 = new BasicProduto("Teclado Gaming", 50, 1.95, 0.10, "Teclado Gaming Mecanico");

        transacaoEnvio1 = new BasicTransactionEncomenda(entidade1, entidade2, TipoTransaction.ENVIO);
        transacaoEnvio1.addTransactionLine(new BasicLinhaTransacao(produto1, 2));
        transacaoEnvio1.addTransactionLine(new BasicLinhaTransacao(produto2, 1));

        transacaoEnvio2 = new BasicTransactionEncomenda(entidade2, entidade1, TipoTransaction.ENVIO);
        transacaoEnvio2.addTransactionLine(new BasicLinhaTransacao(produto1, 7));
        transacaoEnvio2.addTransactionLine(new BasicLinhaTransacao(produto2, 4));

        transacaoEnvio3 = new BasicTransactionEncomenda(entidade1, entidade3, TipoTransaction.ENVIO);
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto1, 2));
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto2, 2));
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto3, 6));

        transacaoEnvio4 = new BasicTransactionEncomenda(entidade3, entidade1, TipoTransaction.ENVIO);
        transacaoEnvio4.addTransactionLine(new BasicLinhaTransacao(produto3, 2));

        transacaoEnvio5 = new BasicTransactionEncomenda(entidade1, entidade2, TipoTransaction.ENVIO);
        transacaoEnvio5.addTransactionLine(new BasicLinhaTransacao(produto1, 12));
        transacaoEnvio5.addTransactionLine(new BasicLinhaTransacao(produto2, 1));
        transacaoEnvio5.addTransactionLine(new BasicLinhaTransacao(produto4, 2));

        encomenda1 = new BasicEncomenda(transacaoEnvio1);
        encomenda2 = new BasicEncomenda(transacaoEnvio2);
        encomenda3 = new BasicEncomenda(transacaoEnvio3);
        encomenda4 = new BasicEncomenda(transacaoEnvio4);
        encomenda5 = new BasicEncomenda(transacaoEnvio5);

        gestaoEncomendas.addEncomenda(encomenda1);
        gestaoEncomendas.addEncomenda(encomenda2);
        gestaoEncomendas.addEncomenda(encomenda3);
        gestaoEncomendas.addEncomenda(encomenda4);
        gestaoEncomendas.addEncomenda(encomenda5);
    }

    @Test
    void assignEncomendas() {
        String expectedMessage,actualMessage;
        boolean expectedResult, actualResult;

        //Test#1
        expectedResult = false;
        actualResult = Expedicoes.assignEncomendas(gestaoEncomendas.getEncomendas(), "");
        assertEquals(expectedResult, actualResult, "Test1: Expected result should be" + expectedResult);

        //Test#2
        expectedResult = true;
        actualResult = Expedicoes.assignEncomendas(gestaoEncomendas.getEncomendas(), "./libs/contentores.json");
        assertEquals(expectedResult, actualResult, "Test2: Expected result should be" + expectedResult);

        //Test#3
        gestaoEncomendas = new BasicGestaoEncomendas();

        expectedResult = false;
        actualResult = Expedicoes.assignEncomendas(gestaoEncomendas.getEncomendas(), "./libs/contentores.json");
        assertEquals(expectedResult, actualResult, "Test3: Expected result should be" + expectedResult);

        //Test#4
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Expedicoes.assignEncomendas(null, "./libs/contentores.json");
        });

        expectedMessage = "encomendas cannot be null.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test4 Expected result should be" + expectedMessage);

        //Test#5 - Erro de Sintaxe
        //Expedicoes.assignEncomendas("encomendas");
    }
}