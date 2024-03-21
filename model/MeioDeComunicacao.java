package model;

public class MeioDeComunicacao {
	public static void meioDeComunicacao (int[] fluxoBrutoDeBits) {
        System.out.println("Meio de Comunicação");
        try {
            
            int [] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
            int [] fluxoBrutoDeBitsPontoB = new int [fluxoBrutoDeBits.length];


            for (int indicePosicao = 0; indicePosicao < fluxoBrutoDeBits.length; indicePosicao++) {
                int numero = fluxoBrutoDeBitsPontoA[indicePosicao];
                int numeroDeBits = Integer.toBinaryString(numero).length();

                if (numeroDeBits <= 8) {
                    numeroDeBits = 8;
                } else if (numeroDeBits <= 16) {
                    numeroDeBits = 16;
                } else if (numeroDeBits <=24) {
                    numeroDeBits = 24;
                } else if (numeroDeBits <= 32) {
                    numeroDeBits = 32;
                }

                numero <<= (32 - numeroDeBits);

                int novoInteiro = 0;
                int displayMask = 1 << 31;

                for (int i = 1; i<= numeroDeBits; i++) {
                  int bit = (numero & displayMask) == 0 ? 0 : 1;

                  novoInteiro <<= 1;
                  novoInteiro = novoInteiro | bit;
                  numero <<=1;

                  if (i == numeroDeBits) {
                    fluxoBrutoDeBitsPontoB[indicePosicao] = novoInteiro;
                  }
                }
            }
        }   catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro no meio de comunicação");
        }
    }

}
