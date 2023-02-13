package estg.ipp.pt.Modulo_Calculo_Custos;

import estg.ipp.pt.Utils.Utils;
import com.orgcom.District;
import estg.ipp.pt.Interfaces.Custos;
import estg.ipp.pt.Interfaces.Encomenda;

public class BasicCustos implements Custos {
    private static double CUSTO_KG_KM = 0.25;
    private static double CUSTO_M3 = 0.15;
    private final int tabelaCustos[][];

    public BasicCustos() {
        this.tabelaCustos = new int[][] {
                { 0, 60, 160, 270, 75, 200, 150, 270, 200, 331, 250, 385, 320, 365, 420, 478, 520, 620 },
                { 60, 0, 100, 217, 55, 180, 125, 250, 175, 309, 234, 363, 300, 340, 400, 450, 500, 600 },
                { 160, 100, 0, 110, 100, 100, 160, 170, 180, 260, 260, 400, 330, 350, 430, 490, 535, 630 },
                { 270, 217, 110, 0, 210, 200, 270, 180, 290, 270, 350, 480, 420, 360, 520, 460, 540, 720 },
                { 75, 55, 100, 210, 0, 120, 75, 200, 120, 260, 180, 310, 250, 300, 350, 400, 450, 550 },
                { 200, 180, 100, 200, 120, 0, 85, 75, 90, 170, 160, 290, 220, 260, 325, 380, 427, 527 },
                { 150, 125, 160, 270, 75, 85, 0, 160, 65, 200, 115, 250, 180, 230, 290, 340, 390, 490 },
                { 270, 250, 170, 180, 200, 75, 160, 0, 160, 95, 255, 310, 250, 180, 350, 280, 365, 550 },
                { 200, 175, 180, 290, 120, 90, 65, 160, 0, 140, 70, 200, 140, 170, 240, 300, 340, 440 },
                { 331, 309, 260, 270, 260, 170, 200, 95, 140, 0, 170, 224, 160, 100, 260, 195, 280, 460 },
                { 250, 234, 260, 350, 180, 160, 115, 255, 70, 170, 0, 140, 80, 170, 180, 240, 280, 380 },
                { 385, 363, 400, 480, 310, 290, 250, 310, 200, 224, 140, 0, 80, 220, 50, 130, 180, 280 },
                { 320, 300, 330, 420, 250, 220, 180, 250, 140, 160, 80, 80, 0, 160, 120, 160, 210, 300 },
                { 365, 340, 350, 360, 300, 260, 230, 180, 170, 100, 170, 220, 160, 0, 200, 100, 180, 380 },
                { 420, 400, 430, 520, 350, 325, 290, 350, 240, 260, 180, 50, 120, 200, 0, 190, 180, 380 },
                { 478, 450, 490, 460, 400, 380, 340, 280, 300, 195, 240, 130, 160, 100, 190, 0, 80, 220 },
                { 520, 500, 535, 540, 450, 427, 390, 365, 340, 280, 280, 180, 210, 180, 180, 80, 0, 147 },
                { 620, 600, 630, 720, 550, 527, 490, 550, 440, 460, 380, 280, 300, 380, 380, 220, 147, 0 } };
    }

    public double calculaCustoEnvio(final Encomenda encomenda) {
        double preco_kg_km = 0.0;
        double preco_m3 = 0.0;
        double distancia = 0;
        District senderDistrict;
        District receiverDistrict;

        if (encomenda == null) {
            throw new IllegalArgumentException("encomenda cannot be null.");
        }

        senderDistrict = Utils.stringLabelToDistrict(encomenda.getTransactionEnvio().getSender().getDistrict());
        receiverDistrict = Utils.stringLabelToDistrict(encomenda.getTransactionEnvio().getReceiver().getDistrict());

        distancia = getDistance(senderDistrict, receiverDistrict);
        preco_kg_km = encomenda.getPeso() * CUSTO_KG_KM * distancia;
        preco_m3 = encomenda.getVolume() * CUSTO_M3;

        encomenda.setCustoEnvio(preco_kg_km + preco_m3);

        return Utils.TwoCasesDecimals(encomenda.getCustoEnvio());
    }

    /**
     * Retorna a distância entre dois distritos
     * 
     * @param senderDistrict   Distrito de partida
     * @param receiverDistrict Distrito de chegada
     * @return A distância entre os dois distritos
     */
    private int getDistance(final District senderDistrict, final District receiverDistrict) {
        return tabelaCustos[senderDistrict.ordinal()][receiverDistrict.ordinal()];
    }
}