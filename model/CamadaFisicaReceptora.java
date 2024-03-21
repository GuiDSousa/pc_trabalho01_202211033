package model;

import java.util.concurrent.Semaphore;

public class CamadaFisicaReceptora {
	public static Semaphore semaforo;

	public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB){
		semaforo = new Semaphore(0);
		int velocidade = 200;
		System.out.println("Camada Física Receptora");

		try {
			int [] fluxoBrutoDeBits = fluxoBrutoDeBitsPontoB;
			System.out.println("\tFluxo Bruto de Bits: ");
			Thread.sleep(velocidade);
		}
	}
}
