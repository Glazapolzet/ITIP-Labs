package org.example;

/* Программа, которая находит и выводит все простые числа меньше 100 */
public class Primes {
    public static void main(String[] args) {
        for (int num = 2; num <= 100; num++) {
            if(IsPrime(num))
                System.out.print(num + " ");
        }
    }

    /* Метод определяет, является ли аргумент простым числом */
    public static boolean IsPrime(int n) {
        for (int i = 2; i < n; i++) {
            if(n % i == 0)
                return false;
        }
        return true;
    }
}
