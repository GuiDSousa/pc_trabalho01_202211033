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

	public static int[] bitsParaInteiros ( int [] vetorDeBits) {
		int adcionar = 0;
		int reduzir = 0;
		int tamanho = vetorDeBits.length;

		int numeroDeBitsUltimoInteiro = Integer.toBinaryString (vetorDeBits[vetorDeBits.length - 1]).length();	

		if(numeroDeBitsUltimoInteiro <= 24) {
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

		int [] vetorDeInteiros = new int [novoTamanho];

		int displayMask = 1 << 31;
		int posicaoI = 0;

		for (int intBits : vetorDeBits) {
			int novoInteiro = 0;

			for (int i = 1; i <= 32; i++) {
				int bit = (intBits & displayMask) == 0 ? 0 : 1;
				novoInteiro <<= 1;
				novoInteiro = novoInteiro | bit;
				intBits <<= 1;

				if (i%8 == 0 && novoInteiro != 0) {
					vetorDeInteiros[posicaoI] = novoInteiro;
					posicaoI++;
					novoInteiro = 0;
				}
			}
			return vetorDeInteiros;
		}
	}

	private static int[] decodificacaoBinaria(int [] quadro) {
		System.out.println("\n\tDecodificacao Binaria: ");

		int[] vetorCodificado = new int [quadro.length];

		int displayMask = 1 << 31;
		int posicaoQuadro = 0;
		int posicaoCodificado = 0;

		while (posicaoQuadro < quadro.length) {
			int numero = quadro[posicaoQuadro];

			int numeroDeBits = Integer.toBinaryString(numero).length();
		}
	}
}
