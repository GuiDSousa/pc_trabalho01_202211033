package model;

public class AplicacaoTransmissora {
    public static void aplicacaoTransmissora (String mensagem) {
        System.out.println("Aplicacao Transmissora");
        try {
            System.out.println("Mensagem: " + mensagem);
            CamadaAplicacaoTransmissora.camadaAplicacaoTransmissora(mensagem);
        } catch (Exception e) {
            System.out.println("[ERRO] - Aplicacao Transmissora");
        }
    }
}
