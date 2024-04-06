/* ***************************************************************
 * Autor............: Guilherme Dias Sousa
 * Matricula........: 202211033
 * Inicio...........: 20/03/2024    
 * Ultima alteracao.: 06/04/2024
 * Nome.............: CamadaFisicaReceptora
 * Funcao...........: Camada responsável por receber a mensagem e decodificar
 *************************************************************** */
package model;

import java.util.concurrent.Semaphore; // Importa a classe Semaphore do pacote java.util.concurrent

public class CamadaFisicaReceptora {
  public static Semaphore semaforo; // Semaforo para sincronizar a execução das threads

  /*
   * Método: camadaFisicaReceptora
   * Função: Recebe a mensagem e decodifica
   * Parametros: int[] fluxoBrutoDeBitsPontoB
   * Retorno: void;
   */
  public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB) {
    semaforo = new Semaphore(0); // Inicializa o semaforo com 0
    int velocidade = 200; // Velocidade de execução
    System.out.println("|Camada Física Receptora|\n");

    try {

      int[] fluxoBrutoDeBits = fluxoBrutoDeBitsPontoB; // Vetor de bits brutos
      Thread.sleep(velocidade);

      System.out.println("\n|Bits Brutos Manipulados - Transmissão|");
      Thread.sleep(velocidade);

      for (int b : fluxoBrutoDeBits) {
        System.out.println(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for

      System.out.println("\n");

      // Switch para escolher o tipo de decodificação
      switch (AplicacaoTransmissora.tipoDeCodificacao) {
        case AplicacaoTransmissora.BINARIA:
          System.out.println("|Decodificacao Binaria|\n");
          fluxoBrutoDeBits = decodificacaoBinaria(fluxoBrutoDeBits);// DECOFICACAO BINARIA
          break;
        case AplicacaoTransmissora.MANCHESTER:
          System.out.println("|Decodificacao Manchester|\n");
          fluxoBrutoDeBits = decodificacaoManchester(fluxoBrutoDeBits);// DECOFICACAO MANCHESTER
          break;
        case AplicacaoTransmissora.MANCHESTER_DIFERENCIAL:
          System.out.println("|Decodificacao Manchester Diferencial|\n");
          fluxoBrutoDeBits = decodificacaoManchesterDiferencial(fluxoBrutoDeBits);// DECOFICACAO MANCHESTER DIFERENCIAL
          break;
      } // Fim do switch

      System.out.println("\n|Bits Brutos Decodificados|");
      Thread.sleep(velocidade);

      for (int b : fluxoBrutoDeBits) {
        System.out.println(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for
      System.out.println("\n");

      int[] quadro = bitsParaInteiros(fluxoBrutoDeBits); // Converte os bits em inteiros

      System.out.println("\nBits por inteiro: ");
      Thread.sleep(velocidade);

      for (int c : quadro) {
        System.out.println("Inteiro [" + c + "] - " + imprimirBits(c) + "\n");
        Thread.sleep(velocidade);
      } // Fim do for
      System.out.println("\n");

      CamadaAplicacaoReceptora.camadaAplicacaoReceptora(quadro); // Chama a camada de aplicação receptora
    } catch (InterruptedException e) {
      System.out.println("Erro na  Camada Física Receptora");
    } // Fim do try
  } // Fim do método camadaFisicaReceptora

  /*
   * Método: bitsParaInteiros
   * Função: Converte os bits em inteiros
   * Parametros: int[] vetorDeBits
   * Retorno: int[];
   */

  public static int[] bitsParaInteiros(int[] vetorDeBits) {
    int adicionar = 0;
    int reduzir = 0;
    int tamanho = vetorDeBits.length; // Tamanho do vetor de bits

    int numeroDeBitsUltimoInteiro = getNumberOfBits(vetorDeBits[vetorDeBits.length - 1]); // Numero de bits do ultimo inteiro

    // Verifica o numero de bits do ultimo inteiro
    if (numeroDeBitsUltimoInteiro <= 24) {
      if (numeroDeBitsUltimoInteiro <= 8) {
        adicionar += 1;
      } else if (numeroDeBitsUltimoInteiro <= 16) {
        adicionar += 2;
      } else {
        adicionar += 3;
      } // Fim do if
      reduzir = 1;
    } // Fim do if

    int novoTamanho = ((tamanho - reduzir) * 4 + adicionar); // Novo tamanho do vetor

    int[] vetorDeInteiros = new int[novoTamanho]; // Vetor de inteiros

    int displayMask = 1 << 31; // Máscara de bits
    int posicaoI = 0;

    for (int intBits : vetorDeBits) {
      int novoInteiro = 0;

      for (int i = 1; i <= 32; i++) {
        int bit = (intBits & displayMask) == 0 ? 0 : 1; // Verifica o bit
        novoInteiro <<= 1; // Desloca o inteiro 1 bit para a esquerda
        novoInteiro = novoInteiro | bit;
        intBits <<= 1;
        if (i % 8 == 0 && novoInteiro != 0) {
          vetorDeInteiros[posicaoI] = novoInteiro;
          posicaoI++;
          novoInteiro = 0;
        } // Fim do if
      } // Fim do for
    } // Fim do for
    return vetorDeInteiros; // Retorna o vetor de inteiros
  }

  /*
   * Método: decodificacaoBinaria
   * Função: Decodifica a mensagem binária
   * Parametros: int[] quadro
   * Retorno: int[];
   */
  private static int[] decodificacaoBinaria(int[] quadro) {
    System.out.println("Decodificacao Binaria: ");

    int[] vetorCodificado = new int[quadro.length]; // Vetor codificado

    int displayMask = 1 << 31; // Máscara de bits
    int posicaoQuadro = 0;
    int posicaoCodificado = 0;

    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero); // Numero de bits
      System.out.println("Numero de bits " + numeroDeBits);

      if (numeroDeBits <= 8) {
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 31) {
        numeroDeBits = 32;
      } // Fim do if
 
      System.out.println("Numero de bits " + numeroDeBits);
      System.out.println("Deslocamento " + (32 - numeroDeBits) + " a esquerda\n");

      numero <<= (32 - numeroDeBits); // Deslocamento a esquerda de um valor de bits
      System.out.println("Bits correspondentes ao numero");
      imprimirBits(numero);

      System.out.println("Bit a Bit:");

      int novoInteiro = 0;

      for (int i = 1; i <= numeroDeBits; i++) {
        int bit = (numero & displayMask) == 0 ? 0 : 1;
        System.out.print(bit + " "); // Remove a quebra de linha
        novoInteiro <<= 1; // Deslocamento de 1 bit para a esquerda
        novoInteiro = novoInteiro | bit;
        numero <<= 1;
        if (i % 8 == 0) {
          System.out.print(" "); // Printa um espaço a cada 8 bits
        } // Fim do if
        if (i == numeroDeBits) {
          System.out.println("\n Novo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro; // Adiciona o novo inteiro ao vetor codificado
          System.out.println("\n");
        } // Fim do if
      } // Fim do for
      System.out.println("\n");
      posicaoQuadro++;
    } // Vetor percorrido // Fim do while
    return vetorCodificado;
  } // Fim do método decodificacaoBinaria

  /*
   * Método: decodificacaoManchester
   * Função: Decodifica a mensagem Manchester
   * Parametros: int[] quadro
   * Retorno: int[];
   */
  private static int[] decodificacaoManchester(int[] quadro) {
    System.out.println("|Decodificacao Manchester: |");

    int adcionar = 0;
    int tamanho = quadro.length; // Tamanho do vetor

    if (tamanho % 2 != 0) {
      adcionar++;
    } // Fim do if

    int novoTamanho = (tamanho) / 2 + adcionar;

    int[] vetorDecodificado = new int[novoTamanho];

    int displayMask = 1 << 31; // Máscara de bits
    int posicaoQuadro = 0;
    int posicaoDecodificado = 0;

    int novoInteiro = 0;
    int bitsAdcionados = 0;

    // Laço para percorrer o vetor
    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero);
      System.out.println("Numero de bits " + numeroDeBits);

      if (numeroDeBits <= 8) {
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 31) {
        numeroDeBits = 32;
      } // Fim do if

      System.out.println("Numero de bits " + numeroDeBits);
      System.out.println("Deslocamento " + (32 - numeroDeBits) + " a esquerda\n");

      numero <<= (32 - numeroDeBits); // Deslocamento a esquerda de um valor de bits
      System.out.println("Bits correspondentes ao numero");
      System.out.println("\n");
      imprimirBits(numero); // Imprime os bits

      for (int i = 1; i <= numeroDeBits; i += 2) {
        int bit_1 = (numero & displayMask) == 0 ? 0 : 1;
        numero <<= 1;
        int bit_2 = (numero & displayMask) == 0 ? 0 : 1;
        numero <<= 1;

        if (bit_1 == 1 & bit_2 == 0) { // Se o bit 1 for 1 e o bit 2 for 0, verificação dos sinais booleanos
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
          bitsAdcionados++;
        } else if (bit_1 == 0 & bit_2 == 1) {
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
          bitsAdcionados++;
        } // Fim do if

        if (bitsAdcionados == 32) {
          vetorDecodificado[posicaoDecodificado] = novoInteiro;
          System.out.println("\tBits decodificados: ");
          System.out.println("\t");
          imprimirBits(novoInteiro);
          posicaoDecodificado++;
          bitsAdcionados = 0;
          novoInteiro = 0;
        } // Fim do if
      } // Fim do for

      System.out.println("\n");
      posicaoQuadro++;
    } // Fim do while

    if (novoInteiro != 0) {
      vetorDecodificado[posicaoDecodificado] = novoInteiro;
      System.out.println("Bits decodificados: ");
      System.out.println("\n");
      imprimirBits(novoInteiro);
    } // Fim do if
    return vetorDecodificado; // Retorna o vetor decodificado
  } // Fim do método decodificacaoManchester

  private static int[] decodificacaoManchesterDiferencial(int[] quadro) {
    System.out.println("|Decodificacao Manchester Diferencial: |");
    int adcionar = 0;
    int tamanho = quadro.length;

    if (tamanho % 2 != 0) {
      adcionar++;
    } // Fim do if

    int novoTamanho = (tamanho) / 2 + adcionar;

    int[] vetorDecodificado = new int[novoTamanho]; // Vetor decodificado

    int displayMask = 1 << 31;
    int posicaoQuadro = 0;
    int posicaoDecodificado = 0;

    boolean sinal = true; // Começa com sinal alto

    int novoInteiro = 0;
    int bitsAdcionados = 0;

    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero);
      System.out.println("Numero de bits " + numeroDeBits);

      if (numeroDeBits <= 8) {
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 31) {
        numeroDeBits = 32;
      } // Fim do if

      System.out.println("Numero de bits " + numeroDeBits);
      System.out.println("Deslocamento " + (32 - numeroDeBits) + " a esquerda\n");

      numero <<= (32 - numeroDeBits); // Deslocamento a esquerda de um valor de bits
      System.out.println("Bits correspondentes ao numero");
      System.out.println("");
      imprimirBits(numero);

      for (int i = 1; i <= numeroDeBits; i += 2) {
        boolean bit_1 = (numero & displayMask) == 0 ? false : true;
        numero <<= 1;
        boolean bit_2 = (numero & displayMask) == 0 ? false : true; // Verifica o bit
        numero <<= 1;

        if (bit_1 == sinal && bit_2 != sinal) {
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 0;
          bitsAdcionados++;
        } else if (bit_1 != sinal && bit_2 == sinal) {
          novoInteiro <<= 1;
          novoInteiro = novoInteiro | 1;
          bitsAdcionados++;
          sinal = !sinal; // Inverte o sinal
        } // Fim do if

        if (bitsAdcionados == 32) {
          vetorDecodificado[posicaoDecodificado] = novoInteiro;
          System.out.println("\nBits decodificados: ");
          System.out.println("\n");
          imprimirBits(novoInteiro);
          posicaoDecodificado++;
          bitsAdcionados = 0;
          novoInteiro = 0;
        } // Fim do if
      } // Fim do for

      System.out.println("\n\n");
      posicaoQuadro++;
    } // Fim do while

    if (novoInteiro != 0) {
      vetorDecodificado[posicaoDecodificado] = novoInteiro;
      System.out.println("\nBits decodificados: ");
      System.out.println("\n");
      imprimirBits(novoInteiro);
    } // Fim do if
    return vetorDecodificado;
  } // Fim do método decodificacaoManchesterDiferencial

  /*
   * Método: imprimirBits
   * Função: Imprime os bits
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
    return bits;
  } // Fim do método imprimirBits

  /*
   * Método: getNumberOfBits
   * Função: Retorna o número de bits
   * Parametros: int number
   * Retorno: int;
   */

  public static int getNumberOfBits(int number) {
    if (number == 0) {
      return 1;
    } // Fim do if

    int numberOfBits = 0;
    while (number > 0) {
      numberOfBits++;
      number >>= 1;
    } // Fim do while

    return numberOfBits;
  } // Fim do método getNumberOfBits
} // Fim da classe CamadaFisicaReceptora
