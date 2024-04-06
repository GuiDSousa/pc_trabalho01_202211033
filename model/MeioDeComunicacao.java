/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: MeioDeComunicacao
 * Funcao...........: A função do meio de comunicação é simular a transmissão de dados entre dois pontos
 *************************************************************** */
package model;

public class MeioDeComunicacao {
  public static void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    System.out.println("\n|Meio de Comunicação|\n");
    try {

      int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits; // Cria um vetor de inteiros com o tamanho do fluxo bruto de bits
      int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBits.length]; // Cria um vetor de inteiros com o tamanho do
                                                                       // fluxo bruto de bits

      // Laço responsável por percorrer o vetor de fluxo bruto de bits
      for (int indicePosicao = 0; indicePosicao < fluxoBrutoDeBits.length; indicePosicao++) {
        int numero = fluxoBrutoDeBitsPontoA[indicePosicao];
        int numeroDeBits = CamadaFisicaReceptora.getNumberOfBits(numero); // Chama o método getNumberOfBits da classe
                                                                          // CamadaFisicaReceptora

        // Verifica se o número de bits é menor ou igual a 8, 16, 24 ou 32
        if (numeroDeBits <= 8) {
          numeroDeBits = 8;
        } else if (numeroDeBits <= 16) {
          numeroDeBits = 16;
        } else if (numeroDeBits <= 24) {
          numeroDeBits = 24;
        } else if (numeroDeBits <= 32) {
          numeroDeBits = 32;
        } // Fim do if

        numero <<= (32 - numeroDeBits); // Desloca os bits para a esquerda

        int novoInteiro = 0;
        int displayMask = 1 << 31; // Desloca 1 para a esquerda 31 vezes

        for (int i = 1; i <= numeroDeBits; i++) {
          int bit = (numero & displayMask) == 0 ? 0 : 1; // Verifica se o bit é 0 ou 1

          novoInteiro <<= 1;
          novoInteiro = novoInteiro | bit;
          numero <<= 1;

          if (i == numeroDeBits) {
            fluxoBrutoDeBitsPontoB[indicePosicao] = novoInteiro;
          } // Fim do if
        } // Fim do for
      } // Fim do for
      System.out.println("\n");
      System.out.println("Fluxo Bruto de Bits Ponto B:\n ");
      CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Erro no meio de comunicação");
    } // Fim do try
  } // Fim do método meioDeComunicacao
} // Fim da classe MeioDeComunicacao
