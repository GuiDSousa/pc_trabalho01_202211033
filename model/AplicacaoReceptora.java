/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: Aplicacao Receptora
 * Funcao...........: Aplicacao Receptora responsável por receber a mensagem e exibir na tela
 *************************************************************** */

package model;

import view.telaController;

public class AplicacaoReceptora {

  /*
   * Método: aplicacaoReceptora
   * Função: Receber a mensagem e exibir na tela
   * Parametros: String mensagem
   * Retorno: void;
   */
  public static void aplicacaoReceptora(String mensagem) {
    System.out.println("Aplicação Receptora: ");

    try {
      System.out.println("Mensagem recebida: " + mensagem);
      new telaController().addMesage(mensagem); // Adiciona a mensagem na tela
    } catch (Exception e) {
      System.out.println("Fim da aplicação receptora");
    } // Fim do try
  } // Fim do método aplicacaoReceptora
} // Fim da classe AplicacaoReceptora
