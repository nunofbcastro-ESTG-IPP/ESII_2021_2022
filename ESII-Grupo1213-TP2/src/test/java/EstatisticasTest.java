import estg.ipp.pt.Enums.TipoTransaction;
import estg.ipp.pt.Interfaces.Encomenda;
import java.time.LocalDate;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Transacoes.*;
import com.orgcom.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstatisticasTest {
    private static String THROWSATTRIBUTESNULL = "The attributes cannot be null.";
    private static String THROWSITERATORNULL = "Iterator<Encomenda> encomendas cannot be null.";

    private Entity entity1;
    private Entity entity2;
    private Entity entity3;
    private Produto produto1;
    private Produto produto2;
    private Produto produto3;

    private Transaction transactionEnvio1;
    private Transaction transactionEnvio2;
    private Transaction transactionEnvio3;
    private Transaction transactionEnvio4;
    private Transaction transactionEnvio5;
    private Transaction transactionEnvio6;
    private Transaction transactionEnvio7;
    private Transaction transactionEnvio8;

    private Encomenda encomenda1;
    private Encomenda encomenda2;
    private Encomenda encomenda3;
    private Encomenda encomenda4;
    private Encomenda encomenda5;
    private Encomenda encomenda6;
    private Encomenda encomenda7;
    private Encomenda encomenda8;

    @BeforeEach
    void setUp() {
        entity1 = new BasicEntidade("Entidade 1", District.AVEIRO, "Santa Maria da Feira", "247326380");
        entity2 = new BasicEntidade("Entidade 2", District.PORTO, "Maia", "260737992");
        entity3 = new BasicEntidade("Entidade 3", District.PORTALEGRE, "Portalegre", "227992830");
        produto1 = new BasicProduto("Lápis", 1, 0.41, 0.10, "Lápis de Carvão");
        produto2 = new BasicProduto("Mesa Gaming", 25, 5, 2.5, "Mesa com muito espaço para colocar coisas");
        produto3 = new BasicProduto("Caixa de PC", 50, 1.65, 0.5, "Caixa de PC Cooler Master");

        transactionEnvio1 = new BasicTransactionEncomenda(entity1, entity2, TipoTransaction.ENVIO);
        transactionEnvio1.addTransactionLine(new BasicLinhaTransacao(produto1, 5));
        encomenda1 = new BasicEncomenda(transactionEnvio1);

        transactionEnvio2 = new BasicTransactionEncomenda(entity2, entity1, TipoTransaction.ENVIO);
        transactionEnvio2.addTransactionLine(new BasicLinhaTransacao(produto2, 1));
        encomenda2 = new BasicEncomenda(transactionEnvio2);

        transactionEnvio3 = new BasicTransactionEncomenda(entity2, entity1, TipoTransaction.ENVIO);
        transactionEnvio3.addTransactionLine(new BasicLinhaTransacao(produto3, 1));
        encomenda3 = new BasicEncomenda(transactionEnvio3);

        transactionEnvio4 = new BasicTransactionEncomenda(entity1, entity2, TipoTransaction.ENVIO);
        transactionEnvio4.addTransactionLine(new BasicLinhaTransacao(produto2, 1));
        encomenda4 = new BasicEncomenda(transactionEnvio4, LocalDate.of(2022, 01, 16), "20220116_077");

        transactionEnvio5 = new BasicTransactionEncomenda(entity2, entity1, TipoTransaction.ENVIO);
        transactionEnvio5.addTransactionLine(new BasicLinhaTransacao(produto3, 2));
        encomenda5 = new BasicEncomenda(transactionEnvio5, LocalDate.of(2022, 01, 17), "20220117_078");

        transactionEnvio6 = new BasicTransactionEncomenda(entity2, entity1, TipoTransaction.ENVIO);
        transactionEnvio6.addTransactionLine(new BasicLinhaTransacao(produto1, 1));
        transactionEnvio6.addTransactionLine(new BasicLinhaTransacao(produto2, 1));
        encomenda6 = new BasicEncomenda(transactionEnvio6, LocalDate.of(2022, 01, 15), "20220115_016");

        transactionEnvio7 = new BasicTransactionEncomenda(entity3, entity2, TipoTransaction.ENVIO);
        transactionEnvio7.addTransactionLine(new BasicLinhaTransacao(produto2, 1));
        encomenda7 = new BasicEncomenda(transactionEnvio7, LocalDate.of(2022, 01, 25), "20220125_079");

        transactionEnvio8 = new BasicTransactionEncomenda(entity1, entity3, TipoTransaction.ENVIO);
        transactionEnvio8.addTransactionLine(new BasicLinhaTransacao(produto3, 2));
        encomenda8 = new BasicEncomenda(transactionEnvio8, LocalDate.of(2022, 01, 28), "20220128_072");

    }

    @Test
    void getNumMedioEncomendasPorDiaTest() {
        String expectedMessage;
        String actualMessage;
        Exception exception;
        double expectedResult;
        double actualResult;
        BasicGestaoEncomendas basicGestaoEncomendas;

        // Test#1
        /*
         * final Encomenda encomendaAux = new BasicEncomenda(transactionEnvio1,
         * LocalDate.of(2022, 01, 02), "20220102_100");
         * expectedResult = 0.21;
         * basicGestaoEncomendas = new estg.ipp.pt.Modulo_Transacoes.BasicGestaoEncomendas();
         * basicGestaoEncomendas.addEncomenda(encomenda1);
         * basicGestaoEncomendas.addEncomenda(encomenda2);
         * basicGestaoEncomendas.addEncomenda(encomendaAux);
         * 
         * actualResult = estg.ipp.pt.Modulo_Transacoes.Estatisticas.getNumMedioEncomendasPorDia(
         * basicGestaoEncomendas.getEncomendas());
         * 
         * assertEquals(expectedResult, actualResult,
         * "Test1: Expected result should be " + expectedResult);
         */

        // Test#2
        basicGestaoEncomendas = new BasicGestaoEncomendas();
        basicGestaoEncomendas.addEncomenda(encomenda2);
        expectedResult = 1;
        actualResult = Estatisticas.getNumMedioEncomendasPorDia(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be " + expectedResult);

        // Test#3
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getNumMedioEncomendasPorDia(null);
        });
        expectedMessage = THROWSITERATORNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be exception " + expectedMessage);

        // Test#4
        // estg.ipp.pt.Modulo_Transacoes.Estatisticas.getNumMedioEncomendasPorDia("encomenda"); ->
        // Erro de sintaxe

        // Test#5
        basicGestaoEncomendas = new BasicGestaoEncomendas();
        expectedResult = 0;
        actualResult = Estatisticas.getNumMedioEncomendasPorDia(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test5: Expected result should be " + expectedResult);
    }

    @Test
    void getNumMedioProdutosPorTransacaoTest() {
        String expectedMessage;
        String actualMessage;
        Exception exception;

        BasicGestaoEncomendas basicGestaoEncomendas = new BasicGestaoEncomendas();

        // Test#1
        double expectedResult = 3;
        basicGestaoEncomendas.addEncomenda(encomenda1);
        basicGestaoEncomendas.addEncomenda(encomenda2);

        double actualResult = Estatisticas.getNumMedioProdutosPorTransacao(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be " + expectedResult);

        // Test#2
        basicGestaoEncomendas.addEncomenda(encomenda3);
        expectedResult = 2.33;
        actualResult = Estatisticas.getNumMedioProdutosPorTransacao(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be " + expectedResult);

        // Test#3

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getNumMedioProdutosPorTransacao(null);
        });
        expectedMessage = THROWSITERATORNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test2: Expected result should be exception " + expectedMessage);

        // Teste#4
        // estg.ipp.pt.Modulo_Transacoes.Estatisticas.getNumMedioProdutosPorTransacao("encomenda");
        // -> Erro de sintaxe

        // Test#5
        basicGestaoEncomendas = new BasicGestaoEncomendas();
        expectedResult = 0;
        actualResult = Estatisticas.getNumMedioProdutosPorTransacao(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test5: Expected result should be " + expectedResult);

    }

    @Test
    void getValorMedioTransacoesTest() {
        String expectedMessage;
        String actualMessage;
        Exception exception;

        final BasicGestaoEncomendas basicGestaoEncomendas = new BasicGestaoEncomendas();

        // Test#1
        double expectedResult = 15;
        basicGestaoEncomendas.addEncomenda(encomenda1);
        basicGestaoEncomendas.addEncomenda(encomenda2);

        double actualResult = Estatisticas.getValorMedioTransacoes(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be " + expectedResult);

        // Test#2
        basicGestaoEncomendas.addEncomenda(encomenda3);
        expectedResult = 26.67;
        actualResult = Estatisticas.getValorMedioTransacoes(basicGestaoEncomendas.getEncomendas());

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be " + expectedResult);

        // Test#3

        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getValorMedioTransacoes(null);
        });
        expectedMessage = THROWSITERATORNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be exception " + expectedMessage);

        // Teste#4
        // estg.ipp.pt.Modulo_Transacoes.Estatisticas.getValorMedioTransacoes("encomenda"); -> Erro
        // de sintaxe
    }

    @Test
    void getTotalCustosEnvioTest() {
        // #Test1
        double expectedResult;
        double actualResult;

        final BasicGestaoEncomendas basicGestaoEncomendas = new BasicGestaoEncomendas();
        basicGestaoEncomendas.addEncomenda(encomenda4);
        basicGestaoEncomendas.addEncomenda(encomenda5);
        basicGestaoEncomendas.addEncomenda(encomenda6);

        expectedResult = 66.87;
        actualResult = Estatisticas.getTotalCustosEnvio(basicGestaoEncomendas.getEncomendas(),
                LocalDate.of(2022, 01, 16), LocalDate.of(2022, 01, 17));

        assertEquals(expectedResult, actualResult, "Test1: Expected result should be " + expectedResult);

        // #Test2
        expectedResult = 97.19;
        actualResult = Estatisticas.getTotalCustosEnvio(basicGestaoEncomendas.getEncomendas(),
                LocalDate.of(2022, 01, 15), LocalDate.of(2022, 01, 16));

        assertEquals(expectedResult, actualResult, "Test2: Expected result should be " + expectedResult);

        // #Test3
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getTotalCustosEnvio(null, LocalDate.of(2022, 01, 16),
                    LocalDate.of(2022, 01, 20));
        });

        String expectedMessage = THROWSATTRIBUTESNULL;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be exception " + expectedMessage);

        // #Test4
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getTotalCustosEnvio(basicGestaoEncomendas.getEncomendas(), null,
                    LocalDate.of(2022, 01, 20));
        });

        expectedMessage = THROWSATTRIBUTESNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test4: Expected result should be exception " + expectedMessage);

        // #Test5
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getTotalCustosEnvio(basicGestaoEncomendas.getEncomendas(), LocalDate.of(2022, 01, 16), null);
        });

        expectedMessage = THROWSATTRIBUTESNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test5: Expected result should be exception " + expectedMessage);

        // Test#6
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getTotalCustosEnvio(basicGestaoEncomendas.getEncomendas(), LocalDate.of(2022, 01, 17),
                    LocalDate.of(2022, 01, 15));
        });

        expectedMessage = "The start date cannot be higher than the end date.";
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test6: Expected result should be exception " + expectedMessage);

        // #Test7
        exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getTotalCustosEnvio(null, null, null);
        });

        expectedMessage = THROWSATTRIBUTESNULL;
        actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test7: Expected result should be exception " + expectedMessage);

        // Test#8 - Erro de Sintaxe
        // getTotalCustosEnvio ( "encomendas", LocalDate.of(2022, 01, 17),
        // LocalDate.of(2022, 01, 15));
    }

    @Test
    void getValorMedioVendasComprasPorDistritoTest(){
        boolean actualResult;
        double matrix [][] = new double[District.values().length][2];
        double[][] expectedMatrix;
        double[][] actualMatrix;
        int Aveiro = District.AVEIRO.ordinal(), Porto = District.PORTO.ordinal(), Portalegre = District.PORTALEGRE.ordinal();

        //#Test1
        final BasicGestaoEncomendas basicGestaoEncomendas = new BasicGestaoEncomendas();
        basicGestaoEncomendas.addEncomenda(encomenda1);
        basicGestaoEncomendas.addEncomenda(encomenda2);
        basicGestaoEncomendas.addEncomenda(encomenda3);

        actualResult = true;

        //Definir os valores esperados
        matrix [Aveiro][0] = 14.68;
        matrix [Aveiro][1] = 66.13;
        matrix [Porto][0] = 66.13;
        matrix [Porto][1] = 14.68;

        expectedMatrix = matrix;
        actualMatrix = Estatisticas.getValorMedioVendasComprasPorDistrito(basicGestaoEncomendas.getEncomendas());

        for (int i = 0; i < expectedMatrix.length; i++){
            for (int j = 0; j < 2; j++){
                if (expectedMatrix[i][j] != actualMatrix[i][j]){
                    actualResult = false;
                }
            }
        }

        assertTrue(actualResult, "Test1: Expected Matrix should be equal to the matrix returned from Estatisticas class");

        //#Test2
        basicGestaoEncomendas.addEncomenda(encomenda7);
        basicGestaoEncomendas.addEncomenda(encomenda8);

        actualResult = true;

        //Definir os valores esperados
        matrix [Aveiro][0] = 86.34;
        matrix [Aveiro][1] = 66.13;
        matrix [Porto][0] = 66.13;
        matrix [Porto][1] = 113.97;
        matrix [Portalegre][0] = 213.25;
        matrix [Portalegre][1] = 158.0;

        expectedMatrix = matrix;
        actualMatrix = Estatisticas.getValorMedioVendasComprasPorDistrito(basicGestaoEncomendas.getEncomendas());

        for (int i = 0; i < expectedMatrix.length; i++){
            for (int j = 0; j < 2; j++){
                if (expectedMatrix[i][j] != actualMatrix[i][j]){
                    actualResult = false;
                }
            }
        }

        assertTrue(actualResult, "Test2: Expected Matrix should be equal to the matrix returned from Estatisticas class");

        //Test#3
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Estatisticas.getValorMedioVendasComprasPorDistrito(null);
        });

        String expectedMessage = THROWSATTRIBUTESNULL;
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage, "Test3: Expected result should be exception " + expectedMessage);

        //Test#4 - Erro de Sintaxe
        //Estatisticas.getValorMedioVendasComprasPorDistrito("encomendas");
    }
}