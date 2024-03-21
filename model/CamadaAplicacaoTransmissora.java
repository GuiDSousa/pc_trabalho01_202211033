package model;

public class CamadaAplicacaoTransmissora {
    public static void camadaAplicacaoTransmissora (String mensagem) {
        int velocidade = 200;
        System.out.println("Camada de Aplicação Transmissora");
        try { 

            char [] arrayCaracteres = mensagem.toCharArray();
            int [] quadro = new int[mensagem.length()];

            for (int i = 0; i< arrayCaracteres.length; i++) {
                quadro [i] = (int) arrayCaracteres[i];
                System.out.println("\tCaractere [" + arrayCaracteres[i] + "] = " + quadro[i]);
                Thread.sleep(velocidade);
            }

            CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro);
        } catch (InterruptedException e) {
            System.out.println("[ERRO] - Camada de Aplicação Transmissora");
        }
}
}