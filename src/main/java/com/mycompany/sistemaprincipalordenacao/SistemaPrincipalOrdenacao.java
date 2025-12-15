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
        
        SistemaConcorrente sistema = new SistemaConcorrente();
        
        System.out.println("=== SISTEMA DE ORDENAÇÃO CONCORRENTE ===\n");

        Thread gerador = new Thread(new Runnable() {
            public void run() {
                int[] tarefas = {45, 12, 89, 34, 67, 23, 78, 56, 91, 14, 
                                72, 39, 81, 25, 63, 47, 29, 84, 51, 36};
                
                for (int i = 0; i < tarefas.length; i++) {
                    sistema.adicionarTarefa(tarefas[i]);
                    sistema.incrementarContador();
                    
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        System.out.println("Gerador interrompido");
                    }
                }
                System.out.println("\n[GERADOR] Todas as tarefas foram criadas");
            }
        }, "Gerador");
        
        Thread processador = new Thread(new Runnable() {
            public void run() {
                sistema.processarTarefas();
                System.out.println("[PROCESSADOR] Finalizou processamento");
            }
        }, "Processador");
        
        Thread ordenador = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(800);
                    sistema.ordenarTarefasConcluidas();
                    
                    Thread.sleep(900);
                    sistema.ordenarTarefasConcluidas();
                    
                    Thread.sleep(700);
                    sistema.ordenarTarefasConcluidas();
                } catch (InterruptedException e) {
                    System.out.println("Ordenador interrompido");
                }
                System.out.println("[ORDENADOR] Finalizou ordenações");
            }
        }, "Ordenador");
        
        gerador.start();
        processador.start();
        ordenador.start();
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
    
    public void adicionarTarefa(int id) {
        synchronized(lock) {
            tarefas.add(id);
        }   
    }
    
    public synchronized void incrementarContador() {
       contador++;
    }
    
    public void processarTarefas() {
        synchronized(lock) {
            while (executando || !tarefas.isEmpty()) {
                if (tarefas.isEmpty()) {
                    try {
                        lock.wait();  
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrompida");
                    }
                    continue;
                }
            
                int tarefa = tarefas.remove(0);
                tarefasConcluidas.add(tarefa);
                System.out.println("[" + Thread.currentThread().getName() + "] Processou tarefa " + tarefa);
            }
        }
    }
    
    public void finalizarExecucao() {
        executando = false;
        synchronized(lock) {
            lock.notifyAll();
        }
    }
    
    public void ordenarTarefasConcluidas() {
        synchronized(lock) {
            if (tarefasConcluidas.isEmpty()) {
                System.out.println("Nenhuma tarefa para ordenar");
                return;
            }
            
            int[] array = new int[tarefasConcluidas.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = tarefasConcluidas.get(i);
            }
            
            System.out.println("\n=== ORDENANDO " + array.length + " TAREFAS ===");
            System.out.print("Antes: ");
            for (int n : array) System.out.print(n + " ");
            
            if (array.length <= 10) {
                System.out.println("\n[Insertion Sort]");
                Ordenador.insertionSort(array);
            } else {
                System.out.println("\n[Merge Sort]");
                Ordenador.mergeSort(array, 0, array.length - 1);
            }
      
            
            System.out.print("Depois: ");
            for (int n : array) System.out.print(n + " ");
            System.out.println("\n");
            
            tarefasConcluidas.clear();
            for (int n : array) {
                tarefasConcluidas.add(n);
            }
        }
    }

}