package model;

public class AplicacaoTransmissora {

    public static final int BINARIA = 1;
    public static final int MANCHESTER = 2;
    public static final int MANCHESTER_DIFERENCIAL = 3;

    static int tipoDeCodificacao = BINARIA;


    public static void setTipoDeCodificacao(int tipoDeCodificacao) {
        AplicacaoTransmissora.tipoDeCodificacao = tipoDeCodificacao;
    }

    public static int getTipoDeCodificacao() {
        return tipoDeCodificacao;
    }

    

    public static void aplicacaoTransmissora (String mensagem) {
        System.out.println("Aplicacao Transmissora");
        try {
            System.out.println("Mensagem: " + mensagem);
            CamadaAplicacaoTransmissora.camadaAplicacaoTransmissora(mensagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
