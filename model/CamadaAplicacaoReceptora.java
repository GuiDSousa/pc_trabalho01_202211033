package model;

public class CamadaAplicacaoReceptora {
    public static void camadaAplicacaoReceptora (int [] quadro) {
        int velocidade = 200;
        System.out.println("Camada de Aplicação Receptora: ");

        try {
            String mensagem = "";
            char[] arrayCaracteres = new char[quadro.length];

            // Adicionando o caractere referente ao valor inteiro da Tabela [ASCII]
            for (int i = 0; i < quadro.length; i++) {
                arrayCaracteres[i] = (char) quadro[i];
                System.out.println("\tCaractere [" + arrayCaracteres[i] + "] = " + quadro[i]);
                mensagem += arrayCaracteres[i];
                Thread.sleep(velocidade);
            }
            Thread.sleep(velocidade);
            AplicacaoReceptora.aplicacaoReceptora(mensagem);
        } catch (InterruptedException e) {
            System.out.println("[ERRO] - Camada de Aplicação Receptora");
        }
    }
}
