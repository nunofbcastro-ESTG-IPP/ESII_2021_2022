import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Transacoes.BasicEntidade;
import estg.ipp.pt.Modulo_Transacoes.BasicLinhaTransacao;
import estg.ipp.pt.Modulo_Transacoes.BasicProduto;
import estg.ipp.pt.Modulo_Transacoes.BasicTransactionEncomenda;
import com.orgcom.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicTransactionEncomendaTest {
    Transaction basicTransactionEncomenda;

    @BeforeEach
    void setUp() {
        Entity sender = new BasicEntidade("Joaquim", District.AVEIRO, "R. do Eng. Carlos Boia 12, 3810-702 Aveiro, Portugal", "278841104");
        Entity receiver = new BasicEntidade("Jose", District.PORTO, "R. de Cunha Júnior nº13 2E, 4250-186 Porto, Portugal", "210819065");
        TipoTransaction tipoTrasaction = TipoTransaction.ENVIO;
        basicTransactionEncomenda = new BasicTransactionEncomenda(sender, receiver, tipoTrasaction);
    }

    @Test
    void addTransactionLineTest() {
        String expectedMessage,actualMessage;
        Exception exception;

        //Test#1

        Produto produto = new BasicProduto("Portatil", 1000,4000, 2.95, "Portatil asus");
        TransactionLine transactionLine1 = new BasicLinhaTransacao(produto, 1);

        assertTrue(basicTransactionEncomenda.addTransactionLine(transactionLine1), "Test1: Expected result should be True");

        //Test#2

        assertFalse(basicTransactionEncomenda.addTransactionLine(transactionLine1), "Test2: Expected result should be False");

        //Test#3

        exception = assertThrows(IllegalArgumentException.class, () -> {
            basicTransactionEncomenda.addTransactionLine(null);
        });
        expectedMessage = "TransactionLine has to be instance of LinhaTransacao.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be exception" + expectedMessage);

        //Test#4

        TransactionLine transactionLine2 = new BasicTransactionLine("Portatil asus",1000, 3);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            basicTransactionEncomenda.addTransactionLine(transactionLine2);
        });
        expectedMessage = "TransactionLine has to be instance of LinhaTransacao.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test4: Expected result should be exception"  + expectedMessage);

        // Teste#5
        // basicTransactionEncomenda.addTransactionLine("encomenda"); -> Erro de sintaxe

    }
}