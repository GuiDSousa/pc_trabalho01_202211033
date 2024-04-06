package model;

import view.telaController;

public class AplicacaoReceptora {
    
    public static void aplicacaoReceptora (String mensagem) {
        System.out.println("Aplicação Receptora: ");

        try {
            System.out.println("Mensagem recebida: " + mensagem);
            new telaController().addMesage(mensagem);
        } catch (Exception e) {
            System.out.println("Fim da aplicação receptora");
        }
    }
}
