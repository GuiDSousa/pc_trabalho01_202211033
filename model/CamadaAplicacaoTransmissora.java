package model;

public class CamadaAplicacaoTransmissora {
    public static void camadaAplicacaoTransmissora (String mensagem) {
        int velocidade = 200;
        System.out.println("\n|Camada de Aplicacao Transmissora:|\n");
        try { 

            char [] arrayCaracteres = mensagem.toCharArray();
            int [] quadro = new int[mensagem.length()];

            for (int i = 0; i< arrayCaracteres.length; i++) {
                quadro [i] = (int) arrayCaracteres[i];
                System.out.println("Caractere ["+arrayCaracteres[i]+"] = ASCII [" + quadro[i] + "]");
                Thread.sleep(velocidade);
            }
            System.out.println("\n");
            CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro);
        } catch (InterruptedException e) {
            System.out.println("Erro na Camada de Aplicação Transmissora");
        }
}
}