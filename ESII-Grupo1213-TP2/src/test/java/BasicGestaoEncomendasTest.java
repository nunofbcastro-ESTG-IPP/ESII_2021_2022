import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.Entidade;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Transacoes.*;
import com.orgcom.District;
import com.orgcom.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicGestaoEncomendasTest {
    BasicGestaoEncomendas gestaoEncomendas = null;
    Exception exception;
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

    @BeforeEach
    void setUp() {
        this.gestaoEncomendas = new BasicGestaoEncomendas();
        entidade1 = new BasicEntidade("Entidade1", District.AVEIRO, "Santa Maria da Feira", "298073404");
        entidade2 = new BasicEntidade("Entidade2", District.PORTO, "Gaia", "270581103");
        entidade3 = new BasicEntidade("Entidade3", District.PORTO, "Porto", "263267156");
        produto1 = new BasicProduto("Headsets Gaming", 30, 1.65, 0.50, "Headsets 7.1");
        produto2 = new BasicProduto("Rato Gaming", 15, 1.65, 0.10, "Rato Gaming 7000 dpis");
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
    void addEncomendaTest() {
        String expectedMessage,actualMessage;

        //Test#1
        boolean expectedResult = true;
        boolean actualResult = gestaoEncomendas.addEncomenda(encomenda4);
        assertTrue(actualResult, "Test1: Expected result should be true");

        //Test#2
        expectedResult = false;
        actualResult = gestaoEncomendas.addEncomenda(encomenda4);
        assertFalse(actualResult, "Test2: Expected result should be false");

        //Test#3
        exception = assertThrows(IllegalArgumentException.class, () -> {
            gestaoEncomendas.addEncomenda(null);
        });
        
        expectedMessage = "Encomenda cannot be null.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be " + expectedMessage);

        //Test#4 
        //Erro de sintaxe
        //gestaoEncomendas.addEncomenda("encomenda");
    }

    @Test
    void cancelarEncomendaTest() {
        String expectedResult, actualResult;
        //Test#1
        assertTrue(gestaoEncomendas.cancelarEncomenda(encomenda1),"Encomenda 1, removida com sucesso");

        //Test#2
        assertFalse(gestaoEncomendas.cancelarEncomenda(encomenda4),"Encomenda 4, não existe na lista de encomendas");

        //Test#3
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gestaoEncomendas.cancelarEncomenda(null);
        });
        expectedResult = "Encomenda cannot be null.";
        actualResult = exception.getMessage();
        assertEquals(expectedResult, actualResult, "A mensagem obtida é " + expectedResult);

        //Test#4
        //Erro de sintaxe
        //assertFalse(gestaoEncomendas.cancelarEncomenda("encomenda5"),"Encomenda 5, não existe na lista de encomendas");
    }

    @Test
    void findEncomendaTest() {
        String expectedMessage, actualMessage;

        // Test#1
        Encomenda expectedResult = encomenda1;
        Encomenda actualResult = this.gestaoEncomendas.findEncomenda(encomenda1.getIdEncomenda());
        assertEquals(expectedResult, actualResult,
                "Test1: Expected result should be" + expectedResult);

        // Test#2
        expectedResult = null;
        actualResult = this.gestaoEncomendas.findEncomenda("20220112_100");
        assertEquals(expectedResult, actualResult, "Test2: Expected result should be null");

        // Test#3
        expectedResult = null;
        actualResult = this.gestaoEncomendas.findEncomenda("");
        assertEquals(expectedResult, actualResult, "Test3: Expected result should be null");

        // Test#4
        // actualResult = this.gestaoEncomendas.findEncomenda(123); -> Erro de sintaxe

        // Test#5
        exception = assertThrows(IllegalArgumentException.class, () -> {
            this.gestaoEncomendas.findEncomenda(null);
        });
        expectedMessage = "idEncomenda cannot be null.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test5: Expected result should be" + expectedMessage);

    }
}