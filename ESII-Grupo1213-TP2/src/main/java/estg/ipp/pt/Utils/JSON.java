package estg.ipp.pt.Utils;

import estg.ipp.pt.Interfaces.Contentor;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.Produto;
import estg.ipp.pt.Modulo_Transacoes.*;
import com.orgcom.District;
import com.orgcom.Entity;
import com.orgcom.Transaction;
import com.orgcom.TransactionLine;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import estg.ipp.pt.Enums.TipoTransaction;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JSON {
    /**
     * Construtor utilizado para privar a instanciação desta classe
     */
    private JSON() {
        throw new UnsupportedOperationException();
    }

    /**
     * Exporta todas as estatísticas para um documento JSON
     *
     * @param encomendas Iterador de encomendas
     * @param dataInicio Data de início do período de tempo utilizado na pesquisa
     * @param dataFim    Data de fim do período de tempo utilizado na pesquisa
     * @param file       Caminho para onde será exportado o documento JSON
     * @return True caso o JSON seja exportado com sucesso, False caso contrário
     * @throws IOException Caso o caminho do ficheiro seja inválido
     */
    public static boolean exportEstatisticas(final Iterator<Encomenda> encomendas, final LocalDate dataInicio,
            final LocalDate dataFim, final String file) throws IOException {
        if (!Files.isValidPath(file)) {
            throw new IOException("Invalid file");
        }

        try {
            final List<Encomenda> encomendasI = Utils.iteratorToArray(encomendas);
            final JSONObject obj = new JSONObject();

            obj.put("Número medio de produtos por transação",
                    Estatisticas.getNumMedioProdutosPorTransacao(encomendasI.iterator()));
            obj.put("Número medio de encomendas por dia",
                    Estatisticas.getNumMedioEncomendasPorDia(encomendasI.iterator()));
            obj.put("Valor medio de transações", Estatisticas.getValorMedioTransacoes(encomendasI.iterator()));
            obj.put("Total de Custos de Envio(Data Inicio:" + dataInicio.toString() + ", Data Fim:" + dataFim.toString()
                    + ")",
                    Estatisticas.getTotalCustosEnvio(encomendasI.iterator(), dataInicio, dataFim));

            final JSONArray vendasComprasPorDistrito = new JSONArray();
            double[][] vendasCompras = Estatisticas.getValorMedioVendasComprasPorDistrito(encomendasI.iterator());

            for (int i = 0; i < vendasCompras.length; i++) {
                final JSONObject datum = new JSONObject();
                datum.put("Distrito", District.values()[i].toString());
                datum.put("Venda", vendasCompras[i][0]);
                datum.put("Compra", vendasCompras[i][1]);

                vendasComprasPorDistrito.add(datum);
            }

            obj.put("Valor medio de vendas e compras por distrito", vendasComprasPorDistrito);

            return Files.writeFile(file, obj.toJSONString());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Exporta para JSON as informações dos contentores e das suas encomendas
     *
     * @param contentores Array List de contentores
     * @param file        Caminho para onde será exportado o documento JSON
     * @return True caso o JSON seja exportado com sucesso, False caso contrário
     * @throws IOException Caso o caminho do ficheiro seja inválido
     */
    public static boolean exportContentores(final List<Contentor> contentores, final String file) throws IOException {
        if (!Files.isValidPath(file)) {
            throw new IOException("Invalid file");
        }

        try {
            final JSONObject objectContentores = new JSONObject();
            JSONArray arrayEncomendas = null;
            final JSONArray arrayContentores = new JSONArray();
            Iterator<Encomenda> iterator;

            for (int i = 0; i < contentores.size(); i++) {
                Map c = new LinkedHashMap(contentores.size());
                arrayEncomendas = new JSONArray();
                iterator = contentores.get(i).getEncomendas();
                Encomenda encomenda = null;

                while (iterator.hasNext()) {
                    Map e = new LinkedHashMap(contentores.get(i).getSizeEncomendas());
                    encomenda = iterator.next();
                    e.put("IdEncomenda", encomenda.getIdEncomenda());
                    e.put("Preço_Total", encomenda.getPrecoTotal());
                    arrayEncomendas.add(e);
                }

                c.put("IdContentor", contentores.get(i).getIdContentor());
                c.put("Distrito", contentores.get(i).getDistrito().toString());
                c.put("Estado", contentores.get(i).getEstado().toString());
                c.put("Capacidade", Utils.TwoCasesDecimals(contentores.get(i).getCapacidade()));
                c.put("Encomendas", arrayEncomendas);
                arrayContentores.add(c);

            }
            objectContentores.put("Contentores", arrayContentores);

            return Files.writeFile(file, objectContentores.toJSONString());
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Importa encomendas através de um ficheiro JSON
     *
     * @param file Caminho do ficheiro json do qual vão ser importadas as encomendas
     * @return Lista de encomendas importadas
     * @throws IOException Caso o caminho do ficheiro seja inválido
     */
    public static List<Encomenda> importEncomendas(final String file) throws IOException {
        if (!Files.isValidPath(file)) {
            throw new IOException("Invalid file");
        }

        final List<Encomenda> encomendas = new ArrayList<>();

        final JSONArray data;
        final JSONParser parser = new JSONParser();

        try {
            data = (JSONArray) ((JSONObject) parser.parse(Files.readFile(file))).get("orders");
        } catch (ParseException e) {
            throw new IOException("File not readable");
        }

        for (final Object datum : data) {
            final JSONObject resultObject = (JSONObject) datum;
            try {
                final String id = String.valueOf(resultObject.get("id"));
                final LocalDate date = Utils.stringToLocalDate(String.valueOf(resultObject.get("date")));

                final Entity sender = createBasicEntidade((JSONObject) resultObject.get("sender"));
                final Entity receiver = createBasicEntidade((JSONObject) resultObject.get("receiver"));

                final List<TransactionLine> transactionLines = getTransactionLines(
                        (JSONArray) resultObject.get("products"));

                encomendas.add(createEncomenda(id, date, sender, receiver, transactionLines));
            } catch (IllegalArgumentException | NullPointerException ignored) {
            }
        }

        return encomendas;
    }

    /**
     * Cria uma entidade com os dados enviados por parâmetro
     * 
     * @param basicEntidadeObject Objeto com os dados da entidade
     * @return Entidade criada com os dados enviados
     * @throws IllegalArgumentException Caso algum dado seja inválido
     */
    private static Entity createBasicEntidade(final JSONObject basicEntidadeObject) throws IllegalArgumentException {
        final String name, nif;
        final String address;
        final District district;

        try {
            name = String.valueOf(basicEntidadeObject.get("name"));
            address = String.valueOf(basicEntidadeObject.get("address"));
            district = Utils.stringLabelToDistrict((String) basicEntidadeObject.get("district"));
            nif = String.valueOf(basicEntidadeObject.get("vat"));

            if (nif == null) {
                throw new IllegalArgumentException("NIF is invalid");
            }
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid data");
        }

        return new BasicEntidade(name, district, address, nif);
    }

    /**
     * Cria uma encomenda com os dados enviados por parâmetro
     * 
     * @param id               Id da encomenda
     * @param date             Data da encomenda
     * @param sender           Entidade emissora da encomenda
     * @param receiver         Entidade recetora da encomenda
     * @param transactionLines Lista com as transações de envio e pagamento da
     *                         encomenda
     * @return Encomenda criada com os dados enviados
     * @throws IllegalArgumentException Caso algum dado seja inválido
     */
    private static Encomenda createEncomenda(final String id, final LocalDate date, final Entity sender,
            final Entity receiver, final List<TransactionLine> transactionLines) throws IllegalArgumentException {
        final Transaction transaction = createTransactionEnvio(sender, receiver, transactionLines);
        return new BasicEncomenda(transaction, date, id);
    }

    /**
     * Cria uma transação de envio com os dados enviados por parâmetro
     * 
     * @param sender           Entidade emissora da encomenda
     * @param receiver         Entidade recetora da encomenda
     * @param transactionLines Lista com as transações de envio e pagamento da
     *                         encomenda
     * @return Transação criada com os dados enviados
     * @throws IllegalArgumentException Caso algum dado seja inválido
     */
    private static Transaction createTransactionEnvio(final Entity sender, final Entity receiver,
            final List<TransactionLine> transactionLines) throws IllegalArgumentException {
        if (transactionLines.isEmpty()) {
            throw new IllegalArgumentException("Invalid data");
        }

        final BasicTransactionEncomenda transaction = new BasicTransactionEncomenda(sender, receiver,
                TipoTransaction.ENVIO);

        for (final TransactionLine transactionLine : transactionLines) {
            transaction.addTransactionLine(transactionLine);
        }

        return transaction;
    }

    /**
     * Cria um lista com todas as linhas de transação adicionadas
     * 
     * @param data JSON Array que contém os vários produtos
     * @return Lista com as linhas de transação
     * @throws IllegalArgumentException Caso algum dado seja inválido
     */
    private static List<TransactionLine> getTransactionLines(final JSONArray data) throws IllegalArgumentException {
        final List<TransactionLine> transactionLines = new ArrayList<>();

        for (final Object datum : data) {
            transactionLines.add(createTransactionLine((JSONObject) datum));
        }

        return transactionLines;
    }

    /**
     * Cria uma linha de transação com os dados enviados por parâmetro
     *
     * @param basicTransactionLineObject Objeto com os dados da linha de transação
     * @return Linha de transação criada com os dados enviados
     * @throws IllegalArgumentException Caso algum dado seja inválido
     */
    private static TransactionLine createTransactionLine(final JSONObject basicTransactionLineObject)
            throws IllegalArgumentException {
        Integer quantity;
        Produto produto;

        try {
            final Object idObject = basicTransactionLineObject.get("id");
            final Integer id = Utils.integerParseWithDefault(idObject);

            final String name = String.valueOf(basicTransactionLineObject.get("name"));
            final String description = String.valueOf(basicTransactionLineObject.get("description"));

            final Object priceObject = basicTransactionLineObject.get("price");
            final Double price = Utils.doubleParseWithDefault(priceObject);

            final Object quantityObject = basicTransactionLineObject.get("quantity");
            quantity = Utils.integerParseWithDefault(quantityObject);

            final Object volume_m3Object = basicTransactionLineObject.get("volume-m3");
            final Double volume_m3 = Utils.doubleParseWithDefault(volume_m3Object);

            final Object weight_kgObject = basicTransactionLineObject.get("weight-kg");
            final Double weight_kg = Utils.doubleParseWithDefault(weight_kgObject);

            if (quantity == null) {
                throw new IllegalArgumentException("Invalid quantity");
            }

            produto = new BasicProduto(name, id, price, volume_m3, weight_kg, description);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid data");
        }

        return new BasicLinhaTransacao(produto, quantity);
    }
}