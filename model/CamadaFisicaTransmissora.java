/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: CamadaFisicaTransmissora
 * Funcao...........: Camada responsável pela codificação dos bits de acordo com os protocolos BINÁRIO, MANCHESTER e MANCHESTER DIFERENCIAL
 *************************************************************** */

package model;

public class CamadaFisicaTransmissora {
  /*
   * Método: camadaFisicaTransmissora
   * Função: Codificar os bits de acordo com os protocolos BINÁRIO, MANCHESTER e
   * MANCHESTER DIFERENCIAL
   * os quais são selecionados através do switch case
   * Parametros: int[] quadro
   * Retorno: void;
   */
  public static void camadaFisicaTransmissora(int[] quadro) {
    int velocidade = 200;
    System.out.println("|Camada Física Transmissora|\n");

    try {
      Thread.sleep(velocidade);

      System.out.println("Bits por inteiro: \n");

      // Exibe os bits de cada inteiro
      for (int c : quadro) {
        System.out.println("Inteiro [" + c + "]" + " = " + imprimirBits(c) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for

      int[] fluxoBrutoDeBits = inteirosParaBits(quadro); // Converte os inteiros para bits

      System.out.println("Bits Brutos Manipulados: ");
      Thread.sleep(velocidade);

      // Exibe os bits manipulados
      for (int b : fluxoBrutoDeBits) {
        System.out.println(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for
      System.out.println();

      Thread.sleep(velocidade);
      Thread.sleep(velocidade);
      System.out.println("Bits Brutos Codificados: \n");

      for (int b : fluxoBrutoDeBits) {
        System.out.println(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();

      // Seleciona o tipo de codificação passando o fluxo bruto de bits
      switch (AplicacaoTransmissora.tipoDeCodificacao) {
        case AplicacaoTransmissora.BINARIA:
          System.out.println("|Codificacao Binaria|\n");
          fluxoBrutoDeBits = codificacaoBinaria(fluxoBrutoDeBits);// DECOFICACAO BINARIA
          break;
        case AplicacaoTransmissora.MANCHESTER:
          System.out.println("|Codificacao Manchester|\n");
          fluxoBrutoDeBits = codificacaoManchester(fluxoBrutoDeBits);// DECOFICACAO MANCHESTER
          break;
        case AplicacaoTransmissora.MANCHESTER_DIFERENCIAL:
          System.out.println("|Codificacao Manchester Diferencial|\n");
          fluxoBrutoDeBits = codificacaoManchesterDiferencial(fluxoBrutoDeBits);// DECOFICACAO MANCHESTER DIFERENCIAL
          break;
      } // Fim do switch case

      System.out.println("Bits Brutos Codificados: \n");
      Thread.sleep(velocidade);

      for (int b : fluxoBrutoDeBits) {
        System.out.println(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for
      System.out.println();

      MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits); // Chama o método meioDeComunicacao
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("Erro na Camada Física Transmissora");
    } // Fim do try
  } // Fim do método camadaFisicaTransmissora

  /*
   * Método: inteirosParaBits
   * Função: Converte os inteiros para bits
   * Parametros: int[] vetorDeInteiros
   * Retorno: int[];
   */

  public static int[] inteirosParaBits(int[] vetorDeInteiros) {
    int novoTamanho = vetorDeInteiros.length / 4;

    // Verifica se o tamanho do vetor de inteiros é divisível por 4 para que não
    // haja perda de bits
    if (vetorDeInteiros.length % 4 != 0) {
      novoTamanho++;
    } // Fim do if

    int[] vetorDeBits = new int[novoTamanho]; // Cria um vetor de inteiros com o novo tamanho

    // Variáveis de controle
    int novoBit = 0;
    int posicao_v = 0;
    int posicao_r = 0;

    while (posicao_v < vetorDeInteiros.length) {
      novoBit <<= 8;
      novoBit = novoBit | vetorDeInteiros[posicao_v]; // Adiciona o inteiro ao novo bit

      if ((posicao_v + 1) % 4 == 0) {
        vetorDeBits[posicao_r] = novoBit; // Adiciona o novo bit ao vetor de bits
        novoBit = 0;
        posicao_r++;
      } // Fim do if
      posicao_v++;
    } // Fim do while

    if (novoBit != 0) {
      vetorDeBits[posicao_r] = novoBit;
    } // Fim do if
    return vetorDeBits;
  } // Fim do método inteirosParaBits

  /*
   * Método: codificacaoBinaria
   * Função: Codificar os bits de acordo com o protocolo BINÁRIO
   * Parametros: int[] quadro
   * Retorno: int[];
   */
  public static int[] codificacaoBinaria(int[] quadro) {
    System.out.println("|Codificação Binária|\n");

    int[] vetorCodificado = new int[quadro.length]; // Cria um vetor de inteiros com o tamanho do quadro

    int displayMask = 1 << 31; // Display mask para isolar o bit mais a esquerda
    int posicaoQuadro = 0;
    int posicaoCodificado = 0;

    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];
      int numeroDeBits = getNumberOfBits(numero); // Chama o método getNumberOfBits criado para não utilizar o
                                                  // Integer.toBinaryString
      System.out.println("Número de bits: " + numeroDeBits);

      // Verifica se o número de bits é menor ou igual a 8, 16, 24 ou 32
      if (numeroDeBits <= 8) {
        numero = 8;
      } else if (numeroDeBits <= 16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {
        numeroDeBits = 32;
      } // Fim do if

      System.out.println("Número de bits: " + numeroDeBits);
      System.out.println("Deslocamento: " + (32 - numeroDeBits) + " a esquerda");

      numero <<= (32 - numeroDeBits);
      System.out.println("Bits do numero");
      imprimirBits(numero); // Chama o método imprimirBits para exibir os bits do número

      System.out.println("Bit a Bit do numero");

      int novoInteiro = 0;

      // Laço para percorrer os bits do número
      for (int i = 1; i <= numeroDeBits; i++) {
        int bit = (numero & displayMask) == 0 ? 0 : 1; // Verifica se o bit é 0 ou 1
        System.out.print(bit);

        novoInteiro <<= 1; // Desloca o novo inteiro uma posição para a esquerda
        novoInteiro = novoInteiro | bit; // Adiciona o bit ao novo inteiro
        numero <<= 1; // Desloca o número uma posição para a esquerda

        if (i % 8 == 0) {
          System.out.print(" ");
        } // Fim do if

        if (i == numeroDeBits) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro; // Adiciona o novo inteiro ao vetor codificado
          System.out.println("\n");
        } // Fim do if
      } // Fim do for

      System.out.println();
      posicaoQuadro++;
    } // Fim do while
    return vetorCodificado; // Retorna o vetor codificado
  } // Fim do método codificacaoBinaria

  /*
   * Método: codificacaoManchester
   * Função: Codificar os bits de acordo com o protocolo MANCHESTER
   * Parametros: int[] quadro
   * Retorno: int[];
   */
  private static int[] codificacaoManchester(int[] quadro) {
    System.out.println("\n|Codificacao Manchester|");

    int reduzir = 0;
    int numeroDeBitsUltimoInteiro = getNumberOfBits(quadro[quadro.length - 1]); // Chama o metodo getNumberOfBits para obter o numero de bits do ultimo inteiro

    // Verifica se o número de bits do último inteiro é menor ou igual a 6 e reduz o
    // tamanho
    if (numeroDeBitsUltimoInteiro <= 6) {
      reduzir = 1;
    } // Fim do if

    int novoTamanho = (quadro.length * 2) - reduzir; // Calcula o novo tamanho

    int[] vetorCodificado = new int[novoTamanho];

    int displayMask = 1 << 31; // Display mask para isolar o bit mais a esquerda
    int posicaoQuadro = 0;
    int posicaoCodificado = 0;

    int novoInteiro = 0;

    while (posicaoQuadro < quadro.length) { // Laço para percorrer o quadro de inteiros
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero);
      System.out.println("\t\tNumero de bits: " + numeroDeBits);

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

      System.out.println("Numero de bits: " + numeroDeBits);
      System.out.println("Deslocamento: " + (32 - numeroDeBits) + " a esquerda");

      numero <<= (32 - numeroDeBits); // Desloca o número para a esquerda
      System.out.println("Bits do numero");
      imprimirBits(numero);

      System.out.println("Bit a Bit do numero");

      // Laço para percorrer os bits do número
      for (int i = 1; i <= numeroDeBits; i++) {
        int bit = (numero & displayMask) == 0 ? 0 : 1;

        if (bit == 1) {
          System.out.print("10"); // Exibe 10 se o bit for 1
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
        } else if (bit == 0) {
          System.out.println("01"); // Exibe 01 se o bit for 0
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
        } // Fim do if

        numero <<= 1; // Desloca o número para a esquerda

        if (i % 4 == 0) {
          System.out.print(" ");
        } // Fim do if

        if (i == 16) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro; // Adiciona o novo inteiro ao vetor codificado
          novoInteiro = 0;
          posicaoCodificado++;
          System.out.println("Bit a Bit do Numero");
        } else if (i == numeroDeBits) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;
          novoInteiro = 0;
          posicaoCodificado++;
          System.out.println("Bit a Bit do Numero");
        } // Fim do if
      } // Fim do for
      System.out.println();
      posicaoQuadro++;
    } // Fim do while
    return vetorCodificado;
  } // Fim do método codificacaoManchester

  /*
   * Método: codificacaoManchesterDiferencial
   * Função: Codificar os bits de acordo
   * com o protocolo MANCHESTER DIFERENCIAL
   * Parametros: int[] quadro
   * Retorno: int[];
   */
  private static int[] codificacaoManchesterDiferencial(int[] quadro) {
    System.out.println("\t|Codificação Manchester Diferencial|");

    int reduzir = 0;
    int numeroDeBitsUltimoInteiro = getNumberOfBits(quadro[quadro.length - 1]); // Chama o método getNumberOfBits para
                                                                                // obter o número de bits do último
                                                                                // inteiro

    if (numeroDeBitsUltimoInteiro <= 16) {
      reduzir = 1;
    } // Fim do if

    int novoTamanho = (quadro.length * 2) - reduzir; // Calcula o novo tamanho
    int[] vetorCodificado = new int[novoTamanho]; // Cria um vetor de inteiros com o novo tamanho

    int displayMask = 1 << 31; // Display mask para isolar o bit mais a esquerda
    int posicaoQuadro = 0;
    int posicaoCodificado = 0;

    // Conjunto de sinais para codificação Manchester Diferencial
    boolean sinal_1 = true;
    boolean sinal_2 = false;
    int novoInteiro = 0;

    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero); // Chama o método getNumberOfBits para obter o número de bits
      System.out.println("Numero de bits: " + numeroDeBits);

      if (numeroDeBits <= 8) {
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {
        numeroDeBits = 32;
      } // Fim do if

      System.out.println("Numero de bits: " + numeroDeBits);
      System.out.println("Deslocamento: " + (32 - numeroDeBits) + " a esquerda");

      numero <<= (32 - numeroDeBits); // Desloca o número para a esquerda
      System.out.println("Bits do numero");
      imprimirBits(numero); // Chama o método imprimirBits para exibir os bits do número

      System.out.println("\nBit a Bit do numero");

      // Laço para percorrer os bits do número
      for (int i = 1; i <= numeroDeBits; i++) {
        int bit = (numero & displayMask) == 0 ? 0 : 1;

        if (bit == 0) {
          sinal_1 = sinal_1;
          sinal_2 = sinal_2;
        } else if (bit == 1) {
          sinal_1 = !sinal_1;
          sinal_2 = !sinal_2;
        }

        if (sinal_1 && !sinal_2) {
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
        } else if (sinal_1 && sinal_2) {
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
        } // Fim do if

        numero <<= 1; // Desloca o número para a esquerda

        if (i == 16) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro; // Adiciona o novo inteiro ao vetor codificado
          novoInteiro = 0;
          posicaoCodificado++;
          System.out.println("Bit a Bit do Numero");
        } else if (i == numeroDeBits) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;
          novoInteiro = 0;
          posicaoCodificado++;
          System.out.println("Bit a Bit do Numero");
        } // Fim do if
      } // Fim do for
      System.out.println();
      posicaoQuadro++;
    } // Fim do while

    return vetorCodificado; // Retorna o vetor codificado
  } // Fim do método codificacaoManchesterDiferencial

  /*
   * Método: imprimirBits
   * Função: Exibir os bits
   * Parametros: int numero
   * Retorno: String;
   */
  public static String imprimirBits(int numero) {
    String bits = "";
    // Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;// 10000000 00000000 00000000 00000000
    // Para cada bit exibe 0 ou 1
    for (int bit = 1; bit <= 32; bit++) {
      // Utiliza displayMask para isolar o bit
      System.out.print((numero & displayMask) == 0 ? '0' : '1');
      bits += (numero & displayMask) == 0 ? '0' : '1';
      numero <<= 1;// Desloca o valor uma posicao para a esquerda
      if (bit % 8 == 0) {
        System.out.print(" ");// Exibe espaco a cada 8 bits
        bits += " ";
      } // Fim do if
    } // Fim do for
    System.out.println();
    return bits; // Retorna os bits
  } // Fim do método imprimirBits

  /*
   * Método: getNumberOfBits
   * Função: Obter o número de bits. Utilizado como alternativa ao
   * Integer.toBinaryString
   * Parametros: int number
   * Retorno: int;
   */
  public static int getNumberOfBits(int number) {
    if (number == 0) {
      return 1;
    }

    int numberOfBits = 0;
    while (number > 0) {
      numberOfBits++;
      number >>= 1;
    } // Fim do while

    return numberOfBits; // Retorna o número de bits
  } // Fim do método getNumberOfBits
} // Fim da classe CamadaFisicaTransmissora
