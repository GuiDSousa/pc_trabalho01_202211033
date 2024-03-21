package model;

public class AplicacaoReceptora {
    
    public static void aplicacaoReceptora (String mensagem) {
        System.out.println("Aplicação Receptora: ");

        try {
            System.out.println("\tMensagem recebida: " + mensagem);
        } catch (Exception e) {
            System.out.println("[ERRO] - Aplicação Receptora");
        }
    }
}
