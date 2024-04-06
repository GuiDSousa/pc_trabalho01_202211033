/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: Aplicacao Transmissora
 * Funcao...........: Aplicação que transmite a mensagem 
 *************************************************************** */

package model;

public class AplicacaoTransmissora {

  // Constantes para possibilitar a selecao do tipo de codificacao
  public static final int BINARIA = 1;
  public static final int MANCHESTER = 2;
  public static final int MANCHESTER_DIFERENCIAL = 3;

  static int tipoDeCodificacao = BINARIA; // Inicializa a variavel tipoDeCodificacao com o valor 1

  /*
   * Método: setTipoDeCodificacao
   * Função: Define o tipo de codificação
   * Parametros: int tipoDeCodificacao
   * Retorno: void
   */
  public static void setTipoDeCodificacao(int tipoDeCodificacao) {
    AplicacaoTransmissora.tipoDeCodificacao = tipoDeCodificacao;
  } // Fim do método setTipoDeCodificacao

  /*
   * Método: getTipoDeCodificacao
   * Função: Retorna o tipo de codificação
   * Parametros: void
   * Retorno: tipoDeCodificacao
   */
  public static int getTipoDeCodificacao() {
    return tipoDeCodificacao;
  } // Fim do método getTipoDeCodificacao

  /*
   * Método: aplicacaoTransmissora
   * Função: Recebe a mensagem e chama a camada de aplicação transmissora
   * Parametros: String mensagem
   * Retorno: void
   */
  public static void aplicacaoTransmissora(String mensagem) {
    System.out.println("Aplicacao Transmissora");
    try {
      System.out.println("Mensagem: " + mensagem);
      CamadaAplicacaoTransmissora.camadaAplicacaoTransmissora(mensagem); // Chama a camada de aplicação transmissora
    } catch (Exception e) {
      e.printStackTrace();
    } // Fim do try
  } // Fim do método aplicacaoTransmissora
} // Fim da classe AplicacaoTransmissora
