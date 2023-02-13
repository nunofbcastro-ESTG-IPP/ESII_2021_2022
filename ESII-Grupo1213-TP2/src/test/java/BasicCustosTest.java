import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.Entidade;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Transacoes.*;
import estg.ipp.pt.Modulo_Calculo_Custos.BasicCustos;
import com.orgcom.District;
import com.orgcom.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicCustosTest {
    BasicGestaoEncomendas gestaoEncomendas;
    Entidade entidade1;
    Entidade entidade2;
    Entidade entidade3;
    Produto produto1;
    Produto produto2;
    Produto produto3;
    Transaction transacaoEnvio1;
    Transaction transacaoEnvio2;
    Transaction transacaoEnvio3;
    Transaction transacaoEnvio4;
    Encomenda encomenda1;
    Encomenda encomenda2;
    Encomenda encomenda3;
    Encomenda encomenda4;
    BasicCustos custos;

    @BeforeEach
    void setUp() {
        gestaoEncomendas = new BasicGestaoEncomendas();
        entidade1 = new BasicEntidade("Entidade1", District.AVEIRO, "Santa Maria da Feira", "226406598");
        entidade2 = new BasicEntidade("Entidade2", District.PORTO, "Gaia", "280740662");
        entidade3 = new BasicEntidade("Entidade3", District.PORTO, "Porto", "243663056");
        produto1 = new BasicProduto("Headsets Gaming", 30, 0.25, 0.50, "Headsets 7.1");
        produto2 = new BasicProduto("Comando PS4", 15, 0.15, 0.10, "Comando PS4 Original Wireless");
        produto3 = new BasicProduto("Teclado Gaming", 50, 1.65, 0.10, "Teclado Gaming Mecanico");

        transacaoEnvio1 = new BasicTransactionEncomenda(entidade1, entidade2, TipoTransaction.ENVIO);
        transacaoEnvio1.addTransactionLine(new BasicLinhaTransacao(produto1, 2));
        transacaoEnvio1.addTransactionLine(new BasicLinhaTransacao(produto2, 1));

        transacaoEnvio2 = new BasicTransactionEncomenda(entidade1, entidade2, TipoTransaction.ENVIO);
        transacaoEnvio2.addTransactionLine(new BasicLinhaTransacao(produto1, 7));
        transacaoEnvio2.addTransactionLine(new BasicLinhaTransacao(produto2, 4));

        transacaoEnvio3 = new BasicTransactionEncomenda(entidade1, entidade3, TipoTransaction.ENVIO);
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto1, 2));
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto2, 2));
        transacaoEnvio3.addTransactionLine(new BasicLinhaTransacao(produto3, 6));

        transacaoEnvio4 = new BasicTransactionEncomenda(entidade3, entidade1, TipoTransaction.ENVIO);
        transacaoEnvio4.addTransactionLine(new BasicLinhaTransacao(produto3, 2));

        encomenda1 = new BasicEncomenda(transacaoEnvio1);
        encomenda2 = new BasicEncomenda(transacaoEnvio2);
        encomenda3 = new BasicEncomenda(transacaoEnvio3);
        encomenda4 = new BasicEncomenda(transacaoEnvio4);

        gestaoEncomendas.addEncomenda(encomenda1);
        gestaoEncomendas.addEncomenda(encomenda2);
        gestaoEncomendas.addEncomenda(encomenda3);
    }

    @Test
    void calculaCusto() {
        double expectedResult;
        double actualResult;
        String expectedMessage;
        String actualMessage;

        custos = new BasicCustos();

        expectedResult = 4.25;
        actualResult = custos.calculaCustoEnvio(encomenda4);

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be exception" + expectedResult);

        // Test#2
        expectedResult = 20.72;
        actualResult = custos.calculaCustoEnvio(encomenda1);

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be exception" + expectedResult);

        // Test#3
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            custos.calculaCustoEnvio(null);
        });

        expectedMessage = "encomenda cannot be null.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "A mensagem obtida é " + expectedMessage);

        // Test#4 - Erro de sintaxe
        // custos.calculaCustoEnvio("encomenda");
    }
}