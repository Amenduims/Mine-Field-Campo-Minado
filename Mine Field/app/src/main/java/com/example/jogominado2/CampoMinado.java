package com.example.jogominado2;


import java.util.Random;

public class CampoMinado {

    int matrizJogo[][];

    public CampoMinado(int altura, int largura){

        matrizJogo = new int[altura][largura];
        preenche(altura, largura);

    }

    public int getIndex(int i, int j){
        return matrizJogo[i][j];
    }

    public void preenche(int largura, int altura){
        Random random = new Random();
        int cont = 0;
        int test;


        while(cont <= (altura*largura*0.15)){
            for(int i = 0; i < largura; i++){
                for(int j = 0; j < altura; j++){
                    test = random.nextInt(10);
                    if(test == 1){
                        matrizJogo[i][j] = -1;
                        cont++;
                    }
                }
            }
        }


        int qtd_bombas = 0;
        for(int i = 0; i < largura; i++){
            for(int j = 0; j < altura; j++){
                if(matrizJogo[i][j] != -1){
                    if((i+1 < largura) && matrizJogo[i+1][j] == -1){
                        qtd_bombas ++;
                    }
                    if((j+1 < altura) && matrizJogo[i][j+1] == -1){
                        qtd_bombas++;
                    }
                    if((i-1 >= 0) && matrizJogo[i-1][j]  == -1){
                        qtd_bombas++;
                    }
                    if((j-1 >= 0) && matrizJogo[i][j-1] == -1){
                        qtd_bombas++;
                    }
                    if((i+1 < largura) && (j+1 < altura) && matrizJogo[i+1][j+1] == -1){
                        qtd_bombas++;
                    }
                    if((i+1 < largura) && (j-1 >= 0) && matrizJogo[i+1][j-1] == -1){
                        qtd_bombas++;
                    }
                    if((i-1 >= 0) && (j+1 < altura) && matrizJogo[i-1][j+1] == -1){
                        qtd_bombas++;
                    }
                    if((i-1 >= 0)&& (j-1 >= 0) && matrizJogo[i-1][j-1] == -1){
                        qtd_bombas++;
                    }

                    if(matrizJogo[i][j] != -1){
                        matrizJogo[i][j] = qtd_bombas;
                        qtd_bombas = 0;
                    }

                }
            }
        }
    }


}
