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
                System.out.println("\tCaractere [" + arrayCaracteres[i] + "] = [ASCII] " + quadro[i] + "\n");
                mensagem += arrayCaracteres[i];
                Thread.sleep(velocidade);
            }

            System.out.println("\nMensagem: [" + mensagem + "]");
            Thread.sleep(velocidade);
            AplicacaoReceptora.aplicacaoReceptora(mensagem);

        } catch (InterruptedException e) {
            System.out.println("Erro na Camada de Aplicação Receptora");
        }
    }
    
}
