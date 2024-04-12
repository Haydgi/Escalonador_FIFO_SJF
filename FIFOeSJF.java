import java.util.Scanner;

public class FIFOeSJF {
	
	public static void main (String[] args) {
		int numProcessos = 0;
		int i;
		Scanner ler = new Scanner(System.in);
		System.out.println("== ESCALONADOR FIFO SJF ==");
		System.out.println("NUMERO DE PROCESSOS:");
		
		numProcessos = ler.nextInt();
		
		int criacaoProcesso[] = new int[numProcessos];
		int ucpProcesso[] = new int[numProcessos];
		
		for(i=0;i<numProcessos;i++){
			System.out.printf("\n== Processo %d == \n", i+1);
			System.out.printf("Tempo de UCP: ");
			ucpProcesso[i] = ler.nextInt();
			System.out.printf("Criacao: ");
			criacaoProcesso[i] = ler.nextInt();
		}

		//~ INICIO FIFO
		System.out.println("\n ====\t  FIFO - Grafico \t==== ");
		int graficoFIFO = 0;
		float tmeFIFO = 0;
		float tmpFIFO = 0;
		float calculoTmeFIFO[] = new float[numProcessos];
		float calculoTmpFIFO[] = new float[numProcessos];
		
		for(i=0;i<numProcessos;i++){
			System.out.printf("\nP%d ", i+1);
			System.out.printf("%d -> ", graficoFIFO);
			if(i == 0){
				calculoTmeFIFO[i]=0;
			}else{
				calculoTmeFIFO[i] = graficoFIFO - criacaoProcesso[i];
			}
			tmeFIFO += calculoTmeFIFO[i];
			graficoFIFO += ucpProcesso[i];
			if(i == 0){
				calculoTmpFIFO[i]=graficoFIFO;
			}else{
				calculoTmpFIFO[i] = graficoFIFO - criacaoProcesso[i];
			}
			tmpFIFO += calculoTmpFIFO[i];
			System.out.printf("%d \n", graficoFIFO);
		}
		tmeFIFO = tmeFIFO / numProcessos;
		tmpFIFO = tmpFIFO / numProcessos;
		System.out.printf("\n\n tempo medio de espera = %.2f \t tempo medio de processamento = %.2f", tmeFIFO, tmpFIFO);

		//~ FIM FIFO E INICIO SJF
		System.out.println("\n\n\n ====\t  SJF - Grafico \t==== ");
		
		int idProcesso[] = new int[numProcessos];
		int tempoConclusao[] = new int[numProcessos];
		int tempoInicio[] = new int[numProcessos]; 
		int tempoRetorno[] = new int[numProcessos];
		int tempoEspera[] = new int[numProcessos];
		int finalizado[] = new int[numProcessos];
		int tempoSistema=0, total=0;
		float mediaEspera=0, mediaRetorno=0;

		while(true){
			int c=numProcessos, min = 999999;
			if (total == numProcessos)
				break;
			for (int j=0; j<numProcessos; j++){
				if ((criacaoProcesso[j] <= tempoSistema) && (finalizado[j] == 0) && (ucpProcesso[j]<min)){
					min=ucpProcesso[j];
					c=j;
				}
			}
			if (c==numProcessos)
				tempoSistema++;
			else{
				tempoInicio[c] = tempoSistema; // Armazena o tempo de início do processo
				tempoConclusao[c]=tempoSistema+ucpProcesso[c];
				tempoSistema+=ucpProcesso[c];
				tempoRetorno[c]=tempoConclusao[c]-criacaoProcesso[c];
				tempoEspera[c]=tempoRetorno[c]-ucpProcesso[c];
				finalizado[c]=1;
				idProcesso[total] = c + 1;
				total++;
			}
		}
		
		for(i=0;i<numProcessos;i++){
			mediaEspera+= tempoEspera[i];
			mediaRetorno+= tempoRetorno[i];
			System.out.printf("P%d %d -> %d \n\n", idProcesso[i], tempoInicio[idProcesso[i]-1], tempoConclusao[idProcesso[i]-1]);
		}
		
		float tmeSJF = mediaEspera/numProcessos, tmpSJF = mediaRetorno/numProcessos;
		
		System.out.printf("\n\n tempo medio de espera = %.2f \t tempo medio de processamento = %.2f", tmeSJF, tmpSJF);
		
		ler.close();
	}
}
