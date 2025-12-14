/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaprincipalordenacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Guyvânia Rosa
 */
public class SistemaPrincipalOrdenacao {

    public static void main(String[] args) {
        int[] teste = {5, 2, 8, 1, 9};
        Ordenador.insertionSort(teste);
        System.out.println(Arrays.toString(teste));    
    
        int[] teste2 = {45, 12, 89, 34, 67, 23, 78, 56, 91, 14};
        Ordenador.mergeSort(teste2, 0, teste2.length - 1);
    }
}

class Ordenador {
    public static void insertionSort(int[] arr) {
       for (int i = 1; i < arr.length; i++) {
            int valor = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > valor) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = valor;
        }
    }
    
    public static void mergeSort(int[] arr, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSort(arr, inicio, meio);
            mergeSort(arr, meio + 1, fim);
            merge(arr, inicio, meio, fim);
        }
    }
    
    private static void merge(int[] arr, int inicio, int meio, int fim) {
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;
        
        int[] esquerda = new int[n1];
        int[] direita = new int[n2];
        
        for (int i = 0; i < n1; i++) esquerda[i] = arr[inicio + i];
        for (int j = 0; j < n2; j++) direita[j] = arr[meio + 1 + j];
        
        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            if (esquerda[i] <= direita[j]) {
                arr[k] = esquerda[i];
                i++;
            } else {
                arr[k] = direita[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = esquerda[i];
            i++;
            k++;
        }
        
        while (j < n2) {
            arr[k] = direita[j];
            j++;
            k++;
        }
    }
}


class SistemaConcorrente {
    private int contador = 0;
    private List<Integer> tarefas = new ArrayList<>();
    private List<Integer> tarefasConcluidas = new ArrayList<>();
    
    private volatile boolean executando = true;
    private final Object lock = new Object();
    
    public synchronized void incrementarContador() {
       
    }
}