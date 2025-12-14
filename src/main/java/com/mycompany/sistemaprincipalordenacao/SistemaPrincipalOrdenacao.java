/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemaprincipalordenacao;

import java.util.Arrays;

/**
 *
 * @author Guyvânia Rosa
 */
public class SistemaPrincipalOrdenacao {

    public static void main(String[] args) {
        int[] teste = {5, 2, 8, 1, 9};
        Ordenador.insertionSort(teste);
        System.out.println(Arrays.toString(teste));    }
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
}