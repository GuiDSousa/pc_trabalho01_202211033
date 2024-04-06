/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: CamadaAplicacaoTransmissora
 * Funcao...........: Camada de Aplicação Transmissora que transmite a mensagem
 *************************************************************** */
package model;

public class CamadaAplicacaoTransmissora {

  /*
   * Método: camadaAplicacaoTransmissora
   * Função: Transmite a mensagem
   * Parametros: String mensagem
   * Retorno: void;
   */
  public static void camadaAplicacaoTransmissora(String mensagem) {
    int velocidade = 200;
    System.out.println("|Camada de Aplicacao Transmissora:|\n");
    try {

      char[] arrayCaracteres = mensagem.toCharArray(); // Converte a mensagem em um vetor de caracteres
      int[] quadro = new int[mensagem.length()]; // Cria um vetor de inteiros com o tamanho da mensagem

      for (int i = 0; i < arrayCaracteres.length; i++) {
        quadro[i] = (int) arrayCaracteres[i];
        System.out.println("Caractere [" + arrayCaracteres[i] + "] = ASCII [" + quadro[i] + "]");
        Thread.sleep(velocidade);
      } // Fim do for
      System.out.println("\n");
      CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro);
    } catch (InterruptedException e) {
      System.out.println("Erro na Camada de Aplicação Transmissora");
    } // Fim do try
  } // Fim do método camadaAplicacaoTransmissora
} // Fim da classe CamadaAplicacaoTransmissora