package model;

import java.util.concurrent.Semaphore;
import view.Painel;
import view.Grafico;

public class CamadaFisicaReceptora {
	public static Semaphore semaforo;

	public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB){
		semaforo = new Semaphore(0);
		int velocidade = 200;
		System.out.println("Camada Física Receptora");

		try {

			Painel.CamadasReceptoras.expandirCamadaFisica();
			Painel.CamadasReceptoras.limparTextoCamadas();
			Painel.CamadasReceptoras.camadaFisica("Recebendo o Fluxo Bruto de Bits: \n\n");


			int [] fluxoBrutoDeBits = fluxoBrutoDeBitsPontoB;
			System.out.println("\tFluxo Bruto de Bits: ");
			Thread.sleep(velocidade);

			Painel.GRAFICO.semaforoEnd.acquire();

			System.out.println ("\n\n\n|Bits Brutos Manipulados|");
			Painel.CamadasReceptoras.camadaFisica("\n\t|Bits Brutos Manipulados|\n");
			Thread.sleep(velocidade);
			for (int b : fluxoBrutoDeBits) {
				System.out.println("\t");
				Thread.sleep(velocidade);
			}
			System.out.println("\n\n\n");

			Grafico.Codificacao tipoDeCodificacao = Painel.GRAFICO.codificacaoSelecionada();

			switch (tipoDeCodificacao) {
				case BINARIA:
					Painel.CAMADAS_RECEPTORAS.camadaFisica("\n|Decodificacao Binaria|\n");
					fluxoBrutoDeBits = decodificacaoBinaria(fluxoBrutoDeBits);
					break;
				case MANCHESTER
					Painel.CAMADAS_RECEPTORAS.camadaFisica("\n|Decodificacao Manchester|\n");
					fluxoBrutoDeBits = decodificacaoManchester(fluxoBrutoDeBits);
					break;
				case MANCHESTER_DIFERENCIAL
					Painel.CAMADAS_RECEPTORAS.camadaFisica("\n|Decodificacao Manchester Diferencial|\n");
					fluxoBrutoDeBits = decodificacaoManchesterDiferencial(fluxoBrutoDeBits);
					break;
			}
			System.out.println("\n\t|Bits Brutos Decodificados|");
			Painel.CAMADAS_RECEPTORAS.camadaFisica("\n\t|Bits Brutos Decodificados|\n");
			Thread.sleep(velocidade);

			for (int b : fluxoBrutoDeBits) {
				System.out.println("\t" + b);
				Painel.CAMADAS_RECEPTORAS.camadaFisica(imprimirBits(b) + "\n");
				Thread.sleep(velocidade);
			}
			System.out.println("\n\n\n");

			int [] quadro = bitsParaInteiros(fluxoBrutoDeBits);

			System.out.println("\n\tBits por inteiro: ");
			Painel.CAMADAS_RECEPTORAS.camadaFisica("\n\tBits por inteiro: \n");
			Thread.sleep(velocidade);

			for (int c : quadro) {
				System.out.println("\tInteiro ["+c+"] - ");
				Painel.CAMADAS_RECEPTORAS.camadaDisica ( "["+c+"] - " + imprimirBits(c) + "\n");
				Thread.sleep(velocidade);
			}
			System.out.println("\n\n\n");

			CamadaAplicacaoReceptora.camadaAplicacaoReceptora(quadro);
		} catch (InterruptedException e) {
			System.out.println("[ERRO] - Camada Física Receptora");
		}
	}

	public static int[] bitsParaInteiros(int[] vetorDeBits) {
		int adcionar = 0;
		int reduzir = 0;
		int tamanho = vetorDeBits.length;

		int numeroDeBitsUltimoInteiro = Integer.toBinaryString(vetorDeBits[vetorDeBits.length - 1]).length();

		if (numeroDeBitsUltimoInteiro <= 24) {
			if (numeroDeBitsUltimoInteiro <= 8) {
				adicionar = 24 - numeroDeBitsUltimoInteiro;
			} else if (numeroDeBitsUltimoInteiro <= 16) {
				adicionar = 16 - numeroDeBitsUltimoInteiro;
			} else {
				adicionar = 8 - numeroDeBitsUltimoInteiro;
			}

			reduzir = 1;
		}

		int novoTamanho = ((tamanho - reduzir) * 4 + adicionar);

		int[] vetorDeInteiros = new int[novoTamanho];

		int displayMask = 1 << 31;
		int posicaoI = 0;

		for (int intBits : vetorDeBits) {
			int novoInteiro = 0;

			for (int i = 1; i <= 32; i++) {
				int bit = (intBits & displayMask) == 0 ? 0 : 1;
				novoInteiro <<= 1;
				novoInteiro = novoInteiro | bit;
				intBits <<= 1;

				if (i % 8 == 0 && novoInteiro != 0) {
					vetorDeInteiros[posicaoI] = novoInteiro;
					posicaoI++;
					novoInteiro = 0;
				}
			}
			return vetorDeInteiros;
		}
	}

	private static int[] decodificacaoBinaria(int[] quadro) {
		System.out.println("\n\tDecodificacao Binaria: ");

		int[] vetorCodificado = new int[quadro.length];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoCodificado = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];

			int numeroDeBits = Integer.toBinaryString(numero).length();
			System.out.println("\tNumero de bits " + numeroDeBits);

			if (numeroDeBits <= 8) {
				numeroDeBits = 8;
			} else if (numeroDeBits <= 16) {
				numeroDeBits = 16;
			} else if (numeroDeBits <= 24) {
				numeroDeBits = 24;
			} else if (numeroDeBits <= 32) {
				numeroDeBits = 32;
			}

			System.out.println("\tNumero de bits " + numeroDeBits);
			System.out.println("\tDeslocamento " + (32 - numeroDeBits) + "a esquerda\n");

			numero <<= (32 - numeroDeBits); // Deslocamento a esquerda de um valor de bits
			System.out.println("\tBits correspondentes ao numero");
			System.out.println("\t");
			imprimirBits(numero);

			System.out.println("\tBit a Bit:");
			System.out.println("\t");

			int novoInteiro = 0;

			for (int i = 1; i <= numeroDeBits; i++) {
				int bit = (numero & displayMask) == 0 ? 0 : 1;
				System.out.println(bit);

				novoInteiro <<= 1; // Deslocamento de 1 bit para a esquerda
				novoInteiro = novoInteiro | bit;
				numero <<= 1;

				if (i % 8 == 0) {
					System.out.println("\t");
				}

				if (i == numeroDeBits) {
					System.out.println("\t Novo Inteiro: ");
					imprimirBits(novoInteiro);
					vetorCodificado[posicaoCodificado] = novoInteiro;
					System.out.println("\t");
				}
			}
			System.out.println("\n\n");
			posicaoQuadro++;
		} // Vetor percorrido // Fim do while

		return vetorCodificado;
	}

	private static int[] decodificacaoManchester (int[] quadro) {
		System.out.println("\n\tDecodificacao Manchester: ");

		int adcionar = 0;
		int tamanho = quadro.length;

		if (tamanho % 2 != 0 ) {
			adcionar++;
		}

		int novoTamanho = (tamanho)/2 + adcionar;

		int [] vetorDecodificado = new int [novoTamanho];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoDecodificado = 0;

		int novoInteiro = 0;
		int bitsAdcionados = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];

			int numeroDeBits = Integer.toBinaryString(numero).length();
			System.out.println("\tNumero de bits " + numeroDeBits);

			if (numeroDeBits<=8) {
				numeroDeBits = 8;
			} else if (numeroDeBits<=16) {
				numeroDeBits = 16;
			} else if (numeroDeBits<=24) {
				numeroDeBits = 24;
			} else if (numeroDeBits<=31) {
				numeroDeBits = 32;
			}

			System.out.println("\tNumero de bits " + numeroDeBits);
			System.out.println("\tDeslocamento " + (32 - numeroDeBits) + " a esquerda\n");

			numero <<= (32-numeroDeBits); // Deslocamento a esquerda de um valor de bits
			System.out.println("\tBits correspondentes ao numero");
			System.out.println("\t");
			imprimirBits(numero);

			for (int i = 1; i<= numeroDeBits; i +=2) {
				int bit_1 = (numero & displayMask) == 0 ? 0 : 1;
				numero <<= 1;
				int bit_2 = (numero & displayMask) == 0 ? 0 : 1;
				numero <<= 1;

				if ( bit_1 == 1 & bit_2 == 0) {
					novoInteiro <<= 1;
					novoInteiro = novoInteiro | 1;
					bitsAdcionados++;
				} else if (bit_1 == 0 & bit_2 == 1) {
					novoInteiro <<=1;
					novoInteiro = novoInteiro | 0;
					bitsAdcionados++;
				}

				if (bitsAdcionados == 32) {
					vetorDecodificado[posicaoDecodificado] = novoInteiro;
					System.out.println("\tBits decodificados: ");
					System.out.println("\t");
					imprimirBits(novoInteiro);
					posicaoDecodificado++;
					bitsAdcionados = 0;
					novoInteiro = 0;
				}
			}

			System.out.println("\n\n");
			posicaoQuadro++;
		}

		if (novoInteiro !=0) {
			vetorDecodificado[posicaoDecodificado] = novoInteiro;
			System.out.println("\tBits decodificados: ");	
			System.out.println("\t");
			imprimirBits(novoInteiro);
		}

		return vetorDecodificado;
	}

	private static int[] decodificacaoManchesterDiferencial(int[] quadro) {
		System.out.println("\n\tDecodificacao Manchester Diferencial: ");

		int adcionar = 0;
		int tamanho = quadro.length;

		if (tamanho % 2 != 0) {
			adcionar++;
		}

		int novoTamanho = (tamanho) / 2 + adcionar;

		int[] vetorDecodificado = new int[novoTamanho];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoDecodificado = 0;

		boolean sinal_1 = true; // Sinal alto
		boolean sinal_2 = false; // Sinal baixo

		int novoInteiro = 0; 
		int bitsAdcionados = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];

			int numeroDeBits = Integer.toBinaryString(numero).length();
			System.out.println("\tNumero de bits " + numeroDeBits);

			if (numeroDeBits<=8) {
				numeroDeBits = 8;	
			} else if (numeroDeBits<=16) {
				numeroDeBits = 16;
			} else if (numeroDeBits<=24) {
				numeroDeBits = 24;
			} else if (numeroDeBits<=32) {
				numeroDeBits = 32;
			}

			System.out.println("\tNumero de bits " + numeroDeBits);
			System.out.println("\tDeslocamento " + (32 - numeroDeBits) + " a esquerda\n");

			numero <<= (32 - numeroDeBits); // Deslocamento a esquerda de um valor de bits
			System.out.println("\tBits correspondentes ao numero");
			System.out.println("\t");
			imprimirBits(numero);

			for (int i = 1; i <= numeroDeBits; i += 2) {
				boolean bit_1 = (numero & displayMask) == 0 ? false : true;
				numero <<=1;
				boolean bit_2 = (numero & displayMask) == 0 ? false : true;
				numero <<=1;

				if(bit_1 == sinal_1 & bit_2 == sinal_2) {
					novoInteiro <<=1;
					novoInteiro = novoInteiro | 0;
					bitsAdcionados++;
				} else if (bit_1 != sinal_1 & bit_2 != sinal_2) {
					novoInteiro <<=1;
					novoInteiro = novoInteiro | 1;
					bitsAdcionados++;
				}

				sinal_1 = bit_1;
				sinal_2 = bit_2;

				if (bitsAdcionados == 32) {
					vetorDecodificado[posicaoDecodificado] = novoInteiro;
					System.out.println("\tBits decodificados: ");
					System.out.println("\t");
					imprimirBits(novoInteiro);
					posicaoDecodificado++;
					bitsAdcionados = 0;
					novoInteiro = 0;
				}
			}

			System.out.println("\n\n");
			posicaoQuadro++;
		}

		if (novoInteiro != 0) {
			vetorDecodificado[posicaoDecodificado] = novoInteiro;
			System.out.println("\tBits decodificados: ");
			System.out.println("\t");
			imprimirBits(novoInteiro);
		}
      return vetorDecodificado;
	}

	public static String imprimirBits(int numero) {
		String bits = "";
		int displayMask = 1 << 31;

		for (int bit = 1; bit <= 32; bit++) {
			int bitAtual = (numero & displayMask) == 0 ? 0 : 1;
			bits += bitAtual;

			numero <<= 1;

			if (bit % 8 == 0) {
				bits += " ";
			}
		}
		System.out.println();
		return bits;

	}
}
