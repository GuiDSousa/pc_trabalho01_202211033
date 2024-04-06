package model;

public class CamadaFisicaTransmissora {
	public static void camadaFisicaTransmissora(int[] quadro) {
		int velocidade = 200;
		System.out.println("|Camada Física Transmissora|\n");

		try {
			Thread.sleep(velocidade);

			System.out.println("Bits por inteiro: \n");

			for (int c : quadro) {
				System.out.println("Inteiro [" + c + "]" + " = " + imprimirBits(c) + "\n");
				Thread.sleep(velocidade);
			}

			int[] fluxoBrutoDeBits = inteirosParaBits(quadro); // Converte os inteiros para bits

			System.out.println("Bits Brutos Manipulados: ");
			Thread.sleep(velocidade);

			for (int b : fluxoBrutoDeBits) {
				System.out.println(imprimirBits(b) + "\n");
				Thread.sleep(velocidade);
			}
			System.out.println();

			Thread.sleep(velocidade);
			Thread.sleep(velocidade);
			System.out.println("Bits Brutos Codificados: \n");

			for (int b: fluxoBrutoDeBits) {
				System.out.println(imprimirBits(b) + "\n");
				Thread.sleep(velocidade);
			}
			System.out.println();

			switch (AplicacaoTransmissora.tipoDeCodificacao) {
				case AplicacaoTransmissora.BINARIA:
					System.out.println("|Codificacao Binaria|\n");
					fluxoBrutoDeBits = codificacaoBinaria(fluxoBrutoDeBits);//DECOFICACAO BINARIA
					break;
				case AplicacaoTransmissora.MANCHESTER:
					System.out.println("|Codificacao Manchester|\n");
					fluxoBrutoDeBits = codificacaoManchester(fluxoBrutoDeBits);//DECOFICACAO MANCHESTER
					break;
				case AplicacaoTransmissora.MANCHESTER_DIFERENCIAL:
					System.out.println("|Codificacao Manchester Diferencial|\n");
					fluxoBrutoDeBits = codificacaoManchesterDiferencial(fluxoBrutoDeBits);//DECOFICACAO MANCHESTER DIFERENCIAL
					break;
			}

			System.out.println("Bits Brutos Codificados: \n");
			Thread.sleep(velocidade);

			for (int b: fluxoBrutoDeBits) {
				System.out.println(imprimirBits(b) + "\n");
				Thread.sleep(velocidade);
			}
			System.out.println();
		
			MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Erro na Camada Física Transmissora");
		}
	}

	public static int[] inteirosParaBits(int[] vetorDeInteiros) {
		int novoTamanho = vetorDeInteiros.length / 4;
	
		if (vetorDeInteiros.length % 4 != 0) {
			novoTamanho++;
		}
	
		int[] vetorDeBits = new int[novoTamanho];
	
		int novoBit = 0;
		int posicao_v = 0;
		int posicao_r = 0;
	
		while (posicao_v < vetorDeInteiros.length) {
			novoBit <<= 8;
			novoBit = novoBit | vetorDeInteiros[posicao_v];
	
			if ((posicao_v + 1) % 4 == 0) {
				vetorDeBits[posicao_r] = novoBit;
				novoBit = 0;
				posicao_r++;
			}
			posicao_v++;
		}
	
		if (novoBit != 0) {
			vetorDeBits[posicao_r] = novoBit;
		}
		return vetorDeBits;
	}

	public static int[] codificacaoBinaria(int[] quadro) {
		System.out.println("|Codificação Binária|\n");
		
		int [] vetorCodificado = new int [quadro.length];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoCodificado = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];
			int numeroDeBits = getNumberOfBits(numero);
			System.out.println("Número de bits: " + numeroDeBits);

			if (numeroDeBits<=8) {
				numero =8;
			} else if (numeroDeBits<=16) {
				numeroDeBits = 16;
			} else if (numeroDeBits<=24) {
				numeroDeBits = 24;
			} else if (numeroDeBits<=32) {
				numeroDeBits = 32;
			}

			System.out.println("Número de bits: " + numeroDeBits);
			System.out.println("Deslocamento: " + (32-numeroDeBits) + " a esquerda");
		
			numero <<= (32 - numeroDeBits);	
			System.out.println("Bits do numero");
			imprimirBits(numero);

			System.out.println("Bit a Bit do numero");

			int novoInteiro = 0;

			for (int i=1; i <=numeroDeBits; i++) {
				int bit = (numero & displayMask) == 0 ? 0 : 1;
				System.out.print(bit);

				novoInteiro <<= 1;
				novoInteiro = novoInteiro | bit;
				numero <<=1;

				if ( i % 8 == 0 ) {
					System.out.print(" ");
				}

				if ( i == numeroDeBits) {
					System.out.println("\nNovo inteiro: ");
					imprimirBits(novoInteiro);
					vetorCodificado[posicaoCodificado] = novoInteiro;
					System.out.println("\n");
				}
			}

			System.out.println();
			posicaoQuadro++;
		}
		return vetorCodificado;
	}

	private static int[] codificacaoManchester(int[] quadro) {
		System.out.println("\n|Codificacao Manchester|");

		int reduzir = 0;
		int numeroDeBitsUltimoInteiro = getNumberOfBits(quadro[quadro.length - 1]);

		if (numeroDeBitsUltimoInteiro <= 6) {
			reduzir = 1;
		}

		int novoTamanho = (quadro.length * 2) - reduzir;

		int [] vetorCodificado = new int [novoTamanho];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoCodificado = 0;

		int novoInteiro = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];

			int numeroDeBits = getNumberOfBits(numero);
			System.out.println("\t\tNumero de bits: " + numeroDeBits);

			if (numeroDeBits <=8) {
				numeroDeBits = 8;
			} else if (numeroDeBits <= 16) {
				numeroDeBits = 16;
			} else if (numeroDeBits <= 24) {
				numeroDeBits = 24;
			} else if (numeroDeBits <= 32) {
				numeroDeBits = 32;
			}

			System.out.println("Numero de bits: " + numeroDeBits);	
			System.out.println("Deslocamento: " + (32 - numeroDeBits) + " a esquerda");

			numero <<= (32 - numeroDeBits);
			System.out.println("Bits do numero");
			imprimirBits(numero);

			System.out.println("Bit a Bit do numero");


			for ( int i = 1; i <= numeroDeBits; i++) {
				int bit = (numero & displayMask) ==0 ? 0 : 1;

				if ( bit ==1) {
					System.out.print("10");
					novoInteiro <<= 1;
					novoInteiro = novoInteiro | 1;
					novoInteiro <<= 1;
					novoInteiro = novoInteiro | 0;
				} else if (bit == 0) {
					System.out.println("01");
					novoInteiro <<= 1;
					novoInteiro = novoInteiro | 0;
					novoInteiro <<= 1;
					novoInteiro = novoInteiro | 1;
				}

				numero <<= 1;

				if ( i % 4 == 0) {
					System.out.print(" ");
				}

				if ( i == 16) {
					System.out.println ("\nNovo inteiro: ");
					imprimirBits(novoInteiro);
					vetorCodificado[posicaoCodificado] = novoInteiro;
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
				}
			}
			System.out.println();
			posicaoQuadro++;
		}
		return vetorCodificado;
	}

  private static int[] codificacaoManchesterDiferencial(int [] quadro) {
    System.out.println("\t|Codificação Manchester Diferencial|");

    int reduzir = 0;
    int numeroDeBitsUltimoInteiro = getNumberOfBits(quadro[quadro.length - 1]);

    if (numeroDeBitsUltimoInteiro <= 16) {
      reduzir = 1;
    }

    int novoTamanho =  (quadro.length * 2) - reduzir;
    int [] vetorCodificado = new int [novoTamanho];

    int displayMask = 1 << 31;
    int posicaoQuadro = 0;
    int posicaoCodificado = 0;

    boolean sinal_1 = true;
    boolean sinal_2 = false;

    int novoInteiro = 0;

    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];

      int numeroDeBits = getNumberOfBits(numero);
      System.out.println("Numero de bits: " + numeroDeBits);

      if (numeroDeBits <= 8) {
        numeroDeBits = 8;
      } else if (numeroDeBits <=16) {
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {
        numeroDeBits = 32;
      }

      System.out.println("Numero de bits: " + numeroDeBits);
      System.out.println("Deslocamento: " + (32 - numeroDeBits) + " a esquerda");

      numero <<= (32 - numeroDeBits);
      System.out.println("Bits do numero");
      imprimirBits(numero);

      System.out.println("\nBit a Bit do numero");


      for (int i = 1; i <= numeroDeBits; i++) {
        int bit = (numero& displayMask) == 0 ? 0 : 1;

        if (bit == 0) {
          sinal_1 = sinal_1;
          sinal_2 = sinal_2;
        } else if (bit ==1) {
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
        }

        numero <<= 1;

        if (i == 16) {
          System.out.println("\nNovo inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;
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
        }
      }
      System.out.println();
      posicaoQuadro++;
    }

    return vetorCodificado;
  }
                       
  public static String imprimirBits(int numero) {
    String bits = "";
    //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    //Para cada bit exibe 0 ou 1
    for (int bit=1; bit<=32; bit++) {
      //Utiliza displayMask para isolar o bit
      System.out.print((numero & displayMask) == 0 ? '0' : '1');
      bits += (numero & displayMask) == 0 ? '0' : '1';
      numero <<= 1;//Desloca o valor uma posicao para a esquerda
      if ( bit % 8 == 0 ) {
        System.out.print(" ");//Exibe espaco a cada 8 bits
        bits += " ";
      }
    }
    System.out.println();
    return bits;
  }

  public static int getNumberOfBits(int number) {
    if (number == 0) {
        return 1;
    }

    int numberOfBits = 0;
    while (number > 0) {
        numberOfBits++;
        number >>= 1;
    }

    return numberOfBits;
}
}
