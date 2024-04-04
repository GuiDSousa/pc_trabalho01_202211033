package model;

import view.telaController;

public class AplicacaoReceptora {

    public static final int BINARIA = 1;
    public static final int MANCHESTER = 2;
    public static final int MANCHESTER_DIFERENCIAL = 3;

    private static int tipoDeCodificacao = BINARIA;
    
    public static void aplicacaoReceptora (String mensagem) {
        System.out.println("Aplicação Receptora: ");

        try {
            System.out.println("\tMensagem recebida: " + mensagem);
            new telaController().addMesage(mensagem);
        } catch (Exception e) {
            System.out.println("[ERRO] - Aplicação Receptora");
        }
    }
}
