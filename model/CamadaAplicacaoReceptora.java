/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: CamadaAplicacaoReceptora
 * Funcao...........: Camada de Aplicação Receptora que recebe a mensagem e exibe na tela
 *************************************************************** */

package model;

public class CamadaAplicacaoReceptora {
  /*
   * Método: camadaAplicacaoReceptora
   * Função: Receber a mensagem e exibir na tela
   * Parametros: int[] quadro
   * Retorno: void;
   */
  public static void camadaAplicacaoReceptora(int[] quadro) {
    int velocidade = 200;
    System.out.println("Camada de Aplicação Receptora: ");
    try {
      String mensagem = "";
      char[] arrayCaracteres = new char[quadro.length];

      // Adicionando o caractere referente ao valor inteiro da Tabela [ASCII]
      for (int i = 0; i < quadro.length; i++) {
        arrayCaracteres[i] = (char) quadro[i]; // Convertendo o valor inteiro para caractere
        System.out.println("Caractere [" + arrayCaracteres[i] + "] = [ASCII] " + quadro[i] + "\n"); // Exibindo o caractere em ASCII para debug
        mensagem += arrayCaracteres[i];
        Thread.sleep(velocidade);
      } // Fim do for

      System.out.println("\nMensagem: [" + mensagem + "]");
      Thread.sleep(velocidade);
      AplicacaoReceptora.aplicacaoReceptora(mensagem); // Chama a aplicação receptora

    } catch (InterruptedException e) {
      System.out.println("Erro na Camada de Aplicação Receptora");
    } // Fim do try
  } // Fim do método camadaAplicacaoReceptora
} // Fim da classe CamadaAplicacaoReceptora
