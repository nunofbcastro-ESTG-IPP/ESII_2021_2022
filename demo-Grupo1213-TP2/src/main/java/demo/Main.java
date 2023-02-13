package demo;

import estg.ipp.pt.Enums.EstadoEncomenda;
import estg.ipp.pt.Interfaces.Encomenda;
import estg.ipp.pt.Interfaces.GestaoEncomendas;
import estg.ipp.pt.Modulo_Transacoes.BasicGestaoEncomendas;
import estg.ipp.pt.Utils.JSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static void importarDados(final GestaoEncomendas gestaoEncomendas){
        String path = Inputs.readString("Path:");
        try {
            if(gestaoEncomendas.importEncomendas(path)){
                System.out.println("Sucesso ao importar");
            }else{
                System.out.println("Erro ao importar");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void cancelaEncomenda(final GestaoEncomendas gestaoEncomendas){
        List<Encomenda> encomendas = new ArrayList<>();
        int i=0;

        for (Iterator<Encomenda> it = gestaoEncomendas.getEncomendas(); it.hasNext(); ) {
            Encomenda encomenda = it.next();
            if(encomenda.getEstado().equals(EstadoEncomenda.EM_PROCESSAMENTO)){
                System.out.println((++i)+". "+encomenda.getIdEncomenda());
                encomendas.add(encomenda);
            }
        }

        if(encomendas.isEmpty()){
            System.out.println("Não existem encomendas para cancelar.");
            return;
        }
        System.out.println("0. Sair");
        int option = Inputs.readInt("Enter an option:", 0, encomendas.size());
        if(option==0){
            return;
        }
        gestaoEncomendas.cancelarEncomenda(encomendas.get(option-1));
        System.out.println("Encomendas cancelada com sucesso.");
    }

    private static void atribuirEncomenda(final GestaoEncomendas gestaoEncomendas){
        String path = Inputs.readString("Path:");
        if(gestaoEncomendas.atribuirEncomendas(path)){
            System.out.println("Sucesso ao Atribuir Encomendas");
        }else {
            System.out.println("Erro ao Atribuir Encomendas");
        }
    }

    private static void exportEstatisticas(final GestaoEncomendas gestaoEncomendas){
        String path = Inputs.readString("Path:");
        LocalDate dataInicio = Inputs.readDate("Data");
        LocalDate dataFim = Inputs.readDate("Data");
        try {
            boolean resultado = JSON.exportEstatisticas(gestaoEncomendas.getEncomendas(), dataInicio, dataFim, path);
            if(resultado){
                System.out.println("Sucesso ao exportar");
            }else {
                System.out.println("Erro ao exportar");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void menu() {
        GestaoEncomendas gestaoEncomendas = new BasicGestaoEncomendas();
        int option;
        do {
            System.out.println("==============================");
            System.out.println("|            MENU            |");
            System.out.println("==============================");
            System.out.println("| Opções:                    |");
            System.out.println("|   1. Importar encomendas   |");
            System.out.println("|   2. Cancelar encomenda    |");
            System.out.println("|   3. Atribuir encomendas   |");
            System.out.println("|   4. Exportar Estatísticas |");
            System.out.println("|   0. Exit                  |");
            System.out.println("==============================");
            option = Inputs.readInt("Enter an option:",0,5);
            switch (option) {
                case 0:
                    break;
                case 1:
                    importarDados(gestaoEncomendas);
                    Inputs.pause();
                    break;
                case 2:
                    cancelaEncomenda(gestaoEncomendas);
                    Inputs.pause();
                    break;
                case 3:
                    atribuirEncomenda(gestaoEncomendas);
                    Inputs.pause();
                    break;
                case 4:
                    exportEstatisticas(gestaoEncomendas);
                    Inputs.pause();
                    break;
            }
        } while (option != 0);
        System.out.println("Obrigado, volte sempre.");
    }

    public static void main(String[] args) {
        menu();
    }
}
