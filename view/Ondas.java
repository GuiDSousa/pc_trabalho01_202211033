package view;
// Classe Ondas
// Classe que modela as ondas e mostra na tela conforme são transmitidas

public class Ondas {
    
    // Método que recebe um array de inteiros e mostra na tela
    public static void ondas (int[] fluxoBrutoDeBits) {
        System.out.println("\n|Ondas|\n");
        try {
            for (int i = 0; i < fluxoBrutoDeBits.length; i++) {
                System.out.println("Onda [" + fluxoBrutoDeBits[i] + "]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na transmissão das ondas");
        }
    }
}
