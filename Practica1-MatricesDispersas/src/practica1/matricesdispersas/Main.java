package practica1.matricesdispersas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adriana
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MatrizTripletas matrizA = new MatrizTripletas(4, 4, "A");
        matrizA.setCelda(1, 1, 1);
        matrizA.setCelda(1, 2, 2);
        matrizA.setCelda(1, 3, 3);
        matrizA.setCelda(1, 4, 0);
        matrizA.setCelda(2, 1, 5);
        matrizA.setCelda(2, 2, 6);
        matrizA.setCelda(2, 3, 0);
        matrizA.setCelda(2, 4, 8);
        matrizA.setCelda(3, 1, 9);
        matrizA.setCelda(3, 2, 0);
        matrizA.setCelda(3, 3, 11);
        matrizA.setCelda(3, 4, 12);
        matrizA.setCelda(4, 1, 0);
        matrizA.setCelda(4, 2, 14);
        matrizA.setCelda(4, 3, 15);
        matrizA.setCelda(4, 4, 16);
        
        System.out.println(matrizA);
                
        System.out.println("Determinante de A: " + matrizA.determinante());
        
        System.out.println("Cofactores de A: " + matrizA.cofactores());
        
        
        MatrizTripletas matrizB = new MatrizTripletas(3, 3, "B");
        matrizB.setCelda(1, 1, 1);
        matrizB.setCelda(1, 2, -2);
        matrizB.setCelda(1, 3, 5);
        matrizB.setCelda(2, 1, 3);
        matrizB.setCelda(2, 2, 3);
        matrizB.setCelda(2, 3, -1);
        matrizB.setCelda(3, 2, 4);
        matrizB.setCelda(3, 3, -2);
        
        System.out.println("Mostrando B");
        System.out.println(matrizB);
        
        System.out.println("Determinante de B: " + matrizB.determinante());
        
        System.out.println("Cofactores de B: " + matrizB.cofactores());
        
        System.out.println("Traspuesta de B: ");
        System.out.println(matrizB.traspuesta());
        
    }
    
}
